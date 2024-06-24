package ncbank.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ncbank.beans.TransferBean;
import ncbank.mapper.TransferMapper;

@Repository
public class TransferDAO {
	@Autowired
	private TransferMapper transferMapper;

	public void addTransfer(TransferBean transferBean) {
		transferMapper.addTransfer(transferBean);
	}

	public List<TransferBean> getTransfer(int userNum, String account) {
		return transferMapper.getTransfer(userNum, account);
	}
}
