package ncbank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import ncbank.beans.ContentBean;

public interface BoardMapper {

	@SelectKey(statement = "select content_seq.nextval from dual", keyProperty = "content_idx", before = true, resultType = int.class)

	// 글쓰기
	@Insert("insert into content_table(content_idx, content_subject, content_text, "
			+ "content_writer_idx, content_board_idx, content_date) "
			+ "values (#{content_idx}, #{content_subject}, #{content_text}, "
			+ "#{content_writer_idx}, #{content_board_idx}, sysdate)")
	void addContentInfo(ContentBean writeContentBean);

	// 게시판 이름
	@Select("select board_info_name " + "from board_info_table " + "where board_info_idx = #{board_info_idx}")
	String getBoardInfoName(int board_info_idx);

	// 글 목록
//	@Select("select a1.content_idx, a1.content_subject, "
//			+ "       to_char(a1.content_date, 'YYYY-MM-DD') as content_date " + "from content_table a1, member a2 "
//			+ "where a1.content_board_idx = #{board_info_idx} "
//			+ "order by a1.content_idx desc")
//	List<ContentBean> getContentList(int board_info_idx, RowBounds rowBounds);

	@Select("SELECT * FROM ( " + "    SELECT a1.content_idx, " + "           a1.content_subject, "
			+ "           TO_CHAR(a1.content_date, 'YYYY-MM-DD') AS content_date, " + "           ROWNUM AS rnum "
			+ "    FROM content_table a1 " + "    WHERE a1.content_board_idx = #{board_info_idx} "
			+ "    ORDER BY a1.content_idx DESC " + ") " + "WHERE rnum BETWEEN #{start} AND #{end}")
	List<ContentBean> getContentList(@Param("board_info_idx") int board_info_idx, @Param("start") int start,
			@Param("end") int end);

	// 글 상세정보 (제목,등록일 , 내용,파일)만 보여주기
//	@Select("select a2.user_name as content_writer_name, "
//			+ "       to_char(a1.content_date, 'YYYY-MM-DD') as content_date, "
//			+ "       a1.content_subject, a1.content_text, a1.content_file , a1.content_writer_idx "
//			+ "from content_table a1, user_table a2 " + "where a1.content_writer_idx = a2.user_idx "
//			+ "      and content_idx = #{content_idx}")
	@Select("select content_idx,content_subject,to_char(content_date,'YYYY-MM-DD') as content_date, content_text from content_table where content_idx=#{content_idx}")
	ContentBean getContentInfo(int content_idx);

	// 해당 게시판의 전체 글의 수 가져오기 페이지를 위해서
	@Select("select count(*) from content_table where content_board_idx = #{content_board_idx}")
	int getContentCnt(int content_board_idx);

}
