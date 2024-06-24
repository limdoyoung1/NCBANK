package ncbank.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ncbank.beans.ExchangeAutoNoticeBean;
import ncbank.mapper.ExchangeAutoNoticeMapper;

@Repository
public class ExchangeAutoNoticeDAO {
	
	@Autowired
	private ExchangeAutoNoticeMapper exchangeAutoNoticeMapper;
	
	
	/* select */
	public List<ExchangeAutoNoticeBean> getAllExchangeAutoNotice() {
		return exchangeAutoNoticeMapper.getAllExchangeAutoNotice();
	}
	
	public ExchangeAutoNoticeBean getExchangeAutoNotice(int user_num) {
		return exchangeAutoNoticeMapper.getExchangeAutoNotice(user_num);
	}
	
	public int getAutoNoticeSendState(int user_num) {
		return exchangeAutoNoticeMapper.getAutoNoticeSendState(user_num);
	}
	
	/* update */
	public void updateExchangeAutoNotice(int send_state, Date update_date, int user_num) {
		exchangeAutoNoticeMapper.updateExchangeAutoNotice(send_state, update_date, user_num);
	}
	
	/* insert */
	public void addExchangeAutoNotice(int user_num, Date update_date) {
		System.out.println("ExchangeAutoNoticeDAO addExchangeAutoNotice()");
		System.out.println("user_num : " + user_num + " | update_date : " + update_date);
		exchangeAutoNoticeMapper.addExchangeAutoNotice(user_num, update_date);
	}
	
	/* delete */
	public void deleteExchangeAutoNotice(int user_num) {
		exchangeAutoNoticeMapper.deleteExchangeAutoNotice(user_num);
	}
	
}
