package ncbank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ncbank.beans.AutoBean;

public interface AutoMapper {
	@Insert("INSERT INTO auto (auto_num, auto_name, auto_money, auto_type, auto_next_date, auto_start, auto_end, to_account, code_organ, from_account, user_num) "
			+ "VALUES (seq_auto.nextval, #{auto_name}, #{auto_money}, #{auto_type}, #{auto_next_date}, #{auto_start}, #{auto_end}, #{to_account}, #{code_organ}, #{from_account}, #{user_num})")
	void addAuto(AutoBean autoBean);

	@Select("SELECT * FROM auto WHERE user_num = #{user_num}")
	List<AutoBean> getAuto(int user_num);

	@Select("SELECT * FROM auto WHERE auto_num = #{auto_num}")
	AutoBean getAutoByAutoNum(int auto_num);

	@Update("UPDATE auto SET auto_name = #{auto_name}, auto_money = #{auto_money}, auto_type = #{auto_type}, auto_next_date = #{auto_next_date}, "
			+ "auto_start = #{auto_start}, auto_end = #{auto_end}, to_account = #{to_account}, code_organ = #{code_organ}, from_account = #{from_account}, user_num = #{user_num} "
			+ "WHERE auto_num = #{auto_num}")
	void updateAuto(AutoBean autoBean);

	@Delete("DELETE FROM auto WHERE auto_num = #{auto_num}")
	void deleteAuto(int auto_num);
}
