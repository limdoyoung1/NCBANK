package ncbank.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ncbank.beans.AccountBean;
import ncbank.beans.CodeBankBean;
import ncbank.beans.CreateExchangeBean;
import ncbank.beans.ExchangeBean;

public interface ExchangeMapper {
	
	// api 데이터 exchange에 저장된거 가져오기.
	 @Select("select "+
            "code_date, "+
            "code_money, "+
            "ex_standard "+
            "from exchange "+
            "where to_char(code_date, 'YY/MM/DD') = #{cuDate}")
	    List<ExchangeBean> getExchangeList(@Param("cuDate") String cuDate);
	
	 @Select("SELECT "+
				"ac.user_num, "+
				"ac.account, "+
				"ac.ac_password "+
				"FROM account ac "+
				"JOIN login lg "+
				"ON ac.user_num = lg.user_num "+
				"WHERE ac.user_num = #{user_num} "+
				"order by ac.user_num ")
		List<AccountBean> getAccount(@Param("user_num") int user_num);
	 
	@Select("select "+
			"code_bank, "+
			"code_organ_name, "+
			"code_bank_name, "+
			"code_bank_address "+
			"from code_bank")
	List<CodeBankBean> getCodeBankName();
	
	// ExchangeAsk에서 얻어낸 정보(createExchangeBean)를 Trade 테이블에 집어넣기
    @Insert("INSERT INTO trade "+
    		"(trade_num, trade_date, trade_money, trade_rate, trade_type, "+
    		"trade_reservation_date, code_bank, user_num, code_money) "+
    		"VALUES (trade_num_seq.nextval, to_date(sysdate,'yy/mm/dd'), #{trade_money}, #{trade_rate}, "+
    		"'매입', #{trade_reservation_date}, #{code_bank}, #{user_num}, #{code_money} )")
    
    void  setTradeByExchangeAsk(CreateExchangeBean createExchangeBean);
    
    // Wallet이 비어있을 때 오라클에 미리 입력해놓은 Trade테이블 데이터를 Wallet테이블에 넣어놓기
    // #1. Wallet테이블에 값이 있나 없나 조건 
    @Select("SELECT COUNT(*) "+
    		"FROM wallet ")
    int firstCheckWalletExists();
    
    // #2. Wallet테이블에 데이터 넣기
    @Insert("INSERT INTO wallet (user_num, code_money, w_balance) "+
    		"SELECT user_num, code_money, SUM(trade_money) AS sum_balance "+
    		"FROM trade "+
    		"WHERE trade_type = '매입' "+
    		"GROUP BY user_num, code_money "+
    		"ORDER BY user_num")
    
	void insertInitialWalletData();
    
    // ExchangeAsk에서 얻어낸 정보(createExchangeBean)를 Wallet 테이블에 집어넣기
    // #1. 조건문을 위한 쿼리
    @Select("SELECT COUNT(*) "+
    		"FROM wallet "+
    		"WHERE user_num = #{user_num} AND code_money = #{code_money}")
    int checkWalletExists(@Param("user_num") int user_num, @Param("code_money") String code_money);
    
    // #2-1. 회원번호랑 통화코드가 둘 다 일치하면 UPDATE
    @Update("UPDATE wallet "+
    		"SET w_balance = w_balance + #{trade_money} "+
    		"WHERE user_num = #{user_num} and code_money = #{code_money}")
    
    void  setWalletUpdateByExchangeAsk(CreateExchangeBean createExchangeBean); 
    
    // #2-2. 둘 중 하나라도 틀리면 INSERT
    @Insert("INSERT INTO "+
    		"wallet ( user_num, code_money, w_balance ) "+
    		"VALUES ( #{user_num}, #{code_money}, #{trade_money} )")
    
    void  setWalletInsertByExchangeAsk(CreateExchangeBean createExchangeBean); 
    	

    	
    
    
	
	/*
	// 검색된 은행 정보를 가져오는 쿼리
	@Select("SELECT code_bank, code_organ_name, code_bank_address, code_bank_name " +
	        "FROM code_bank " +
	        "WHERE code_bank_address LIKE '%' || #{keyword} || '%'")
	List<CodeBankBean> searchBankByKeyword(@Param("keyword") String keyword);
	*/
	
}
