package ncbank.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ncbank.beans.ContentBean;
import ncbank.beans.PageBean;
import ncbank.beans.UserBean;
import ncbank.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Resource(name = "loginUserBean")
	private UserBean loginUserbean;

//	@ModelAttribute("boardInfoList")
//	public List<BoardInfoList> boardInfoList(){
//		return boardService.getAllBoardInfo();
//	}
//	

	@GetMapping("/main")
	public String main(@RequestParam("board_info_idx") int board_info_idx,
			@RequestParam(value = "page", defaultValue = "1") int page, Model model) {

		model.addAttribute("board_info_idx", board_info_idx);
		String boardInfoName = boardService.getBoardInfoName(board_info_idx);
		model.addAttribute("boardInfoName", boardInfoName);

		List<ContentBean> contentList = boardService.getContentList(board_info_idx, page);
		model.addAttribute("contentList", contentList);

		PageBean pageBean = boardService.getContentCnt(board_info_idx, page);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("page", page);

		model.addAttribute("loginUserId", loginUserbean.getId());

//		System.out.println(loginUserbean.getId());

		return "board/main";
	}

	@GetMapping("/read")
	public String read(@RequestParam("board_info_idx") int board_info_idx, @RequestParam("content_idx") int content_idx,
			@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		model.addAttribute("board_info_idx", board_info_idx);
		model.addAttribute("content_idx", content_idx);
		model.addAttribute("loginUserBean", loginUserbean);
		/*
		 * model.addAttribute("loginUserBean", loginU serBean); 아마 글 수정삭제 때문에 필요한거 같으넫
		 * 우리한텐 필요 없을듯?
		 */

		ContentBean readContentBean = boardService.getContentInfo(content_idx);
		model.addAttribute("readContentBean", readContentBean);
		model.addAttribute("page", page);

		return "board/read";
	}

	@GetMapping("/write")
	public String write(@ModelAttribute("writeContentBean") ContentBean writeContentBean,
			@RequestParam("board_info_idx") int board_info_idx) {

		writeContentBean.setContent_board_idx(board_info_idx);

		return "board/write";
	}

	@PostMapping("/writer_pro")
	public String writer_pro(@Valid @ModelAttribute("writeContentBean") ContentBean writeContentBean,
			BindingResult result) {

		if (result.hasErrors()) {
			System.out.println("유효성 검사 에러 발생:");

			// 각 필드별 에러 메시지 출력
			for (FieldError error : result.getFieldErrors()) {
				System.out.println(error.getField() + " - " + error.getDefaultMessage());
			}
			return "board/write";
		}

		boardService.addContentInfo(writeContentBean);

		return "board/write_success";
	}

}
