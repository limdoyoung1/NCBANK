package ncbank.controller;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import ncbank.beans.AccountBean;
import ncbank.beans.AutoBean;
import ncbank.beans.GroupAccountBean;
import ncbank.beans.TransferBean;
import ncbank.beans.UserBean;
import ncbank.mapper.GroupAccountMapper;
import ncbank.service.GroupAccountService;

@Controller
@RequestMapping("/groupAccount")
public class GroupAccountController {

	@Autowired
	private GroupAccountService groupAccountService;

	@Autowired
	private GroupAccountMapper groupAccountMapper;

	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

	@GetMapping("/create")
	public String create(Model model) {
		if (loginUserBean.isUserLogin()) {
			List<AccountBean> accountList = groupAccountService.getAccountsByUserNum(loginUserBean.getUser_num());
			model.addAttribute("accountList", accountList);
		} else {
			model.addAttribute("accountList", null);
		}
		return "groupAccount/groupAccountCreate";
	}

	@PostMapping("/groupAccountOpened")
	public String opened(@RequestParam String groupname, @RequestParam String accounts, Model model) {
		model.addAttribute("groupname", groupname);
		model.addAttribute("accounts", accounts);

		String accountNumber = accounts.replaceAll("\\[.*\\]", "").trim();
		List<AutoBean> accountInfoList = groupAccountService.infoList(accountNumber, loginUserBean.getUser_num());
		if (!accountInfoList.isEmpty()) {
			model.addAttribute("accountInfo", accountInfoList.get(0));
		}

		return "groupAccount/groupAccountOpened";
	}

	@PostMapping("/groupAccountOpenedNext")
	public String validatePassword(@RequestParam String accounts, @RequestParam String inputPassword,
			@RequestParam String groupname, @RequestParam String auto_next_date, @RequestParam String auto_type,
			@RequestParam String auto_money, Model model) {
		String accountNumber = accounts.replaceAll("\\[.*\\]", "").trim();
		AccountBean accountBean = groupAccountService.getAccountByAccountNumber(accountNumber);

		if (accountBean != null && accountBean.getAc_password().equals(inputPassword)) {
			model.addAttribute("groupname", groupname);
			model.addAttribute("accounts", accounts);
			model.addAttribute("auto_next_date", auto_next_date);
			model.addAttribute("auto_type", auto_type);
			model.addAttribute("auto_money", auto_money);

			List<AutoBean> accountInfoList = groupAccountService.infoList(accountNumber, loginUserBean.getUser_num());
			if (!accountInfoList.isEmpty()) {
				model.addAttribute("accountInfo", accountInfoList.get(0));
			}

			model.addAttribute("applicationDate",
					new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
			return "groupAccount/groupAccountOpenedNext";
		} else {
			model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
			model.addAttribute("groupname", groupname);
			model.addAttribute("accounts", accounts);
			model.addAttribute("auto_next_date", auto_next_date);
			model.addAttribute("auto_type", auto_type);
			model.addAttribute("auto_money", auto_money);

			return "groupAccount/groupAccountOpened";
		}
	}

	@PostMapping("/complete")
	public String complete(@RequestParam String accounts, @RequestParam String groupname,
			@RequestParam String inputPassword, @RequestParam String auto_next_date, @RequestParam String auto_type,
			@RequestParam String auto_money, Model model) {
		String accountNumber = accounts.replaceAll("\\[.*\\]", "").trim();
		AccountBean accountBean = groupAccountService.getAccountByAccountNumber(accountNumber);

		if (accountBean != null && accountBean.getAc_password().equals(inputPassword)) {
			model.addAttribute("groupname", groupname);
			model.addAttribute("accounts", accounts);

			List<AutoBean> accountInfoList = groupAccountService.infoList(accountNumber, loginUserBean.getUser_num());
			if (!accountInfoList.isEmpty()) {
				model.addAttribute("accountInfo", accountInfoList.get(0));
			}

			model.addAttribute("applicationDate",
					new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));

			// 그룹 정보 저장
			GroupAccountBean groupAccount = new GroupAccountBean();
			groupAccount.setUser_num(loginUserBean.getUser_num());
			groupAccount.setGroup_joindate(new java.sql.Date(System.currentTimeMillis()));
			groupAccount.setGroup_leader(String.valueOf(loginUserBean.getUser_num()));
			groupAccount.setGroup_name(groupname);

			try {
				groupAccountService.createGroupAccount(groupAccount);
				groupAccountService.updateAccountType(accountNumber);
			} catch (SQLException e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", "그룹 정보 저장 중 오류가 발생했습니다.");
				return "groupAccount/groupAccountOpenedNext";
			}

			Integer group_num = groupAccountService.getLatestGroupNumByUser(loginUserBean.getUser_num());
			if (group_num != null) {
				GroupAccountBean groupInfo = groupAccountService.getGroupInfo(group_num);
				model.addAttribute("groupInfo", groupInfo);
			} else {
				model.addAttribute("errorMessage", "그룹 번호를 가져오는 중 오류가 발생했습니다.");
				return "groupAccount/groupAccountOpenedNext";
			}

			return "groupAccount/groupAccountComplete";
		} else {
			model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
			model.addAttribute("groupname", groupname);
			model.addAttribute("accounts", accounts);
			model.addAttribute("auto_next_date", auto_next_date);
			model.addAttribute("auto_type", auto_type);
			model.addAttribute("auto_money", auto_money);
			return "groupAccount/groupAccountOpenedNext";
		}
	}

