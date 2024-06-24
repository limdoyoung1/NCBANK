package ncbank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import ncbank.beans.BoardInfoBean;

// 테이블 생성 -> Bean (DTO) 생성 -> Mapper로 Bean을 SQL 세션 영역에 등록
public interface TopMenuMapper {

	// @Select : 쿼리문 등록
	@Select("select board_info_idx, board_info_name " + "from board_info_table " + "order by board_info_idx")
	List<BoardInfoBean> getTopMenuList();

}
