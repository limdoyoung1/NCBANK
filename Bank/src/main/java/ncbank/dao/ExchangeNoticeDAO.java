package ncbank.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ncbank.beans.ExchangeNoticeBean;
import ncbank.mapper.ExchangeNoticeMapper;

@Repository
public class ExchangeNoticeDAO {
	
	@Autowired
	private ExchangeNoticeMapper exchangeNoticeMapper;
	
	/* select */
	public List<ExchangeNoticeBean> getAllExchangeRateNotice() {
		return exchangeNoticeMapper.getAllExchangeRateNotice();
	}
	public ExchangeNoticeBean getExchangeRateNotice(int user_num) {
		return exchangeNoticeMapper.getExchangeRateNotice(user_num);
	}
	
	/* update */
	public void updateExchangeRateNotice(ExchangeNoticeBean noticeBean) {
		exchangeNoticeMapper.updateExchangeRateNotice(noticeBean);
	}

	/* delete */
	public void deleteExchangeRateNotice(int user_num) {
		exchangeNoticeMapper.deleteExchangeRateNotice(user_num);
	}
	
	/* insert */
	public void addExchangeRateNotice(ExchangeNoticeBean noticeBean) {
		//exchangeNoticeMapper.addExchangeRateNotice(noticeBean);
		try { // 같은 code_money 의 데이터가 추가안되게 막아놓음 그에 대한 예외를 잡는 try/catch
		    exchangeNoticeMapper.addExchangeRateNotice(noticeBean);
		} catch (Exception e) {
			e.printStackTrace();
		    System.out.println("데이터 삽입 중 오류 발생: " + e.getMessage());
		}
	}
}
