package ncbank.beans;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AccountBean {
	private String account;
	@Size(max = 4, min = 4)
	@Pattern(regexp = "\\d+", message = "숫자만 입력해야 합니다.")
	private String ac_password;
	@Size(max = 30, min = 1)
	@Pattern(regexp = "\\d+", message = "숫자만 입력해야 합니다.")
	private String ac_balance;
	private Integer ac_type;
	private Date ac_date;
	private int user_num;

	public int getUser_num() {
		return user_num;
	}

	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}

	public String getAc_password() {
		return ac_password;
	}

	public void setAc_password(String ac_password) {
		this.ac_password = ac_password;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getAc_date() {
		return ac_date;
	}

	public void setAc_date(Date ac_date) {
		this.ac_date = ac_date;
	}

	public int getAc_type() {
		return (ac_type != null) ? ac_type : 0;
	}

	public void setAc_type(Integer ac_type) {
		this.ac_type = ac_type;
	}

	public String getAc_balance() {
		return ac_balance;
	}

	public void setAc_balance(String ac_balance) {
		this.ac_balance = ac_balance;
	}
}
