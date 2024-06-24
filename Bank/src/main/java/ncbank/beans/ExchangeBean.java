package ncbank.beans;

public class ExchangeBean {
	
	private String code_date;  
    private String code_money;  
    private float ex_buy; 
    private float ex_sell;  
    private float ex_standard;
    
    

	public String getCode_date() {
		return code_date;
	}
	public void setCode_date(String code_date) {
		this.code_date = code_date;
	}
	public String getCode_money() {
		return code_money;
	}
	public void setCode_money(String code_money) {
		this.code_money = code_money;
	}
	public float getEx_buy() {
		return ex_buy;
	}
	public void setEx_buy(float ex_buy) {
		this.ex_buy = ex_buy;
	}
	public float getEx_sell() {
		return ex_sell;
	}
	public void setEx_sell(float ex_sell) {
		this.ex_sell = ex_sell;
	}
	public float getEx_standard() {
		return ex_standard;
	}
	public void setEx_standard(float ex_standard) {
		this.ex_standard = ex_standard;
	}   
    
      

}
