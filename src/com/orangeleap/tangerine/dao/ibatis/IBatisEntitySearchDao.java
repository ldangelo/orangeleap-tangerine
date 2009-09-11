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

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.EntitySearchDao;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.common.PaginatedResult;

/** 
 * Corresponds to the ENTITY_SEARCH table
 */
@Repository("entitySearchDAO")
public class IBatisEntitySearchDao extends AbstractIBatisDao implements EntitySearchDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisEntitySearchDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    

	@Override
	public void insertOrUpdateEntitySearchText(String entityType, Long entityId, String searchText) {

        Map<String, Object> params = setupParams();

        params.put("entityType", entityType);
        params.put("entityId", entityId);
        params.put("searchText", searchText); // making searchtext null is like deleting
        
        boolean isnew = getSqlMapClientTemplate().queryForList("SELECT_ENTITY_SEARCH_BY_TYPE_AND_ID", params).isEmpty();
        if (isnew) {
        	getSqlMapClientTemplate().insert("INSERT_ENTITY_SEARCH", params);
        } else {
        	getSqlMapClientTemplate().update("UPDATE_ENTITY_SEARCH", params);
        }
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginatedResult search(String entityType, String searchText, String sortColumn, String dir, int start, int limit, Locale locale) {
		
        Map<String, Object> params = setupSortParams(null, null, sortColumn, dir, start, limit, locale);
        
        params.put("entityType", entityType); // optional ?
        params.put("searchText", searchText);
        
        List rows = getSqlMapClientTemplate().queryForList("ENTITY_SEARCH_PAGINATED", params);
        Long count = (Long)getSqlMapClientTemplate().queryForObject("ENTITY_SEARCH_ROWCOUNT",params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
	}
	
}