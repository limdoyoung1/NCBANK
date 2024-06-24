package ncbank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import ncbank.beans.CodeOrganBean;

public interface CodeOrganMapper {
	@Select("select * from code_organ")
	List<CodeOrganBean> getCode_organ_name();
}
