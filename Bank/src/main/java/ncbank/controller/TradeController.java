package ncbank.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ncbank.beans.CrerateTradeBean;
import ncbank.beans.PageBean;
import ncbank.beans.UserBean;
import ncbank.beans.WalletBean;
import ncbank.service.TradeService;

@Controller
@RequestMapping("/exchange")
public class TradeController {

    @Autowired
    private TradeService tradeService;
    
    @Resource(name="loginUserBean")
    UserBean loginUserBean;
    
    @GetMapping("/exchangeHistory")
    public String getTradeList(
            @RequestParam(value="page", defaultValue="1") int page,
            Model model, HttpSession session) {

        UserBean loginUserBean = (UserBean) session.getAttribute("loginUserBean");
        if (loginUserBean != null && loginUserBean.isUserLogin()) {
            int contentPageCnt = 10;  // 페이지당 항목 수
            int paginationCnt = 5;    // 페이지 버튼 수

            List<CrerateTradeBean> tradePlusList = tradeService.getTradePlusList(loginUserBean.getUser_num(), page, contentPageCnt);
            int totalCnt = tradeService.getTradeCount(loginUserBean.getUser_num());
            
            // 페이지
            PageBean pageBean = new PageBean(totalCnt, page, contentPageCnt, paginationCnt);

            model.addAttribute("tradePlusList", tradePlusList);
            model.addAttribute("pageBean", pageBean);
            model.addAttribute("page", page);
        } else {
            return "exchange/exchangeAllNotLogin";
        }

        return "exchange/exchangeHistory";
    }
    
    @GetMapping("/exchangeWallet")
    public String getWalletList(Model model, HttpSession session) {
        UserBean loginUserBean = (UserBean) session.getAttribute("loginUserBean");
        if (loginUserBean != null && loginUserBean.isUserLogin()) {
            int user_num = loginUserBean.getUser_num();
            List<WalletBean> walletAllList = tradeService.getWalletAllList(user_num);
            model.addAttribute("walletAllList", walletAllList);
        } else {
            return "exchange/exchangeAllNotLogin";
        }

        return "exchange/exchangeWallet";
    }
    
    
}
