package ncbank.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncbank.beans.ExchangeRateBean;
import ncbank.dao.ExchangeRateDAO;
import ncbank.utility.DateManager;
import ncbank.utility.ExchangeRateDTO;
import ncbank.utility.ExchangeRateUtility;

@Service
public class ExchangeRateService {

	@Autowired
	private ExchangeRateDAO exchangeRateDAO;
	@Autowired
	private ExchangeRateUtility exchangeRateUtils;

	@Autowired
	private DateManager dateManager;

	@Autowired
	private CodeMoneyService codeMoneyService;
	
	
	public List<ExchangeRateBean> getAllExchangeRate() {
		List<ExchangeRateBean> allExchangeRateList = exchangeRateDAO.getAllExchangeRate();
		if (null == allExchangeRateList || allExchangeRateList.isEmpty()) {
			System.out.println("ExchangeRateService getAllExchangeRate()");
			System.out.println("allExchangeRateList error");
			return null;
		}
	   	return allExchangeRateList;
	}
	
	// DB에 저장되어 있는 데이터중 가장 최신의 환율 데이터 가져오기 (크롤링x)
	public List<ExchangeRateBean> getFinalExchangeRate() {
		List<ExchangeRateBean> finalExchangeRateList = exchangeRateDAO.getFinalExchangeRate();
		if (null == finalExchangeRateList || finalExchangeRateList.isEmpty()) {
			System.out.println("ExchangeRateService getFinalExchangeRate()");
			System.out.println("finalExchangeRateList error");
			return null;
		}
    	return finalExchangeRateList;
    }
	
	// 최신 환율 데이터중 원하는 동화의 데이터
    public ExchangeRateBean findFinalExchangeRate(String code_money) {
    	return exchangeRateDAO.findFinalExchangeRate(code_money);
    }
	
	// 현재 날짜 기준으로 최종환율고시일의 환율을 찾아옴 (DB탐색후 없으면 크롤링)
	public List<ExchangeRateBean> findFinalExchangeRate() {
		
		String exchangeRateDate = dateManager.getCurrentDate("yyyyMMdd");
		List<ExchangeRateBean> beanList = getExchangeRate(exchangeRateDate);

		int count = 0;
		while (null == beanList) {
			exchangeRateDate = dateManager.getMoveDate(exchangeRateDate, -1, "yyyyMMdd");
			beanList = getExchangeRate(exchangeRateDate);
			
			System.out.println("exchangeRateDate : " + exchangeRateDate);
			
			if (null == beanList) {
				System.out.println("while null count : " + (++count));
			} else {
				count = 0;
			}
			
			if (7 < count) { // 무한루프 방지
				break;
			}
		}
		if (null == beanList) {
			
			System.out.println("ExchangeRateService findFinalExchangeRate()");
			System.out.println("beanList is null");
			System.out.println("exchangeRateDate : " + exchangeRateDate);
			return null;
		}
		
		return beanList;
	}

	// 지정 날짜 환율정보를 DB에서 탐색해 가져옴 (탐색 후 없으면 크롤링)
	public List<ExchangeRateBean> getExchangeRate(String strDate) {
		List<ExchangeRateBean> beanList = exchangeRateDAO.getExchangeRate(strDate);
		System.out.println("beanList : " + beanList);
		if (null == beanList || beanList.isEmpty()) { // DB에 등록된 환율정보가 없으면 크롤링
			System.out.println("ExchangeRateService getExchangeRate()");
			System.out.println("beanList is null - not get DB data");
			// 매번크롤링하는거 좀 그런거같음 DB에 싹 저장하고 쓴다? -> 나중에 개선좀
			beanList = fetchExchangeRate(strDate);
		}

		return beanList;
	}
	
	// 범위 날짜 환율 정보 - DB에서 가져오기
	 public List<ExchangeRateBean> getDateRangeExchangeRate(String startDate, String endDate) {
		 System.out.println("ExchangeRateService getExchangeRate()");
		 System.out.println("startDate : " + startDate);
		 System.out.println("endDate : " + endDate);
		 
		 List<ExchangeRateBean> DateRangeBeanList = exchangeRateDAO.getDateRangeExchangeRate(startDate, endDate);
		 if (null == DateRangeBeanList || DateRangeBeanList.isEmpty()) {
			 System.out.println("ExchangeRateService getExchangeRate()");
			 System.out.println("beanList is null - not get DB data");
			 return null;
		 }
		 return DateRangeBeanList;
	 }

