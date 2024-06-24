package ncbank.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ncbank.beans.AccountBean;
import ncbank.beans.UserBean;
import ncbank.mapper.AccountMapper;

@Repository
public class AccountDAO {
	@Autowired
	private AccountMapper accountMapper;

	public UserBean getUserInfo(int userNum) {
		return accountMapper.getUserInfo(userNum);
	}

	public List<AccountBean> getAccount(int userNum) {
		return accountMapper.getAccount(userNum);
	}

	public void createAccount(AccountBean accountBean) {
		accountMapper.createAccount(accountBean);
	}

	public void updateAccountBalance(AccountBean accountBean) {
		accountMapper.updateAccountBalance(accountBean);
	}

	public AccountBean getAccountByNumber(String accountNumber) {
		return accountMapper.getAccountByNumber(accountNumber);
	}
}
