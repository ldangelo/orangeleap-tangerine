package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.CustomFieldDao;
import com.orangeleap.tangerine.domain.customization.CustomField;

/**
 * @version 1.0
 */
@Repository("customFieldDAO")
public class IBatisCustomFieldDao extends AbstractIBatisDao implements CustomFieldDao {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisCustomFieldDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }


    @SuppressWarnings("unchecked")
	@Override
	public List<CustomField> readCustomFieldsByEntityAndFieldName(Long constituentId, String entityType, String fieldName) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCustomFieldsByEntityAndFieldName: entityType = " + entityType);
        }
        Map<String, Object> params = setupParams();
        params.put("entityId", constituentId);
        params.put("entityType", entityType);
        params.put("fieldName", fieldName);
        return getSqlMapClientTemplate().queryForList("SELECT_CUSTOM_FIELD_BY_ENTITY", params);
	}
   
	@Override
	public void maintainCustomFieldsByEntityAndFieldName(Long entityId, String entityType, String fieldName, List<CustomField> list) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainCustomFieldsByEntityAndFieldName: entityId = " + entityId + ", fieldName = " + fieldName);
        }
        Map<String, Object> params = setupParams();
        params.put("entityId", entityId);
        params.put("entityType", entityType);
        params.put("fieldName", fieldName);
        getSqlMapClientTemplate().delete("DELETE_CUSTOM_FIELD", params);
        int seq = 0;
        for (CustomField cf : list) {
        	cf.setSequenceNumber(seq++);
        	getSqlMapClientTemplate().insert("INSERT_CUSTOM_FIELD", cf);
        }
	}
	
	
	@Override
	public void deleteCustomField(CustomField customField) {
        if (logger.isTraceEnabled()) {
            logger.trace("deleteCustomField: id = " + customField.getId());
        }
        if (customField.getId() == null) throw new RuntimeException("Custom field to delete has no id: "+customField);
        Map<String, Object> params = setupParams();
        params.put("entityId", customField.getEntityId());
        params.put("entityType", customField.getEntityType());
        params.put("id", customField.getId());
        getSqlMapClientTemplate().delete("DELETE_CUSTOM_FIELD", params);
	}


   
}
