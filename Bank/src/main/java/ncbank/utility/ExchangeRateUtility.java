package ncbank.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ncbank.beans.ExchangeRateBean;

@Repository // Bean으로 등록
public class ExchangeRateUtility {

	/* 요청 URL */
	private final String requestURL = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON";
	/* 요청 변수 */
	// 인증키
	private final String authKey = "V8mETPuk3L60CYJjpfWW3nssE0ZbRgiY"; // 실제 인증 키로 교체
	// 검색 요청 API타입 : AP01 - 환율, AP02 - 대출금리, AP03 - 국제금리
	private final String dataType = "AP01";

    @Autowired
    private DateManager dateManager;

	// 검색 요청 날짜 : 20150101 (DEFAULT)현재일
	public List<ExchangeRateBean> fetchExchangeRateList(String searchDate) {

        HttpURLConnection con = null;
        List<ExchangeRateBean> beanList = null;
        int responseCode = -1;
        
        System.out.println("ExchangeRateUtility - fetchExchangeRateList()");
        System.out.println("searchDate : " + searchDate);
        Date date = dateManager.parseStringToDate(searchDate, "yyyyMMdd");
        if (null == date) {
            System.out.println("searchDate null");
            return null;
        }

        try {
            // 요청 URL 설정
            String strUrl = buildRequestURL(searchDate);
            System.out.println("strUrl : " + strUrl);
            URL url = new URL(strUrl);
            con = (HttpURLConnection) url.openConnection();
            
            if (null == con) {
            	System.out.println("HttpURLConnection is null");
            }
            
            System.out.println("con : " + con);
            
            // 요청 초기 설정
            con.setRequestMethod("GET");
            
            System.out.println("여기 1");
            // 10 초
            con.setConnectTimeout(10000); // 연길시도 시간
            con.setReadTimeout(10000); // 데이터 요청 시간
            
            System.out.println("여기 2");
            
            // HTTP 응답 상태코드 가져옴 (보통 300 이상의 상태코드는 실패로 간주)
            responseCode = con.getResponseCode();
            System.out.println("여기 3");
            System.out.println("responseCode : " + responseCode);
            System.out.println("여기 4");
            BufferedReader br = null;

			if (responseCode > 299) { // HTTP 에러
				br = new BufferedReader(new InputStreamReader(con.getErrorStream(), StandardCharsets.UTF_8));
				System.err.println("HTTP 연결 실패. 상태 코드: " + responseCode);
				return null;
			} else { // 성공
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
				System.out.println("HTTP 연결 성공");
			}

			String inputLine;
			StringBuilder responseContent = new StringBuilder();
			while ((inputLine = br.readLine()) != null) {
				responseContent.append(inputLine);
			}
			br.close();

			if (0 >= responseContent.length()) { // 응답 내용 없을때 - 보통 지원하지 않는 날짜에서의 요청
				System.out.println("해당 날짜에 대한 환율 데이터를 찾을 수 없습니다.");
				return null;
			}

            beanList = new ArrayList<ExchangeRateBean>();
            // 해당날짜에 대한 달러, 엔 등 모든 통화 정보가 들어있음 -> array
            JSONArray resultArray = new JSONArray(responseContent.toString());
            if (0 >= resultArray.length()) {
            	System.out.println("resultArray length Error");
            	return null;
            }
            System.out.println("resultArray length : " + resultArray.length());
            
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject resultObject = resultArray.getJSONObject(i);
                ExchangeRateBean bean = new ExchangeRateBean();

				// RESULT - 조회 결과 (1: 성공, 2: DATA 코드 오류, 3: 인증코드 오류, 4: 일일제한 횟수 마감)
				int result = resultObject.getInt("result");
				System.out.println("조회 결과: " + result);

				if (1 == result) {
					System.out.println("1 : 응답 성공");

					setupExchangeRateBean(bean, resultObject, searchDate);

					System.out.println(bean.getCode_money() + " | " + bean.getCode_date());
					beanList.add(bean);
					System.out.println("add");

				} else if (2 == result) {
					System.out.println("2 : DATA 코드 오류");
				} else if (3 == result) {
					System.out.println("3 : 인증코드 오류");
				} else if (4 == result) {
					System.out.println("4 : 일일제한 횟수 마감");
				} else {
					System.out.println("알 수 없는 응답코드");
				}

				System.out.println("==========");

			} // for

        } catch (MalformedURLException e) {
            System.err.println("잘못된 URL 형식." + e.getMessage());
            System.out.println("responseCode : " + responseCode);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("입출력 오류. : " + e.getMessage());
            System.out.println("responseCode : " + responseCode);
            e.printStackTrace();
        } catch (Exception e) {
        	System.err.println("일반적 에외." + e.getMessage());
        	 System.out.println("responseCode : " + responseCode);
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

		return beanList;
	}

