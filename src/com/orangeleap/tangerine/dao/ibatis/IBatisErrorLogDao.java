package com.orangeleap.tangerine.dao.ibatis;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.ErrorLogDao;

/** 
 * Corresponds to the AUDIT table
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
		
        Map<String, Object> params = setupParams();
        params.put("message", message);
        params.put("context", context);
        params.put("constituentId", constituentId);
        params.put("createDate", new java.util.Date());
        getSqlMapClientTemplate().insert("INSERT_ERROR_LOG", params);
        
	}
	
}