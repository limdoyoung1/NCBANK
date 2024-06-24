package ncbank.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ncbank.beans.AccountBean;
import ncbank.beans.AutoBean;
import ncbank.beans.GroupAccountBean;
import ncbank.beans.TransferBean;
import ncbank.beans.UserBean;
import ncbank.mapper.GroupAccountMapper;

@Repository
public class GroupAccountDAO {

	@Autowired
	private GroupAccountMapper groupAccountMapper;

	public List<AccountBean> selectAccountByUserNum(int user_num) {
		List<AccountBean> accountList = groupAccountMapper.selectAccountByUserNum(user_num);
		System.out.println("GroupAccountDAO - selectAccountByUserNum: " + user_num);
		System.out.println("GroupAccountDAO accountList : " + accountList);
		return accountList;
	}

	public List<AutoBean> getAccountInfo(String account, int user_num) {
		List<AutoBean> accountInfoList = groupAccountMapper.infoList(account, user_num);
		System.out.println("DAO accountInfoList: " + accountInfoList);
		return accountInfoList;
	}
	
	
	public void createGroupAccount(GroupAccountBean groupAccount) throws SQLException {
        try {
            groupAccountMapper.createGroupAccount(groupAccount);
            System.out.println("Group account successfully created in DAO: " + groupAccount);
        } catch (Exception e) {
            System.err.println("Error in DAO during group account creation: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void updateAccountType(String account) throws SQLException {
        try {
            System.out.println("Executing updateAccountType in DAO for account: " + account);
            groupAccountMapper.updateAccountType(account);
            System.out.println("Account type successfully updated in DAO: " + account);
        } catch (Exception e) {
            System.err.println("Error in DAO during account type update: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
	
    
    
    
    
    public GroupAccountBean selectGroupAccountByGroupNum(int group_num) {
        return groupAccountMapper.selectGroupAccountByGroupNum(group_num);
    }
    
    
    
    
    

	public AccountBean getAccountByAccountNumber(String account) {
		return groupAccountMapper.selectAccountByAccountNumber(account);
	}

	public List<AccountBean> selectGroupAccount(int user_num) {
		return groupAccountMapper.selectGroupAccount(user_num);
	}

	public List<AccountBean> selectGroupAccountsByUserNum(int user_num) {
		List<AccountBean> selectGroupAccountsByUserNum = groupAccountMapper.selectGroupAccountsByUserNum(user_num);
		System.out.println("DAO selectGroupAccountsByUserNum = " + selectGroupAccountsByUserNum);
		return selectGroupAccountsByUserNum;
	}
	
	
	
	
	
	
	
	

	public AutoBean getAutoInfo(String account) {
		AutoBean getAutoInfo = groupAccountMapper.getAutoInfo(account);
		System.out.println("DAO getAutoInfo = " + getAutoInfo);
		return getAutoInfo;
	}

	public GroupAccountBean getGroupInfobyAcc(String account) {
		GroupAccountBean getGroupInfobyAcc = groupAccountMapper.getGroupInfobyAcc(account);
		System.out.println("DAO getGroupInfo = " + getGroupInfobyAcc);
		return getGroupInfobyAcc;
	}

	public List<UserBean> getGroupMembers(int user_num) {
		List<UserBean> getGroupMembers = groupAccountMapper.getGroupMembers(user_num);
		System.out.println("DAO getGroupMembers = " + getGroupMembers);
		return getGroupMembers;
	}

	public List<GroupAccountBean> getGroupMemberDetails(int user_num) {
		List<GroupAccountBean> getGroupMemberDetails = groupAccountMapper.getGroupMemberDetails(user_num);
		System.out.println("DAO getGroupMemberDetails = " + getGroupMemberDetails);
		return getGroupMemberDetails;
	}
	
	public UserBean getGroupLeaderNameByAccount(String account) {
	    System.out.println("DAO: getGroupLeaderNameByAccount called with account: " + account); // 디버그용 로그
	    UserBean groupLeaderName = groupAccountMapper.getGroupLeader(account);
	    System.out.println("DAO groupLeaderName = " + groupLeaderName);
	    return groupLeaderName;
	}
	
	public AccountBean totalBalance(String account) {
		AccountBean totalBalance = groupAccountMapper.totalBalance(account);
		System.out.println("DAO totalBalance = " + totalBalance);
		return totalBalance;
	}
	
	public int getGroupNumByAccount(String account) {
	    return groupAccountMapper.getGroupNumByAccount(account);
	}
	
	
	public void deleteGroup(int group_num) throws SQLException {
        groupAccountMapper.deleteGroup(group_num);
    }

    public void updateAccountTypeToNormal(String account) throws SQLException {
        groupAccountMapper.updateAccountTypeToNormal(account);
    }

    public void deleteMember(int userNum) throws Exception {
        System.out.println("Deleting member with userNum: " + userNum); // 디버그용 로그
        groupAccountMapper.deleteMember(userNum);
        System.out.println("Member with userNum: " + userNum + " deleted successfully"); // 디버그용 로그
    }
    
    public List<TransferBean> getAccountTransfers(String account) {
        return groupAccountMapper.getAccountTransfers(account);
    }
    
    public String getAccountNumberByGroupNum(int group_num) {
        return groupAccountMapper.getAccountNumberByGroupNum(group_num);
    }
	
	

}
