package ncbank.beans;

import java.sql.Date;

public class CrerateTradeBean {
	private int trade_num;
    private Date trade_date;
    private int trade_money; // 환전 후 얻게 될 금액( ex 1000엔)
    private float trade_rate; // 적용 환율
    private String trade_type;
    private Date trade_reservation_date;
    private int code_bank;
    private int user_num;
    
    private String code_bank_tel ;
	private String code_bank_fax ;
	private String code_money;
	private String code_bank_name;

	
	
	
	
	
	public String getCode_bank_name() {
		return code_bank_name;
	}
	public void setCode_bank_name(String code_bank_name) {
		this.code_bank_name = code_bank_name;
	}
	public String getCode_money() {
		return code_money;
	}
	public void setCode_money(String code_money) {
		this.code_money = code_money;
	}
	public int getTrade_num() {
		return trade_num;
	}
	public void setTrade_num(int trade_num) {
		this.trade_num = trade_num;
	}
	public Date getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}
	public int getTrade_money() {
		return trade_money;
	}
	public void setTrade_money(int trade_money) {
		this.trade_money = trade_money;
	}
	public float getTrade_rate() {
		return trade_rate;
	}
	public void setTrade_rate(float trade_rate) {
		this.trade_rate = trade_rate;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public Date getTrade_reservation_date() {
		return trade_reservation_date;
	}
	public void setTrade_reservation_date(Date trade_reservation_date) {
		this.trade_reservation_date = trade_reservation_date;
	}
	public int getCode_bank() {
		return code_bank;
	}
	public void setCode_bank(int code_bank) {
		this.code_bank = code_bank;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public String getCode_bank_tel() {
		return code_bank_tel;
	}
	public void setCode_bank_tel(String code_bank_tel) {
		this.code_bank_tel = code_bank_tel;
	}
	public String getCode_bank_fax() {
		return code_bank_fax;
	}
	public void setCode_bank_fax(String code_bank_fax) {
		this.code_bank_fax = code_bank_fax;
	}
	
	
	
	
}
