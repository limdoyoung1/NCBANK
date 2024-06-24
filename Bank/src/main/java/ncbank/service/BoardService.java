package ncbank.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import ncbank.beans.ContentBean;
import ncbank.beans.PageBean;
import ncbank.beans.UserBean;
import ncbank.dao.BoardDao;

@Service
@PropertySource("/WEB-INF/properties/option.properties")
public class BoardService {

	@Value("${path.upload}")
	private String path_upload;

	@Value("${page.listcnt}")
	private int page_listcnt;

	@Value("${page.paginationcnt}")
	private int page_paginationcnt;

	@Autowired
	private BoardDao boardDao;

	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

	public void addContentInfo(ContentBean writeContentBean) {

		writeContentBean.setContent_writer_idx(loginUserBean.getUser_num());

		boardDao.addContentInfo(writeContentBean);

	}

	public String getBoardInfoName(int board_info_idx) {
		return boardDao.getBoardInfoName(board_info_idx);
	}

	public List<ContentBean> getContentList(int board_info_idx, int page) {
	    int start = (page - 1) * page_listcnt + 1;
	    int end = start + page_listcnt - 1;
	    return boardDao.getContentList(board_info_idx, start, end);
	}

	public ContentBean getContentInfo(int content_idx) {
		return boardDao.getContentInfo(content_idx);
	}

	// 페이지 관련
	public PageBean getContentCnt(int content_board_idx, int currentPage) {
		int contentCnt = boardDao.getContentCnt(content_board_idx);

		PageBean pageBean = new PageBean(contentCnt, currentPage, page_listcnt, page_paginationcnt);
		return pageBean;
	}

	/*
	 * // 이미지 처리 때문에 서비스 들어와야 함 public void modifyContentInfo(ContentBean
	 * modifyContentBean) {
	 * 
	 * MultipartFile uploadFile = modifyContentBean.getUpload_file();
	 * 
	 * if (uploadFile.getSize() > 0) { String fileName = saveUploadFile(uploadFile);
	 * modifyContentBean.setContent_file(fileName); }
	 * 
	 * 
	 * }
	 */

}
