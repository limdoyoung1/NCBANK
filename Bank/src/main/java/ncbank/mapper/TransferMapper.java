package ncbank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import ncbank.beans.TransferBean;

public interface TransferMapper {

	@Insert("INSERT INTO transfer (trans_num, trans_type, trans_money, trans_balance, trans_text, trans_date, from_account, to_account, code_organ, user_num) "
			+ "VALUES (SEQ_TRANSFER.NEXTVAL, #{trans_type}, #{trans_money}, #{trans_balance}, #{trans_text}, SYSDATE, #{from_account}, #{to_account}, #{code_organ}, #{user_num})")
	public void addTransfer(TransferBean transferBean);

//	@Select("select * from transfer where user_num=#{userNum} and from_account=#{account}")
	@Select("<script>" + "SELECT t.*, o.code_organ_name " + // 수정함: code_organ_name 추가
			"FROM transfer t " + "LEFT JOIN code_organ o ON t.code_organ = o.code_organ " + // 수정함: code_organ 조인 추가
			"WHERE t.user_num = #{userNum} " + "<if test='account != null'> AND t.from_account = #{account}</if>"
			+ "ORDER BY trans_date desc" + "</script>")
	List<TransferBean> getTransfer(@Param("userNum") int userNum, @Param("account") String account);
//	@Select("<script>" + "SELECT * FROM transfer WHERE user_num = #{userNum} "
//			+ "<if test='account != null'>AND from_account = #{account} </if>" + +"</script>")

}
