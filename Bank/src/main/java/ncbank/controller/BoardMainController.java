package ncbank.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ncbank.beans.BoardInfoBean;
import ncbank.beans.ContentBean;
import ncbank.service.BoardMainSerivce;
import ncbank.service.TopMenuService;

@Controller
public class BoardMainController {
	
	@Autowired
	private BoardMainSerivce boardMainSerivce;
	
	@Autowired
	private TopMenuService topMenuService;
	
	@GetMapping("/main")
	public String main(Model model) {
		
		ArrayList<List<ContentBean>> list = new ArrayList<List<ContentBean>>();
		
		for(int i=1; i<5; i++) {
			List<ContentBean> list1 = boardMainSerivce.getMainList(i);
			list.add(list1);
		}
		
		model.addAttribute("list", list);
		
		List<BoardInfoBean> board_list = topMenuService.getTopMenuList(); //게시판의 번호와 이름을 가져옴
		model.addAttribute("board_list", board_list);
		
		return "main";
	}

}

