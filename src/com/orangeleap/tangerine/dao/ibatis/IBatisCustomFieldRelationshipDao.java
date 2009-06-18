package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.CustomFieldRelationshipDao;
import com.orangeleap.tangerine.domain.customization.CustomFieldRelationship;

/** 
 * Corresponds to the CUSTOM_FIELD_RELATIONSHIP table
 */
@Repository("customFieldRelationshipDAO")
public class IBatisCustomFieldRelationshipDao extends AbstractIBatisDao implements CustomFieldRelationshipDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisCustomFieldRelationshipDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    
    @Override
    public CustomFieldRelationship readById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (CustomFieldRelationship)getSqlMapClientTemplate().queryForObject("SELECT_CUSTOM_FIELD_RELATIONSHIP_BY_ID", params);
    }
    
    @Override
    public CustomFieldRelationship readByFieldDefinitionId(String id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readByFieldDefinitionId: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (CustomFieldRelationship)getSqlMapClientTemplate().queryForObject("SELECT_CUSTOM_FIELD_RELATIONSHIP_BY_MASTER_FIELD_DEFINITION_ID", params);
    }
    
    @Override
    public CustomFieldRelationship maintainCustomFieldRelationship(CustomFieldRelationship customFieldRelationship) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainCustomFieldRelationship: Id = " + customFieldRelationship.getId());
        }
        customFieldRelationship.setSiteName(getSiteName());
        return (CustomFieldRelationship)insertOrUpdate(customFieldRelationship, "CUSTOM_FIELD_RELATIONSHIP");
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<CustomFieldRelationship> readAllBySite() {
        Map<String, Object> params = setupParams();
        return getSqlMapClientTemplate().queryForList("SELECT_CUSTOM_FIELD_RELATIONSHIP_BY_SITE", params);
	}
   

}