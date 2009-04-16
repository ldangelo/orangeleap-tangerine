package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.PicklistDao;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.type.EntityType;

/**
 * Implementation of the PicklistDao for iBatis for tables PICKLIST & PICKLIST_ITEM
 * @version 1.0
 */
@Repository("picklistDAO")
public class IBatisPicklistDao extends AbstractIBatisDao implements PicklistDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisPicklistDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @Override
    public Picklist readPicklistById(Long picklistId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPicklistById: picklistId = " + picklistId);
        }
        Map<String, Object> params = setupParams();
        params.put("picklistId", picklistId);
        return (Picklist)getSqlMapClientTemplate().queryForObject("SELECT_BY_PICKLIST_ID", params);
    }

    @SuppressWarnings("unchecked")
	public Picklist readPicklistByNameId(String picklistNameId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPicklistByNameId: picklistNameId = " + picklistNameId);
        }
        Map<String, Object> params = setupParams();
        params.put("picklistNameId", picklistNameId);
        List<Picklist> list = getSqlMapClientTemplate().queryForList("SELECT_BY_PICKLIST_NAME_ID", params);
        if (list.size() == 0) {
            return null;
        }
        return list.get(list.size()-1);
    }

    @Override
    public Picklist maintainPicklist(Picklist picklist) {
       
    	if (logger.isTraceEnabled()) {
            logger.trace("maintainPicklist: picklistId = " + picklist.getId());
        }
        
        // Sanity check
        Picklist dbPicklist = readPicklistById(picklist.getId());
        if (dbPicklist != null && !dbPicklist.getSite().getName().equals(getSiteName())) {
            throw new RuntimeException("Cannot modify default picklist.");
        }
        
        picklist.setSite(new Site(getSiteName()));
        insertOrUpdate(picklist, "PICKLIST");
        
        // They can't delete picklist items currently, only inactivate them
        for (PicklistItem item : picklist.getPicklistItems()) {
        	item.setPicklistId(picklist.getId());
            insertOrUpdate(item, "PICKLIST_ITEM");
        }

        return picklist;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Picklist> listPicklists() {
        if (logger.isTraceEnabled()) {
            logger.trace("listPicklists:");
        }
        return getSqlMapClientTemplate().queryForList("SELECT_PICKLIST_BY_SITE_NAME", getSiteName());
    }

	@Override
    public Picklist readPicklistByFieldName(String fieldName, EntityType entityType) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPicklistByFieldName: fieldName = " + fieldName + " entityType = " + entityType);
        }
        Picklist picklist = getPicklist(fieldName, entityType, false);
        if (picklist != null) {
            return picklist;
        }
        return getPicklist(fieldName, entityType, true);
    }

    @SuppressWarnings("unchecked")
	private Picklist getPicklist(String fieldName, EntityType entityType, boolean useDefault) {
        Map<String, Object> params = setupParams();
        params.put("fieldName", fieldName);
        params.put("entityType", entityType);
        List<Picklist> picklists = getSqlMapClientTemplate().queryForList("SELECT_PICKLIST_BY_SITE_AND_FIELD_NAME", params);
        for (Picklist picklist: picklists) {
        	if (picklist.getSite() == null) {
        		if (useDefault) {
                    return picklist;
                }
        	} else {
        		if (!useDefault && picklist.getSite().getName().equals(getSiteName())) {
                    return picklist;
                }
        	}
        }
        return null;
    }
    
    @Override
    public PicklistItem readPicklistItemById(Long picklistItemId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPicklistItemById: picklistId = " + picklistItemId);
        }
        Map<String, Object> params = setupParams();
        params.put("picklistItemId", picklistItemId);
        return (PicklistItem) getSqlMapClientTemplate().queryForObject("SELECT_PICKLIST_ITEM_BY_ID", params);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public PicklistItem readPicklistItemByName(String picklistNameId, String picklistItemName) {
        Map<String, Object> params = setupParams();
        params.put("picklistNameId", picklistNameId);
        params.put("picklistItemName", picklistItemName);
        List<PicklistItem> list = getSqlMapClientTemplate().queryForList("SELECT_PICKLIST_ITEM_BY_PICKLIST_NAME_ID_AND_ITEM_NAME", params);
        if (list.size() == 0) {
            return null;
        }
        return list.get(list.size()-1);
        
    }
	

    @Override
	public PicklistItem maintainPicklistItem(PicklistItem picklistItem) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainPicklistItem: picklistItemId = " + picklistItem.getId());
        }
    	if (picklistItem.getId() == null) {
            getSqlMapClientTemplate().insert("INSERT_PICKLIST_ITEM", picklistItem);
    	} else {
            getSqlMapClientTemplate().update("UPDATE_PICKLIST_ITEM", picklistItem);
    	}
        return picklistItem;
    }
}
