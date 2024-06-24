package ncbank.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ncbank.beans.ExchangeNoticeBean;
import ncbank.beans.ExchangeRateBean;
import ncbank.beans.UserBean;
import ncbank.service.ExchangeAutoNoticeService;
import ncbank.service.ExchangeNoticeService;
import ncbank.service.ExchangeRateService;
import ncbank.utility.DateManager;
import ncbank.utility.EmailManager;
import ncbank.utility.ExchangeRateDTO;


@Controller
@RequestMapping("/exchange")
public class ExchangeNoticController {

    @Autowired
    private ExchangeNoticeService exchangeNoticeService;
    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private ExchangeAutoNoticeService autoNoticeService;

    @Autowired
    private DateManager dateManager;
    @Autowired
    private EmailManager emailManager;

    @Resource(name = "loginUserBean")
    private UserBean loginUserBean;

    /* ==========[환율 알림]========== */
    // URL 패턴으로 구분
    // noticContentIndex - 1:안내 2:등록 3:변경 4:등록/변경 성공 5:삭제 성공

    // 기본 알림 페이지 - 알림 안내
    @GetMapping("/notice")
    public String notice(
    		@RequestParam(value="noticContentIndex", defaultValue="0") int noticContentIndex,
    		Model model) {
    	if (0 >= noticContentIndex) { // 예외상황 == 안내페이지로 1
    		noticContentIndex = 1;
    	}
    	
    	model.addAttribute("noticContentIndex", noticContentIndex);
    	
        return "exchange/notice";
    }

    // 알림 등록
    @GetMapping("noticeRegister")
    public String noticeRegister(
            @ModelAttribute("ExchangeNoticeBean") ExchangeNoticeBean exchangeNoticeBean,
            Model model) {
        if (null == loginUserBean || !loginUserBean.isUserLogin()) {
            return "user/not_login";
        }

        // 기존 등록된 알림이 있다면 변경 페이지로
        ExchangeNoticeBean noticeBean = exchangeNoticeService.getExchangeRateNotice(loginUserBean.getUser_num());

        if (null != noticeBean) { // 이 유저가 신청한 알림 데이터가 있다면 변경창으로
            return noiticeEdit(noticeBean, model);
        }

        return notice(2, model);
    }

    @PostMapping("noticeRegisterPro")
    public String noticeRegisterPro(
            @ModelAttribute("ExchangeNoticeBean") ExchangeNoticeBean exchangeNoticeBean,
            Model model) {
        if (null == loginUserBean || !loginUserBean.isUserLogin()) {
            return "user/not_login";
        }
        
        // 입력 오륲 임시 예외처리
        if (null == exchangeNoticeBean.getNotice_email() || exchangeNoticeBean.getNotice_email().isEmpty()
        		|| 0.0f >= exchangeNoticeBean.getNotice_rate()) {
        	return notice(2, model);
        }
        
        System.out.println("userNum : " + loginUserBean.getUser_num());
        exchangeNoticeBean.setUser_num(loginUserBean.getUser_num());
        exchangeNoticeBean
                .setNotice_date(dateManager.parseStringToDate(dateManager.getCurrentDate("yyyyMMdd"), "yyyyMMdd"));
        exchangeNoticeService.addExchangeRateNotice(exchangeNoticeBean);

        autoNoticeService.addExchangeAutoNotice(loginUserBean.getUser_num());

        return notice(4, model);
    }

    // 알림 변경
    @GetMapping("noticeEdit")
    public String noiticeEdit(
            @ModelAttribute("ExchangeNoticeBean") ExchangeNoticeBean exchangeNoticeBean,
            Model model) {
        if (null == loginUserBean || !loginUserBean.isUserLogin()) {
            return "user/not_login";
        }

        ExchangeNoticeBean noticeBean = exchangeNoticeService.getExchangeRateNotice(loginUserBean.getUser_num());
        model.addAttribute("ExchangeNoticeBean", noticeBean);

        return notice(3, model);
    }

    @PostMapping("noticeEditPro")
    public String noticeEditPro(
            @ModelAttribute("ExchangeNoticeBean") ExchangeNoticeBean exchangeNoticeBean,
            Model model) {
        if (null == loginUserBean || !loginUserBean.isUserLogin()) {
            return "user/not_login";
        }
        
        if (null == exchangeNoticeBean.getNotice_email() || exchangeNoticeBean.getNotice_email().isEmpty()
        		|| 0.0f >= exchangeNoticeBean.getNotice_rate()) {
        	return notice(3, model);
        }
        
        exchangeNoticeService.updateExchangeRateNotice(exchangeNoticeBean);
        // 환율 알림 변경 시 자동알림의 state 를 변경해 다시 알림을 받을 준비를 한다
        autoNoticeService.updateExchangeAutoNotice(0, loginUserBean.getUser_num());

        return noiticeRegisterEditSuccess(model);
    }

    // 알림 삭제
    @GetMapping("noticeDeletPro")
    public String noticeDeletPro(Model model) {
        if (null == loginUserBean || !loginUserBean.isUserLogin()) {
            return "user/not_login";
        }
        ExchangeNoticeBean noticeBean = exchangeNoticeService.getExchangeRateNotice(loginUserBean.getUser_num());
        model.addAttribute("ExchangeNoticeBean", noticeBean);
        
        // 환율 알림 삭제 시 해당 자동알림 정보도 삭제 (fk조건때문에 먼저삭제?)
        autoNoticeService.deleteExchangeAutoNotice(loginUserBean.getUser_num());
        
        exchangeNoticeService.deleteExchangeRateNotice(loginUserBean.getUser_num());


        return noticeDeletSuccess(model);
    }

