package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.FieldDao;
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

    @SuppressWarnings("unchecked")
    @Override
    public List<FieldRelationship> readMasterFieldRelationships(String masterFieldDefId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readMasterFieldRelationships: masterFieldDefId = " + masterFieldDefId);
        }
        Map<String, Object> params = setupParams();
        params.put("id", masterFieldDefId);
        return getSqlMapClientTemplate().queryForList("SELECT_FIELD_REL_BY_MASTER_FIELD_DEF_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FieldRelationship> readDetailFieldRelationships(String detailFieldDefId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readDetailFieldRelationships: detailFieldDefId = " + detailFieldDefId);
        }
        Map<String, Object> params = setupParams();
        params.put("id", detailFieldDefId);
        return getSqlMapClientTemplate().queryForList("SELECT_FIELD_REL_BY_DETAIL_FIELD_DEF_ID", params);
    }
}