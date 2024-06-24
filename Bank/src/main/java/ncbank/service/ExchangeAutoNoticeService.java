package ncbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncbank.beans.ExchangeAutoNoticeBean;
import ncbank.dao.ExchangeAutoNoticeDAO;
import ncbank.utility.DateManager;

@Service
public class ExchangeAutoNoticeService {
	
	@Autowired
	private ExchangeAutoNoticeDAO exchangeAutoNoticeDAO;
	
	@Autowired
	private DateManager dateManager;
	
	/* select */
	public List<ExchangeAutoNoticeBean> getAllExchangeAutoNotice() {
		return exchangeAutoNoticeDAO.getAllExchangeAutoNotice();
	}
	
	public ExchangeAutoNoticeBean getExchangeAutoNotice(int user_num) {
		return exchangeAutoNoticeDAO.getExchangeAutoNotice(user_num);
	}
	
	public int getAutoNoticeSendState(int user_num) {
		return exchangeAutoNoticeDAO.getAutoNoticeSendState(user_num);
	}
	
	/* update */
	public void updateExchangeAutoNotice(int send_state, int user_num) {
		exchangeAutoNoticeDAO.updateExchangeAutoNotice(send_state,
				dateManager.parseStringToDate(dateManager.getCurrentDate("yyyyMMdd"), "yyyyMMdd"), 
				user_num);
	}
	
	/* insert */
	public void addExchangeAutoNotice(int user_num) {
		exchangeAutoNoticeDAO.addExchangeAutoNotice(user_num,
				dateManager.parseStringToDate(dateManager.getCurrentDate("yyyyMMdd"), "yyyyMMdd"));
	}
	
	/* delete */
	public void deleteExchangeAutoNotice(int user_num) {
		exchangeAutoNoticeDAO.deleteExchangeAutoNotice(user_num);
	}
	
}
