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
import com.orangeleap.tangerine.dao.PaymentHistoryDao;
import com.orangeleap.tangerine.domain.PaymentHistory;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    @SuppressWarnings("unchecked")
    @Override
    public List<PaymentHistory> readAllPaymentHistoryByConstituentId(Long constituentId, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllPaymentHistoryByConstituentId: constituentId = " + constituentId + " sortPropertyName = " + sortPropertyName +
                    " direction = " + direction + " start = " + start + " limit = " + limit);
        }
        Map<String, Object> params = setupSortParams(StringConstants.PAYMENT_HISTORY, "PAYMENT_HISTORY.PAYMENT_HISTORY_RESULT", sortPropertyName, direction, start, limit, locale);
        params.put(StringConstants.CONSTITUENT_ID, constituentId);

        return getSqlMapClientTemplate().queryForList("SELECT_LIMITED_PAYMENT_HISTORY_BY_CONSTITUENT_ID", params);
    }

    @Override
    public int readCountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentId: constituentId = " + constituentId);
        }
        Map<String,Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return (Integer) getSqlMapClientTemplate().queryForObject("SELECT_PAYMENT_HISTORY_COUNT_BY_CONSTITUENT_ID", params);
    }
}
