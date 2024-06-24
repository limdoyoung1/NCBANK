package ncbank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ncbank.beans.AccountBean;
import ncbank.beans.UserBean;

public interface AccountMapper {

	@Select("select * from member where user_num = #{userNum}")
	UserBean getUserInfo(@Param("userNum") int userNum);

	@Select("select * from account where user_num = #{userNum} order by ac_date desc")
	List<AccountBean> getAccount(@Param("userNum") int userNum);

	@Update("UPDATE account SET ac_balance = #{ac_balance} WHERE account = #{account}")
	void updateAccountBalance(AccountBean accountBean);

	@Insert("INSERT INTO account (account, ac_password, ac_balance, ac_type, ac_date, user_num) VALUES (#{account}, #{ac_password}, DEFAULT, #{ac_type}, SYSDATE, #{user_num})")
	void createAccount(AccountBean accountBean);

	@Select("SELECT * FROM account WHERE account = #{accountNumber}")
	AccountBean getAccountByNumber(@Param("accountNumber") String accountNumber);
}
