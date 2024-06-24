package ncbank.interceptor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import ncbank.beans.ExchangeAutoNoticeBean;
import ncbank.beans.ExchangeNoticeBean;
import ncbank.beans.UserBean;
import ncbank.service.ExchangeAutoNoticeService;
import ncbank.service.ExchangeNoticeService;
import ncbank.utility.DateManager;
import ncbank.utility.EmailManager;
import ncbank.utility.ExchangeRateDTO;

public class ExchangeAutoNoticeInterceptor implements HandlerInterceptor {

	private ExchangeAutoNoticeService autoNoticeService;
	private ExchangeNoticeService noticeService;
	private UserBean loginUserBean;
	private DateManager dateManager;
	private EmailManager emailManager;
	
	private String noticeEmail;
	
	public ExchangeAutoNoticeInterceptor(ExchangeAutoNoticeService autoNoticeService,
			ExchangeNoticeService noticeService, UserBean loginUserBean, DateManager dateManager,
			EmailManager emailManager) {
		this.autoNoticeService = autoNoticeService;
		this.noticeService = noticeService;
		this.loginUserBean = loginUserBean;
		this.dateManager = dateManager;
		this.emailManager = emailManager;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		sendNoticeMail(request, response);

		return true;
	}

	private void sendNoticeMail(HttpServletRequest request, HttpServletResponse response) {
		
		noticeEmail = "";
		
		// 로그인 상태가 아니면 메일전송 x
		if (null == loginUserBean && !loginUserBean.isUserLogin()) {
			System.out.println("ExchangeAutoNoticeInterceptor sendNoticeMail()");
			System.out.println("not login");
			return;
		}

		ExchangeAutoNoticeBean autoNoticeBean = autoNoticeService.getExchangeAutoNotice(loginUserBean.getUser_num());
		if (null == autoNoticeBean) {
			System.out.println("ExchangeAutoNoticeInterceptor sendNoticeMail()");
			System.out.println("autoNoticeBean is null");
			return;
		}

		if (1 == autoNoticeBean.getNotice_send_state()) { // 이미 보냈으면 X
			System.out.println("ExchangeAutoNoticeInterceptor sendNoticeMail()");
			System.out.println("sendState : 1 (이미보낸상태)");
			return;
		}

		// 환율선이 무너졌는가 체크 후 jsp 파일 데이터 채우기
		if (!setJspContentData(request)) {
			System.out.println("setJspContentData : false");
			return;
		}
		

		/* 메일전송 */
		// 메일 보내는 기준 잡아야됨 -> Intercepter 에서?
		String rootPath = request.getServletContext().getRealPath("/");
		// inlinePath : 인라인 이미지 이름, imgPath : 실제 이미지 경로
		Map<String, String> inlinePathImgs = new HashMap<String, String>();

		inlinePathImgs.put("<NCBankIcon>", rootPath + "resources/img/NCBankIcon_2.png");

		emailManager.sendJspEmailWithInlineImage(noticeEmail, "[NC은행] 환율알림서비스 안내",
				"/WEB-INF/views/exchange/sendNoticeMail.jsp", inlinePathImgs, request, response);

		// 메일을 보냈으면 상태를 갱신시킨다
		autoNoticeService.updateExchangeAutoNotice(1, loginUserBean.getUser_num());

	} // sendNoticeMail()

	// jsp 파일에 들어가야될 데이터 채우기
	private boolean setJspContentData(HttpServletRequest request) {
		System.out.println("ExchangeAutoNoticeInterceptor sendNoticeMail()");

		// 신청한 알림에 대한 정보
		ExchangeNoticeBean exchangeNoticeBean = noticeService.getExchangeRateNotice(loginUserBean.getUser_num());
		if (null == exchangeNoticeBean) {
			System.out.println("ExchangeAutoNoticeInterceptor sendNoticeMail()");
			System.out.println("exchangeNoticeBean is null");
			return false;
		}

		// ExchangeRateInterceptor 에서 저장한 최종고시 환율정보
		List<ExchangeRateDTO> rateDtoList = (List<ExchangeRateDTO>) request.getAttribute("FinalExchangeRateList");
		if (null == rateDtoList || rateDtoList.isEmpty()) {
			System.out.println("ExchangeAutoNoticeInterceptor sendNoticeMail()");
			System.out.println("rateDtoList is null");
			return false;
		}

		ExchangeRateDTO exchangeRateBean = null;
		// 알림을 신청한 통화의 최종고시 환율 탐색
		for (ExchangeRateDTO dto : rateDtoList) {
			if (exchangeNoticeBean.getCode_money().toUpperCase().equals(dto.getCode_money().toUpperCase())) { 
				exchangeRateBean = dto;
				break;
			}
		}
		if (null == exchangeRateBean) {
			System.out.println("ExchangeAutoNoticeInterceptor sendNoticeMail()");
			System.out.println("exchangeRateBean is null");
			return false;
		}

		// 설정한 환율선이 무너졌는가 ? -> 근데 이걸 여기서 하는게 맞나?
		// 알림 신청한 기준에 따라 적용할 기준 정하기 - 1: 매매기준, 2: 송금할때, 3: 송금받을때
		int rateType = exchangeNoticeBean.getNotice_rate_type();
		BigDecimal defaultRate;

		switch (rateType) {
		case 1: // 매매 기준율
			defaultRate = BigDecimal.valueOf(exchangeRateBean.getEx_standard());
			break;
		case 2: // 송금 할때
			defaultRate = BigDecimal.valueOf(exchangeRateBean.getEx_buy());
			break;
		case 3: // 송금 받을때
			defaultRate = BigDecimal.valueOf(exchangeRateBean.getEx_sell());
			break;
		default: // 예외는 매매 기준율로
			defaultRate = BigDecimal.valueOf(exchangeRateBean.getEx_standard());
			break;
		}

		// 알림으로 설정한 환율 선
		BigDecimal noticeRate = BigDecimal.valueOf(exchangeNoticeBean.getNotice_rate());
		System.out.println("defaultRate : " + defaultRate);
		System.out.println("noticeRate : " + noticeRate);

		// 환율선 비교
		int comparisonResult = defaultRate.compareTo(noticeRate);
		System.out.println("comparisonResult : " + comparisonResult);

		// defaultRate == noticeRate : 0 - 알림 O
		// defaultRate > noticeRate : 1 - 알림 O
		// defaultRate < noticeRate : -1 - 알림 X

		// 최종고시 환율(defaultRate)이 무너지지 않았을경우 예외처리
		if (0 > comparisonResult) {
			System.out.println("ExchangeAutoNoticeInterceptor sendNoticeMail()");
			System.out.println("defaultRate < noticeRate");
			return false;
		}

		/* 환율선이 무너너졌을경우 데이터를 채운다. */
		// 알림설정 날짜
		String noticeDate = dateManager.parseDateToString(exchangeNoticeBean.getNotice_date(), "yyyy.MM.dd");
		System.out.println("noticeDate : " + noticeDate);
		request.setAttribute("ExchangeNoticeBean", exchangeNoticeBean);
		request.setAttribute("noticeDate", noticeDate);

		// 최종고시 환율 날짜
		String inquiryDate = dateManager.parseDateToString(exchangeRateBean.getCode_date(), "yyyy.MM.dd");
		System.out.println("inquiryDate : " + inquiryDate);
		request.setAttribute("ExchangeRateBean", exchangeRateBean);
		request.setAttribute("inquiryDate", inquiryDate);
		
		noticeEmail = exchangeNoticeBean.getNotice_email();
		
		return true;

	} // setJspContentData()

} // class
