package ncbank.beans;

import java.sql.Date;

public class CreateExchangeBean {
	

	private String code_money; // 통화코드
    private int trade_money; // 환전 후 얻게 될 금액( ex 1000엔)
    private float exchange_payMoney; // 원화 금액(내가 낼 돈임)
    private float exchange_cash_buying_rate; // 현찰매입율
    private float trade_rate; // 적용 환율
    private float preferential_money; // 우대 금액
    private Date trade_reservation_date; // 예약 날자.
    private String code_bank_name; // 지점이름
    private String account; // 계좌
    
    private int code_bank; // 지점번호
    private int user_num; // 회원번호
    
	
	
    
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public int getCode_bank() {
		return code_bank;
	}
	public void setCode_bank(int code_bank) {
		this.code_bank = code_bank;
	}

	public String getCode_money() {
		return code_money;
	}
	public void setCode_money(String code_money) {
		this.code_money = code_money;
	}
	public int getTrade_money() {
		return trade_money;
	}
	public void setTrade_money(int trade_money) {
		this.trade_money = trade_money;
	}
	public float getExchange_payMoney() {
		return exchange_payMoney;
	}
	public void setExchange_payMoney(float exchange_payMoney) {
		this.exchange_payMoney = exchange_payMoney;
	}
	public float getExchange_cash_buying_rate() {
		return exchange_cash_buying_rate;
	}
	public void setExchange_cash_buying_rate(float exchange_cash_buying_rate) {
		this.exchange_cash_buying_rate = exchange_cash_buying_rate;
	}
	public float getTrade_rate() {
		return trade_rate;
	}
	public void setTrade_rate(float trade_rate) {
		this.trade_rate = trade_rate;
	}
	public float getPreferential_money() {
		return preferential_money;
	}
	public void setPreferential_money(float preferential_money) {
		this.preferential_money = preferential_money;
	}
	public Date getTrade_reservation_date() {
		return trade_reservation_date;
	}
	public void setTrade_reservation_date(Date trade_reservation_date) {
		this.trade_reservation_date = trade_reservation_date;
	}
	public String getCode_bank_name() {
		return code_bank_name;
	}
	public void setCode_bank_name(String code_bank_name) {
		this.code_bank_name = code_bank_name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
    
    

    
    
	
	

}
