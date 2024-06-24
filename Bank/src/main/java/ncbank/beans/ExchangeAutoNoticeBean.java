package ncbank.beans;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

// 자동알림을 위한 Bean
public class ExchangeAutoNoticeBean {
	
	// 어떤 알림에 대한 자동알림인지
	private int notice_num;
	// 알림을 보냈는지에 대한 여부 0 : 안보냄 , 1 : 보냄
	private int notice_send_state;
	// 갱신날짜
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date notice_update_date;
	
	public int getNotice_num() {
		return notice_num;
	}
	public void setNotice_num(int notice_num) {
		this.notice_num = notice_num;
	}
	
	public int getNotice_send_state() {
		return notice_send_state;
	}
	public void setNotice_send_state(int notice_send_state) {
		this.notice_send_state = notice_send_state;
	}
	
	public Date getNotice_update_date() {
		return notice_update_date;
	}
	public void setNotice_update_date(Date notice_update_date) {
		this.notice_update_date = notice_update_date;
	}
	
	
	
}