    // success
    @GetMapping("noticeRegisterEditSuccess")
    public String noiticeRegisterEditSuccess(Model model) {
		if (null == loginUserBean || !loginUserBean.isUserLogin()) {
			return "user/not_login";
		}

        return notice(4, model);
    }

    @GetMapping("noticeDeletSuccess")
    public String noticeDeletSuccess(Model model) {
        if (null == loginUserBean || !loginUserBean.isUserLogin()) {
            return "user/not_login";
        }

        return notice(5, model);
    }
    
    // 입력오류 - 마저해야됨
    @GetMapping("noticeInputError")
    public String noticeInputError(
    		@RequestParam(value="noticContentIndex", defaultValue="0") int noticContentIndex,
    		Model model) {
        if (null == loginUserBean || !loginUserBean.isUserLogin()) {
            return "user/not_login";
        }
        
        model.addAttribute("noticContentIndex", noticContentIndex);
        
        return "exchange/noticeInputError";
    }
    
    // 메일전송
    @GetMapping("sendNoticeMail")
    // Spring MVC에서 자동으로 HttpServletRequest와 HttpServletResponse 객체를 컨트롤러 메서드에 주입
    public String sendNoticeMail(HttpServletRequest request, HttpServletResponse response) {

        if (null == loginUserBean && !loginUserBean.isUserLogin()) {
            return "user/not_login";
        }
        /* jsp 내용 메일 */
        ExchangeNoticeBean exchangeNoticeBean = exchangeNoticeService
                .getExchangeRateNotice(loginUserBean.getUser_num());
        System.out.println("exchangeNoticeBean : " + exchangeNoticeBean);
        // 신청한 알림정보

        // Bean으로 빼보기
        String noticeDate = dateManager.parseDateToString(exchangeNoticeBean.getNotice_date(), "yyyy.MM.dd");

        request.setAttribute("ExchangeNoticeBean", exchangeNoticeBean);
        request.setAttribute("noticeDate", noticeDate);

        // 신청한 통화의 최근고지환율정보
        ExchangeRateBean rateBean = exchangeRateService.findFinalExchangeRate(exchangeNoticeBean.getCode_money());
        ExchangeRateDTO rateDTO = exchangeRateService.convertExchangeDTO(rateBean);
        String inquiryDate = dateManager.parseDateToString(rateDTO.getCode_date(), "yyyy.MM.dd");

        request.setAttribute("ExchangeRateBean", rateDTO);
        request.setAttribute("inquiryDate", inquiryDate);

        /* 메일전송 */
        // 메일 보내는 기준 잡아야됨 -> Intercepter 에서?
        String rootPath = request.getServletContext().getRealPath("/");
        // inlinePath : 인라인 이미지 이름, imgPath : 실제 이미지 경로
        Map<String, String> inlinePathImgs = new HashMap<String, String>();

        inlinePathImgs.put("<NCBankIcon>", rootPath + "resources/img/NCBankIcon_2.png");

        emailManager.sendJspEmailWithInlineImage("jcjhjg12@gmail.com", "인라인 이미지 메일 테스트",
                "/WEB-INF/views/exchange/sendNoticeMail.jsp", inlinePathImgs, request, response);

        return "exchange/rateInquiry";
    }
    
    @GetMapping("sendNoticeMailView")
    // Spring MVC에서 자동으로 HttpServletRequest와 HttpServletResponse 객체를 컨트롤러 메서드에 주입
    public String sendNoticeMailView(HttpServletRequest request, HttpServletResponse response) {
        if (null == loginUserBean && !loginUserBean.isUserLogin()) {
            return "user/not_login";
        }
        System.out.println("sendNoticeMailView()");
        /* jsp 내용 메일 */
        ExchangeNoticeBean exchangeNoticeBean = exchangeNoticeService
                .getExchangeRateNotice(loginUserBean.getUser_num());
        if (null == exchangeNoticeBean) {
        	System.out.println("exchangeNoticeBean is null");
        	return "exchange/sendNoticeMail";
        }
        
        // 신청한 알림정보

        // Bean으로 빼보기
        String noticeDate = dateManager.parseDateToString(exchangeNoticeBean.getNotice_date(), "yyyy.MM.dd");

        request.setAttribute("ExchangeNoticeBean", exchangeNoticeBean);
        request.setAttribute("noticeDate", noticeDate);

        // 신청한 통화의 최근고지환율정보
        ExchangeRateBean rateBean = exchangeRateService.findFinalExchangeRate(exchangeNoticeBean.getCode_money());
        ExchangeRateDTO rateDTO = exchangeRateService.convertExchangeDTO(rateBean);
        String inquiryDate = dateManager.parseDateToString(rateDTO.getCode_date(), "yyyy.MM.dd");

        request.setAttribute("ExchangeRateBean", rateDTO);
        request.setAttribute("inquiryDate", inquiryDate);
        
        request.setAttribute("NCBankIcon2", "img/NCBankIcon_2.png");

        return "exchange/sendNoticeMail";
    }

} // class
