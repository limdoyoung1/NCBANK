package ncbank.beans;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserBean {
	
	private int user_num;
	@Size(min = 2, max = 4)
	@Pattern(regexp = "[가-힣]*") // ㄱ에서 부터 끝까지 읽음
	private String name;
	
	private String address; // 최종 주소
	
	@Pattern(regexp = "^0\\d{1,2}(-|\\))\\d{3,4}-\\d{4}$",message="전화번호 형식을 확인해주세요") // 전화번호 형식
	private String phone;
	@Pattern(regexp = "\\d{6}\\-[1-4]\\d{6}") // 주민등록번호 형식
	private String resident; // 주민번호
	private String email; // 이메일
	/* /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i */
	private String join_date;
	@Size(min = 4, max = 20)
	@Pattern(regexp = "[a-zA-Z0-9]*") // 영어 또는 숫자만 입력 자주 쓰이는 정규식(Regular Expression)
	private String id;
	@Size(min = 1, max = 20)
	@Pattern(regexp = "[a-zA-Z0-9]*")
	private String pwd;
	/* /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$ 영문 숫자 특수기호 조합 8자리 이상 */
	@Size(min = 1, max = 20)
	@Pattern(regexp = "[a-zA-Z0-9]*")
	private String pwd2;
	
	// 주소 api를 위한 필드 선언
	private String add1; // 우편번호
	private String add2; // 도로명주소
	private String add3; // 상세주소
	
	private String resident1;
	private String resident2;
	
	private String salt;

	// sms 인증 코드
	private String verificationCode;

	private boolean idExistCheck = true;

	private boolean userLogin;

	// 초기값 로그인이 안되어 있는 상태
	public UserBean() {
		this.userLogin = false;
	}

	public boolean isUserLogin() {
		return userLogin;
	}

	public void setUserLogin(boolean userLogin) {
		this.userLogin = userLogin;
	}

	public int getUser_num() {
		return user_num;
	}

	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getResident() {
		return resident;
	}

	public void setResident(String resident) {
		this.resident = resident;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJoin_date() {
		return join_date;
	}

	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public String getPwd2() {
		return pwd2;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}

	public boolean isIdExistCheck() {
		return idExistCheck;
	}

	public void setIdExistCheck(boolean idExistCheck) {
		this.idExistCheck = idExistCheck;
	}

	public String getAdd1() {
		return add1;
	}

	public void setAdd1(String add1) {
		this.add1 = add1;
	}

	public String getAdd2() {
		return add2;
	}

	public void setAdd2(String add2) {
		this.add2 = add2;
	}

	public String getAdd3() {
		return add3;
	}

	public void setAdd3(String add3) {
		this.add3 = add3;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	

	public String getResident1() {
		return resident1;
	}

	public void setResident1(String resident1) {
		this.resident1 = resident1;
	}

	public String getResident2() {
		return resident2;
	}

	public void setResident2(String resident2) {
		this.resident2 = resident2;
	}

	@Override
	public String toString() {
		String tmp = this.name + "/" + this.id + "/" + this.resident + "/" + this.pwd + "/" + this.pwd2;

		return tmp;
	}
}
