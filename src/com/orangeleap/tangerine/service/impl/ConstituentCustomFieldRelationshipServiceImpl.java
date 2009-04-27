package com.orangeleap.tangerine.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.ConstituentCustomFieldRelationshipDao;
import com.orangeleap.tangerine.dao.ConstituentDao;
import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;
import com.orangeleap.tangerine.service.AuditService;
import com.orangeleap.tangerine.service.ConstituentCustomFieldRelationshipService;
import com.orangeleap.tangerine.service.RelationshipService;
import com.orangeleap.tangerine.util.TangerineUserHelper;

@Service("constituentCustomFieldRelationshipService")
public class ConstituentCustomFieldRelationshipServiceImpl extends AbstractTangerineService implements ConstituentCustomFieldRelationshipService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    @Resource(name = "constituentDAO")
    private ConstituentDao constituentDao;

    @Resource(name = "constituentCustomFieldRelationshipDAO")
    private ConstituentCustomFieldRelationshipDao constituentCustomFieldRelationshipDao;

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "relationshipService")
    private RelationshipService relationshipService;


    // Used for adding / deleting custom field instances and updating date ranges.  
    // Adds or updates items in place, validates date ranges and deletes any existing custom fields not in the passed list.
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
	public List<ConstituentCustomFieldRelationship> maintainConstituentCustomFieldRelationships(List<ConstituentCustomFieldRelationship> constituentCustomFieldRelationships) {
	    
    	// Validate the site can access the constituents.
    	for (ConstituentCustomFieldRelationship constituentCustomFieldRelationship : constituentCustomFieldRelationships) {
    		Long constituentId = constituentCustomFieldRelationship.getConstituentId();
    	    if (null == constituentDao.readConstituentById(constituentId)) return null;
    	}
    	
        // TODO
    	
        //relationshipService.maintainRelationships(constituent);
        //auditService.auditObject(constituentCustomFieldRelationship, constituentCustomFieldRelationship);  // TODO
        return constituentCustomFieldRelationships;
    }
    
    // Used for custom field maintenance 
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ConstituentCustomFieldRelationship maintainConstituentCustomFieldRelationshipCustomFields(ConstituentCustomFieldRelationship constituentCustomFieldRelationship) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainConstituentCustomFieldRelationship: id = " + constituentCustomFieldRelationship.getId());
        }
	    // If the site can't read the constituent, don't udpate the ccr.
	    if (null == constituentDao.readConstituentById(constituentCustomFieldRelationship.getConstituentId())) return null;
        constituentCustomFieldRelationship = constituentCustomFieldRelationshipDao.maintainConstituentCustomFieldRelationship(constituentCustomFieldRelationship);
        
        //relationshipService.maintainRelationships(constituent);
        //auditService.auditObject(constituentCustomFieldRelationship, constituentCustomFieldRelationship);  // TODO
        return constituentCustomFieldRelationship;
    }

    @Override
	public ConstituentCustomFieldRelationship readById(Long id) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("ConstituentCustomFieldRelationshipService.readById: id = " + id);
	    }
	    ConstituentCustomFieldRelationship constituentCustomFieldRelationship = constituentCustomFieldRelationshipDao.readById(id);
	    // If the site can't read the constituent, don't return the ccr.
	    if (null == constituentDao.readConstituentById(constituentCustomFieldRelationship.getConstituentId())) return null;
	    return constituentCustomFieldRelationship;
	}

}
