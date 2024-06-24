package ncbank.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ncbank.beans.AccountBean;
import ncbank.beans.CodeBankBean;
import ncbank.beans.CreateExchangeBean;
import ncbank.beans.ExchangeBean;
import ncbank.mapper.ExchangeMapper;

@Repository
public class ExchangeDao {
	
	@Autowired
    private ExchangeMapper exchangeMapper;
	
	
	
	// api 데이터 exchange에 저장된거 가져오기.
    public List<ExchangeBean> getExchangeList(String cuDate) {
    	
    		
        	List<ExchangeBean> exchangeAskList = exchangeMapper.getExchangeList(cuDate);
	    	return exchangeAskList;

    }
    
    // code_bank - code_bank_name 엮은 데이터 가져오기
    public List<CodeBankBean> getCodeBankName(){
    	List<CodeBankBean> codeBankNameList = exchangeMapper.getCodeBankName();
    	return codeBankNameList;
    }
    
    // user_name이랑 account 데이터 가져오기
    public List<AccountBean> getAccount(int user_num){
    	List<AccountBean> getAccountList = exchangeMapper.getAccount(user_num);
    	return getAccountList;
    }
    
    // ExchangeAsk 에서 신청한 데이터를 Trade에 넣기
    public void setTradeByExchangeAsk(CreateExchangeBean createExchangeBean){
    	exchangeMapper.setTradeByExchangeAsk(createExchangeBean);
    	
    }
    
    // --Wallet이 비어있을 때 오라클에 미리 입력해놓은 Trade테이블 데이터를 Wallet테이블에 넣어놓기
    // #1. Wallet테이블에 값이 있나 없나 조건 
    public boolean firstCheckWalletExists() {
        return exchangeMapper.firstCheckWalletExists() > 0;
    }
    
    // #2. Wallet테이블에 데이터 넣기
    public void insertInitialWalletData() {
    	exchangeMapper.insertInitialWalletData();
    }
    
    
    // --ExchangeAsk에서 얻어낸 정보(createExchangeBean)를 Wallet 테이블에 집어넣기
    // #1. 조건문
    public boolean checkWalletExists(int user_num, String code_money) {
        return exchangeMapper.checkWalletExists(user_num, code_money) > 0;
    }
    
    // #2-1. 회원번호랑 통화코드가 둘 다 일치하면 UPDATE
    public void setWalletUpdateByExchangeAsk(CreateExchangeBean createExchangeBean) {
    	exchangeMapper.setWalletUpdateByExchangeAsk(createExchangeBean);
    }
    
    // #2-2. 둘 중 하나라도 틀리면 INSERT
    public void  setWalletInsertByExchangeAsk(CreateExchangeBean createExchangeBean) {
    	exchangeMapper.setWalletInsertByExchangeAsk(createExchangeBean);
    }

    /*
    public List<CodeBankBean> searchBankByKeyword(String keyword) {
        try {
            return exchangeMapper.searchBankByKeyword(keyword);
        } catch (Exception e) {
            e.printStackTrace();  // 로그에 예외 출력
            return new ArrayList<>();  // 빈 리스트 반환하여 클라이언트에 에러를 전송하지 않음
        }
    }
    */
    
   
    
}
