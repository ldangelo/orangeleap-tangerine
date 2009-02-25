package com.mpower.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.PaymentHistoryDao;
import com.mpower.domain.model.PaymentHistory;

@Repository("paymentHistoryDAO")
public class IBatisPaymentHistoryDao extends AbstractIBatisDao implements PaymentHistoryDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
  
    @Autowired
    public IBatisPaymentHistoryDao(SqlMapClient sqlMapClient) {
        super();
        super.setSqlMapClient(sqlMapClient);
    }

	@Override
	public PaymentHistory addPaymentHistory(PaymentHistory paymentHistory) {
		
        if (logger.isDebugEnabled()) {
            logger.debug("addPaymentHistory:"+paymentHistory.getPerson().getId());
        }
        
        getSqlMapClientTemplate().insert("INSERT_PAYMENT_HISTORY", paymentHistory);
        return paymentHistory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentHistory> readPaymentHistory(Long personId) {
        Map<String, Object> params = setupParams();
		params.put("personId", personId);
		List<PaymentHistory> payments =getSqlMapClientTemplate().queryForList("SELECT_PAYMENT_HISTORY_FOR_PERSON", params);
        return payments;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentHistory> readPaymentHistoryBySite() {
        Map<String, Object> params = setupParams();
		List<PaymentHistory> payments =getSqlMapClientTemplate().queryForList("SELECT_PAYMENT_HISTORY_FOR_SITE", params);
        return payments;
	}
}
