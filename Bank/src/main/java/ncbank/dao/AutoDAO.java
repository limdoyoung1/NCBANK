package ncbank.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ncbank.beans.AutoBean;
import ncbank.mapper.AutoMapper;

@Repository
public class AutoDAO {
	@Autowired
	private AutoMapper autoMapper;

	public void addAuto(AutoBean autoBean) {
		autoMapper.addAuto(autoBean);
	}

	public List<AutoBean> getAuto(int user_num) {
		return autoMapper.getAuto(user_num);
	}

	public AutoBean getAutoByAutoNum(int auto_num) {
		return autoMapper.getAutoByAutoNum(auto_num);
	}

	public void updateAuto(AutoBean autoBean) {
		autoMapper.updateAuto(autoBean);
	}

	public void deleteAuto(int auto_num) {
		autoMapper.deleteAuto(auto_num);
	}
}
