package ncbank.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncbank.beans.AccountBean;
import ncbank.beans.UserBean;
import ncbank.dao.AccountDAO;

@Service
public class AccountService {
	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

	@Autowired
	private AccountDAO accountDAO;

	private static final String codeOrgan = "005";

	private static final int userAccountLength = 8;

	public int getUserNum() {
		return loginUserBean.getUser_num();
	}

	public UserBean getUserInfo(int userNum) {
		return accountDAO.getUserInfo(userNum);
	}

	public List<AccountBean> getAccount(int userNum) {
		return accountDAO.getAccount(userNum);
	}

	public void updateAccountBalance(AccountBean accountBean) {
		accountDAO.updateAccountBalance(accountBean);
	}

	public AccountBean getAccountByNumber(String accountNumber) {
		return accountDAO.getAccountByNumber(accountNumber);
	}

	// 실제 회원가입 시 사용되는 함수
	public String generateAccountNumberForNewMember(int userNum, int sequenceNumber) {
		return generateAccountNumber(userNum, sequenceNumber);
	}

	// 계좌번호 생성
	public void createAccount(AccountBean accountBean) throws Exception {
		int userNum = accountBean.getUser_num();
		// 동일한 회원의 모든 계좌 조회
		List<AccountBean> accounts = accountDAO.getAccount(userNum);

		// 최근 계좌 개설일 찾기
		Date lastCreateDate = getLastAccountCreateDate(accounts);

		// 30일이 지났는지 확인
		if (lastCreateDate != null) {
			long diffInMillis = new Date().getTime() - lastCreateDate.getTime();
			long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

			if (diffInDays < 30) {
				throw new Exception("계좌 개설 후 30일이 지나지 않았습니다.");
			}
		}

		// 가장 높은 일련번호 찾기
		int maxSequenceNumber = findMaxSequenceNumber(accounts);
		// 새로운 계좌번호 생성
		String accountNumber = generateAccountNumber(accountBean.getUser_num(), maxSequenceNumber + 1);
		accountBean.setAccount(accountNumber);
		// 계좌 생성
		accountDAO.createAccount(accountBean);
	}

	private Date getLastAccountCreateDate(List<AccountBean> accounts) {
		Date lastCreateDate = null;
		for (AccountBean account : accounts) {
			Date createDate = account.getAc_date();
			if (lastCreateDate == null || createDate.after(lastCreateDate)) {
				lastCreateDate = createDate;
			}
		}
		return lastCreateDate;
	}

	private int findMaxSequenceNumber(List<AccountBean> accounts) {
		int maxSequenceNumber = 0;
		for (AccountBean account : accounts) {
			String accountNumber = account.getAccount();
			int sequenceNumber = extractSequenceNumber(accountNumber);
			if (sequenceNumber > maxSequenceNumber) {
				maxSequenceNumber = sequenceNumber;
			}
		}
		return maxSequenceNumber;
	}

	private int extractSequenceNumber(String accountNumber) {
		// 계좌번호에서 일련번호 추출하는 로직
		String sequenceStr = accountNumber.substring(11, 13); // 계좌 번호에서 일련번호를 나타내는 12, 13번째 숫자만 추출
		return Integer.parseInt(sequenceStr);
	}

	// Luhn 알고리즘을 사용하여 계좌번호에 검증 숫자를 추가하는 함수
	private String generateAccountNumber(int userNum, int sequenceNumber) {
		// 은행코드(3자리) + 회원번호(8자리) + 일련번호(2자리)
		String baseAccountNumber = codeOrgan + formatMemberNumber(userNum) + String.format("%02d", sequenceNumber);
		// Luhn 알고리즘으로 검증 숫자 계산
		int checkDigit = calculateLuhnCheckDigit(baseAccountNumber);
		// 계좌번호에 검증 숫자 추가하여 반환
		return baseAccountNumber + checkDigit;
	}

	// 회원번호를 8자리로 포맷팅하는 함수 (앞을 0으로 채움)
	private String formatMemberNumber(int userNum) {
		return String.format("%0" + userAccountLength + "d", userNum);
	}

	public boolean isValidAccountNumber(String accountNumber) {
		int checkDigit = Character.getNumericValue(accountNumber.charAt(accountNumber.length() - 1));
		String baseNumber = accountNumber.substring(0, accountNumber.length() - 1);
		return calculateLuhnCheckDigit(baseNumber) == checkDigit;
	}

	// Luhn 알고리즘으로 검증 숫자 계산하는 함수
	private int calculateLuhnCheckDigit(String baseNumber) {
		int sum = 0;
		boolean alternate = false;
		for (int i = baseNumber.length() - 1; i >= 0; i--) {
			int digit = Integer.parseInt(String.valueOf(baseNumber.charAt(i)));
			if (alternate) {
				digit *= 2;
				if (digit > 9) {
					digit -= 9;
				}
			}
			sum += digit;
			alternate = !alternate;
		}
		return (sum % 10 == 0) ? 0 : (10 - (sum % 10));
	}
}
