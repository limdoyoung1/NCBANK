package ncbank.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

// 환율에 따라 환전금액을 계산해주는 클래스
@Component
public class ExchangeCalculator {
	
	/* 기본 통화 : 원화 KRW */
	private String defaultExCode = "KRW";
	private float defaultDealbasR;

	public ExchangeCalculator() {}
	
	
	// 통화, 나라명, money + 매매기준율
	
	
	// 금융계산에서 float,double로 연산하는것 보단 BigDecimal를 사용해 연산하는게 좋음
	// 바꿀 금액(원화), 전환할 통화코드, 해당 통화의 매매기준율
	// 근데 intercepter 에서 리퀘스트영역 등록시 굳이 매매기준율 받아올 필요없어보임
	public BigDecimal convertedAmount(BigDecimal amount, String targetExCode, BigDecimal exchangeRate) {
		// BigDecimal.compareTo : 값 비교, 소숫점 맨끝 0 무시하고 값 동일 0, 적으면 -1, 많으면 1 
		if (exchangeRate.compareTo(BigDecimal.ZERO) == 0) {
			System.out.println("환율이 0");
			return null;
		}
		
		// new BigDecimal(0.01); double 타입 그대로 초기화시 기대값과 다른값을 가짐
		
		// String 형태로 생성
		BigDecimal test1 = new BigDecimal("1.12");
		// double 형태로 생성
		BigDecimal test2 = BigDecimal.valueOf(1.12);


		// amount / exchangeRate -> 소숫점 2자리 까지 표현, 반올림 형식
		BigDecimal convertedAmount = amount.divide(exchangeRate, 2, RoundingMode.HALF_UP);
		return convertedAmount;
	}
	
}
