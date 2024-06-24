package ncbank.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ncbank.beans.CrerateTradeBean;
import ncbank.beans.WalletBean;
import ncbank.mapper.TradeMapper;

@Repository
public class TradeDao {

    @Autowired
    private TradeMapper tradeMapper;
    
    // 특정 조건의 Trade 테이블 전체 다 불러오기 + 페이지
    public List<CrerateTradeBean> getTradePlusList(int user_num, RowBounds rowBounds) {
        return tradeMapper.getTradePlusList(user_num, rowBounds);
    }
    // 얘도 페이지
    public int getTradeCount(int user_num) {
        return tradeMapper.getTradeCount(user_num);
    }
    
    // Wallet 테이블 전체 다 불러오기
    
    public List<WalletBean> getWalletAllList(int user_num){
    	List<WalletBean> walletAllList = tradeMapper.getWalletAllList(user_num);
    	//System.out.println("getWalletAllList() Dao : "+walletAllList);
    	return walletAllList;
    }
    
    /*
    public List<TradeBean> getTradeListByDateRange(Date startDate, Date endDate) {
        return tradeMapper.getTradeListByDateRange(startDate, endDate);
    }
    */
    
    
}