	@PostMapping("/getGroupInfo")
	@ResponseBody
	public GroupAccountBean getGroupInfo(@RequestParam("group_num") int group_num) {
		return groupAccountService.getGroupInfo(group_num);
	}

	@GetMapping("/acceptInvite")
	public String showAcceptInvitePage(@RequestParam("group_num") int group_num, Model model) {
		model.addAttribute("group_num", group_num);
		System.out.println("group_num: " + group_num); // 디버깅 로그 추가
		return "groupAccount/groupAccountAcceptInvite";
	}

	@PostMapping("/acceptInvite")
	public String acceptInvite(@RequestParam int group_num, @RequestParam String action, Model model,
			HttpSession session) {
		if (!loginUserBean.isUserLogin()) {
			session.setAttribute("notLoginMessage", "로그인 해주세요.");
			session.setAttribute("redirectAfterLogin",
					"groupAccount/acceptInvite?group_num=" + group_num + "&action=" + action);
			System.out.println("Redirect URL set in session: " + "groupAccount/acceptInvite?group_num=" + group_num
					+ "&action=" + action);
			return "groupAccount/showAlert";
		}

		return handleAcceptInvite(group_num, action, model);
	}

	@GetMapping("/acceptInviteAction")
	public String handleAcceptInvite(@RequestParam int group_num, @RequestParam String action, Model model) {
		if (action.equals("accept")) {
			GroupAccountBean existingGroupAccount = groupAccountService.getGroupInfo(group_num);
			if (existingGroupAccount == null) {
				model.addAttribute("errorMessage", "그룹 정보를 가져오는 중 오류가 발생했습니다.");
				return "groupAccount/groupAccountAcceptInvite";
			}

			GroupAccountBean newMember = new GroupAccountBean();
			newMember.setGroup_num(group_num);
			newMember.setUser_num(loginUserBean.getUser_num());
			newMember.setGroup_joindate(new java.sql.Date(System.currentTimeMillis()));
			newMember.setGroup_leader("0");
			newMember.setGroup_name(existingGroupAccount.getGroup_name());

			try {
				groupAccountService.addMemberToGroup(newMember);
			} catch (SQLException e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", "초대 수락 중 오류가 발생했습니다.");
				return "groupAccount/groupAccountAcceptInvite";
			}

			model.addAttribute("message", "초대를 수락하였습니다.");
			return "groupAccount/groupAccountAcceptSuccess";
		} else {
			model.addAttribute("message", "초대를 거절하였습니다.");
			return "groupAccount/groupAccountAcceptInvite";
		}
	}

	@GetMapping("/management")
	public String management(Model model) {
		if (loginUserBean.isUserLogin()) {
			List<AccountBean> groupAccountList = groupAccountService
					.selectGroupAccountsByUserNum(loginUserBean.getUser_num());
			model.addAttribute("groupAccountList", groupAccountList);
		} else {
			model.addAttribute("groupAccountList", null);
		}
		return "groupAccount/groupAccountManagement";
	}

	@GetMapping("/members")
	public String members(Model model) {
		if (loginUserBean.isUserLogin()) {
			List<AccountBean> groupAccountList = groupAccountService
					.selectGroupAccountsByUserNum(loginUserBean.getUser_num());
			model.addAttribute("groupAccountList", groupAccountList);
		} else {
			model.addAttribute("groupAccountList", null);
		}
		return "groupAccount/groupAccountMembers";
	}

