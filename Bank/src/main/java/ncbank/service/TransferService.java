package ncbank.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncbank.beans.AccountBean;
import ncbank.beans.TransferBean;
import ncbank.beans.UserBean;
import ncbank.dao.TransferDAO;
import ncbank.validator.ExceptionMessage;

@Service
public class TransferService {
	// 입금 1, 출금 2
	@Autowired
	private TransferDAO transferDAO;

	@Autowired
	private AccountService accountService;

	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

	public int getUserNum() {
		return loginUserBean.getUser_num();
	}

	public List<TransferBean> getTransfer(int userNum, String account) {
		List<TransferBean> transfers = transferDAO.getTransfer(userNum, account);
		return transfers;
	}

	public void addTransfer(TransferBean transferBean) throws Exception {
		// 출금 계좌와 입금 계좌가 동일할 경우 예외 발생
		if (transferBean.getFrom_account().equals(transferBean.getTo_account())) {
			throw new ExceptionMessage("출금 계좌와 입금 계좌는 동일할 수 없습니다");
		}

		AccountBean fromAccount = accountService.getAccountByNumber(transferBean.getFrom_account());

		// 계좌 잔액 확인 및 업데이트
		double fromBalance = Double.parseDouble(fromAccount.getAc_balance());
		double transferAmount = Double.parseDouble(transferBean.getTrans_money());

		if (fromBalance < transferAmount) {
			throw new ExceptionMessage("잔액이 부족합니다");
		}
		// 내부 계좌와 외부 계좌 구분
		if ("005".equals(transferBean.getCode_organ())) {

			AccountBean toAccount = accountService.getAccountByNumber(transferBean.getTo_account());

			// Luhn 알고리즘 검사
			if (!accountService.isValidAccountNumber(transferBean.getTo_account())) {
				throw new ExceptionMessage("입금 계좌번호가 유효하지 않습니다");
			}

			// 내부 이체 입금 내역 추가
			if (toAccount != null) {
				double toBalance = Double.parseDouble(toAccount.getAc_balance());
				toBalance += transferAmount;
				toAccount.setAc_balance(String.valueOf(Math.round(toBalance))); // 정수로 저장
				accountService.updateAccountBalance(toAccount);

				TransferBean depositBean = new TransferBean();
				depositBean.setTrans_type(1); // 입금
				depositBean.setTrans_money(String.valueOf(Math.round(transferAmount))); // 이체 금액 설정
				depositBean.setTrans_text(transferBean.getTrans_text());
				depositBean.setFrom_account(transferBean.getFrom_account());
				depositBean.setTo_account(transferBean.getTo_account());
				depositBean.setCode_organ("005"); // 입금은행 설정
				depositBean.setTrans_balance(String.valueOf(Math.round(toBalance))); // 입금 후 잔액 설정
				depositBean.setUser_num(transferBean.getUser_num()); // 회원 번호 설정
				transferDAO.addTransfer(depositBean);
			}
		}

		// 출금 내역 추가
		fromBalance -= transferAmount;
		fromAccount.setAc_balance(String.valueOf(Math.round(fromBalance))); // 정수로 저장
		accountService.updateAccountBalance(fromAccount);

		TransferBean withdrawBean = new TransferBean();
		withdrawBean.setTrans_type(2); // 출금
		withdrawBean.setTrans_money(String.valueOf(Math.round(transferAmount))); // 이체 금액 설정
		withdrawBean.setTrans_text(transferBean.getTrans_text());
		withdrawBean.setFrom_account(transferBean.getFrom_account());
		withdrawBean.setTo_account(transferBean.getTo_account());
		withdrawBean.setCode_organ(transferBean.getCode_organ()); // 출금은행 설정
		withdrawBean.setTrans_balance(String.valueOf(Math.round(fromBalance))); // 출금 후 잔액 설정
		withdrawBean.setUser_num(transferBean.getUser_num()); // 회원 정보 설정
		transferDAO.addTransfer(withdrawBean);

		// 외부 이체 처리 추가
		if (!"005".equals(transferBean.getCode_organ())) {
			TransferBean externalDepositBean = new TransferBean();
			externalDepositBean.setTrans_type(1); // 입금
			externalDepositBean.setTrans_money(String.valueOf(Math.round(transferAmount))); // 이체 금액 설정
			externalDepositBean.setTrans_text(transferBean.getTrans_text());
			externalDepositBean.setFrom_account(transferBean.getFrom_account());
			externalDepositBean.setTo_account(transferBean.getTo_account());
			externalDepositBean.setCode_organ(transferBean.getCode_organ()); // 입금은행 설정
			externalDepositBean.setTrans_balance("0"); // 외부 이체는 잔액 설정이 필요 없음
			externalDepositBean.setUser_num(transferBean.getUser_num()); // 회원 번호 설정
			transferDAO.addTransfer(externalDepositBean);
		}
	}
}
