package ncbank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session, Model model) {
		String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
        if (redirectUrl != null) {
            model.addAttribute("redirectAfterLogin", redirectUrl);
            session.removeAttribute("redirectAfterLogin");
            System.out.println("Redirect URL found in session: " + redirectUrl); // 디버깅 메시지
        }
//      return "index"; //WEB-INF/views/index.jsp - configureViewResolvers
		return "redirect:main"; // redirect: -> 주소매핑(주소재요청) get방식

	}

}
