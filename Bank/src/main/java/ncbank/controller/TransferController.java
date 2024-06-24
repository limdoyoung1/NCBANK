package ncbank.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ncbank.beans.AccountBean;
import ncbank.beans.CodeOrganBean;
import ncbank.beans.TransferBean;
import ncbank.beans.UserBean;
import ncbank.service.AccountService;
import ncbank.service.CodeOrganService;
import ncbank.service.TransferService;
import ncbank.validator.ExceptionMessage;

@Controller
@RequestMapping("/trans")
public class TransferController {

	@Autowired
	private TransferService transferService;

	@Autowired
	private CodeOrganService codeOrganService;

	@Autowired
	private AccountService accountService;

	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

	@GetMapping("/transferCheck")
	public String TransferCheck(@RequestParam(name = "account", required = false) String account, Model model) {

		if (null == loginUserBean || !loginUserBean.isUserLogin()) {
			return "user/not_login";
		}

		int userNum = loginUserBean.getUser_num();
		List<TransferBean> transfers;
		if (account != null && !account.isEmpty()) {
			transfers = transferService.getTransfer(userNum, account);
		} else {
			transfers = transferService.getTransfer(userNum, null);
		}

		model.addAttribute("transfers", transfers);

		List<AccountBean> accounts = accountService.getAccount(userNum);
		model.addAttribute("accounts", accounts);

		List<CodeOrganBean> codeOrganNames = codeOrganService.getCode_organ_name();
		model.addAttribute("codeOrganNames", codeOrganNames);
		return "trans/transferCheck";
	}

	@GetMapping("/transfer")
	public String Transfer(Model model) {

		if (null == loginUserBean || !loginUserBean.isUserLogin()) {
			return "user/not_login";
		}

		int userNum = loginUserBean.getUser_num();
		List<AccountBean> accounts = accountService.getAccount(userNum);
		model.addAttribute("accounts", accounts);

		List<CodeOrganBean> codeOrganNames = codeOrganService.getCode_organ_name();
		model.addAttribute("codeOrganNames", codeOrganNames);

		model.addAttribute("accountBean", new AccountBean());
		model.addAttribute("transferBean", new TransferBean());
		return "trans/transfer";
	}

	@PostMapping("/transfer_confirm")
	public String transferConfirm(@Valid @ModelAttribute("transferBean") TransferBean transferBean,
			@RequestParam("ac_password") String acPassword, Model model, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			int userNum = loginUserBean.getUser_num();
			List<AccountBean> accounts = accountService.getAccount(userNum);
			model.addAttribute("accounts", accounts);

			List<CodeOrganBean> codeOrganNames = codeOrganService.getCode_organ_name();
			model.addAttribute("codeOrganNames", codeOrganNames);
			return "trans/transfer";
		}

		// 계좌 비밀번호 확인
		int userNum = loginUserBean.getUser_num();
		List<AccountBean> accounts = accountService.getAccount(userNum);

		AccountBean fromAccount = accounts.stream()
				.filter(account -> account.getAccount().equals(transferBean.getFrom_account())).findFirst()
				.orElse(null);

		if (fromAccount == null || !fromAccount.getAc_password().equals(acPassword)) {
			result.rejectValue("from_account", "error.from_account", "비밀번호가 일치하지 않습니다");
			model.addAttribute("accounts", accounts);

			List<CodeOrganBean> codeOrganNames = codeOrganService.getCode_organ_name();
			model.addAttribute("codeOrganNames", codeOrganNames);
			return "trans/transfer";
		}

		// code_organ이 "005"일 때만 계좌번호 유효성 확인
		if ("005".equals(transferBean.getCode_organ())
				&& !accountService.isValidAccountNumber(transferBean.getTo_account())) { // 유효성 검사 조건 추가
			result.rejectValue("to_account", "error.to_account", "유효한 계좌번호가 아닙니다");
			model.addAttribute("accounts", accounts);

			List<CodeOrganBean> codeOrganNames = codeOrganService.getCode_organ_name();
			model.addAttribute("codeOrganNames", codeOrganNames);
			return "trans/transfer";
		}

		try {
			transferBean.setUser_num(loginUserBean.getUser_num());
			transferService.addTransfer(transferBean);
		} catch (ExceptionMessage e) {
			System.out.println("ExceptionMessage : " + e);
			result.rejectValue("to_account", "error.to_account", e.getMessage());
			model.addAttribute("accounts", accounts);

			List<CodeOrganBean> codeOrganNames = codeOrganService.getCode_organ_name();
			model.addAttribute("codeOrganNames", codeOrganNames);
			model.addAttribute("transferError", true); // 이체 실패 여부를 모델에 추가
			return "trans/transfer";
		}

		return "redirect:/trans/transferCheck";
	}
}