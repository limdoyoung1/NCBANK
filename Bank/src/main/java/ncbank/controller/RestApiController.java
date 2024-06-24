package ncbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ncbank.service.UserService;

// 크롤링을 해서 얻어온 데이터를 Service에게 전달
// Rest : 비동기식 - 크롤링 해서 가져온 데이터를 이런식으로 사용
// 데이터를 바로 화면에 뿌려야한다 -> Rest 로 사용해야 함
@RestController
public class RestApiController {

	@Autowired
	private UserService userService;

	// 해당 경로로 가서 데이터를 가져와라.
	@GetMapping("/user/checkUserIdExist/{id}") // "/user/checkUserIdExist/?{user_id}" 와 같음
	public String checkUserIdExist(@PathVariable String id) {
		// @PathVariable : 주소에 직접 데이터 붙이기

		boolean check = userService.checkUserExist(id);

		// return check + "";
		// valueOf() : String 반환 근데 null 이면 "null" 문자열로 처리
		return String.valueOf(check); // Servlet 에서 jsp로 가게 설정해놓음
	}

}
