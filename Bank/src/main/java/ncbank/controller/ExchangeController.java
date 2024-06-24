package ncbank.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ncbank.beans.AccountBean;
import ncbank.beans.CodeBankBean;
import ncbank.beans.CreateExchangeBean;
import ncbank.beans.ExchangeBean;
import ncbank.beans.UserBean;
import ncbank.service.ExchangeService;

@Controller
@RequestMapping("/exchange")
public class ExchangeController {
    
    @Autowired
    private ExchangeService exchangeService;
    
    @Resource(name="loginUserBean")
    UserBean loginUserBean;
    
    
    @GetMapping("/exchangeAsk")
    public String getExchangeList(@ModelAttribute("createExchangeBean") CreateExchangeBean createExchangeBean,
                                  Model model, HttpSession session) throws ParseException {
    	
        UserBean loginUserBean = (UserBean) session.getAttribute("loginUserBean");
        if (loginUserBean != null && loginUserBean.isUserLogin()) {
            int user_num = loginUserBean.getUser_num();
            System.out.println("loginUserBean.getUser_num(): " + user_num);

            // 현재 날짜를 나타내는 Date 객체 생성
            Date d = new Date();
            // 날짜를 특정 형식으로 포맷하기 위한 SimpleDateFormat 객체 생성
            SimpleDateFormat cuDate1 = new SimpleDateFormat("yy/MM/dd");
            // 현재 날짜를 나타내는 Calendar 객체 생성
            Calendar c = Calendar.getInstance();
            // 연, 월, 일 값 추출
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;  // Calendar.MONTH에 1을 더함
            int day = c.get(Calendar.DAY_OF_MONTH);
            // 연, 월, 일을 두 자리수 형식으로 포맷팅하여 문자열로 변환
            String cuDate = String.format("%02d/%02d/%02d", year % 100, month, day);

            // 문자열로 된 날짜를 Date 객체로 파싱
            Date date = cuDate1.parse(cuDate);

            // api 데이터 exchange에 저장된거 가져오기.
            List<ExchangeBean> exchangeAskList = exchangeService.getExchangeList(cuDate);
            // code_bank - code_bank_name 엮은 전체 데이터 가져오기
            List<CodeBankBean> codeBankNameList = exchangeService.getCodeBankName();
            // user_name이랑 account 데이터 가져오기
            List<AccountBean> getAccountList = exchangeService.getAccount(user_num);
            
            // 밑에 건 환전신청 값를 기존 Wallet 테이블에 추가 또는 업뎃하는 것. 즉, trade 테이블에 미리 넣어놓은 데이터는 아직 Wallet에 없다.
            // 오라클에 trade에 미리 넣어놓은 값이 있을 거다. 그거를 Wallet에 넣어놓는다.
            exchangeService.setCheckAndInsertFirstWalletData();
            

            model.addAttribute("exchangeAskList", exchangeAskList);
            model.addAttribute("codeBankNameList", codeBankNameList);
            model.addAttribute("getAccountList", getAccountList);
            model.addAttribute("user_num", user_num);
        } else {
            //System.out.println("User not logged in or session expired.");
        	
            return "exchange/exchangeAllNotLogin";
        }

        return "exchange/exchangeAsk";
    }
	
	@PostMapping("/exchangeAskSuccess")
	public String getExchangeAskSuccess(@ModelAttribute("createExchangeBean") CreateExchangeBean createExchangeBean, Model model) {
	    // 가져온 createExchangeBean Test
	    // System.out.println("createExchangeBeancode_bank_name: " + createExchangeBean.getCode_bank_name());
	    // System.out.println("createExchangeBean.code_bank: " + createExchangeBean.getCode_bank());
	    // System.out.println("createExchangeBean.user_num: " + createExchangeBean.getUser_num());
	    
		//여기서 Trade 에 INSERT 하는 것과 Wallet에 보내는 거 동시 실행
	    exchangeService.setTradeByExchangeAsk(createExchangeBean);
	    
	    model.addAttribute("createExchangeBean", createExchangeBean);
	    return "exchange/exchangeAskSuccess";
	}
	
	
	
    
}
