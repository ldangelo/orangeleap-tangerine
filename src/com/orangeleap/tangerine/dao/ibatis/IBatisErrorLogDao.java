package com.orangeleap.tangerine.dao.ibatis;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.ErrorLogDao;

/** 
 * Corresponds to the ERROR_LOG table
 */
@Repository("errorLogDAO")
public class IBatisErrorLogDao extends AbstractIBatisDao implements ErrorLogDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisErrorLogDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    // Call the service method, not this method, in order to get a new transaction.
	@Override
	public void addErrorMessage(String message, String context, Long constituentId) {
	
		if (message == null) message = "";
		
        Map<String, Object> params = setupParams();

        Long lastid = (Long)getSqlMapClientTemplate().queryForObject("GET_LAST_ERROR_ID", params);
        params.put("id", lastid);
        String lastmessage = (String)getSqlMapClientTemplate().queryForObject("GET_ERROR_MESSAGE_BY_ID", params);
        if (message.equals(lastmessage))
        {
        	// Don't repeat same message twice in a row.
        	logger.debug("Skipping duplicate log table message for: "+message);
        	return;
        }

        params = setupParams();
        params.put("message", message);
        params.put("context", context);
        params.put("constituentId", constituentId);
        params.put("createDate", new java.util.Date());
        getSqlMapClientTemplate().insert("INSERT_ERROR_LOG", params);
        
	}

	@Override
	public void removeErrorMessagesOlderThanDays(int days) {
        Map<String, Object> params = setupParams();
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        Date cutoffdate = cal.getTime();
        
        params.put("cutoffdate", cutoffdate);
		getSqlMapClientTemplate().delete("DELETE_ERROR_LOG", params);
	}
	
}