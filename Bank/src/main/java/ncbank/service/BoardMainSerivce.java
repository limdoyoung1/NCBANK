package ncbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncbank.beans.ContentBean;
import ncbank.dao.BoardDao;

//메인화면에서 게시판의 일부를 보여줄 때 5개씩 보여준다고 보여주는 코드
@Service
public class BoardMainSerivce {
	
	@Autowired
	private BoardDao boardDao;
	
	public List<ContentBean> getMainList(int board_info_idx){
		int start = 1;
		int end = 4;
		return boardDao.getContentList(board_info_idx, start, end);
	}

}
