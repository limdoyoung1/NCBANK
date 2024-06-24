package ncbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncbank.beans.CodeOrganBean;
import ncbank.dao.CodeOrganDAO;

@Service
public class CodeOrganService {

	@Autowired
	private CodeOrganDAO codeOrganDAO;

	public List<CodeOrganBean> getCode_organ_name() {
		return codeOrganDAO.getCode_organ_name();
	}
}
