package com.orangeleap.tangerine.dao.ibatis;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldRelationship;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;

/** 
 * Corresponds to the FIELD tables
 */
@Repository("fieldDAO")
public class IBatisFieldDao extends AbstractIBatisDao implements FieldDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisFieldDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    private Map<String, Object> setupFieldParams(String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId) {
        if (logger.isTraceEnabled()) {
            logger.trace("setupFieldParams: sectionName = " + sectionName + " fieldDefinitionId = " + fieldDefinitionId + " secondaryFieldDefinitionId = " + secondaryFieldDefinitionId);
        }
        Map<String, Object> params = setupParams();
        params.put("sectionName", sectionName);
        params.put("fieldDefinitionId", fieldDefinitionId);
        params.put("secondaryFieldDefinitionId", secondaryFieldDefinitionId);
        return params;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public FieldRequired readFieldRequired(String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readFieldRequired: sectionName = " + sectionName + " fieldDefinitionId = " + fieldDefinitionId + " secondaryFieldDefinitionId = " + secondaryFieldDefinitionId);
        }
        List<FieldRequired> list = getSqlMapClientTemplate().queryForList("SELECT_FIELD_REQUIRED_BY_SITE_SECTION_FIELD_DEF_ID", setupFieldParams(sectionName, fieldDefinitionId, secondaryFieldDefinitionId));
        if (list.size() == 0) {
       	    return null;
        } else {
            return list.get(list.size()-1);
        }
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public FieldValidation readFieldValidation(String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readFieldValidation: sectionName = " + sectionName + " fieldDefinitionId = " + fieldDefinitionId + " secondaryFieldDefinitionId = " + secondaryFieldDefinitionId);
        }
         List<FieldValidation> list = getSqlMapClientTemplate().queryForList("SELECT_FIELD_VALIDATION_BY_SITE_SECTION_FIELD_DEF_ID", setupFieldParams(sectionName, fieldDefinitionId, secondaryFieldDefinitionId));
         if (list.size() == 0) {
        	 return null;
         } else {
             return list.get(list.size()-1);
         }
    }

	@Override
    public FieldRelationship readFieldRelationship(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readFieldRelationship: id=" + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (FieldRelationship)getSqlMapClientTemplate().queryForObject("SELECT_FIELD_RELATIONSHIP_BY_ID", params);
    }

	@Override
    public FieldDefinition readFieldDefinition(String id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readFieldDefinition: id=" + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (FieldDefinition)getSqlMapClientTemplate().queryForObject("SELECT_FIELD_DEFINITION_BY_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FieldRelationship> readMasterFieldRelationships(String masterFieldDefId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readMasterFieldRelationships: masterFieldDefId = " + masterFieldDefId);
        }
        Map<String, Object> params = setupParams();
        params.put("id", masterFieldDefId);
        List<FieldRelationship> result = getSqlMapClientTemplate().queryForList("SELECT_FIELD_REL_BY_MASTER_FIELD_DEF_ID", params);
        return filterForSite(result);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<FieldRelationship> readDetailFieldRelationships(String detailFieldDefId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readDetailFieldRelationships: detailFieldDefId = " + detailFieldDefId);
        }
        Map<String, Object> params = setupParams();
        params.put("id", detailFieldDefId);
        List<FieldRelationship> result =  getSqlMapClientTemplate().queryForList("SELECT_FIELD_REL_BY_DETAIL_FIELD_DEF_ID", params);
        return filterForSite(result);
    }
    
    private List<FieldRelationship> filterForSite(List<FieldRelationship> list) {
        Iterator<FieldRelationship> it1 =  list.iterator();
        while (it1.hasNext()) {
        	FieldRelationship fr1 = it1.next();
        	if (fr1.getSite() == null) {  
                Iterator<FieldRelationship> it2 =  list.iterator();
                while (it2.hasNext()) {
                	FieldRelationship fr2 = it2.next();
                	if (fr2.getSite() != null 
                			&& fr1.getMasterRecordField().getId().equals(fr2.getMasterRecordField().getId()) 
                			&& fr1.getDetailRecordField().getId().equals(fr2.getDetailRecordField().getId())) 
                	{
                		logger.debug("Using overridden relationship for site: "+fr2.getId());
                		it1.remove(); // site-specific override
                		break;
                	}
                }
        	}
        }
        return list;
    }


}