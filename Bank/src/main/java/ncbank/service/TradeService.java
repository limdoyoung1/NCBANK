package ncbank.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncbank.beans.CrerateTradeBean;
import ncbank.beans.WalletBean;
import ncbank.dao.TradeDao;

@Service
public class TradeService {

    @Autowired
    private TradeDao tradeDao;

    // 특정 조건의 Trade 테이블 전체 다 불러오기 + 페이지
    public List<CrerateTradeBean> getTradePlusList(int user_num, int page, int contentPageCnt) {
        int start = (page - 1) * contentPageCnt;
        RowBounds rowBounds = new RowBounds(start, contentPageCnt);
        return tradeDao.getTradePlusList(user_num, rowBounds);
    }
    // 얘도 페이지
    public int getTradeCount(int user_num) {
        return tradeDao.getTradeCount(user_num);
    }
    
    // Wallet 테이블 전체 다 불러오기
    public List<WalletBean> getWalletAllList(int user_num){
    	List<WalletBean> walletAllList = tradeDao.getWalletAllList(user_num);
    	//System.out.println("getWalletAllList() Service : "+walletAllList);
    	return walletAllList;
    	
    }
    
    /*
    public List<TradeBean> getTradeListByDateRange(Date startDate, Date endDate) {
        return tradeDao.getTradeListByDateRange(startDate, endDate);
    }
    */
    
    
}
