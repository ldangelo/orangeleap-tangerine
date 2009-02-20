package com.mpower.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.FieldDao;
import com.mpower.domain.model.customization.FieldRelationship;
import com.mpower.domain.model.customization.FieldRequired;
import com.mpower.domain.model.customization.FieldValidation;

/** 
 * Corresponds to the FIELD tables
 */
@Repository("fieldDAO")
public class IBatisFieldDao extends AbstractIBatisDao implements FieldDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisFieldDao(SqlMapClient sqlMapClient) {
        super();
        super.setSqlMapClient(sqlMapClient);
    }
    
    private Map<String, Object> setupParams(String siteName, String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId) {
        if (logger.isDebugEnabled()) {
            logger.debug("setupParams: siteName = " + siteName + " sectionName = " + sectionName + " fieldDefinitionId = " + fieldDefinitionId + " secondaryFieldDefinitionId = " + secondaryFieldDefinitionId);
        }
        Map<String, Object> params = new HashMap<String, Object>(4);
        params.put("siteName", siteName);
        params.put("sectionName", sectionName);
        params.put("fieldDefinitionId", fieldDefinitionId);
        params.put("secondaryFieldDefinitionId", secondaryFieldDefinitionId);
        return params;
    }
    
    @Override
    public FieldRequired readFieldRequired(String siteName, String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readFieldRequired: siteName = " + siteName + " sectionName = " + sectionName + " fieldDefinitionId = " + fieldDefinitionId + " secondaryFieldDefinitionId = " + secondaryFieldDefinitionId);
        }
        return (FieldRequired)getSqlMapClientTemplate().queryForObject("SELECT_FIELD_REQUIRED_BY_SITE_SECTION_FIELD_DEF_ID", setupParams(siteName, sectionName, fieldDefinitionId, secondaryFieldDefinitionId));
    }
    
    @Override
    public FieldValidation readFieldValidation(String siteName, String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readFieldValidation: siteName = " + siteName + " sectionName = " + sectionName + " fieldDefinitionId = " + fieldDefinitionId + " secondaryFieldDefinitionId = " + secondaryFieldDefinitionId);
        }
        return (FieldValidation)getSqlMapClientTemplate().queryForObject("SELECT_FIELD_VALIDATION_BY_SITE_SECTION_FIELD_DEF_ID", setupParams(siteName, sectionName, fieldDefinitionId, secondaryFieldDefinitionId));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FieldRelationship> readMasterFieldRelationships(String masterFieldDefId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readMasterFieldRelationships: masterFieldDefId = " + masterFieldDefId);
        }
        return getSqlMapClientTemplate().queryForList("SELECT_FIELD_REL_BY_MASTER_FIELD_DEF_ID", masterFieldDefId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FieldRelationship> readDetailFieldRelationships(String detailFieldDefId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readDetailFieldRelationships: detailFieldDefId = " + detailFieldDefId);
        }
        return getSqlMapClientTemplate().queryForList("SELECT_FIELD_REL_BY_DETAIL_FIELD_DEF_ID", detailFieldDefId);
    }
}