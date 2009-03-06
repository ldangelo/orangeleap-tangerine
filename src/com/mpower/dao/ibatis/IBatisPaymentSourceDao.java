package com.mpower.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.PaymentSourceDao;
import com.mpower.domain.model.PaymentSource;

/** 
 * Corresponds to the PAYMENT_SOURCE table
 */
@Repository("paymentSourceDAO")
public class IBatisPaymentSourceDao extends AbstractIBatisDao implements PaymentSourceDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisPaymentSourceDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @Override
    public PaymentSource maintainPaymentSource(PaymentSource paymentSource) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainPaymentSource: paymentSource = " + paymentSource);
        }
        return (PaymentSource)insertOrUpdate(paymentSource, "PAYMENT_SOURCE");
    }

    @Override
    public PaymentSource readPaymentSourceById(Long paymentSourceId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPaymentSourceById: paymentSourceId = " + paymentSourceId);
        }
        Map<String, Object> params = setupParams();
        params.put("paymentSourceId", paymentSourceId);
        return (PaymentSource)getSqlMapClientTemplate().queryForObject("SELECT_PAYMENT_SOURCE_BY_ID", params);
    }

    @Override
    public PaymentSource readPaymentSourceByProfile(Long constituentId, String profile) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPaymentSourceByProfile: constituentId = " + constituentId + " profile = " + profile);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("profile", profile);
        return (PaymentSource)getSqlMapClientTemplate().queryForObject("SELECT_PAYMENT_SOURCE_BY_CONSTITUENT_ID_PROFILE", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PaymentSource> readActivePaymentSources(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readActivePaymentSources: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_ACTIVE_PAYMENT_SOURCES_BY_CONSTITUENT_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PaymentSource> readActivePaymentSourcesByTypes(Long constituentId, List<String> paymentTypes) {
        if (logger.isDebugEnabled()) {
            logger.debug("readActivePaymentSourcesByTypes: constituentId = " + constituentId + " paymentTypes = " + paymentTypes);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("paymentTypes", paymentTypes);
        return getSqlMapClientTemplate().queryForList("SELECT_ACTIVE_PAYMENT_SOURCES_BY_CONSTITUENT_ID_TYPES", params);
    }
}