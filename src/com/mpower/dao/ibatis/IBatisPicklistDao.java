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
@Repository("fieldDAO")
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
        Picklist ret =  (Picklist)getSqlMapClientTemplate().queryForObject("SELECT_BY_PICKLIST_ID", picklistId);

        List<PicklistItem> items = getSqlMapClientTemplate().queryForList("SELECT_PICKLISTITEM_BY_PICKLISTID", picklistId);
        ret.setPicklistItems(items);
        return ret;
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

        List<Picklist> ret = getSqlMapClientTemplate().queryForList("SELECT_BY_SITENAME", siteName);

        // for each of the picklists to be returned, add the picklistitems list to it
        for(Picklist list : ret) {
            List<PicklistItem> items = getSqlMapClientTemplate().queryForList("SELECT_PICKLISTITEM_BY_PICKLISTID", list.getId());
            list.setPicklistItems(items);
        }

        return ret;
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
        Picklist ret = (Picklist) getSqlMapClientTemplate().queryForObject("SELECT_BY_SITE_AND_FIELD_NAME", params);
        List<PicklistItem> items = getSqlMapClientTemplate().queryForList("SELECT_PICKLISTITEM_BY_PICKLISTID", ret.getId());
        ret.setPicklistItems(items);
        return ret;
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
