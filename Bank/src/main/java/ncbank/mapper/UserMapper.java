package ncbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ncbank.beans.UserBean;

public interface UserMapper {

    // jsp에서 ? 로 설정한 뒤 값을 채움 -> MyBatis 에서 #{} 으로 바로 값 설정
    @Select("SELECT COUNT(*) FROM login where id = #{id}")
    public int checkUserIdExist(String id);

    @Select("SELECT COUNT(*) FROM login")
    public int userCount();

    @Select("SELECT COUNT(*) FROM member WHERE phone=#{phone}")
    public int checkUserPhoneExist(String phone);

    @Select("SELECT COUNT(*) FROM member WHERE resident=#{resident}")
    public int checkUserResidentExist(String resident);

    // 회원가입
    @Insert("insert into member " + "(user_num  , name , address , phone , resident , email, join_date) "
            + "values (#{user_num},#{name},#{address},#{phone},#{resident},#{email},#{join_date})")
    public void addMember(UserBean bean);

    @Insert("insert into login (user_num, id, pwd, salt) values (#{user_num},#{id},#{pwd},#{salt})")
    public void addLogin(UserBean bean);

    // 로그인 시 회원가입 정보 확인
    @Select("SELECT m.user_num,m.name,m.phone,m.resident,m.join_date, l.id,l.pwd,l.salt" + "	from member m "
            + "	left join login l " + "	on m.user_num = l.user_num where l.id=#{id}")
    UserBean getLoginUserInfo(UserBean tempLoginUserBean);

    // 아이디 찾기
    @Select("select l.id from login l join member m on m.user_num =l.user_num where m.name = #{name} and m.phone = #{phone}")
    public String findMemberId(UserBean findMemberIDBean);

    // 비밀번호 변경 pwd, salt
    @Update("update login set pwd=#{pwd},salt=#{salt} where id=#{id}")
    public void findMemberPwd(UserBean findMemberPwdBean);
    
    //마이페이지에 정보 띄우기용
    @Select("select * from member m join login l on l.user_num = m.user_num where m.user_num = #{userNum}")
    UserBean getUserInfo(@Param("userNum") int userNum);
    
    //정보수정
    @Update("update member set phone=#{phone},email=#{email} where id=#{id}")
    public void updateUserInfo(UserBean userBean);
}
