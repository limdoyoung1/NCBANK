package ncbank.utility;

import java.util.Date;

// 출력용 데이터 집합 DTO
public class ExchangeRateDTO {
    // ExchangeRate 에서 채울 값
    private Date code_date = null; // 일시
    private String code_money = ""; // 통화 코드
    private float ex_buy = 0.0f; // 구매 시 환율 - ttb 송금 받을때
    private float ex_sell = 0.0f; // 판매 시 환율 - tts 송금 보낼때
    private float ex_standard = 0.0f; // 매매 기준율
    // CodeMoney 에서 채울 값
    private String code_money_name = ""; // 통화코드 명

    // 스트링 타입 날짜
    private String code_date_str;
    

	public Date getCode_date() {
        return code_date;
    }

    public void setCode_date(Date code_date) {
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

    public String getCode_money_name() {
        return code_money_name;
    }

    public void setCode_money_name(String code_money_name) {
        this.code_money_name = code_money_name;
    }

	public String getCode_date_str() {
		return code_date_str;
	}

	public void setCode_date_str(String code_date_str) {
		this.code_date_str = code_date_str;
	}
    
  	
}
