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
		if (getSiteName() == null) return; // Do not log to db outside of a site context (uses file log only).
		
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