	// 지정 날짜 환율정보 크롤링 후 DB에 추가
	private List<ExchangeRateBean> fetchExchangeRate(String strDate) {
		// 여기서 null 을 뱉으면 API에서 제공 불가능한 날짜의 환율
		List<ExchangeRateBean> beanList = exchangeRateUtils.fetchExchangeRateList(strDate);
		
		if (null == beanList || beanList.isEmpty()) { // 크롤링한 환율정보가 없을경우
			System.out.println("ExchangeRateService fetchExchangeRate()");
			System.out.println("beanList is null");
			return null;
		}

		for (ExchangeRateBean bean : beanList) { // 환율정보를 DB추가
			exchangeRateDAO.addExchangeRate(bean);
		}
		return beanList;
	}
	
	// start ~ end 기간의 환율정보 DB에 추가 - 백 데이터 추가용
	public void addRateInquiry_DateRange(String startDate, String endDate) {

		String exchangeRateDate = startDate;
		
		int count = 0;
		while (!dateManager.isDatesEqual(exchangeRateDate, endDate)) {
			List<ExchangeRateBean> beanList = getExchangeRate(exchangeRateDate);
			exchangeRateDate = dateManager.getMoveDate(exchangeRateDate, 1, "yyyyMMdd");
			System.out.println("exchangeRateDate : " + exchangeRateDate);
			if (null == beanList) {
				System.out.println("while null count : " + (++count));	
				System.out.println("ExchangeRateService addRateInquiry_DateRange()");
				System.out.println("beanList is null - date : " + exchangeRateDate);
			} else {
				count = 0;
			}
			if (7 < count) { // 무한루프 방지
				break;
			}
		}
	}

	/* 환율정보Bean을 출력용 DTO로 전환 */
	// List<ExchangeRateBean> -> List<ExchangeRateDTO>
	public List<ExchangeRateDTO> convertExchangeDTOList(List<ExchangeRateBean> beanList) {
		if (null == beanList || beanList.isEmpty()) {
			System.out.println("ExchangeRateService convertExchangeDTOList()");
			System.out.println("beanList error");
			return null;
		}

		List<ExchangeRateDTO> rateDtoList = new ArrayList<>();

		for (ExchangeRateBean rateBean : beanList) {
			ExchangeRateDTO dto = new ExchangeRateDTO();
			exchangeBeanToDto(rateBean, dto);
			dto.setCode_money_name(codeMoneyService.getCodeMoneyName(rateBean.getCode_money()));
			rateDtoList.add(dto);
		}

		return rateDtoList;
	}
	
	// ExchangeRateBean -> ExchangeRateDTO
	public ExchangeRateDTO convertExchangeDTO(ExchangeRateBean rateBean) {
		if (null == rateBean) {
			System.out.println("ExchangeRateService convertExchangeDTO()");
			System.out.println("bean error");
			return null;
		}

		ExchangeRateDTO rateDto = new ExchangeRateDTO();
		
		exchangeBeanToDto(rateBean, rateDto);
		String codeMoneyName = codeMoneyService.getCodeMoneyName(rateBean.getCode_money());
		if (null == codeMoneyName) {
			System.out.println("ExchangeRateService convertExchangeDTO()");
			System.out.println("codeMoneyName is null");
		}
		rateDto.setCode_money_name(codeMoneyName);
		
		return rateDto;
	}

	// ExchangeRateBean -> ExchangeRateDTO로 데이터 옮기기
	private void exchangeBeanToDto(ExchangeRateBean bean, ExchangeRateDTO dto) {
		dto.setCode_date(bean.getCode_date());
		dto.setCode_money(bean.getCode_money());
		dto.setEx_buy(bean.getEx_buy());
		dto.setEx_sell(bean.getEx_sell());
		dto.setEx_standard(bean.getEx_standard());
		dto.setCode_date_str(dateManager.parseDateToString(bean.getCode_date(), "yyyy-MM-dd"));
	}

}
