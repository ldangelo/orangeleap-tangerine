package com.orangeleap.tangerine.dao.ibatis;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.ConstituentCustomFieldRelationshipDao;
import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;

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
        constituentCustomFieldRelationship.setSiteName(getSiteName());
        return (ConstituentCustomFieldRelationship)insertOrUpdate(constituentCustomFieldRelationship, "CONSTITUENT_CUSTOM_FIELD_RELATIONSHIP");
    }

	@Override
	public ConstituentCustomFieldRelationship readByConstituentFieldDefinitionCustomFieldIds(Long constituentId, String fieldDefinitionId, String customFieldValue, Date customFieldStartDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllByConstituentAndField: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("fieldDefinitionId", fieldDefinitionId);
        params.put("customFieldValue", customFieldValue);
        params.put("customFieldStartDate", customFieldStartDate);
        return (ConstituentCustomFieldRelationship)getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_CUSTOM_FIELD_RELATIONSHIP_BY_CONSTITUENT_AND_FIELD_DEFINITION_AND_CUSTOM_FIELD", params);
	}


	@Override
	public void deleteConstituentCustomFieldRelationship(Long constituentId, String fieldDefinitionId, String customFieldValue, Date customFieldStartDate) {
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("fieldDefinitionId", fieldDefinitionId);
        params.put("customFieldValue", customFieldValue);
        params.put("customFieldStartDate", customFieldStartDate);
        getSqlMapClientTemplate().delete("DELETE_CONSTITUENT_CUSTOM_FIELD_RELATIONSHIP", params);
	}
   

}