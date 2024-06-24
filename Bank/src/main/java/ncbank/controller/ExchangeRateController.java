package ncbank.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ncbank.beans.ExchangeRateBean;
import ncbank.service.ExchangeRateService;
import ncbank.utility.DateManager;
import ncbank.utility.ExchangeRateDTO;

@Controller
@RequestMapping("/exchange")
public class ExchangeRateController {

    @Autowired
    ExchangeRateService exchangeRateService;

    @Autowired
    private DateManager dateManager;
    
    @GetMapping("/main")
    public String main() {
        return "exchange/main";
    }

    /* ==========[환율 조회]========== */
    
    /* 환율 조회페이지에서 날짜를 선택한 경우 */
    @GetMapping("/rateInquiryDate")
    // @RequestParam("inquiryDay") : name이 inquiryDay 인 값을 가져온다 (input 의 name 이
    // inquiryDay)
    // @DateTimeFormat(pattern = "yyyyMMdd") : inquiryDay를 Date로 변환하기 위한 포멧 설정
    public String rateInquiryDate(@RequestParam("inquiryDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date inquiryDate,
            Model model) {
        System.out.println("rateInquiryDate()");
        System.out.println("inquiryDate : " + inquiryDate);
        List<ExchangeRateBean> rateBeanList = exchangeRateService.getExchangeRate(
        		dateManager.parseDateToString(inquiryDate, "yyyyMMdd"));
        List<ExchangeRateDTO> rateDtoList = exchangeRateService.convertExchangeDTOList(rateBeanList);
        
        if (null == rateDtoList) { // 해당날짜의 환율정보가 없음.
        	// 이걸 페이지 전환없이 할려면 ajax? -> 나중에 고민좀
        	return "exchange/not_rateInquiry";
        }
        
        model.addAttribute("ExchangeRateList", rateDtoList);
        model.addAttribute("inquiryDate1", dateManager.parseDateToString(inquiryDate, "yyyy.MM.dd"));
        model.addAttribute("inquiryDate2", dateManager.parseDateToString(inquiryDate, "yyyy-MM-dd"));

        return "exchange/rateInquiry";
    }

    /* 환율 페이지 기본 - 현재 날짜 기준 */
    @GetMapping("/rateInquiry")
    public String rateInquiry(Model model) {
        System.out.println("rateInquiry()");
        
        // 현재날짜의 환율정보 가져옴
        System.out.println("currentDate : " + dateManager.getCurrentDate("yyyyMMdd"));
        String searchDate = null;

        // 최근고시 환율 정보
        List<ExchangeRateBean> rateBeanList = exchangeRateService.findFinalExchangeRate();
        // 출력용 DTO로 전환
        List<ExchangeRateDTO> rateDtoList = exchangeRateService.convertExchangeDTOList(rateBeanList);
        
        if (null != rateDtoList) {
        	model.addAttribute("ExchangeRateList", rateDtoList);
        	searchDate = dateManager.parseDateToString(rateDtoList.get(0).getCode_date(), "yyyyMMdd");
        }
        
        model.addAttribute("inquiryDate1", dateManager.changeStringDateFormat(searchDate, "yyyyMMdd", "yyyy.MM.dd"));
        model.addAttribute("inquiryDate2", dateManager.changeStringDateFormat(searchDate, "yyyyMMdd", "yyyy-MM-dd"));

        return "exchange/rateInquiry";
    }
    
    // 시작날짜 ~ 끝날짜 까지의 환율정보를 DB에 추가 - 백 데이터 추가용
    // + 환율 그래프 기간 요청시 ?
    @GetMapping("addRateInquiry_DateRange")
    public String addRateInquiry_DateRange() {
    	
    	exchangeRateService.addRateInquiry_DateRange(
    			dateManager.getMoveDate(dateManager.getCurrentDate("yyyyMMdd"), -13, 0, "yyyyMMdd"), 
    			dateManager.getCurrentDate("yyyyMMdd"));
    	
    	return "exchange/rateInquiry";
    }
    
    @GetMapping("miniRateInquiry")
    public String miniRateInquiry(
    		@RequestParam(value="ISOCode", defaultValue="USD") String ISOCode,
    		Model model) {
    	
    	System.out.println("ExchangeRateController miniRateInquiry()");
    	
    	List<ExchangeRateDTO> rateDTOList = new ArrayList<ExchangeRateDTO>();
    	
    	String[] ISOCodeArr = ISOCode.split(",");
    	System.out.println("ISOCodeArr" + ISOCodeArr);
    	for (String ISO : ISOCodeArr) {
    		ExchangeRateBean rateBean = exchangeRateService.findFinalExchangeRate(ISO);
        	if (null == rateBean) {
        		System.out.println("ExchangeRateController miniRateInquiry()");
        		System.out.println("rateBean is null");
        		continue;
        	}
        	
        	ExchangeRateDTO rateDTO = exchangeRateService.convertExchangeDTO(rateBean);
        	if (null == rateDTO) {
        		System.out.println("ExchangeRateController miniRateInquiry()");
        		System.out.println("rateDTO is null");
        		continue;
        	}
			
        	rateDTOList.add(rateDTO);
		}
    	if (null == rateDTOList || rateDTOList.isEmpty()) {
    		System.out.println("ExchangeRateController miniRateInquiry()");
    		System.out.println("rateDTOList is null");
    		return "exchange/miniRateInquiry";
    	}
    	
    	model.addAttribute("rateDTOList", rateDTOList);
    	System.out.println("succese");
    	
    	return "exchange/miniRateInquiry";
    }
    
    /* ==========[환율 계산기]========== */
    
    /* 환율 계산기 */
    @GetMapping("/calculator")
    public String calculator(Model model) {
    	    	
    	// DB에 존재하는 환율 데이터중 최종 고시일의 환율 데이터를 가져온다 (크롤링x)
    	List<ExchangeRateBean> finalExchangeRateList = exchangeRateService.getFinalExchangeRate();
    	if (null == finalExchangeRateList) { // 이경우는 거의 없다고 보면됨.
    		System.out.println("ExchangeController calculator()");
    		System.out.println("finalExchangeRateList is null");
    		return "exchange/calculator";
    	}
    	
    	// 출력용 DTO로 전환
        List<ExchangeRateDTO> rateDtoList = exchangeRateService.convertExchangeDTOList(finalExchangeRateList);
        
        String inquiryDate = dateManager.parseDateToString(rateDtoList.get(0).getCode_date(), "yyyy.MM.dd");
        
        // if 3개월 까지의 차트정보를 보여줄꺼면 현재날짜 기준 3개월전까지 데이터를 들고가야 됨.
        model.addAttribute("ExchangeRateList", rateDtoList);
        model.addAttribute("inquiryDate", inquiryDate);
        
        // 한국 원 정보는 따로 가져감
        ExchangeRateBean beanKRW = exchangeRateService.findFinalExchangeRate("KRW");
        ExchangeRateDTO dtoKRW = exchangeRateService.convertExchangeDTO(beanKRW);
        
        model.addAttribute("beanKRW", dtoKRW);
        
        return "exchange/calculator";
    }
    
    
    /* ==========[환율 차트]========== */
    @GetMapping("/rateChart")
    public String rateChart(HttpServletRequest request, Model model) {
    	
    	/*
    	[인터셉터에서 데이터를 세팅하는걸로 바꿈]
    	// DB에 존재하는 환율 데이터중 지정한 날짜범위의 데이터를 가져옴
    	List<ExchangeRateBean> ExchangeRateList = exchangeRateService.getDateRangeExchangeRate( 
    			dateManager.getMoveDate(dateManager.getCurrentDate("yyyyMMdd"), -6, 0, "yyyyMMdd"),
    			dateManager.getCurrentDate("yyyyMMdd"));
    	if (null == ExchangeRateList) {
    		System.out.println("ExchangeController calculator()");
    		System.out.println("ExchangeRateList is null");
    		return "exchange/rateChart";
    	}
    	// 출력용 DTO로 전환
        List<ExchangeRateDTO> rateDtoList = exchangeRateService.convertExchangeDTOList(ExchangeRateList);
        
        // if - 3개월 까지의 차트정보를 보여줄꺼면 현재날짜 기준 3개월전까지 데이터를 들고가야 됨.
        model.addAttribute("ExchangeRateList", rateDtoList);
        
        //System.out.println("ExchangeController rateChart()");
        String startDate = dateManager.getMoveDate(dateManager.getCurrentDate("yyyyMMdd"), -6, 0, "yyyyMMdd");
        
        for (var val : rateDtoList) {
        	if (!dateManager.isDatesEqual(startDate, dateManager.parseDateToString(val.getCode_date(), "yyyyMMdd"))) {
        		startDate = dateManager.parseDateToString(val.getCode_date(), "yyyyMMdd");
        	}
		}
		*/
    	
    	return "exchange/rateChart";
    }
    
}
