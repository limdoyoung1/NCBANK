package ncbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncbank.beans.CodeMoneyBean;
import ncbank.dao.CodeMoneyDAO;

@Service
public class CodeMoneyService {

	@Autowired
	private CodeMoneyDAO codeMoneyDAO;

	public List<CodeMoneyBean> getCodeMoneyList() {
		List<CodeMoneyBean> codeMoneyList = codeMoneyDAO.getCodeMoneyList();
		if (null == codeMoneyList || codeMoneyList.isEmpty()) {
			return null;
		}
		return codeMoneyList;
	}
	
	public String getCodeMoneyName(String code_money) {
		String codeMoneyName = codeMoneyDAO.getCodeMoneyName(code_money);
		if (null == codeMoneyName || codeMoneyName.isEmpty()) {
			System.out.println("CodeMoneyService getCodeMoneyName()");
			System.out.println("codeMoneyName is null");
			return null;
		}
		return codeMoneyName;
	}

}
