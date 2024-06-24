package ncbank.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import ncbank.beans.UserBean;
import ncbank.service.TopMenuService;

/*Controller가 아닌 Interceptor에서 내려가는 경우도 있음(Service는 생략가능 , serivce에 있는것을 interceptor화(servlet에 등록))*/
public class TopMenuInterceptor implements HandlerInterceptor {

	// Interceptor는 Autowired 를 막아줘야 한다 => servlet 에 등록시 Autowired 충돌문에?
	// @Autowired : Interceptor 처리된 클래스는 Autowired 사용 불가능
	public TopMenuService topMenuService;

	public TopMenuInterceptor(TopMenuService _topMenuService) {
		topMenuService = _topMenuService;
	}

	// preHandle 무언가 일어나기 전 시점
//	@Autowired : Interceptor 처리된 클래스에서는 오토와이어드를 사용할 수 없음
	private UserBean loginUserBean;

	// DIS
	public TopMenuInterceptor(TopMenuService topMenuService, UserBean loginUserBean) {
		this.topMenuService = topMenuService;
		this.loginUserBean = loginUserBean;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// List<BoardInfoBean> topMenuList = topMenuService.getTopMenuList();
		// request.setAttribute("topMenuList", topMenuList);
		request.setAttribute("loginUserBean", loginUserBean); // idx랑 name
		return true;
	}

}
