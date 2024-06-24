package ncbank.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ncbank.beans.CodeMoneyBean;
import ncbank.mapper.CodeMoneyMapper;

@Repository
public class CodeMoneyDAO {

	@Autowired
	private CodeMoneyMapper codeMoneyMapper;

	public List<CodeMoneyBean> getCodeMoneyList() {
		return codeMoneyMapper.getCodeMoneyList();
	}
	public String getCodeMoneyName(String code_money) {
		return codeMoneyMapper.getCodeMoneyName(code_money);
	}
 	
}
