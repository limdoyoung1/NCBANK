package ncbank.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import ncbank.beans.ContentBean;
import ncbank.beans.UserBean;
import ncbank.service.BoardService;

public class CheckUserInterceptor implements HandlerInterceptor {

	private UserBean loginUserBean;
	private BoardService boardService;

	public CheckUserInterceptor(UserBean loginUserBean, BoardService boardService) {
		this.loginUserBean = loginUserBean;
		this.boardService = boardService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String str1 = request.getParameter("content_idx"); // content_idx = String
		int content_idx = Integer.parseInt(str1);

		ContentBean currentContentBean = boardService.getContentInfo(content_idx);

		if (currentContentBean.getContent_writer_idx() != loginUserBean.getUser_num()) {
			// 경로를 읽어서
			String contextPath = request.getContextPath();
			// not_writer 호출
			response.sendRedirect(contextPath + "/board/not_writer");

			return false;
		}
		return true;

	}

}
