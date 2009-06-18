package com.orangeleap.tangerine.dao.ibatis;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.List;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.ErrorLogDao;
import com.orangeleap.tangerine.domain.ErrorLog;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

/** 
 * Corresponds to the ERROR_LOG table
 */
@Repository("errorLogDAO")
public class IBatisErrorLogDao extends AbstractIBatisDao implements ErrorLogDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisErrorLogDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    // Call the service method, not this method, in order to get a new transaction.
	@Override
	public void addErrorMessage(String message, String context, Long constituentId) {
	
		if (message == null) message = "";
		
        Map<String, Object> params = setupParams();

        params.put("message", message);
        params.put("context", context);
        params.put("constituentId", constituentId);
        params.put("createDate", new java.util.Date());
        getSqlMapClientTemplate().insert("INSERT_ERROR_LOG", params);
        
	}

	@Override
    public PaginatedResult readErrorMessages(String sortColumn, String dir, int start, int limit) {
        Map<String, Object> params = setupSortParams(sortColumn, dir, start, limit);

        List rows = getSqlMapClientTemplate().queryForList("ERROR_LOG_FOR_SITE_PAGINATED", params);
        Long count = (Long)getSqlMapClientTemplate().queryForObject("ERROR_LOG_FOR_SITE_ROWCOUNT",params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
	}
	
}