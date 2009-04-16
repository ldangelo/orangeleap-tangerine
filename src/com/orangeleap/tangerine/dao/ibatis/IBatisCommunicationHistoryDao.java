package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.CommunicationHistoryDao;
import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@Repository("communicationHistoryDAO")
public class IBatisCommunicationHistoryDao extends AbstractIBatisDao implements CommunicationHistoryDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisCommunicationHistoryDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
	@Override
	public CommunicationHistory maintainCommunicationHistory(CommunicationHistory communicationHistory) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("maintainCommunicationHistory: communicationHistoryId = " + communicationHistory.getId());
	    }
        if (!communicationHistory.getPerson().getSite().getName().equals(getSiteName())) {
            throw new RuntimeException("Person object does not belong to current site.");
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
}
