package com.mpower.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.PicklistDao;
import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.PicklistItem;
import com.mpower.type.EntityType;

/**
 * Implementation of the PicklistDao for iBatis
 * @version 1.0
 */
@Repository("picklistDAO")
public class IBatisPicklistDao extends AbstractIBatisDao implements PicklistDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisPicklistDao(SqlMapClient sqlMapClient) {
        super();
        super.setSqlMapClient(sqlMapClient);
    }

    @Override
    public Picklist readPicklistById(String picklistId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPicklistById: picklistId = " + picklistId);
        }
        return (Picklist)getSqlMapClientTemplate().queryForObject("SELECT_BY_PICKLIST_ID", picklistId);
    }

    @Override
    public Picklist maintainPicklist(Picklist picklist) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainPicklist: picklist = " + picklist);
        }

        getSqlMapClientTemplate().update("UPDATE_PICKLIST", picklist);

        List<PicklistItem> items = picklist.getPicklistItems();
        for(PicklistItem item : items) {
            item.setPicklist(picklist);
            maintainPicklistItem(item);
        }

        return picklist;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Picklist> listPicklists() {
        if (logger.isDebugEnabled()) {
            logger.debug("listPicklists:");
        }
        return getSqlMapClientTemplate().queryForList("SELECT_BY_SITENAME", getSiteName());
    }

    @Override
    public Picklist readPicklistByFieldName(String fieldName, EntityType entityType) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPicklistByFieldName: fieldName = " + fieldName + " entityType = " + entityType);
        }
        Map<String, Object> params = setupParams();
        params.put("fieldName", fieldName);
        params.put("entityType", entityType);
        return (Picklist) getSqlMapClientTemplate().queryForObject("SELECT_BY_SITE_AND_FIELD_NAME", params);
    }

    @Override
    public PicklistItem readPicklistItemById(Long picklistItemId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPicklistItemById: picklistId = " + picklistItemId);
        }
        return (PicklistItem) getSqlMapClientTemplate().queryForObject("SELECT_PICKLISTITEM_BY_ID", picklistItemId);
    }

    @Override
	public PicklistItem maintainPicklistItem(PicklistItem picklistItem) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainPicklistItem: picklistItem = " + picklistItem);
        }
        getSqlMapClientTemplate().update("UPDATE_PICKLISTITEM", picklistItem);
        return picklistItem;
    }
}
