package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisCustomFieldDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }


    @SuppressWarnings("unchecked")
	@Override
	public List<CustomField> readCustomFieldsByConstituentAndFieldName(Long personId, String fieldName) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCustomFieldsByConstituentAndFieldName: personid = " + personId);
        }
        Map<String, Object> params = setupParams();
        params.put("entityId", personId);
        params.put("entityType", "person");
        params.put("fieldName", fieldName);
        return getSqlMapClientTemplate().queryForList("SELECT_CUSTOM_FIELD_BY_ENTITY_AND_FIELD_NAME", params);
	}
   
	public void maintainCustomFieldsByConstituentAndFieldName(Long personId, String fieldName, List<CustomField> list) {
        Map<String, Object> params = setupParams();
        params.put("entityId", personId);
        params.put("entityType", "person");
        params.put("fieldName", fieldName);
        getSqlMapClientTemplate().delete("DELETE_CUSTOM_FIELD", params);
        int seq = 0;
        for (CustomField cf : list) {
        	cf.setSequenceNumber(seq++);
        	getSqlMapClientTemplate().insert("INSERT_CUSTOM_FIELD", cf);
        }
	}

   
}
