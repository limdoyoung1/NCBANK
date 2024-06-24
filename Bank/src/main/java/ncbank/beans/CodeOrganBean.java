package ncbank.beans;

public class CodeOrganBean {
	private String code_organ;
	private String code_organ_name;

	public String getCode_organ() {
		return code_organ;
	}

	public void setCode_organ(String code_organ) {
		this.code_organ = code_organ;
	}

	public String getCode_organ_name() {
		return code_organ_name;
	}

	public void setCode_organ_name(String code_organ_name) {
		this.code_organ_name = code_organ_name;
	}

	@Override
	public String toString() {
		return code_organ_name;
	}

}
