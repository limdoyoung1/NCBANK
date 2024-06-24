//package ncbank.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import ncbank.service.ChatGptService;
//
//@Controller
//public class ChatController {
//	@Autowired
//	private ChatGptService chatGptService;
//
//	@GetMapping("/chat")
//	public String chat(@RequestParam(name = "prompt", required = false) String prompt, Model model) {
//		if (prompt != null && !prompt.isEmpty()) {
//			String response = chatGptService.getChatGptResponse(prompt);
//			model.addAttribute("response", response);
//		}
//		return "chat";
//	}
//}
