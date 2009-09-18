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
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.EntitySearchDao;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.EntitySearch;
import com.orangeleap.tangerine.util.OLLogger;

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
    

	@SuppressWarnings("unchecked")
	@Override
	public void insertOrUpdateEntitySearchText(String entityType, Long entityId, String searchText) {

        Map<String, Object> params = setupParams();

        params.put("entityType", entityType);
        params.put("entityId", entityId);
        params.put("searchText", searchText); // making searchtext blank is like deleting
        
        List<EntitySearch> list = getSqlMapClientTemplate().queryForList("SELECT_ENTITY_SEARCH_BY_TYPE_AND_ID", params);
        boolean isnew = list.isEmpty();
        if (isnew) {
        	getSqlMapClientTemplate().insert("INSERT_ENTITY_SEARCH", params);
        } else {
        	getSqlMapClientTemplate().update("UPDATE_ENTITY_SEARCH", params);
        }
		
	}

	@Override
    public void updateFullTextIndex(AbstractEntity entity) {
    	try {
	    	Set<String> set = entity.getFullTextSearchKeywords();
	    	StringBuilder sb = new StringBuilder();
	    	for (String s : set) sb.append(s + " ");
	    	insertOrUpdateEntitySearchText(entity.getType(), entity.getId(), sb.toString());
    	} catch (Exception e) {
    		logger.error("Unable to index AbstractEntity " + entity, e);
    	}
    }


	
}