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
import com.orangeleap.tangerine.dao.CommunicationHistoryDao;
import com.orangeleap.tangerine.domain.CommunicationHistory;
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

@Repository("communicationHistoryDAO")
public class IBatisCommunicationHistoryDao extends AbstractIBatisDao implements CommunicationHistoryDao {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisCommunicationHistoryDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
	@Override
	public CommunicationHistory maintainCommunicationHistory(CommunicationHistory communicationHistory) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("maintainCommunicationHistory: communicationHistoryId = " + communicationHistory.getId());
	    }
        if (!communicationHistory.getConstituent().getSite().getName().equals(getSiteName())) {
            throw new RuntimeException("Constituent object does not belong to current site.");
        }
        insertOrUpdate(communicationHistory, "COMMUNICATION_HISTORY");
        return communicationHistory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginatedResult readCommunicationHistoryByConstituentId(Long constituentId, SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaymentHistoryByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        sortinfo.addParams(params);

		params.put("constituentId", constituentId);

        List rows = getSqlMapClientTemplate().queryForList("SELECT_COMMUNICATION_HISTORY_FOR_CONSTITUENT_ID_PAGINATED", params);
        Long count = (Long)getSqlMapClientTemplate().queryForObject("COMMUNICATION_HISTORY_FOR_CONSTITUENT_ID_ROWCOUNT",params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
	}

	@Override
	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCommunicationHistoryById: communicationHistoryId = " + communicationHistoryId);
        }
        Map<String, Object> params = setupParams();
		params.put("id", communicationHistoryId);
		return (CommunicationHistory) getSqlMapClientTemplate().queryForObject("SELECT_COMMUNICATION_HISTORY_BY_ID", params);
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<CommunicationHistory> readAllCommunicationHistoryByConstituentId(Long constituentId, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllCommunicationHistoryByConstituentId: constituentId = " + constituentId + " sortPropertyName = " + sortPropertyName +
                    " direction = " + direction + " start = " + start + " limit = " + limit);
        }
        Map<String, Object> params = setupSortParams(StringConstants.COMMUNICATION_HISTORY, "COMMUNICATION_HISTORY.COMMUNICATION_HISTORY_RESULT",
                sortPropertyName, direction, start, limit, locale);
        params.put(StringConstants.CONSTITUENT_ID, constituentId);

        return getSqlMapClientTemplate().queryForList("SELECT_LIMITED_COMMUNICATION_HISTORY_BY_CONSITUENT_ID", params);
    }

    @Override
    public int readCountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentId: constituentId = " + constituentId);
        }
        Map<String,Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return (Integer) getSqlMapClientTemplate().queryForObject("SELECT_COMMUNICATION_HISTORY_COUNT_BY_CONSTITUENT_ID", params);
    }
}
