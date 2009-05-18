package com.orangeleap.tangerine.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.CustomFieldRelationshipDao;
import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.customization.CustomFieldRelationship;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldRelationship;
import com.orangeleap.tangerine.service.AuditService;
import com.orangeleap.tangerine.service.CustomFieldRelationshipService;
import com.orangeleap.tangerine.service.RelationshipService;
import com.orangeleap.tangerine.util.TangerineUserHelper;

@Service("customFieldRelationshipService")
public class CustomFieldRelationshipServiceImpl extends AbstractTangerineService implements CustomFieldRelationshipService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    @Resource(name = "customFieldRelationshipDAO")
    private CustomFieldRelationshipDao customFieldRelationshipDao;

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "relationshipService")
    private RelationshipService relationshipService;


    // Used for adding / deleting custom field instances and updating date ranges.  
    // Adds or updates items in place, validates date ranges and deletes any existing custom fields not in the passed list.
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
	public List<CustomFieldRelationship> maintainCustomFieldRelationships(List<CustomFieldRelationship> CustomFieldRelationships) {
	    
    	
        // TODO
    	
        //relationshipService.maintainRelationships();
        //auditService.auditObject(CustomFieldRelationship, CustomFieldRelationship);  // TODO
        return CustomFieldRelationships;
    }
    
    // Used for custom field maintenance 
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomFieldRelationship maintainCustomFieldRelationshipCustomFields(CustomFieldRelationship CustomFieldRelationship) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainCustomFieldRelationship: id = " + CustomFieldRelationship.getId());
        }
        CustomFieldRelationship = customFieldRelationshipDao.maintainCustomFieldRelationship(CustomFieldRelationship);
        
        //auditService.auditObject(CustomFieldRelationship, CustomFieldRelationship);  // TODO
        return CustomFieldRelationship;
    }

    @Override
	public CustomFieldRelationship readById(Long id) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("CustomFieldRelationshipService.readById: id = " + id);
	    }
	    CustomFieldRelationship CustomFieldRelationship = customFieldRelationshipDao.readById(id);
	    return CustomFieldRelationship;
	}

    @Override
	public String getMasterFieldDefinitonId(String id) {
	    // Get corresponding master field definition id for relationship
    	FieldDefinition fd = fieldDao.readFieldDefinition(id);
    	List<FieldRelationship> frs = fieldDao.readMasterFieldRelationships(fd.getId());
    	if (frs.size() == 0) frs = fieldDao.readDetailFieldRelationships(fd.getId());
    	FieldRelationship fr = frs.get(0);
    	if (frs.size() == 0)  return null;
    	return fr.getMasterRecordField().getId();
	}

    @Override
	public CustomFieldRelationship readByFieldDefinitionId(String id) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("CustomFieldRelationshipService.readByFieldDefinitionId: id = " + id);
	    }

	    CustomFieldRelationship CustomFieldRelationship = customFieldRelationshipDao.readByFieldDefinitionId(id);
	    return CustomFieldRelationship;
	}

    @Override
    public List<CustomFieldRelationship> readAllBySite() {
	    return customFieldRelationshipDao.readAllBySite();
    }

}
