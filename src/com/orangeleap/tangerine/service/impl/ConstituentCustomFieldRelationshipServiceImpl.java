package com.orangeleap.tangerine.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.ConstituentCustomFieldRelationshipDao;
import com.orangeleap.tangerine.dao.ConstituentDao;
import com.orangeleap.tangerine.domain.Constituent;
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


    // Used for custom field maintenance 
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ConstituentCustomFieldRelationship maintainConstituentCustomFieldRelationship(ConstituentCustomFieldRelationship constituentCustomFieldRelationship) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainConstituentCustomFieldRelationship: id = " + constituentCustomFieldRelationship.getId());
        }
	    // If the site can't read the constituent, don't udpate the ccr.
	    if (null == constituentDao.readConstituentById(constituentCustomFieldRelationship.getConstituentId())) return null;
        constituentCustomFieldRelationship = constituentCustomFieldRelationshipDao.maintainConstituentCustomFieldRelationship(constituentCustomFieldRelationship);
        
        auditService.auditObject(constituentCustomFieldRelationship); 
        return constituentCustomFieldRelationship;
    }
    
    public void deleteConstituentCustomFieldRelationship(Long entityId, String masterFieldDefinitionId, String value, Date startDate) {
	    if (null == constituentDao.readConstituentById(entityId)) return;
        constituentCustomFieldRelationshipDao.deleteConstituentCustomFieldRelationship(entityId, masterFieldDefinitionId, value, startDate);
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
    
    public ConstituentCustomFieldRelationship readByConstituentFieldDefinitionCustomFieldIds(Long constituentId, String fieldDefinitionId, String customFieldValue, Date customFieldStartDate) {
	    Constituent constituent = constituentDao.readConstituentById(constituentId);
	    // If the site can't read the constituent, don't return the ccr.
	    if (constituent == null) return null;
	    ConstituentCustomFieldRelationship constituentCustomFieldRelationship = constituentCustomFieldRelationshipDao.readByConstituentFieldDefinitionCustomFieldIds(constituentId, fieldDefinitionId, customFieldValue, customFieldStartDate);
	    return constituentCustomFieldRelationship;
	}

	  

}
