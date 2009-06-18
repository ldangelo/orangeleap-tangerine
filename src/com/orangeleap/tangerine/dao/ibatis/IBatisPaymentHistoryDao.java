package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.PaymentHistoryDao;
import com.orangeleap.tangerine.domain.PaymentHistory;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@Repository("paymentHistoryDAO")
public class IBatisPaymentHistoryDao extends AbstractIBatisDao implements PaymentHistoryDao {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
  
    @Autowired
    public IBatisPaymentHistoryDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

	@Override
	public PaymentHistory addPaymentHistory(PaymentHistory paymentHistory) {
        if (logger.isTraceEnabled()) {
            logger.trace("addPaymentHistory: constituentId = " + paymentHistory.getConstituent().getId());
        }
        return (PaymentHistory)insertOrUpdate(paymentHistory, "PAYMENT_HISTORY");
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginatedResult readPaymentHistoryByConstituentId(Long constituentId, SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaymentHistoryByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        sortinfo.addParams(params);

		params.put("constituentId", constituentId);

        List rows = getSqlMapClientTemplate().queryForList("SELECT_PAYMENT_HISTORY_FOR_CONSTITUENT_ID_PAGINATED", params);
        Long count = (Long)getSqlMapClientTemplate().queryForObject("PAYMENT_HISTORY_FOR_CONSTITUENT_ID_ROWCOUNT",params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PaginatedResult readPaymentHistoryBySite(SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaymentHistoryBySite:");
        }
        Map<String, Object> params = setupParams();
        sortinfo.addParams(params);

        List rows = getSqlMapClientTemplate().queryForList("SELECT_PAYMENT_HISTORY_FOR_SITE_PAGINATED", params);
        Long count = (Long)getSqlMapClientTemplate().queryForObject("PAYMENT_HISTORY_FOR_SITE_ROWCOUNT",params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
	}
}
