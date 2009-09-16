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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.ErrorLogDao;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.common.PaginatedResult;

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
        Map<String, Object> params = setupSortParams(null, null, sortColumn, dir, start, limit, null);

        List rows = getSqlMapClientTemplate().queryForList("ERROR_LOG_FOR_SITE_PAGINATED", params);
        Long count = (Long)getSqlMapClientTemplate().queryForObject("ERROR_LOG_FOR_SITE_ROWCOUNT",params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
	}
	
	@Override
	public void logDbStatus() {
		
        try {
        	
        	Connection connection = getDataSource().getConnection(); // should use current connection

            StringBuilder sb = new StringBuilder();
            PreparedStatement ps = connection.prepareStatement("SHOW ENGINE INNODB STATUS");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	sb.append(rs.getString(1)+"\r\n");
            	sb.append(rs.getString(2)+"\r\n");
            	sb.append(rs.getString(3)+"\r\n");
            }
            rs.close();
            ps.close();
            
            logger.error(sb.toString());
        
        } catch (Exception e) {
        	logger.error(e);
        }
	
	}
	
}