package ncbank.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import ncbank.beans.CodeMoneyBean;
import ncbank.beans.ExchangeRateBean;
import ncbank.service.CodeMoneyService;
import ncbank.service.ExchangeRateService;
import ncbank.utility.DateManager;
import ncbank.utility.ExchangeRateDTO;

public class ExchangeRateInterceptor implements HandlerInterceptor {

	private ExchangeRateService exchangeRateService;

	private CodeMoneyService codeMoneyService; 
	
	// manager 들을 리퀘스트 영역에 등록해서 사용한다? ㄱㅊ을지도?
	private DateManager dateManager;
	
	public ExchangeRateInterceptor(ExchangeRateService exchangeRateService,
			CodeMoneyService codeMoneyService,
			DateManager dateManager) {
		this.exchangeRateService = exchangeRateService;
		this.codeMoneyService = codeMoneyService;
		this.dateManager = dateManager;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		/* 통화 코드 */
		List<CodeMoneyBean> codeMoneyList = codeMoneyService.getCodeMoneyList();
		if (null == codeMoneyList) {
			System.out.println("ExchangeRateInterceptor preHandle()");
			System.out.println("codeMoneyList is null");
			return true;
		}
		request.setAttribute("codeMoneyList", codeMoneyList);
		
		/* 최종 고시 환율 (DB에 존재, 크롤링은 X) */
    	List<ExchangeRateBean> finalExchangeRateList = exchangeRateService.getFinalExchangeRate();
    	if (null == finalExchangeRateList) { // 이경우는 거의 없다고 보면됨. -> 근데 만약 없으면 크롤링
    		finalExchangeRateList = exchangeRateService.findFinalExchangeRate();
    	}
    	if (null == finalExchangeRateList) {
    		System.out.println("ExchangeRateInterceptor preHandle()");
    		System.out.println("finalExchangeRateList is null");
    		return true;
    	}
    	// Bean -> DTO
        List<ExchangeRateDTO> fianlRateDtoList = exchangeRateService.convertExchangeDTOList(finalExchangeRateList);
        if (null == fianlRateDtoList) {
			System.out.println("ExchangeRateInterceptor preHandle()");
			System.out.println("fianlRateDtoList is null");
			return true;
		}
		request.setAttribute("FinalExchangeRateList", fianlRateDtoList);
		
		String inquiryDate = dateManager.parseDateToString(fianlRateDtoList.get(0).getCode_date(), "yyyy.MM.dd");
        request.setAttribute("finalInquiryDate", inquiryDate);
        
		/* 환율 차트 */
		// DB에 존재하는 환율 데이터 중 지정한 날짜범위에 해당하는 데이터를 가져옴
    	List<ExchangeRateBean> ExchangeRateList = exchangeRateService.getDateRangeExchangeRate( 
    			dateManager.getMoveDate(dateManager.getCurrentDate("yyyyMMdd"), -6, 0, "yyyyMMdd"),
    			dateManager.getCurrentDate("yyyyMMdd"));
    	if (null == ExchangeRateList) {
    		System.out.println("ExchangeController calculator()");
    		System.out.println("ExchangeRateList is null");
    		return true;
    	}
    	// 출력용 DTO로 전환
        List<ExchangeRateDTO> rateDtoList = exchangeRateService.convertExchangeDTOList(ExchangeRateList);
        
        request.setAttribute("ExchangeRateList", rateDtoList);
		
        
		return true;
	} // preHandle

} // class
