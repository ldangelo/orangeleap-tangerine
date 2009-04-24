package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.ConstituentCustomFieldRelationshipDao;
import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;
import com.orangeleap.tangerine.domain.customization.CustomField;

/** 
 * Corresponds to the CONSTITUENT_CUSTOM_FIELD_RELATIONSHIP table
 */
@Repository("constituentCustomFieldRelationshipDAO")
public class IBatisConstituentCustomFieldRelationshipDao extends AbstractIBatisDao implements ConstituentCustomFieldRelationshipDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisConstituentCustomFieldRelationshipDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    
    @Override
    public ConstituentCustomFieldRelationship readById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (ConstituentCustomFieldRelationship)getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_CUSTOM_FIELD_RELATIONSHIP_BY_ID", params);
    }
    
    @Override
    public ConstituentCustomFieldRelationship maintainConstituentCustomFieldRelationship(ConstituentCustomFieldRelationship constituentCustomFieldRelationship) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainConstituentCustomFieldRelationship: Id = " + constituentCustomFieldRelationship.getId());
        }
        return (ConstituentCustomFieldRelationship)insertOrUpdate(constituentCustomFieldRelationship, "CONSTITUENT_CUSTOM_FIELD_RELATIONSHIP");
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<ConstituentCustomFieldRelationship> readAllByConstituentAndRelationship(Long personId, Long relationshipId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllByConstituentAndRelationship: personid = " + personId);
        }
        Map<String, Object> params = setupParams();
        params.put("personId", personId);
        params.put("relationshipId", relationshipId);
        return getSqlMapClientTemplate().queryForList("SELECT_CONSTITUENT_CUSTOM_FIELD_RELATIONSHIP_BY_CONSTITUENT_RELATIONSHIP", params);
	}
   
    @SuppressWarnings("unchecked")
	@Override
	public List<CustomField> readAllCustomFieldsByConstituent(Long personId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllCustomFieldsByConstituentAndRelationship: personid = " + personId);
        }
        Map<String, Object> params = setupParams();
        params.put("entityId", personId);
        params.put("entityType", "person");
        return getSqlMapClientTemplate().queryForList("SELECT_CUSTOM_FIELD_BY_ENTITY", params);
	}
   

}