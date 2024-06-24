package ncbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncbank.beans.ExchangeNoticeBean;
import ncbank.dao.ExchangeNoticeDAO;

@Service
public class ExchangeNoticeService {

	@Autowired
	private ExchangeNoticeDAO exchangeNoticeDAO;
	
	public List<ExchangeNoticeBean> getAllExchangeRateNotice() {
		return exchangeNoticeDAO.getAllExchangeRateNotice();
	}
	public ExchangeNoticeBean getExchangeRateNotice(int user_num) {
		return exchangeNoticeDAO.getExchangeRateNotice(user_num);
	}
	
	
	public void updateExchangeRateNotice(ExchangeNoticeBean noticeBean) {
		if (null == noticeBean) {
			System.out.println("ExchangeNoticeService updateExchangeRateNotice()");
			System.out.println("noticeBean is null");
			return;
		}
		exchangeNoticeDAO.updateExchangeRateNotice(noticeBean);
	}

	
	public void deleteExchangeRateNotice(int user_num) {
		exchangeNoticeDAO.deleteExchangeRateNotice(user_num);
	}
	
	
	public void addExchangeRateNotice(ExchangeNoticeBean noticeBean) {
		System.out.println("ExchangeNoticeService addExchangeRateNotice()");
		exchangeNoticeDAO.addExchangeRateNotice(noticeBean);
	}
	
	
}