	@GetMapping("/groupAccountInfo")
	@ResponseBody
	public AutoBean getGroupAccountInfo(@RequestParam("account") String account) {
		return groupAccountService.getAutoInfo(account);
	}

	@GetMapping("/groupInfo")
	@ResponseBody
	public GroupAccountBean getGroupInfo(@RequestParam("account") String account) {
		GroupAccountBean groupInfo = groupAccountService.getGroupInfobyAcc(account);
		if (groupInfo != null) {
			groupInfo.setGroup_num(groupAccountService.getGroupNumByAccount(account));
		}
		return groupInfo;
	}

	@GetMapping("/groupMembers")
	@ResponseBody
	public List<UserBean> getGroupMembers(@RequestParam("account") String account) {
		return groupAccountService.getGroupMembers(loginUserBean.getUser_num());
	}

	@GetMapping("/groupMemberDetails")
	@ResponseBody
	public List<GroupAccountBean> getGroupMemberDetails(@RequestParam("account") String account) {
		return groupAccountService.getGroupMemberDetails(loginUserBean.getUser_num());
	}

	@GetMapping(value = "/groupLeader", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String getGroupLeader(@RequestParam("account") String account) {
		UserBean groupLeaderName = groupAccountService.getGroupLeaderNameByAccount(account);
		System.out.println("Controller groupLeaderName = " + groupLeaderName);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(groupLeaderName);
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"error\": \"Failed to convert object to JSON\"}";
		}
	}

	@GetMapping("/totalBalance")
	@ResponseBody
	public AccountBean totalBalance(@RequestParam("account") String account) {
		AccountBean totalBalance = groupAccountService.totalBalance(account);
		System.out.println("Controller totalBalance = " + totalBalance);
		return totalBalance;
	}

	@GetMapping("/about")
	public String about() {
		
		return "groupAccount/groupAccountAbout";
	}

	@GetMapping("/book")
	public String book(Model model) {
		if (loginUserBean.isUserLogin()) {
			List<AccountBean> groupAccountList = groupAccountService
					.selectGroupAccountsByUserNum(loginUserBean.getUser_num());
			model.addAttribute("groupAccountList", groupAccountList);
		} else {
			model.addAttribute("groupAccountList", null);
		}
		return "groupAccount/groupAccountBook";
	}

	@PostMapping("/deleteMember")
	@ResponseBody
	public ResponseEntity<?> deleteMember(@RequestParam("user_num") int userNum) {
	    try {
	        System.out.println("Received request to delete member with userNum: " + userNum); // 디버그용 로그
	        groupAccountService.deleteMember(userNum);
	        System.out.println("Member deletion successful"); // 디버그용 로그
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        System.out.println("Error during member deletion: " + e.getMessage()); // 디버그용 로그
	        return ResponseEntity.status(500).body("회원 삭제 중 오류 발생");
	    }
	}
	
	 @GetMapping("/getAccountTransfers")
	    @ResponseBody
	    public List<TransferBean> getAccountTransfers(@RequestParam("account") String account) {
	        return groupAccountService.getAccountTransfers(account);
	    }

	 @GetMapping("/delete")
	 public String deleteGroup(@RequestParam("group_num") int group_num, Model model) {
	     GroupAccountBean groupAccount = groupAccountService.getGroupInfo(group_num);
	     if (groupAccount != null && groupAccount.getGroup_leader().equals("1") && groupAccount.getUser_num() == loginUserBean.getUser_num()) {
	         try {
	             String accountNumber = groupAccountService.getAccountNumberByGroupNum(group_num);
	             groupAccountService.deleteGroup(group_num, accountNumber);
	             model.addAttribute("message", "모임이 성공적으로 해체되었습니다.");
	             return "groupAccount/groupAccountDeleted"; // 성공 메시지를 보여주는 JSP로 리디렉션
	         } catch (SQLException e) {
	             e.printStackTrace();
	             model.addAttribute("errorMessage", "모임 해체 중 오류가 발생했습니다.");
	             return "groupAccount/groupAccountManagement"; // 오류 메시지를 보여주는 JSP로 리디렉션
	         }
	     } else {
	         model.addAttribute("errorMessage", "모임 해체 권한이 없습니다.");
	         return "groupAccount/groupAccountManagement"; // 오류 메시지를 보여주는 JSP로 리디렉션
	     }
	 }
	 
}
