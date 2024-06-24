package ncbank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import ncbank.beans.ExchangeRateBean;

public interface ExchangeRateMapper {

	/* Select */
	
	// 전체 환율 데이터 가져오기
	@Select("select * from exchange ")
	public List<ExchangeRateBean> getAllExchangeRate();
	 
    // DB에 저장되어 있는 데이터중 가장 최신의 환율 데이터 가져오기
    @Select("select * from exchange "
    		+ "where code_date = (select max(code_date) from exchange)")
    public List<ExchangeRateBean> getFinalExchangeRate();
    
    // 최신 환율 데이터중 원하는 동화의 데이터
    @Select("select * from exchange "
    		+ "where code_date = (select max(code_date) from exchange) "
    		+ "and code_money = #{code_money}")
    public ExchangeRateBean findFinalExchangeRate(String code_money);
    
    // 지정한 날짜의 환율정보 가져오기
    @Select("select code_date, ex_buy, ex_sell, ex_standard, code_money " +
            "from exchange " + "where code_date = to_date(#{date}, 'yyyyMMdd')")
    public List<ExchangeRateBean> getExchangeRate(String date);
    
    
    // 범위 날짜 환율데이터 가져오기
    @Select("select * from exchange "
    		+ "where code_date between to_date(#{startDate}, 'yyyyMMdd') " 
    		+ "and to_date(#{endDate}, 'yyyyMMdd') "
    		+ "order by code_date")
    public List<ExchangeRateBean> getDateRangeExchangeRate(@Param("startDate") String startDate, @Param("endDate") String endDate);
    
    /* insert */
    // 환율 정보 추가 - fk제약조건에 맞춰서
    @Insert("insert into exchange(code_date, code_money, ex_buy, ex_sell, ex_standard) " 
    		+ "values(#{code_date}, (select code_money from code_money where code_money = #{code_money}) "
            + ", #{ex_buy}, #{ex_sell}, #{ex_standard})")
    public void addExchangeRate(ExchangeRateBean exchangeBean);
    
}
