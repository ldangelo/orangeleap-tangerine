package com.mpower.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.interfaces.SiteDao;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.Site;
import com.mpower.domain.model.customization.CustomField;
import com.mpower.domain.model.customization.FieldDefinition;
import com.mpower.domain.model.customization.FieldRequired;
import com.mpower.domain.model.customization.FieldValidation;
import com.mpower.domain.model.customization.SectionDefinition;
import com.mpower.domain.model.customization.SectionField;
import com.mpower.security.MpowerAuthenticationToken;
import com.mpower.security.MpowerLdapAuthoritiesPopulator;
import com.mpower.service.PersonService;
import com.mpower.service.SiteService;
import com.mpower.service.customization.FieldService;
import com.mpower.service.customization.MessageService;
import com.mpower.service.customization.PageCustomizationService;
import com.mpower.service.exception.PersonValidationException;
import com.mpower.type.MessageResourceType;
import com.mpower.type.PageType;

@Service("siteService")
public class SiteServiceImpl implements SiteService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "personService")
    private PersonService personService;

    @Resource(name = "fieldService")
    private FieldService fieldService;

    @Resource(name = "messageService")
    private MessageService messageService;

    @Resource(name = "pageCustomizationService")
    private PageCustomizationService pageCustomizationService;

    @Resource(name = "siteDAO")
    private SiteDao siteDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized Site createSiteAndUserIfNotExist(String siteName) {
    	if (logger.isDebugEnabled()) {
    	    logger.debug("createSiteAndUserIfNotExist: siteName = " + siteName);
    	}
        Site site = siteDao.readSite(siteName);
        if (site == null) {
            site = siteDao.createSite(new Site(siteName));
        }

        MpowerAuthenticationToken authentication = (MpowerAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
  	    Long constituentId = authentication.getPersonId();
  	    if (constituentId == null) {
          	Person person = personService.readConstituentByLoginId(authentication.getName());
          	if (person == null) {
          	    try {
					person = createPerson(authentication, siteName);
				} 
          	    catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("Unable to create new user record.");
				}
          	} 
          	authentication.setPersonId(person.getId());
  	    }
        return site;
    }
    
    // Create a Person object row corresponding to the login user.
    private Person createPerson(MpowerAuthenticationToken authentication, String siteName)  throws PersonValidationException, javax.naming.NamingException {
        Person constituent = personService.createDefaultConstituent();
        constituent.setFirstName(authentication.getUserAttributes().get(MpowerLdapAuthoritiesPopulator.FIRST_NAME));
        constituent.setLastName(authentication.getUserAttributes().get(MpowerLdapAuthoritiesPopulator.LAST_NAME));
        constituent.setConstituentIndividualRoles("user");
        constituent.setLoginId(authentication.getName());
        return personService.maintainConstituent(constituent);
    }
     
    @Override
    public List<Site> readSites() {
        return siteDao.readSites();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, FieldRequired> readRequiredFields(PageType pageType, List<String> roles) {
        Map<String, FieldRequired> returnMap = new ListOrderedMap(); // ListOrderedMap used to ensure the order of the fields is maintained
        List<SectionField> fields = getSectionFields(pageType, roles);
        if (fields != null) {
            for (SectionField sectionField : fields) {
                FieldRequired fieldRequired = fieldService.lookupFieldRequired(sectionField);
                String key = sectionField.getFieldDefinition().getFieldName();
                if (sectionField.getSecondaryFieldDefinition() != null) {
                    key += "." + sectionField.getSecondaryFieldDefinition().getFieldName();
                }
                if (fieldRequired != null && fieldRequired.isRequired()) {
                    returnMap.put(key, fieldRequired);
                }
            }
        }
        return returnMap;
    }

    @Override
    public Map<String, String> readFieldLabels(PageType pageType, List<String> roles, Locale locale) {
        Map<String, String> returnMap = new HashMap<String, String>();
        List<SectionField> sfs = getSectionFields(pageType, roles);
        if (sfs != null) {
            for (SectionField sectionField : sfs) {
                String labelText = null;
                // TODO: find out how to get current locale
                if (locale != null) {
                    labelText = messageService.lookupMessage(MessageResourceType.FIELD_LABEL, sectionField.getFieldLabelName(), locale);
                }
                if (GenericValidator.isBlankOrNull(labelText)) {
                    if (!sectionField.isCompoundField()) {
                        labelText = sectionField.getFieldDefinition().getDefaultLabel();
                    } else {
                        labelText = sectionField.getSecondaryFieldDefinition().getDefaultLabel();
                    }
                }
                String key = sectionField.getFieldDefinition().getFieldName();
                if (sectionField.getSecondaryFieldDefinition() != null) {
                    key += "." + sectionField.getSecondaryFieldDefinition().getFieldName();
                }
                returnMap.put(key, labelText);
            }
        }
        return returnMap;
    }

    @Override
    public Map<String, FieldValidation> readFieldValidations(PageType pageType, List<String> roles) {
        Map<String, FieldValidation> returnMap = new HashMap<String, FieldValidation>();
        List<SectionField> fields = getSectionFields(pageType, roles);
        if (fields != null) {
            for (SectionField sectionField : fields) {
                FieldValidation fieldValidation = fieldService.lookupFieldValidation(sectionField);
                String key = sectionField.getFieldDefinition().getFieldName();
                if (sectionField.getSecondaryFieldDefinition() != null) {
                    key += "." + sectionField.getSecondaryFieldDefinition().getFieldName();
                }
                if (fieldValidation != null && !StringUtils.isEmpty(fieldValidation.getRegex())) {
                    returnMap.put(key, fieldValidation);
                }
            }
        }
        return returnMap;
    }

    private List<SectionField> getSectionFields(PageType pageType, List<String> roles) {
        List<SectionField> fields = new ArrayList<SectionField>();
        List<SectionDefinition> sectionDefinitions = pageCustomizationService.readSectionDefinitionsByPageTypeRoles(pageType, roles);
        if (sectionDefinitions != null) {
            for (SectionDefinition sectionDefinition : sectionDefinitions) {
                List<SectionField> currentSectionFields = pageCustomizationService.readSectionFieldsBySection(sectionDefinition);
                if (currentSectionFields != null) {
                    fields.addAll(currentSectionFields);
                }
            }
        }
        return fields;
    }

    @Override
    public Map<String, Object> readFieldValues(PageType pageType, List<String> roles, Object object) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<SectionField> sfs = getSectionFields(pageType, roles);
        if (sfs != null) {
            BeanWrapperImpl bean = new BeanWrapperImpl(object);
            for (SectionField sectionField : sfs) {
                String key = sectionField.getFieldDefinition().getFieldName();
                if (sectionField.getSecondaryFieldDefinition() != null) {
                    key += "." + sectionField.getSecondaryFieldDefinition().getFieldName();
                }
                try {
                    Object value = bean.getPropertyValue(key);
                    if (value instanceof CustomField) {
                        value = bean.getPropertyValue(key + ".value");
                    }
                    returnMap.put(key, value);
                } catch (BeansException e) {
                    // it is ok if the property doesn't exist
                }
            }
            returnMap.put("id", bean.getPropertyValue("id"));
        }
        return returnMap;
    }

    @Override
    public Map<String, FieldDefinition> readFieldTypes(PageType pageType, List<String> roles) {
        Map<String, FieldDefinition> returnMap = new HashMap<String, FieldDefinition>();
        List<SectionField> sfs = getSectionFields(pageType, roles);
        if (sfs != null) {
            for (SectionField sectionField : sfs) {
                String key = sectionField.getFieldDefinition().getFieldName();
                if (sectionField.getSecondaryFieldDefinition() != null) {
                    key += "." + sectionField.getSecondaryFieldDefinition().getFieldName();
                }
                returnMap.put(key, sectionField.getFieldDefinition());
            }
        }
        return returnMap;
    }
}
