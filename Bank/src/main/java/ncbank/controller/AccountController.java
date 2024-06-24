package ncbank.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ncbank.beans.AccountBean;
import ncbank.beans.UserBean;
import ncbank.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private AccountService accountService;

	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

	@GetMapping("/accountCheck")
	public String AccountCheck(Model model) {
		// 로그인 확인
		if (null == loginUserBean || !loginUserBean.isUserLogin()) {
			return "user/not_login";
		}
		// 로그인한 회원의 회원번호 저장
		int userNum = loginUserBean.getUser_num();
		List<AccountBean> accounts = accountService.getAccount(userNum);
		model.addAttribute("accounts", accounts);

		return "account/accountCheck";
	}

	@GetMapping("/accountCreate")
	public String AccountCreate(Model model) {
		// 로그인 확인
		if (null == loginUserBean || !loginUserBean.isUserLogin()) {
			return "user/not_login";
		}
		// 로그인한 회원의 회원번호 저장
		int userNum = loginUserBean.getUser_num();

		UserBean users = null;
		try {
			users = accountService.getUserInfo(userNum);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			return "account/accountCreate";
		}

		model.addAttribute("users", users);
		model.addAttribute("accountBean", new AccountBean());

		return "account/accountCreate";
	}

	@PostMapping("/accountCreate")
	public String createAccount(@ModelAttribute AccountBean accountBean, @RequestParam("acPassword") String acPassword,
			@RequestParam("acPasswordConfirm") String acPasswordConfirm, Model model) {
		// 로그인 확인
		if (null == loginUserBean || !loginUserBean.isUserLogin()) {
			return "user/not_login";
		}
		// 로그인한 회원의 회원번호를 계좌 정보에 저장
		accountBean.setUser_num(loginUserBean.getUser_num());

		// 비밀번호와 비밀번호 확인 비교
		if (!acPassword.equals(acPasswordConfirm)) {
			model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다");

			// 유저 정보를 다시 모델에 추가
			UserBean users = accountService.getUserInfo(loginUserBean.getUser_num());
			model.addAttribute("users", users);
			model.addAttribute("accountBean", accountBean);
			return "account/accountCreate";
		}

		// 비밀번호 설정
		accountBean.setAc_password(acPassword);

		try {
			accountService.createAccount(accountBean);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			// 유저 정보를 다시 모델에 추가
			UserBean users = accountService.getUserInfo(loginUserBean.getUser_num());
			model.addAttribute("users", users);
			model.addAttribute("accountBean", accountBean);
			return "account/accountCreate";
		}

		return "redirect:/account/accountCheck";
	}
}