	private String buildRequestURL(String searchDate) {
		return requestURL + "?authkey=" + authKey + "&searchdate=" + searchDate + "&data=" + dataType;
	}

	private void setupExchangeRateBean(ExchangeRateBean bean, JSONObject resultObject, String searchDate)
			throws ParseException {
		/*
		 * get으로 가져올때 대문자면 오류남.
		 * {"result":1,"cur_unit":"AED","ttb":"371.3","tts":"378.81",
		 * "deal_bas_r":"375.06","bkpr":"375","yy_efee_r":"0","ten_dd_efee_r":"0",
		 * "kftc_bkpr":"375","kftc_deal_bas_r":"375.06","cur_nm":"아랍에미리트 디르함"}
		 */

        // 일시
        bean.setCode_date(dateManager.parseStringToDate(searchDate, "yyyyMMdd"));
        
        // 여기서 (100) 을 검사해서 달려있는애들은 따로 데이터를 추가해야될듯?
        // cur_unit - 통화 코드
        String curUnit = resultObject.getString("cur_unit").replace("(100)", "");
        bean.setCode_money(curUnit);

		// cur_nm 국가/통화명
		String curNm = resultObject.getString("cur_nm");

		// float 치환하는데 , 때문에 에러남
		// 1. replace([기존문자], [바꿀문자]) - 단순 컴마만 제거 시 | 2. NumberFormat - 숫자다룰시 좋음
		NumberFormat format = NumberFormat.getInstance(Locale.getDefault());

		// ttb 송금 받을때
		String ttb = resultObject.getString("ttb").replace(",", "");
		bean.setEx_buy(format.parse(ttb).floatValue());

		// tts 송금 보낼때
		String tts = resultObject.getString("tts");
		bean.setEx_sell(format.parse(tts).floatValue());

		// deal_bas_r 매매 기준율
		String dealBasR = resultObject.getString("deal_bas_r".toLowerCase());
		bean.setEx_standard(format.parse(dealBasR).floatValue());

		/* 아직 사용하진 않는데 제공 데이터들 */
		// bkpr 장부가격
		String bkpr = resultObject.getString("bkpr");

		// yy_efee_r 년환가로율
		String yyEfeeR = resultObject.getString("yy_efee_r");

		// ten_dd_efee_r 10일 환가로료율
		String tenDdEfeeR = resultObject.getString("ten_dd_efee_r");

		// kftc_bkpr 서울외국환중개 장부가격
		String kftcBkpr = resultObject.getString("kftc_bkpr");

		// kftc_deal_bas_r 서울외국환중개 매매기준율
		String kftcDealBasR = resultObject.getString("kftc_deal_bas_r");

		// 확인용 출력
		System.out.println("통화코드: " + curUnit);
		System.out.println("국가/통화명: " + curNm);
		System.out.println("전신환(송금) 받으실때: " + ttb);
		System.out.println("전신환(송금) 보내실때: " + tts);
		System.out.println("매매 기준율: " + dealBasR);
		System.out.println("장부가격: " + bkpr);
		System.out.println("년환가료율: " + yyEfeeR);
		System.out.println("10일환가료율: " + tenDdEfeeR);
		System.out.println("서울외국환중개 장부가격: " + kftcBkpr);
		System.out.println("서울외국환중개 매매기준율: " + kftcDealBasR);
	}

}
