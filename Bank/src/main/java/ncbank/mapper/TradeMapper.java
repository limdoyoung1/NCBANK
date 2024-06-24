package ncbank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import ncbank.beans.CrerateTradeBean;
import ncbank.beans.WalletBean;

public interface TradeMapper {
	
	// 특정 조건의 Trade 테이블 전체 다 불러오기 + 페이지
	@Select("select tr.trade_num, tr.trade_date, tr.trade_type, "+
			"tr.code_money, tr.trade_money, tr.trade_rate, "+
			"cb.code_bank_name, cb.code_bank_tel, cb.code_bank_fax "+
			"from trade tr "+
			"join code_bank cb "+
			"on tr.code_bank = cb.code_bank "+
			"and tr.user_num = #{user_num} "+
			"order by tr.trade_num desc")
    List<CrerateTradeBean> getTradePlusList(@Param("user_num") int user_num, RowBounds rowBounds);
	// 얘도 페이지
	@Select("SELECT COUNT(*) FROM trade WHERE user_num=#{user_num}")
    int getTradeCount(int user_num);
	
	// Wallet 테이블 전체 다 불러오기
	@Select("SELECT code_money, w_balance "+
			"FROM wallet "+
			"WHERE user_num=#{user_num}")
    
	List<WalletBean> getWalletAllList(int user_num);
	
	/*
    // startDate 와 endDate 
    @Select("<script>" +
            "SELECT trade_num, trade_date, trade_money, trade_rate, trade_type, code_money " +
            "FROM trade " +
            "WHERE trade_date BETWEEN #{startDate} AND #{endDate}" +
            "</script>")
    List<TradeBean> getTradeListByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    */
    
    // 회원번호, 통화코드 별 금액합산
	/*
	 * @Select("select tr.code_money, tr.user_num, sum(tr.trade_money) as trade_money_sum "
	 * + "from trade tr "+ "group by tr.user_num, tr.code_money order by
	 * tr.user_num)
	 */    
}
