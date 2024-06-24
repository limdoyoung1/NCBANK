package ncbank.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ncbank.beans.CodeOrganBean;
import ncbank.mapper.CodeOrganMapper;

@Repository
public class CodeOrganDAO {
	@Autowired
	private CodeOrganMapper codeOrganMapper;

	public List<CodeOrganBean> getCode_organ_name() {
		return codeOrganMapper.getCode_organ_name();
	}
}
