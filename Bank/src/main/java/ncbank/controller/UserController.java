package ncbank.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ncbank.beans.UserBean;
import ncbank.dao.UserDAO;
import ncbank.service.UserService;
import ncbank.util.Encrypt;
import ncbank.validator.UserValidator;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private Encrypt encrypt;

	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

	@Autowired
	private UserDAO userDAO;

	@GetMapping("/login")
	public String login(@ModelAttribute("tempLoginBean") UserBean tempLoginBean,
			@RequestParam(value = "fail", defaultValue = "false") boolean fail, Model model) {
		model.addAttribute("fail", fail);
		return "user/login";
	}

	@PostMapping("/login_pro")
	public String login_pro(@Valid @ModelAttribute("tempLoginBean") UserBean tempLoginBean, HttpServletRequest request,
			Model model, BindingResult result) {
		System.out.println("What't wrong?");
		System.out.println(tempLoginBean);
		System.out.println(request);

		if (result.hasErrors()) {

			return "user/login";
		}

		userService.getLoginUserInfo(request, tempLoginBean);

		if (loginUserBean.isUserLogin()) {
			String redirectUrl = (String) request.getSession().getAttribute("redirectAfterLogin");
			if (redirectUrl != null) {
				request.getSession().removeAttribute("redirectAfterLogin");
				System.out.println("Redirect URL found and set in session: " + redirectUrl); // 디버깅 메시지
				return "redirect:/" + redirectUrl; // 리다이렉트 URL로 이동
			}
			return "user/login_success";
		} else {
			model.addAttribute("fail", true);
			return "user/login_fail";
		}
	}

	@GetMapping("/join")
	public String join(@ModelAttribute("mBean") UserBean mBean) {
		return "user/join";
	}

	@PostMapping("/join_pro")
	public String join_pro(@Valid @ModelAttribute("mBean") UserBean mBean, BindingResult memberResult, Model model) {

		mBean.setResident(mBean.getResident1() + "-" + mBean.getResident2());

		if (memberResult.hasErrors()) {
			memberResult.getFieldErrorCount();
			List<ObjectError> temp = memberResult.getAllErrors();
			for (ObjectError e : temp) {
				System.out.println(e.getDefaultMessage());
			}
			return "user/join";
		}

		if (!userService.canRegister(mBean.getPhone(), mBean.getResident())) {
			model.addAttribute("errorMessage", "이미 가입되어 있는 전화번호나 주민번호가 존재합니다");
			return "user/join";
		}

		String address = (mBean.getAdd2() != null && !mBean.getAdd2().isEmpty()) ? mBean.getAdd2() : mBean.getAdd3();
		mBean.setAddress(address);

		userService.addUserInfo(mBean);

		return "user/join_success";
	}

	@GetMapping("/findID")
	public String findID(@ModelAttribute("findMemberIDBean") UserBean findMemberIDBean) {

		return "user/findID";
	}

	@PostMapping("findID_pro")
	public String findID_pro(@ModelAttribute("findMemberIDBean") UserBean findMemberIDBean, BindingResult result,
			Model model) {
		String checkedID = userDAO.findMemberId(findMemberIDBean);
		System.out.println("checkedID : " + checkedID);

		if (checkedID == null) {
			model.addAttribute("message", "아이디를 찾을 수 없습니다.");
			return "user/findID";
		}

		findMemberIDBean.setId(checkedID); // 저장
		model.addAttribute("id", checkedID);
		return "user/findID_success";
	}

	/*
	 * @GetMapping("findpwd") public String
	 * findpwd(@ModelAttribute("findMemberPwdBean") UserBean findMemberPwdBean) {
	 * return "user/findpwd"; }
	 * 
	 * @PostMapping("/findpwd_pro") public String
	 * findpwd_pro(@ModelAttribute("findMemberPwdBean") UserBean findMemberPwdBean,
	 * BindingResult result, Model model) { if (result.hasErrors()) { return
	 * "user/findPWD"; } userService.findMemberPwd(findMemberPwdBean); return
	 * "user/findpwd_success"; }
	 */

	@GetMapping("/mypage")
	public String myPage(Model model, HttpSession session) {
		// 세션에서 로그인된 사용자 정보 가져오기
		UserBean loginUserBean = (UserBean) session.getAttribute("loginUserBean");

		if (loginUserBean == null) {
			return "redirect:/login"; // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
		}

		// 사용자 정보 조회
		int userNum = loginUserBean.getUser_num();
		UserBean user = userService.getUserInfo(userNum);

		// 모델에 사용자 정보 추가
		model.addAttribute("user", user);

		return "user/mypage";
	}

	/*
	 * @PostMapping("/modify_pro") public String
	 * modify_pro(@RequestParam("password") String
	 * password, @ModelAttribute("users") UserBean userBean, HttpServletRequest
	 * request, Model model) { UserBean loginUserBean = (UserBean)
	 * request.getSession().getAttribute("loginUserBean"); if (loginUserBean ==
	 * null) { return "user/mypage"; } UserBean tempLoginUserBean = new UserBean();
	 * tempLoginUserBean.setId(loginUserBean.getId());
	 * tempLoginUserBean.setPwd(password); userService.getLoginUserInfo(request,
	 * tempLoginUserBean); if (loginUserBean.isUserLogin()) { // 비밀번호가 일치하면 업데이트 로직
	 * 수행 userService.updateUserInfo(userBean); return "user/mypage"; } else {
	 * model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다."); return
	 * "redirect:/main"; }
	 * 
	 * }
	 */
	@GetMapping("/logout")
	public String logout() {
		loginUserBean.setUserLogin(false);
		return "user/logout";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		UserValidator validator1 = new UserValidator();
		binder.addValidators(validator1);
	}

	// 문자인증
	@PostMapping("/memberPhoneCheck")
	public @ResponseBody String memberPhoneCheck(@RequestParam(value = "to") String to) {
		System.out.println("------------------------------");
		String code = String.format("%06d", (int) (Math.random() * 900000));
		String text = ("[NC BANK] " + code);
		System.out.println(text);
		return code;
//      실제로 쓸때는 위에꺼 주석 처리하고 밑에꺼 써야댐
//		String vr = userService.verificationCode(to);
//		return vr;
	}
}
