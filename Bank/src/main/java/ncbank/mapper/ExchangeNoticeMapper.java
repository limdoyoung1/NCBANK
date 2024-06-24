package ncbank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ncbank.beans.ExchangeNoticeBean;

public interface ExchangeNoticeMapper {
	
	/* select */
	@Select("select * from notice ")
	public List<ExchangeNoticeBean> getAllExchangeRateNotice();
	
	@Select("select * from notice " 
			+ "where user_num = (select user_num from member where user_num = #{user_num})")
	public ExchangeNoticeBean getExchangeRateNotice(int user_num);
	
	//  만약 한 유저가 여러 통화의 알림을 설정할수 있다고 하면 사용될 mapper
	@Select("select * from notice "
			+ "where user_num = (select user_num from member where user_num = #{user_num}) "
			+ "and code_money = (select code_money from code_money where code_money = #{code_money})")
	public ExchangeNoticeBean getExchangeRateNotice2(int user_num, String code_money);
	
	/* update */
	@Update("update notice " 
			+ "set notice_rate_type = #{notice_rate_type}, notice_rate = #{notice_rate}, notice_email = #{notice_email}, "
			+ "code_money = (select code_money from code_money where code_money = #{code_money}) "
			+ "where user_num = (select user_num from member where user_num = #{user_num})")
	public void updateExchangeRateNotice(ExchangeNoticeBean noticeBean);
	
	
	/* delete */
	@Delete("delete notice "
			+ "where user_num = (select user_num from member where user_num = #{user_num}) ")
	public void deleteExchangeRateNotice(int user_num);
	
	// 통화코드까지 비교해서 지우기
	@Delete("delete notice "
			+ "where user_num = (select user_num from member where user_num = #{user_num}) "
			+ "and code_money = (select code_money from code_money where code_money = #{code_money})")
	public void deleteExchangeRateNotice2(ExchangeNoticeBean noticeBean);
	
	
	/* insert */
	// 테이블안에 같은 code_money 없으면 추가
	// 여기 조건 손봐야됨 회원 한명당 하나의 알림만 가질수 있게 (중복 불가)
	@Insert("insert into notice(notice_num, notice_rate_type, notice_rate, notice_email, notice_date, user_num, code_money) "
			+ "select notice_seq.nextval, #{notice_rate_type}, #{notice_rate}, #{notice_email}, #{notice_date}, m.user_num, cm.code_money "
			+ "from member m, code_money cm "
			+ "where m.user_num = #{user_num} and cm.code_money = #{code_money} "
			+ "and not exists (select 1 from notice n where n.code_money = #{code_money} and n.user_num = #{user_num})")
	public void addExchangeRateNotice(ExchangeNoticeBean noticeBean);
	
	@Insert("insert into notice(notice_num, notice_rate_type, notice_rate, notice_email, notice_date, user_num, code_money) "
			+ "values(notice_seq.nextval, #{notice_rate_type}, #{notice_rate}, #{notice_email}, #{notice_date}, "
			+ "(select user_num from member where user_num = #{user_num}),  "
			+ "(select code_money from code_money where code_money = #{code_money}))")
	public void addExchangeRateNotice2(ExchangeNoticeBean noticeBean);
	
}
