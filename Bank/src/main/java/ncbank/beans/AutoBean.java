package ncbank.beans;

import java.util.Date;

public class AutoBean {
	private int auto_num;
	private String auto_name;
	private String auto_money;
	private String auto_type;
	private String auto_next_date;
	private Date auto_start;
	private Date auto_end;
	private String to_account;
	private String code_organ;
	private String from_account;
	private int user_num;

	private String code_organ_name;

	public String getCode_organ_name() {
		return code_organ_name;
	}

	public void setCode_organ_name(String code_organ_name) {
		this.code_organ_name = code_organ_name;
	}

	public int getAuto_num() {
		return auto_num;
	}

	public void setAuto_num(int auto_num) {
		this.auto_num = auto_num;
	}

	public String getAuto_name() {
		return auto_name;
	}

	public void setAuto_name(String auto_name) {
		this.auto_name = auto_name;
	}

	public String getAuto_money() {
		return auto_money;
	}

	public void setAuto_money(String auto_money) {
		this.auto_money = auto_money;
	}

	public String getAuto_type() {
		return auto_type;
	}

	public void setAuto_type(String auto_type) {
		this.auto_type = auto_type;
	}

	public String getAuto_next_date() {
		return auto_next_date;
	}

	public void setAuto_next_date(String auto_next_date) {
		this.auto_next_date = auto_next_date;
	}

	public Date getAuto_start() {
		return auto_start;
	}

	public void setAuto_start(Date auto_start) {
		this.auto_start = auto_start;
	}

	public Date getAuto_end() {
		return auto_end;
	}

	public void setAuto_end(Date auto_end) {
		this.auto_end = auto_end;
	}

	public String getTo_account() {
		return to_account;
	}

	public void setTo_account(String to_account) {
		this.to_account = to_account;
	}

	public String getCode_organ() {
		return code_organ;
	}

	public void setCode_organ(String code_organ) {
		this.code_organ = code_organ;
	}

	public String getFrom_account() {
		return from_account;
	}

	public void setFrom_account(String from_account) {
		this.from_account = from_account;
	}

	public int getUser_num() {
		return user_num;
	}

	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
}
