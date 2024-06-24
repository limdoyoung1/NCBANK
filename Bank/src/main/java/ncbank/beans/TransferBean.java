package ncbank.beans;

import java.util.Date;

public class TransferBean {
	private int trans_num;
	private int trans_type;
	private String trans_money;
	private String trans_balance;
	private String trans_text;
	private Date trans_date;
	private String to_account;
	private String from_account;
	private String code_organ;
	private int user_num;
	private String code_organ_name;

	public int getUser_num() {
		return user_num;
	}

	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}

	public String getTrans_money() {
		return trans_money;
	}

	public void setTrans_money(String trans_money) {
		this.trans_money = trans_money;
	}

	public String getCode_organ_name() {
		return code_organ_name;
	}

	public void setCode_organ_name(String code_organ_name) {
		this.code_organ_name = code_organ_name;
	}

	public int getTrans_num() {
		return trans_num;
	}

	public void setTrans_num(int trans_num) {
		this.trans_num = trans_num;
	}

	public int getTrans_type() {
		return trans_type;
	}

	public void setTrans_type(int trans_type) {
		this.trans_type = trans_type;
	}

	public String getTrans_balance() {
		return trans_balance;
	}

	public void setTrans_balance(String trans_balance) {
		this.trans_balance = trans_balance;
	}

	public String getTrans_text() {
		return trans_text;
	}

	public void setTrans_text(String trans_text) {
		this.trans_text = trans_text;
	}

	public Date getTrans_date() {
		return trans_date;
	}

	public void setTrans_date(Date trans_date) {
		this.trans_date = trans_date;
	}

	public String getTo_account() {
		return to_account;
	}

	public void setTo_account(String to_account) {
		this.to_account = to_account;
	}

	public String getFrom_account() {
		return from_account;
	}

	public void setFrom_account(String from_account) {
		this.from_account = from_account;
	}

	public String getCode_organ() {
		return code_organ;
	}

	public void setCode_organ(String code_organ) {
		this.code_organ = code_organ;
	}
}
