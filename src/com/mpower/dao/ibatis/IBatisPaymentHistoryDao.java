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
        super(sqlMapClient);
    }

	@Override
	public PaymentHistory addPaymentHistory(PaymentHistory paymentHistory) {
        if (logger.isDebugEnabled()) {
            logger.debug("addPaymentHistory: personId = " + paymentHistory.getPerson().getId());
        }
        return (PaymentHistory)insertOrUpdate(paymentHistory, "PAYMENT_HISTORY");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentHistory> readPaymentHistoryByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPaymentHistoryByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
		params.put("constituentId", constituentId);
		return getSqlMapClientTemplate().queryForList("SELECT_PAYMENT_HISTORY_FOR_CONSTITUENT_ID", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentHistory> readPaymentHistoryBySite() {
        if (logger.isDebugEnabled()) {
            logger.debug("readPaymentHistoryBySite:");
        }
        Map<String, Object> params = setupParams();
		return getSqlMapClientTemplate().queryForList("SELECT_PAYMENT_HISTORY_FOR_SITE", params);
	}
}
