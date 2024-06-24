package ncbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncbank.beans.AccountBean;
import ncbank.beans.CodeBankBean;
import ncbank.beans.CreateExchangeBean;
import ncbank.beans.ExchangeBean;
import ncbank.dao.ExchangeDao;

@Service
public class ExchangeService {
	
	@Autowired
    private ExchangeDao exchangeDao;
	
	// api 데이터 exchange에 저장된거 가져오기.
    public List<ExchangeBean> getExchangeList(String cuDate) {
    	
    	
    	List<ExchangeBean> exchangeAskList = exchangeDao.getExchangeList(cuDate);
    		
        return exchangeAskList;

    }
    
    // code_bank - code_bank_name 엮은 데이터 가져오기
    public List<CodeBankBean> getCodeBankName(){
    	List<CodeBankBean> codeBankNameList = exchangeDao.getCodeBankName();
    	return codeBankNameList;
    }
    
    // user_name이랑 account 데이터 가져오기
    public List<AccountBean> getAccount(int user_num){
    	List<AccountBean> getAccountList = exchangeDao.getAccount(user_num);
    	return getAccountList;
    }
    
    
    public void setTradeByExchangeAsk(CreateExchangeBean createExchangeBean) {
    	// Trade 테이블에 데이터 넣기
    	exchangeDao.setTradeByExchangeAsk(createExchangeBean);
    	// 밑에꺼 실행(Wallet 테이블에 데이터 넣기)
    	setUpdateOrInsertWallet(createExchangeBean);
    	
    }
    
    // ExchangeAsk에서 얻어낸 정보(createExchangeBean)를 Wallet 테이블에 집어넣기
    
    private void setUpdateOrInsertWallet(CreateExchangeBean createExchangeBean) {
        // #1. 지갑에 해당 회원번호와 통화 코드가 있는지 확인
        boolean walletExists = exchangeDao.checkWalletExists(createExchangeBean.getUser_num(), createExchangeBean.getCode_money());
        
        if (walletExists) {
            // #2-1. 회원번호랑 통화코드가 둘 다 일치하면 UPDATE
            exchangeDao.setWalletUpdateByExchangeAsk(createExchangeBean);
        } else {
            // #2-2. 둘 중 하나라도 틀리면 INSERT
            exchangeDao.setWalletInsertByExchangeAsk(createExchangeBean);
        }
    }
    
    // Wallet이 비어있을 때 오라클에 미리 입력해놓은 Trade테이블 데이터를 Wallet테이블에 넣어놓기
    // #1. Wallet테이블에 값이 있나 없나 조건
    public void setCheckAndInsertFirstWalletData() {
        
    	boolean firstWalletExists = exchangeDao.firstCheckWalletExists();
        
        if(!firstWalletExists) {
        	// #2. Wallet테이블에 데이터 넣기
        	exchangeDao.insertInitialWalletData();
        } 
        
    }
    
    
    /*
    public List<CodeBankBean> searchBankByKeyword(String keyword) {
        System.out.println("검색어 서비스: " + keyword);
        try {
            List<CodeBankBean> results = exchangeDao.searchBankByKeyword(keyword);
            System.out.println("검색 결과 서비스: " + results);
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;  // 예외를 다시 던져서 상위 레이어에서 처리
        }
    }
    */
    
 
}
