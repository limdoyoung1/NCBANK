package ncbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncbank.beans.BoardInfoBean;
import ncbank.dao.TopMenuDAO;

@Service
public class TopMenuService {

	@Autowired
	private TopMenuDAO topMenuDAO;

	public List<BoardInfoBean> getTopMenuList() {
		List<BoardInfoBean> topMenuList = topMenuDAO.getTopMenuList();
		return topMenuList; // selelct의 결과값이 넘어옴
	}

}