package ncbank.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ncbank.beans.UserBean;
import ncbank.mapper.UserMapper;

@Repository
public class UserDAO {

	@Autowired
	private UserMapper userMapper;

	public boolean checkUserExist(String id) {
		return (0 == userMapper.checkUserIdExist(id));
	}

	public boolean checkUserPhoneExist(String phone) {
		return (0 == userMapper.checkUserPhoneExist(phone));
	}

	public boolean checkUserResidentExist(String resident) {
		return (0 == userMapper.checkUserResidentExist(resident));
	}

	// Mapper -> DAO -> Service로 가도되고 안가도 됨

	public void addUserInfo(UserBean mBean) {
		int size = userMapper.userCount();
		mBean.setUser_num(size + 1);
		mBean.setAddress(mBean.getAdd2() + "  " + mBean.getAdd3());
		mBean.setResident(mBean.getResident1() + "-" + mBean.getResident2());

		userMapper.addMember(mBean);
		userMapper.addLogin(mBean);
	}
	

	public UserBean getLoginUserInfo(UserBean tempLoginUserBean) {
		return userMapper.getLoginUserInfo(tempLoginUserBean);
	}

	// 아이디 찾기
	public String findMemberId(UserBean findMemberIDBean) {
		return userMapper.findMemberId(findMemberIDBean);
	}

	// 비밀번호 찾기
	public void findMemberPwd(UserBean findMemberPwdBean) {
		userMapper.findMemberPwd(findMemberPwdBean);
	}
	
	public UserBean getUserInfo(int userNum) {
		return userMapper.getUserInfo(userNum);
	}
	
	 public void updateUserInfo(UserBean userBean) {
		 userMapper.updateUserInfo(userBean);
	 }

}
