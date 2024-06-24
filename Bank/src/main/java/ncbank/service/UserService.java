package ncbank.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ncbank.beans.UserBean;
import ncbank.dao.UserDAO;
import ncbank.mapper.UserMapper;
import ncbank.util.Encrypt;
import ncbank.util.SmsSender;

// 서비스를 받는다 - 데이터를 가져와서 가공작업을 한다.
@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserDAO userDaO;

	@Autowired
	private Encrypt encrypt;

	@Autowired
	private SmsSender smsSender;

	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

	@Value("${coolsms.apiKey")
	private String apiKey;

	@Value("${coolsms.apiSecret")
	private String apiSecret;

	public boolean checkUserExist(String id) {
		return userDaO.checkUserExist(id);
	}

	public boolean canRegister(String phone, String resident) {
		int phoneCount = userMapper.checkUserPhoneExist(phone);
		int residentCount = userMapper.checkUserResidentExist(resident);

		return phoneCount == 0 && residentCount == 0;
	}

	public void addUserInfo(UserBean mBean) {
		String salt = encrypt.getSalt(); // 솔토
		String encryptPasswd = encrypt.getEncrypt(mBean.getPwd(), salt); // 암호화된 비번 => 솔트 + 내가 입력한 비밀번호

		mBean.setPwd(encryptPasswd);
		mBean.setSalt(salt);

		userDaO.addUserInfo(mBean);
	}

	public void getLoginUserInfo(HttpServletRequest request, UserBean tempLoginUserBean) {

		UserBean dbUserBean = userDaO.getLoginUserInfo(tempLoginUserBean);
		if (dbUserBean != null) {
			String checkSalt = dbUserBean.getSalt(); // ㅅㅌ
			String checkpasswd = dbUserBean.getPwd(); // DB PWD
			String newPasswd = tempLoginUserBean.getPwd(); // newPasswd == 암호화전
			String pwd = encrypt.getEncrypt(newPasswd, checkSalt); // pwd -> newPwd 암호화한거
			System.out.println(checkpasswd);
			System.out.println(pwd);

			if (pwd.equals(checkpasswd)) {
				loginUserBean.setId(dbUserBean.getId());
				loginUserBean.setName(dbUserBean.getName());
				loginUserBean.setUser_num(dbUserBean.getUser_num());
				loginUserBean.setUserLogin(true);

				HttpSession session = request.getSession();
				session.setAttribute("loginUserBean", loginUserBean);

				System.out.println("Logged in user: " + loginUserBean.getId() + " - " + loginUserBean.getName() + " - "
						+ loginUserBean.getUser_num());

			} else {
				loginUserBean.setUserLogin(false); // 로그인 실패
				System.out.println("Login failed. User not found.");
			}

		}
	}

	public String verificationCode(String phone) {

		String code = String.format("%06d", (int) (Math.random() * 900000));
		String text = ("[NC BANK] " + code);
		String result = smsSender.Smsvr(phone, text);

		if (result.equals("success")) {
			return code;
		} else {
			return "fail";
		}
	}
	/*
	 * 비밀번호찾기 버튼 눌렀을떄 나오는 함수 public void findMemberPwd(UserBean findMemberPwdBean) {
	 * 
	 * String newsalt = encrypt.getSalt(); //새 솔트값 받았어
	 * 
	 * String newPwd = findMemberPwdBean.getPwd(); //암호화전 String newpass =
	 * encrypt.getEncrypt(findMemberPwdBean.getPwd(), newsalt);
	 * 
	 * findMemberPwdBean.setPwd(newpass); findMemberPwdBean.setSalt(newsalt);
	 * 
	 * userDaO.findMemberPwd(findMemberPwdBean); }
	 * 
	 */

	public UserBean getUserInfo(int userNum) {
		return userDaO.getUserInfo(userNum);
	}

	public void updateUserInfo(UserBean userBean) {
		userDaO.updateUserInfo(userBean);
	}

}
