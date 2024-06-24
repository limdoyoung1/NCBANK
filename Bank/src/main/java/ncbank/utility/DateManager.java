package ncbank.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

// @Component 로 Spring한테 알려 Bean으로 등록하면 싱글톤처럼 사용가능
@Component
public class DateManager {

    private DateManager() {}

    // pattern ex) yyyyMMdd, yyyy.MM.dd, yyyy-MM.dd
    /* 현재날짜 가져오기 */
    public String getCurrentDate(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }
    
    // 현재날짜를 기준으로 이동시킨 날짜 가져오기
    public String getMoveDate(int moveDay, String pattern) {
    	// 오늘날짜
    	Date today = new Date();
    	// Calendar 인스턴스에 오늘날짜 설정
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(today);
    	
    	// moveDay 만큼 이동한 날짜
    	calendar.add(Calendar.DAY_OF_MONTH, moveDay);
    	Date moveDate = calendar.getTime();
    	
    	// Date -> String
    	return parseDateToString(moveDate, pattern);
    }
    
    // 지정한날짜를 기준으로 일을 이동시킨 날짜 가져오기
    public String getMoveDate(String strInputDate, int moveDay, String pattern) {
    	
    	// 지정한 날짜
    	Date inputDate = parseStringToDate(strInputDate, pattern);
    	// Calendar 인스턴스에 지정한 날짜 설정
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(inputDate);
    	
    	// moveDay 만큼 이동한 날짜
    	if (0 != moveDay) {
    		calendar.add(Calendar.DAY_OF_MONTH, moveDay);
    	}
   
    	Date moveDate = calendar.getTime();
    	
    	// Date -> String
    	String moveDateStr = parseDateToString(moveDate, pattern);
    	
    	
    	return moveDateStr;
    }
    
    // 지정한 날짜를 기준으로 월, 일을 이동시킨 날짜 가져오기 (기준날짜, 월, 일, 패턴)
    public String getMoveDate(String strInputDate, int moveMonth, int moveDay, String pattern) {
        
        Date inputDate = parseStringToDate(strInputDate, pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        
        // 월 이동
        if (moveMonth != 0) {
            calendar.add(Calendar.MONTH, moveMonth);
        }
        // 일 이동
        if (moveDay != 0) {
            calendar.add(Calendar.DAY_OF_MONTH, moveDay);
        }
        
        Date moveDate = calendar.getTime();
        
        // Date -> String
        String moveDateStr = parseDateToString(moveDate, pattern);
        
        return moveDateStr;
    }
    
    /* String -> Date */
    public Date parseStringToDate(String strDate, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return formatter.parse(strDate);
        } catch (Exception e) {
        	System.out.println("ExchangeNoticController parseStringToDate() - catch");
            System.out.println("parseDate Error : " + strDate);
            e.printStackTrace();
        }
        return null;
    }

    /* Date -> String */
    public String parseDateToString(Date date, String pattern) {
    	if (null == date) {
    		System.out.println("ExchangeNoticController parseDateToString()");
    		System.out.println("date is null");
    		return null;
    	}
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
    
    /* String의 pattern만 변경 | ex) 20240604 -> 2024-06-04 */
    public String changeStringDateFormat(String strDate, String inputPattern, String outputPattern) {
    	
    	// 입력 날짜 형식
    	SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
    	
    	// 출력 날짜 형식
    	SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
    	
    	try {
			// String -> Date
    		Date date = inputFormat.parse(strDate);
			// Date -> String
    		String outStrDate = outputFormat.format(date);
    		return outStrDate;
		} catch (Exception e) {
			System.out.println("ExchangeNoticController changeStringDateFormat() - catch");
			e.printStackTrace();
		}
    	
    	return null;
    }
    /* DATE의 pattern만 변경 | ex) 20240604 -> 2024-06-04 */
    public Date changeDateFormat(Date date, String inputPattern, String outputPattern) {
    	
    	// 입력 날짜 형식
    	SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
    	
    	// 출력 날짜 형식
    	SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
    	
    	try {
			// Date -> String
    		String strDate = inputFormat.format(date);
			// Date -> String
    		Date outDate = outputFormat.parse(strDate);
    		return outDate;
		} catch (Exception e) {
			System.out.println("ExchangeNoticController changeDateFormat() - catch");
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    // Date1 과 2 가 같은지 비교
    public boolean isDatesEqual(String strDate1, String strDate2) {
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    	boolean isEqual = false;
    	
    	try {
    		Date date1 = format.parse(strDate1);
        	Date date2 = format.parse(strDate2);
        	isEqual = date1.equals(date2);
		} catch (Exception e) {
			System.out.println("ExchangeNoticController isDatesEqual() - catch");
			e.printStackTrace();
		}
    	
    	return isEqual;
    }
    
}
