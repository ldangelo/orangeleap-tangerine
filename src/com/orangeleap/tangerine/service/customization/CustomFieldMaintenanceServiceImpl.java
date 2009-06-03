package com.orangeleap.tangerine.service.customization;

import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
import com.orangeleap.tangerine.service.impl.AbstractTangerineService;
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
    	
    		if (StringUtils.trimToNull(customFieldRequest.getLabel()) == null || StringUtils.trimToNull(customFieldRequest.getFieldName()) == null) {
    			throw new RuntimeException("Field name and label are required");
    		}
    		
            Site site = new Site(tangerineUserHelper.lookupUserSiteName());

            FieldDefinition fieldDefinition = getFieldDefinition(customFieldRequest, site);
            pageCustomizationService.maintainFieldDefinition(fieldDefinition);
            
    		PageType editPage = PageType.valueOf(customFieldRequest.getEntityType());
    		addSectionDefinitionsAndValidations(editPage, customFieldRequest, fieldDefinition, site);

    		if (!customFieldRequest.getEntityType().equals("person")) {
    			PageType viewPage = PageType.valueOf(customFieldRequest.getEntityType()+"View");
    			addSectionDefinitionsAndValidations(viewPage, customFieldRequest, fieldDefinition, site);
    		}
    		
    		updateTheGuru(customFieldRequest);
    		
    		// Flush section/field definition cache for all tomcat instances
            cacheGroupDao.updateCacheGroupTimestamp(CacheGroupType.PAGE_CUSTOMIZATION);
            
	}
    
	// Modify the guru elements to support new custom field
    private void updateTheGuru(CustomFieldRequest customFieldRequest) {
    	// TODO
    }
    
    private void addSectionDefinitionsAndValidations(PageType pageType, CustomFieldRequest customFieldRequest, FieldDefinition fieldDefinition, Site site) {

        // Default to add to first section after last field.
    	SectionDefinition sectionDefinition = getDefaultSection(pageType);
		int fieldOrder = getNextFieldOrder(sectionDefinition);
        
        SectionField sectionField = getSectionField(fieldOrder, sectionDefinition, fieldDefinition, site);
        pageCustomizationService.maintainSectionField(sectionField);
        
        FieldValidation fieldValidation = getFieldValidation(customFieldRequest, fieldDefinition, sectionDefinition);
        
        if (StringUtils.trimToNull(fieldValidation.getRegex()) != null) {
        	pageCustomizationService.maintainFieldValidation(fieldValidation);
        }

    }
    
    private SectionField getSectionField(int fieldOrder, SectionDefinition sectionDefinition, FieldDefinition fieldDefinition, Site site) {
        SectionField sectionField = new SectionField();
        sectionField.setFieldOrder(fieldOrder);
        sectionField.setSectionDefinition(sectionDefinition);
        sectionField.setSite(site);
        sectionField.setFieldDefinition(fieldDefinition);
        return sectionField;
    }
    
    private FieldValidation getFieldValidation(CustomFieldRequest customFieldRequest, FieldDefinition fieldDefinition, SectionDefinition sectionDefinition) {
        FieldValidation fieldValidation = new FieldValidation();
        fieldValidation.setFieldDefinition(fieldDefinition);
        fieldValidation.setSectionName(sectionDefinition.getSectionName());
        String regex = null;
    	String type = customFieldRequest.getValidationType();
    	if (type.equals("email")) regex = "extensions:isEmail";
    	if (type.equals("url")) regex = "extensions:isUrl";
    	if (type.equals("numeric")) regex = "^[0-9]*$";
    	if (type.equals("alphanumeric")) regex = "^[a-zA-Z0-9]*$";
    	if (type.equals("regex")) regex = customFieldRequest.getRegex().trim();
        fieldValidation.setRegex(regex);
        return fieldValidation;
    }
    
    private FieldDefinition getFieldDefinition(CustomFieldRequest customFieldRequest, Site site) {
        FieldDefinition newFieldDefinition = new FieldDefinition();
        newFieldDefinition.setDefaultLabel(customFieldRequest.getLabel());
        newFieldDefinition.setEntityType(EntityType.valueOf(customFieldRequest.getEntityType()));
        if (customFieldRequest.getEntityType().equals("person")) newFieldDefinition.setEntityAttributes(customFieldRequest.getConstituentType());
        newFieldDefinition.setFieldType(customFieldRequest.getFieldType());
        newFieldDefinition.setSite(site);
        String fieldName = "customFieldMap["+ customFieldRequest.getFieldName()+"]";
        newFieldDefinition.setFieldName(fieldName);
        String id = site.getName() + "-" + customFieldRequest.getEntityType() + "." + fieldName;
        newFieldDefinition.setId(id);
        return newFieldDefinition;
    }
    
    private SectionDefinition getDefaultSection(PageType pageType) {
        List<SectionDefinition> definitions = pageCustomizationService.readSectionDefinitionsByPageTypeRoles(pageType, tangerineUserHelper.lookupUserRoles());
        Collections.sort(definitions, new Comparator<SectionDefinition>() {
			@Override
			public int compare(SectionDefinition o1, SectionDefinition o2) {
				return o1.getSectionOrder().compareTo(o2.getSectionOrder());
			}
        });
        if (definitions.size() == 0) throw new RuntimeException("Default page for "+pageType.getName()+" has no sections");
        return definitions.get(0);
    }
    
    // Get next field on page from end
    private int getNextFieldOrder(SectionDefinition sectionDefinition) {
    	List<SectionField> sectionFields = pageCustomizationService.readSectionFieldsBySection(sectionDefinition);
        Collections.sort(sectionFields, new Comparator<SectionField>() {
			@Override
			public int compare(SectionField o1, SectionField o2) {
				return o1.getFieldOrder().compareTo(o2.getFieldOrder());
			}
        });
        int fieldOrder = 0;
        if (sectionFields.size() > 0) {
        	fieldOrder = sectionFields.get(sectionFields.size()-1).getFieldOrder().intValue() + 1000;
        }
        return fieldOrder;
    }
		

}
