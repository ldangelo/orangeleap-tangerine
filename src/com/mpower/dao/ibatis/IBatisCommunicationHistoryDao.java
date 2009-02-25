package com.mpower.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.CommunicationHistoryDao;
import com.mpower.domain.model.CommunicationHistory;

@Repository("communicationHistoryDAO")
public class IBatisCommunicationHistoryDao extends AbstractIBatisDao implements CommunicationHistoryDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisCommunicationHistoryDao(SqlMapClient sqlMapClient) {
        super();
        super.setSqlMapClient(sqlMapClient);
    }
    
	@Override
	public CommunicationHistory maintainCommunicationHistory(CommunicationHistory communicationHistory) {
         if (!communicationHistory.getPerson().getSite().getName().equals(getSiteName())) throw new RuntimeException("Person object does not belong to current site.");
		 insertOrUpdate(communicationHistory, "COMMUNICATION_HISTORY");
         return communicationHistory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommunicationHistory> readCommunicationHistoryByPerson(Long personId) {
        Map<String, Object> params = setupParams();
		params.put("personId", personId);
		List<CommunicationHistory> history = getSqlMapClientTemplate().queryForList("SELECT_COMMUNICATION_HISTORY_BY_PERSON", params);
        return history;
	}
	

	@Override
	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId) {
        Map<String, Object> params = setupParams();
		params.put("id", communicationHistoryId);
		CommunicationHistory history = (CommunicationHistory) getSqlMapClientTemplate().queryForObject("SELECT_COMMUNICATION_HISTORY_BY_ID", params);
        return history;
	}
}
