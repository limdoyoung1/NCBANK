package ncbank.beans;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class ExchangeNoticeBean {
	
	private int notice_num;
	// 1: 매매기준, 2: 송금할때, 3: 송금받을때 
	private int notice_rate_type;
	private float notice_rate;
	private String notice_email;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date notice_date;
	private int user_num;
	private String code_money;
	
	
	public int getNotice_num() {
		return notice_num;
	}
	public void setNotice_num(int notice_num) {
		this.notice_num = notice_num;
	}
	
	public int getNotice_rate_type() {
		return notice_rate_type;
	}
	public void setNotice_rate_type(int notice_rate_type) {
		this.notice_rate_type = notice_rate_type;
	}
	
	public float getNotice_rate() {
		return notice_rate;
	}
	public void setNotice_rate(float notice_rate) {
		this.notice_rate = notice_rate;
	}

	public String getNotice_email() {
		return notice_email;
	}
	public void setNotice_email(String notice_email) {
		this.notice_email = notice_email;
	}
	
	public Date getNotice_date() {
		return notice_date;
	}
	public void setNotice_date(Date notice_date) {
		this.notice_date = notice_date;
	}
	
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
	
	
}
