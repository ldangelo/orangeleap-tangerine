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
	    if (logger.isDebugEnabled()) {
	        logger.debug("maintainCommunicationHistory: communicationHistory = " + communicationHistory);
	    }
        if (!communicationHistory.getPerson().getSite().getName().equals(getSiteName())) {
            throw new RuntimeException("Person object does not belong to current site.");
        }
        insertOrUpdate(communicationHistory, "COMMUNICATION_HISTORY");
        return communicationHistory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommunicationHistory> readCommunicationHistoryByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCommunicationHistoryByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
		params.put("constituentId", constituentId);
		return getSqlMapClientTemplate().queryForList("SELECT_COMMUNICATION_HISTORY_BY_CONSTITUENT_ID", params);
	}

	@Override
	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCommunicationHistoryById: communicationHistoryId = " + communicationHistoryId);
        }
        Map<String, Object> params = setupParams();
		params.put("id", communicationHistoryId);
		return (CommunicationHistory) getSqlMapClientTemplate().queryForObject("SELECT_COMMUNICATION_HISTORY_BY_ID", params);
	}
}
