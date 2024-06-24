package ncbank.beans;

public class WalletBean {
	int user_num; // 회원번호
	String code_money; // 통화코드
	int w_balance; // 보유금액
	
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public String getCode_money() {
		return code_money;
	}
	public void setCode_money(String code_money) {
		this.code_money = code_money;
	}
	public int getW_balance() {
		return w_balance;
	}
	public void setW_balance(int w_balance) {
		this.w_balance = w_balance;
	}
	
	
	
}
