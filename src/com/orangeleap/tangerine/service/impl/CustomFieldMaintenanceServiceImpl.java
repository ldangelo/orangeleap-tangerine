package com.orangeleap.tangerine.service.impl;

import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.controller.customField.CustomFieldRequest;
import com.orangeleap.tangerine.dao.CacheGroupDao;
import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.AuditService;
import com.orangeleap.tangerine.service.RelationshipService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.customization.CustomFieldMaintenanceService;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.type.CacheGroupType;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.TangerineUserHelper;

import edu.emory.mathcs.backport.java.util.Collections;

@Service("customFieldMaintenanceService")
public class CustomFieldMaintenanceServiceImpl extends AbstractTangerineService implements CustomFieldMaintenanceService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    @Resource(name = "cacheGroupDAO")
    private CacheGroupDao cacheGroupDao;

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "relationshipService")
    private RelationshipService relationshipService;
    
    @Resource(name = "siteService")
    private SiteService siteService;
    
    @Resource(name = "pageCustomizationService")
    private PageCustomizationService pageCustomizationService;
    

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
	public void addCustomField(CustomFieldRequest customFieldRequest) {
		
            List<SectionDefinition> definitions = pageCustomizationService.readSectionDefinitionsByPageTypeRoles(PageType.valueOf(customFieldRequest.getEntityType()), tangerineUserHelper.lookupUserRoles());
            Collections.sort(definitions, new Comparator<SectionDefinition>() {
				@Override
				public int compare(SectionDefinition o1, SectionDefinition o2) {
					return o1.getSectionOrder().compareTo(o2.getSectionOrder());
				}
            });
            if (definitions.size() == 0) throw new RuntimeException("Default page for "+customFieldRequest.getEntityType()+" has no sections");
            
            // Default to add to first section after last field.
            SectionDefinition defaultSection = definitions.get(0);
            List<SectionField> sectionFields = pageCustomizationService.readSectionFieldsBySection(defaultSection);
            Collections.sort(sectionFields, new Comparator<SectionField>() {
				@Override
				public int compare(SectionField o1, SectionField o2) {
					return o1.getFieldOrder().compareTo(o2.getFieldOrder());
				}
            });
            int fieldOrder = 0;
            if (sectionFields.size() > 0) {
            	fieldOrder = sectionFields.get(0).getFieldOrder().intValue() + 1000;
            }
            
            Site site = new Site(tangerineUserHelper.lookupUserSiteName());
            
            FieldDefinition newFieldDefinition = new FieldDefinition();
            newFieldDefinition.setDefaultLabel(customFieldRequest.getLabel());
            newFieldDefinition.setEntityType(EntityType.valueOf(customFieldRequest.getEntityType()));
            if (customFieldRequest.equals("person")) newFieldDefinition.setEntityAttributes(customFieldRequest.getConstituentType());
            newFieldDefinition.setFieldType(customFieldRequest.getFieldType());
            newFieldDefinition.setSite(site);
            String fieldName = "customFieldMap["+ customFieldRequest.getFieldName()+"]";
            newFieldDefinition.setFieldName(fieldName);
            String id = site.getName() + "-" + customFieldRequest.getEntityType() + "." + fieldName;
            newFieldDefinition.setId(id);
            
            SectionField newSectionField = new SectionField();
            newSectionField.setFieldOrder(fieldOrder);
            newSectionField.setSectionDefinition(defaultSection);
            newSectionField.setSite(site);
            newSectionField.setFieldDefinition(newFieldDefinition);
            
            FieldValidation fieldValidation = new FieldValidation();
            fieldValidation.setFieldDefinition(newFieldDefinition);
            fieldValidation.setSectionName(defaultSection.getSectionName());
            String regex = null;
        	String type = customFieldRequest.getValidationType();
        	if (type.equals("email")) regex = "extensions:email";
        	if (type.equals("url")) regex = "extensions:url";
        	if (type.equals("numeric")) regex = "^[0-9]*$";
        	if (type.equals("alphanumeric")) regex = "^[a-zA-Z0-9]*$";
        	if (type.equals("regex")) regex = customFieldRequest.getRegex().trim();
            fieldValidation.setRegex(regex);

            pageCustomizationService.maintainFieldDefinition(newFieldDefinition);
            pageCustomizationService.maintainSectionField(newSectionField);
            pageCustomizationService.maintainFieldValidation(fieldValidation);
            
    		// TODO modify clementine views to support new custom field
    		
    		// Flush section/field definition cache for all tomcat instances
            cacheGroupDao.updateCacheGroupTimestamp(CacheGroupType.PAGE_CUSTOMIZATION);
            
		}
		

}
