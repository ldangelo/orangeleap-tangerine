package com.orangeleap.tangerine.service.customization;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.dao.PicklistDao;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

// TODO: Need a service to clear the cache and this class needs to observe that class
@Service("fieldService")
public class FieldServiceImpl implements FieldService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;
    
    @Resource(name = "picklistDAO")
    private PicklistDao picklistDao;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

    @Resource(name = "picklistCache")
    private Cache picklistCache;
    

    @Override
    public FieldRequired lookupFieldRequired(SectionField currentField) {
        if (logger.isDebugEnabled()) {
            logger.debug("lookupFieldRequired: currentField = " + currentField);
        }
        return fieldDao.readFieldRequired(currentField.getSectionDefinition().getSectionName(), currentField.getFieldDefinition().getId(), currentField.getSecondaryFieldDefinition() == null ? null : currentField.getSecondaryFieldDefinition().getId());
    }

    @Override
    public FieldValidation lookupFieldValidation(SectionField currentField) {
        if (logger.isDebugEnabled()) {
            logger.debug("lookupFieldValidation: currentField = " + currentField);
        }
        return fieldDao.readFieldValidation(currentField.getSectionDefinition().getSectionName(), currentField.getFieldDefinition().getId(), currentField.getSecondaryFieldDefinition() == null ? null : currentField.getSecondaryFieldDefinition().getId());
    }

    @Override
    public Picklist readPicklistByFieldNameEntityType(String fieldName, EntityType entityType) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPicklistByFieldNameEntityType: fieldName = " + fieldName + " entityType = " + entityType);
        }

        String siteName = tangerineUserHelper.lookupUserSiteName();
        siteName = (siteName == null ? "DEFAULT" : siteName);
        String key = siteName + "." + fieldName + "." + entityType.name();

        Element ele = picklistCache.get(key);
        Picklist picklist = null;

        if(ele == null) {
            picklist = picklistDao.readPicklistByFieldName(fieldName, entityType);
            picklistCache.put(new Element(key,picklist));
        } else {
            picklist = (Picklist)ele.getValue();
        }

        return picklist;
    }
}