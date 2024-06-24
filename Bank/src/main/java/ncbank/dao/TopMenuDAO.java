package ncbank.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ncbank.beans.BoardInfoBean;
import ncbank.mapper.TopMenuMapper;

// @Repository : Spring이 해당 클래스를 컴포넌트 스캔 과정에서 자동으로 감지하고, Spring 컨테이너에 빈으로 등록한다.
@Repository
public class TopMenuDAO {

	// Jpa 에서는 Mapper만 사라진 형태임
	// Bean 을 SQL 세션영역에 등록을 해놨다면 @Autowired로 TopMenuMapper 인터페이스의 구현체를 주입받음
	@Autowired
	private TopMenuMapper topMenuMapper;

	public List<BoardInfoBean> getTopMenuList() {
		// TopMenuMapper 에서 @Select 로 등록한 쿼리문의 결과를 받아온다.
		List<BoardInfoBean> topMenuList = topMenuMapper.getTopMenuList();

		return topMenuList; // selelct의 결과값이 넘어옴
	}

}
