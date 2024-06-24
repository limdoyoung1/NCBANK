package ncbank.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ncbank.beans.AccountBean;
import ncbank.beans.AutoBean;
import ncbank.beans.GroupAccountBean;
import ncbank.beans.TransferBean;
import ncbank.beans.UserBean;
import ncbank.dao.GroupAccountDAO;
import ncbank.mapper.GroupAccountMapper;

@Service
public class GroupAccountService {

    @Autowired
    private GroupAccountDAO groupAccountDAO;

    @Autowired
    private GroupAccountMapper groupAccountMapper;

    @Resource(name = "loginUserBean")
    private UserBean loginUserBean;

    public List<AccountBean> getAccountsByUserNum(int user_num) {
        List<AccountBean> accountList = groupAccountDAO.selectAccountByUserNum(user_num);
        System.out.println("GroupAccountService - getAccountsByUserNum: " + user_num);
        System.out.println("GroupAccountService - accountList : " + accountList);
        return accountList;
    }

    public List<AutoBean> infoList(String account, int user_num) {
        List<AutoBean> accountInfoList = groupAccountDAO.getAccountInfo(account, user_num);
        System.out.println("GroupAccountService - infoList for account: " + account + ", user_num: " + user_num);
        System.out.println("GroupAccountService - accountInfoList: " + accountInfoList);
        return accountInfoList;
    }

    public AccountBean getAccountByAccountNumber(String account) {
        return groupAccountDAO.getAccountByAccountNumber(account);
    }

    
    
    @Transactional
    public void createGroupAccount(GroupAccountBean groupAccount) throws SQLException {
        System.out.println("Creating Service group account: " + groupAccount);
        groupAccountDAO.createGroupAccount(groupAccount);
        System.out.println("Group account created: " + groupAccount);
    }

    @Transactional
    public void updateAccountType(String account) throws SQLException {
        System.out.println("Updating Service account type for account: " + account);
        groupAccountDAO.updateAccountType(account);
        System.out.println("Account type updated for account: " + account);
    }
    
    


    public GroupAccountBean getGroupInfo(int group_num) {
        return groupAccountDAO.selectGroupAccountByGroupNum(group_num);
    }
    
    

    public Integer getLatestGroupNumByUser(int user_num) {
        return groupAccountMapper.getLatestGroupNumByUser(user_num);
    }

    public List<AccountBean> selectGroupAccount(int user_num) {
        return groupAccountDAO.selectGroupAccount(user_num);
    }
    
  
    public void addMemberToGroup(GroupAccountBean groupAccount) throws SQLException {
    	groupAccountMapper.addMemberToGroup(groupAccount);
    }
    

    
    public List<AccountBean> selectGroupAccountsByUserNum(int user_num) {
        List<AccountBean> selectGroupAccountsByUserNum = groupAccountDAO.selectGroupAccountsByUserNum(user_num);
        System.out.println("Service selectGroupAccountsByUserNum = " + selectGroupAccountsByUserNum);
        return selectGroupAccountsByUserNum;
    }
    

    

    public AutoBean getAutoInfo(String account) {
        AutoBean getAutoInfo = groupAccountDAO.getAutoInfo(account);
        System.out.println("Service getAutoInfo = " + getAutoInfo);
        return getAutoInfo;
    }

    public GroupAccountBean getGroupInfobyAcc(String account) {
        GroupAccountBean getGroupInfobyAcc = groupAccountDAO.getGroupInfobyAcc(account);
        System.out.println("Service getGroupInfo = " + getGroupInfobyAcc);
        return getGroupInfobyAcc;
    }

    public List<UserBean> getGroupMembers(int user_num) {
        List<UserBean> getGroupMembers = groupAccountDAO.getGroupMembers(user_num);
        System.out.println("Service getGroupMembers = " + getGroupMembers);
        return getGroupMembers;
    }

    public List<GroupAccountBean> getGroupMemberDetails(int user_num) {
        List<GroupAccountBean> getGroupMemberDetails = groupAccountDAO.getGroupMemberDetails(user_num);
        System.out.println("Service getGroupMemberDetails = " + getGroupMemberDetails);
        return getGroupMemberDetails;
    }
    
    public UserBean getGroupLeaderNameByAccount(String account) {
        System.out.println("Service: getGroupLeaderNameByAccount called with account: " + account); // 디버그용 로그
        UserBean groupLeaderName = groupAccountDAO.getGroupLeaderNameByAccount(account);
        System.out.println("Service groupLeaderName = " + groupLeaderName);
        return groupLeaderName;
    }
    
    public AccountBean totalBalance(String account) {
		AccountBean totalBalance = groupAccountDAO.totalBalance(account);
		System.out.println("Service totalBalance = " + totalBalance);
		return totalBalance;
	}
    
    public int getGroupNumByAccount(String account) {
        return groupAccountDAO.getGroupNumByAccount(account);
    }
    
    @Transactional
    public void deleteGroup(int group_num, String account) throws SQLException {
        groupAccountDAO.deleteGroup(group_num);
        groupAccountDAO.updateAccountTypeToNormal(account);
    }

    public void deleteMember(int userNum) throws Exception {
        System.out.println("Deleting member with userNum: " + userNum); // 디버그용 로그
        groupAccountDAO.deleteMember(userNum);
        System.out.println("Member with userNum: " + userNum + " deleted successfully"); // 디버그용 로그
    }
    
    public List<TransferBean> getAccountTransfers(String account) {
        return groupAccountDAO.getAccountTransfers(account);
    }
    
    public String getAccountNumberByGroupNum(int group_num) {
        return groupAccountDAO.getAccountNumberByGroupNum(group_num);
    }
    
    
    
}
