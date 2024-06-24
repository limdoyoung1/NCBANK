package ncbank.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ncbank.beans.ExchangeAutoNoticeBean;

public interface ExchangeAutoNoticeMapper {
	
	/* select */
	@Select("select * from auto_notice ")
	public List<ExchangeAutoNoticeBean> getAllExchangeAutoNotice();
	
	@Select("select * from auto_notice "
			+ "where notice_num = (select notice_num from notice where user_num = #{user_num})")
	public ExchangeAutoNoticeBean getExchangeAutoNotice(int user_num);
	
	@Select("select notice_send_state " + "from auto_notice "
			+ "where notice_num = (select notice_num from notice where user_num = #{user_num})")
	public int getAutoNoticeSendState(int user_num);
	
	/* update */
	@Update("update auto_notice set notice_send_state = #{send_state}, "
			+ "notice_update_date = #{update_date} "
			+ "where notice_num = (select notice_num from notice where user_num = #{user_num})")
	public void updateExchangeAutoNotice(@Param("send_state") int send_state, 
			@Param("update_date") Date update_date, @Param("user_num") int user_num);
	
	/* insert */
	@Insert("insert into auto_notice(notice_num, notice_update_date) " 
			+ "values((select notice_num from notice where user_num = #{user_num}), " 
			+ "#{update_date})")
	public void addExchangeAutoNotice(@Param("user_num") int user_num, @Param("update_date") Date update_date);
	
	/* delete */
	@Delete("delete auto_notice " 
			+ "where notice_num = (select notice_num from notice where user_num = #{user_num})")
	public void deleteExchangeAutoNotice(int user_num);
	
}
