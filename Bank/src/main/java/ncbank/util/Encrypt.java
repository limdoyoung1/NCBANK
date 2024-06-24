package ncbank.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.stereotype.Repository;

@Repository
public class Encrypt {
	public String getSalt() {

		// random 생성
		SecureRandom rd = new SecureRandom();
		byte[] salt = new byte[20];

		// 난수 생성
		rd.nextBytes(salt);

		// 문자열로 변경하기
		StringBuffer sb = new StringBuffer();
		for (byte b : salt) {
			sb.append(String.format("%02x", b));
		}
		;
		return sb.toString();
	}

	public String getEncrypt(String password, String salt) {
		// pwd => 회원가입,로그인 시 들어가는 값,salt는 랜덤값 => DB salt에 저장 ,DB에 있는 pwd는 암화된 값
		String result = "";
		// SHA-256 알고리즘 생성
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			System.out.println("PW + SALT 적용 전 : " + password + salt);
			md.update((password + salt).getBytes());
			byte[] pwdsalt = md.digest();

			StringBuffer sb = new StringBuffer();
			for (byte b : pwdsalt) {
				sb.append(String.format("%02x", b));
			}
			result = sb.toString();
			System.out.println("PW + SALT 적용 후 : " + result);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

}
