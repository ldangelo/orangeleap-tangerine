package com.mpower.dao.ibatis;

import com.mpower.dao.interfaces.CustomFieldDao;
import com.mpower.domain.model.customization.CustomField;
import com.ibatis.sqlmap.client.SqlMapClient;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @version 1.0
 */
public class IBatisCustomFieldDao extends AbstractIBatisDao implements CustomFieldDao {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisCustomFieldDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    /**
     * Read the custom fields for the specified entity type and ID. The combination
     * of these two values uniquely identify a custom field.
     * @param entityId the ID of the entity the custom field is associated with
     * @param entityType the type of the entity
     * @return the Map of CustomFields, keyed by name
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, CustomField> readCustomFields(Long entityId, String entityType) {

        IBatisCustomFieldHelper helper = new IBatisCustomFieldHelper(getSqlMapClientTemplate());
        return helper.readCustomFields(entityId, entityType);
    }

    @Override
    public void maintainCustomFields(Map<String, CustomField> customFields) {

        IBatisCustomFieldHelper helper = new IBatisCustomFieldHelper(getSqlMapClientTemplate());
        helper.maintainCustomFields(customFields);
    }
   
}
