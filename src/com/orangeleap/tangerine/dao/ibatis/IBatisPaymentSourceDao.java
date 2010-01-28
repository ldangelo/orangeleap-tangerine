/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.dao.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.PaymentSourceDao;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/** 
 * Corresponds to the PAYMENT_SOURCE table
 */
@Repository("paymentSourceDAO")
public class IBatisPaymentSourceDao extends AbstractIBatisDao implements PaymentSourceDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisPaymentSourceDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @Override
    public PaymentSource maintainPaymentSource(PaymentSource paymentSource) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainPaymentSource: paymentSourceId = " + paymentSource.getId());
        }
        return (PaymentSource)insertOrUpdate(paymentSource, "PAYMENT_SOURCE");
    }

    @Override
    public PaymentSource readPaymentSourceById(Long paymentSourceId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaymentSourceById: paymentSourceId = " + paymentSourceId);
        }
        Map<String, Object> params = setupParams();
        params.put("paymentSourceId", paymentSourceId);
        return (PaymentSource)getSqlMapClientTemplate().queryForObject("SELECT_PAYMENT_SOURCE_BY_ID", params);
    }

    @Override
    public PaymentSource readPaymentSourceByProfile(Long constituentId, String profile) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaymentSourceByProfile: constituentId = " + constituentId + " profile = " + profile);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("profile", profile);
        return (PaymentSource)getSqlMapClientTemplate().queryForObject("SELECT_PAYMENT_SOURCE_BY_CONSTITUENT_ID_PROFILE", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PaymentSource> readAllPaymentSources(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllPaymentSources: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_PAYMENT_SOURCES_BY_CONSTITUENT_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PaymentSource> readActivePaymentSources(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readActivePaymentSources: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_ACTIVE_PAYMENT_SOURCES_BY_CONSTITUENT_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PaymentSource> readActivePaymentSourcesByTypes(Long constituentId, List<String> paymentTypes) {
        if (logger.isTraceEnabled()) {
            logger.trace("readActivePaymentSourcesByTypes: constituentId = " + constituentId + " paymentTypes = " + paymentTypes);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("paymentTypes", paymentTypes);
        return getSqlMapClientTemplate().queryForList("SELECT_ACTIVE_PAYMENT_SOURCES_BY_CONSTITUENT_ID_TYPES", params);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PaymentSource> readExistingCreditCards(String creditCardNumber) {
        if (logger.isTraceEnabled()) {
            logger.trace("readExistingCreditCards: creditCardNumber = " + creditCardNumber);
        }
        Map<String, Object> params = setupParams();
        params.put("creditCardNumberEncrypted", creditCardNumber);
        return getSqlMapClientTemplate().queryForList("SELECT_BY_CREDIT_CARD_NUM", params);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PaymentSource> readExistingAchAccounts(String achAccountNum, String achRoutingNum) {
        if (logger.isTraceEnabled()) {
            logger.trace("readExistingAchAccounts: achAccountNum = " + achAccountNum + " achRoutingNum = " + achRoutingNum);
        }
        Map<String, Object> params = setupParams();
        params.put("achAccountNumberEncrypted", achAccountNum);
        params.put("achRoutingNumber", achRoutingNum);
        return getSqlMapClientTemplate().queryForList("SELECT_BY_ACH_NUM", params);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentSource> readExistingCheckAccounts(String checkAccountNum, String checkRoutingNum) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("readExistingCheckAccounts: checkAccountNum = " + checkAccountNum + " checkRoutingNum = " + checkRoutingNum);
	    }
	    Map<String, Object> params = setupParams();
	    params.put("checkAccountNumberEncrypted", checkAccountNum);
	    params.put("checkRoutingNumber", checkRoutingNum);
	    return getSqlMapClientTemplate().queryForList("SELECT_BY_CHECK_NUM", params);
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<PaymentSource> readAllPaymentSourcesByConstituentId(Long constituentId, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllPaymentSourcesByConstituentId: constituentId = " + constituentId + " sortPropertyName = " + sortPropertyName +
                    " direction = " + direction + " start = " + start + " limit = " + limit);
        }
        Map<String, Object> params = setupSortParams(StringConstants.PAYMENT_SOURCE, "PAYMENT_SOURCE.PAYMENT_SOURCE_LIST_RESULT", sortPropertyName, direction, start, limit, locale);
        params.put(StringConstants.CONSTITUENT_ID, constituentId);

        return getSqlMapClientTemplate().queryForList("SELECT_LIMITED_PAYMENT_SOURCES_BY_CONSTITUENT_ID", params);
    }

    @Override
    public int readCountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentId: constituentId = " + constituentId);
        }
        Map<String,Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return (Integer) getSqlMapClientTemplate().queryForObject("SELECT_PAYMENT_SOURCE_COUNT_BY_CONSTITUENT_ID", params);
    }
}