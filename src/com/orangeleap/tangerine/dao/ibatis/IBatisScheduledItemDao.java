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

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.ScheduledItemDao;
import com.orangeleap.tangerine.domain.ScheduledItem;
import com.orangeleap.tangerine.util.OLLogger;

@Repository("scheduledItemDAO")
public class IBatisScheduledItemDao extends AbstractIBatisDao implements ScheduledItemDao {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisScheduledItemDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
	@Override
    public ScheduledItem maintainScheduledItem(ScheduledItem scheduledItem) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("maintainScheduledItem: scheduledItemId = " + scheduledItem.getId());
	    }
        Long lookupUserId = tangerineUserHelper.lookupUserId();
        if (lookupUserId != null) {
        	scheduledItem.setModifiedBy(lookupUserId);
        }
        insertOrUpdate(scheduledItem, "SCHEDULED_ITEM");
        return scheduledItem;
	}

    public void deleteScheduledItemById(Long scheduledItemId) {
        if (logger.isTraceEnabled()) {
            logger.trace("deleteScheduledItemById: scheduledItemId = " + scheduledItemId);
        }
        Map<String, Object> params = setupParams();
		params.put("id", scheduledItemId);
		getSqlMapClientTemplate().delete("DELETE_SCHEDULED_ITEM_BY_ID", params);
	}
	
    public void deleteSchedule(String sourceEntity, Long sourceEntityId) {
        if (logger.isTraceEnabled()) {
            logger.trace("deleteSchedule: " + sourceEntity+" "+sourceEntityId);
        }
        Map<String, Object> params = setupParams();
		params.put("sourceEntity", sourceEntity);
		params.put("sourceEntityId", sourceEntityId);
		getSqlMapClientTemplate().delete("DELETE_SCHEDULE", params);
	}
	
	
	@Override
    public ScheduledItem readScheduledItemById(Long scheduledItemId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readScheduledItemById: scheduledItemId = " + scheduledItemId);
        }
        Map<String, Object> params = setupParams();
		params.put("id", scheduledItemId);
		return (ScheduledItem) getSqlMapClientTemplate().queryForObject("SELECT_SCHEDULED_ITEM_BY_ID", params);
	}
	
    @SuppressWarnings("unchecked")
    @Override
    public List<ScheduledItem> readScheduledItemsBySourceEntityId(String sourceEntity, Long sourceEntityId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readScheduledItemsBySourceEntityId:"+sourceEntity+" "+sourceEntityId);
        }
        Map<String, Object> params = setupParams();
		params.put("sourceEntity", sourceEntity);
		params.put("sourceEntityId", sourceEntityId);
        return getSqlMapClientTemplate().queryForList("SELECT_SCHEDULED_ITEMS_BY_SOURCE_ENTITY", params);
    }

    @SuppressWarnings("unchecked")
	@Override
    public ScheduledItem getNextItemToRun(String sourceEntity, Long sourceEntityId) {
        if (logger.isTraceEnabled()) {
            logger.trace("getNextItemReadyToProcess:"+sourceEntity+" "+sourceEntityId);
        }
        Map<String, Object> params = setupParams();
		params.put("sourceEntity", sourceEntity);
		params.put("sourceEntityId", sourceEntityId);
        List<ScheduledItem> list = getSqlMapClientTemplate().queryForList("SELECT_NON_COMPLETED_SCHEDULED_ITEMS_BY_SOURCE_ENTITY", params);
        if (list.size() == 0) return null; else return list.get(0);
    }

	@Override
    public List<ScheduledItem> getItemsReadyToProcess(String sourceEntity, Date processingDate) {
    	return getItemsReadyToProcess(sourceEntity, null, processingDate);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<ScheduledItem> getItemsReadyToProcess(String sourceEntity, String scheduledItemType, Date processingDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("getItemsReadyToProcess:"+sourceEntity+" "+processingDate);
        }
        Map<String, Object> params = setupParams();
		params.put("sourceEntity", sourceEntity);
		params.put("scheduledItemType", scheduledItemType);
		params.put("processingDate", processingDate);
        return getSqlMapClientTemplate().queryForList("SELECT_SCHEDULED_ITEMS_BY_TYPE_AND_DATE", params);
    }

	
}
