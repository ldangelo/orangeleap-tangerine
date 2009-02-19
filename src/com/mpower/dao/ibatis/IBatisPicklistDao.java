package com.mpower.dao.ibatis;

import com.mpower.dao.interfaces.PicklistDao;
import com.mpower.domain.customization.*;
import com.mpower.type.EntityType;
import com.ibatis.sqlmap.client.SqlMapClient;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the FieldDao for iBatis
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

    @SuppressWarnings("unchecked")
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
    public List<Picklist> listPicklists(String siteName) {
        if (logger.isDebugEnabled()) {
            logger.debug("listPicklists: siteName = " + siteName);
        }

        return getSqlMapClientTemplate().queryForList("SELECT_BY_SITENAME", siteName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Picklist readPicklistBySiteAndFieldName(String siteName, String fieldName, EntityType entityType) {

        if (logger.isDebugEnabled()) {
            logger.debug("readPicklistBySiteAndFieldName: siteName = " + siteName);
        }
        Map<String, Object> params = new HashMap<String, Object>(3);
        params.put("siteName", siteName);
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
