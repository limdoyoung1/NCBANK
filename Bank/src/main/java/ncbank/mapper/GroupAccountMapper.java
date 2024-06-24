package ncbank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ncbank.beans.AccountBean;
import ncbank.beans.AutoBean;
import ncbank.beans.GroupAccountBean;
import ncbank.beans.TransferBean;
import ncbank.beans.UserBean;

public interface GroupAccountMapper {

    @Select("SELECT account, ac_type FROM account WHERE user_num = #{user_num}")
    List<AccountBean> selectAccountByUserNum(@Param("user_num") int user_num);

    @Select("SELECT a.auto_type, a.auto_next_date, a.auto_money, ac.ac_password "
            + "FROM auto a, account ac, member m "
            + "WHERE a.to_account = ac.account AND ac.user_num = m.user_num AND ac.account = #{account} AND m.user_num = #{user_num}")
    List<AutoBean> infoList(@Param("account") String account, @Param("user_num") int user_num);

    @Select("SELECT account, ac_password FROM account WHERE account = #{account}")
    AccountBean selectAccountByAccountNumber(@Param("account") String account);

    
    
    
    @Insert("INSERT INTO groupinfo (group_num, user_num, group_joindate, group_leader, group_name) " +
            "VALUES (groupinfo_seq.nextval, #{user_num}, #{group_joindate,jdbcType=DATE}, '1', #{group_name, jdbcType=VARCHAR})")
    @Options(useGeneratedKeys = true, keyProperty = "group_num", keyColumn = "group_num")
    void createGroupAccount(GroupAccountBean groupAccount);

    @Update("UPDATE account SET ac_type = 1 WHERE account = #{account}")
    void updateAccountType(@Param("account") String account);

    @Insert("INSERT INTO groupinfo (group_num, user_num, group_joindate, group_leader, group_name) " +
    		"VALUES (#{group_num}, #{user_num}, #{group_joindate}, '0', #{group_name, jdbcType=VARCHAR})")
    void addMemberToGroup(GroupAccountBean groupAccount);

    
    @Delete("DELETE FROM groupinfo WHERE group_num = #{group_num}")
    void deleteGroup(@Param("group_num") int group_num);

    @Update("UPDATE account SET ac_type = 0 WHERE account = #{account}")
    void updateAccountTypeToNormal(@Param("account") String account);

    @Select("SELECT account FROM account WHERE user_num = (SELECT user_num FROM groupinfo WHERE group_num = #{group_num}) AND ROWNUM = 1")
    String getAccountNumberByGroupNum(@Param("group_num") int group_num);
    
    
    
    @Select("SELECT group_leader, group_name FROM groupinfo WHERE group_num = #{group_num}")
    String selectGroupLeaderByGroupNum(@Param("group_num") int group_num);

    @Select("SELECT * FROM groupinfo WHERE group_num = #{group_num}")
    GroupAccountBean inviteGroup(@Param("group_num") int group_num);

    @Select("SELECT group_num FROM groupinfo WHERE user_num = #{user_num} ORDER BY group_joindate DESC FETCH FIRST 1 ROWS ONLY")
    Integer getLatestGroupNumByUser(@Param("user_num") int user_num);
    
    @Select("SELECT a.account, g.group_num, g.user_num, g.group_leader " +
            "FROM groupinfo g, account a " +
            "WHERE a.ac_type = 1 AND g.group_num IN (SELECT group_num FROM groupinfo WHERE user_num = #{user_num}) AND g.user_num = a.user_num")
    List<AccountBean> selectGroupAccount(@Param("user_num") int user_num);

  
   
    
    @Select("SELECT * FROM groupinfo WHERE group_num = #{group_num}")
    GroupAccountBean selectGroupAccountByGroupNum(@Param("group_num") int group_num);
    
    
    
    @Select("SELECT group_num FROM groupinfo WHERE user_num = (SELECT user_num FROM account WHERE account = #{account})")
    int getGroupNumByAccount(@Param("account") String account);
    
    @Select("SELECT a.account, g.group_num, g.user_num, g.group_leader " +
            "FROM groupinfo g, account a " +
            "WHERE a.ac_type = 1 AND g.group_num IN (SELECT group_num FROM groupinfo WHERE user_num = #{user_num}) AND g.user_num = a.user_num")
    List<AccountBean> selectGroupAccountsByUserNum(@Param("user_num") int user_num);

    
    
    
    @Select("SELECT a.auto_money, a.auto_type, a.auto_next_date, a.auto_end " +
            "FROM auto a " +
            "JOIN account acc ON a.to_account = acc.account " +
            "WHERE acc.account = #{account} AND acc.ac_type = 1")
    AutoBean getAutoInfo(@Param("account") String account);

    @Select("SELECT g.group_joindate, g.group_leader, g.group_name " +
            "FROM groupinfo g " +
            "JOIN account acc ON g.user_num = acc.user_num " +
            "WHERE acc.account = #{account} AND acc.ac_type = 1")
    GroupAccountBean getGroupInfobyAcc(@Param("account") String account);

    @Select("SELECT m.name, m.phone " +
            "FROM member m " +
            "JOIN groupinfo g ON g.user_num = m.user_num " +
            "WHERE g.group_num = (SELECT group_num FROM groupinfo WHERE user_num = #{user_num})")
    List<UserBean> getGroupMembers(@Param("user_num") int user_num);

    @Select("SELECT g.group_leader, g.group_joindate " +
            "FROM groupinfo g " +
            "WHERE g.group_num = (SELECT group_num FROM groupinfo WHERE user_num = #{user_num})")
    List<GroupAccountBean> getGroupMemberDetails(@Param("user_num") int user_num);

    
    @Select("SELECT m.name " +
            "FROM member m, groupinfo g " +
            "WHERE m.user_num = g.user_num AND g.group_leader = 1 AND g.group_num = (SELECT group_num FROM account WHERE account = #{account})")
    UserBean getGroupLeader(@Param("account") String account);
    
    @Select("select a.ac_balance "
    		+ "from account a, groupinfo g "
    		+ "where a.user_num = g.user_num and a.ac_type=1 and account = #{account}")
    AccountBean totalBalance(@Param("account") String account);
    
    @Delete("DELETE FROM groupinfo WHERE user_num = 2")
    void deleteMember(int userNum);
    
    @Select("SELECT * FROM transfer WHERE trans_type = 2 and user_num = 1")
    List<TransferBean> getAccountTransfers(String account);
}
