package com.orangeleap.tangerine.dao.ibatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.controller.customField.CustomFieldRequest;
import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.Site;
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
    
    @Override
    public FieldDefinition maintainFieldDefinition(FieldDefinition fieldDefinition) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainFieldDefinition: maintainFieldDefinitionId = " + fieldDefinition.getId());
        }
        return (FieldDefinition)insertOrUpdateFieldDefinition(fieldDefinition);
    }
    
    @Override
    public FieldValidation maintainFieldValidation(FieldValidation fieldValidation) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainFieldValidation: FieldValidationId = " + fieldValidation.getId());
        }
        return (FieldValidation)insertOrUpdate(fieldValidation, "FIELD_VALIDATION");
    }
    
    @Override
    public FieldRelationship maintainFieldRelationship(FieldRelationship fieldRelationship) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainFieldRelationship: FieldRelationship = " + fieldRelationship.getId());
        }
        return (FieldRelationship)insertOrUpdate(fieldRelationship, "FIELD_RELATIONSHIP");
    }
    
    
    private FieldDefinition insertOrUpdateFieldDefinition(final FieldDefinition o) {
        if (logger.isTraceEnabled()) {
            logger.trace("insertOrUpdateFieldDefinition: id = " + o.getId());
        }
        Site site = new Site(getSiteName());
    	o.setSite(site);

        if (o.getNumericId() <= 0) {
            Long generatedId = (Long)getSqlMapClientTemplate().insert("INSERT_FIELD_DEFINITION", o);
            if (logger.isDebugEnabled()) {
                logger.debug("insertOrUpdate: generatedId = " + generatedId + " for o = " + o.getClass().getName());
            }
            o.setNumericId(generatedId);
        }
        else {
            getSqlMapClientTemplate().update("UPDATE_FIELD_DEFINITION", o);
        }
        return o;
    }
    
    private static final Map<String, String> GURU_REPORT_FIELD_GROUPS = new HashMap<String, String>();
    static {
    	GURU_REPORT_FIELD_GROUPS.put("constituent", "Contact Details");
    	GURU_REPORT_FIELD_GROUPS.put("gift", "Gift Information");
    	GURU_REPORT_FIELD_GROUPS.put("communicationHistory", "Touch Point Information");
    }
    private static final Map<String, String> GURU_REPORT_FIELD_ALIASS = new HashMap<String, String>();
    static {
    	GURU_REPORT_FIELD_ALIASS.put("constituent", "CONSTITUENT_CONSTITUENT_ID");
    	GURU_REPORT_FIELD_ALIASS.put("gift", "GIFT_GIFT_ID");
    	GURU_REPORT_FIELD_ALIASS.put("communicationHistory", "COMMUNICATION_HISTORY_COMMUNICATION_HISTORY_ID");
    }
    
    
    // Using raw sql to change to theguru schema and execute some functions.
    public void maintainCustomFieldGuruData(CustomFieldRequest customFieldRequest) {
    	
    	try {
    		

			boolean split = "true".equals(System.getProperty("mysql.splitDatabases"));

			String entityType = customFieldRequest.getEntityType();
    		String fieldname = customFieldRequest.getFieldName().replace("\'", "");
    		String fieldlabel = customFieldRequest.getLabel().replace("\'", "");

    		logger.debug("Adding GURU report metadata for "+fieldname+"...");

    		String reportFieldType = GURU_REPORT_FIELD_GROUPS.get(entityType);
    		if (reportFieldType == null) {
    			logger.error("Guru field group not defined for "+entityType);
    			return;
    		}
    		String reportFieldAlias = GURU_REPORT_FIELD_ALIASS.get(entityType);
    		if (reportFieldAlias == null) {
    			logger.error("Guru field alias not defined for "+entityType);
    			return;
    		}
    		int guruReportFieldType = 1;
    		if (customFieldRequest.getFieldType().equals("DATE")) guruReportFieldType = 4;
    		String reportfieldName = customFieldRequest.getLabel().toUpperCase().replace(" ", "").replace("\'", "");
    		
    		
    		Connection connection = getDataSource().getConnection();
    		try {

    			Statement stat = connection.createStatement();
	    		stat.execute("USE "+(split ? getSiteName() : "")+"theguru");  
	    		stat.close();
	    		
	    		List<Long> ids = new ArrayList<Long>();
	    		PreparedStatement ps = connection.prepareStatement("select REPORTFIELDGROUP_ID from REPORTFIELDGROUP where Name = ?");
	    		ps.setString(1, reportFieldType);
	    		ResultSet rs = ps.executeQuery();
	    		while (rs.next()) {
	    			ids.add(rs.getLong(1));
	    		}
	    		rs.close();
	    		ps.close();
	    		
	    		for (Long id : ids) {
		    		String sql = "CALL INSERTREPORTFIELDWITHALIAS('"+reportFieldAlias+"', '"+reportfieldName+"', 'GETCUSTOMFIELD("+reportFieldAlias+", ''"+entityType+"'', ''"+fieldname+"'')', '"+fieldlabel+"', b'0', "+guruReportFieldType+", "+id+")";
		    		logger.debug("Executing: "+sql);
		    		stat = connection.createStatement();
		    		stat.execute(sql);
		    		stat.close();
	    		}
	    		
	    		if (ids.size() > 0) {
	    			logger.info("Added GURU report metadata for "+fieldname);
	    		} else {
	    			logger.error("No REPORTFIELDGROUP_IDs found for "+reportFieldType);
	    		}
    		
    		} finally {
    			Statement stat = connection.createStatement();
    			stat.execute("USE "+(split ? getSiteName() : "orangeleap"));
    			stat.close();
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.error("Unable to add GURU metadata for custom field.", e);
    		//throw new RuntimeException(e); // don't rollback if guru not available.
    	}
    	
    }


}