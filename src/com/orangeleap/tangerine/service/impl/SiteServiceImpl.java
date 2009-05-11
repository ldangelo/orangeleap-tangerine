package com.orangeleap.tangerine.service.impl;

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
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.SiteDao;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.security.TangerineAuthenticationToken;
import com.orangeleap.tangerine.security.TangerineLdapAuthoritiesPopulator;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.VersionService;
import com.orangeleap.tangerine.service.customization.FieldService;
import com.orangeleap.tangerine.service.customization.MessageService;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.type.MessageResourceType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.TangerineUserHelper;

@Service("siteService")
public class SiteServiceImpl extends AbstractTangerineService implements SiteService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "constituentService")
    private ConstituentService constituentService;

    @Resource(name = "fieldService")
    private FieldService fieldService;

    @Resource(name = "messageService")
    private MessageService messageService;

    @Resource(name = "pageCustomizationService")
    private PageCustomizationService pageCustomizationService;

    @Resource(name = "siteDAO")
    private SiteDao siteDao;

    @Resource(name = "versionService")
    private VersionService versionService;
    
    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized Site createSiteAndUserIfNotExist(String siteName) {
    	if (logger.isTraceEnabled()) {
    	    logger.trace("createSiteAndUserIfNotExist: siteName = " + siteName);
    	}
    	
    	versionService.checkOrangeLeapSchemaCompatible();
    	
        Site site = siteDao.readSite(siteName);
        if (site == null) {
            site = siteDao.createSite(new Site(siteName));
        }

        TangerineAuthenticationToken authentication = tangerineUserHelper.getToken();
        
  	    if (tangerineUserHelper.lookupUserId() == null) {
          	Person constituent = constituentService.readConstituentByLoginId(tangerineUserHelper.lookupUserName());
          	if (constituent == null) {
          	    try {
					constituent = createPerson(authentication, siteName);
				} 
          	    catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("Unable to create new user record.");
				}
          	} 
          	authentication.setPersonId(constituent.getId());
  	    }
        return site;
    }
    
    // Create a Person object row corresponding to the login user.
    private Person createPerson(TangerineAuthenticationToken authentication, String siteName)  throws ConstituentValidationException, javax.naming.NamingException {
        Person constituent = constituentService.createDefaultConstituent();
        constituent.setFirstName(authentication.getUserAttributes().get(TangerineLdapAuthoritiesPopulator.FIRST_NAME));
        constituent.setLastName(authentication.getUserAttributes().get(TangerineLdapAuthoritiesPopulator.LAST_NAME));
        constituent.setConstituentIndividualRoles("user");
        constituent.setLoginId(authentication.getName());
        return constituentService.maintainConstituent(constituent);
    }
     
    @Override
    public List<Site> readSites() {
        return siteDao.readSites();
    }

    @Override
    public Site readSite(String site) {
        return siteDao.readSite(site);
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

    @Override
    public GrantedAuthority[] readDistinctRoles() {
    	List<String> roles = pageCustomizationService.readDistintSectionDefinitionsRoles();
    	List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
    	for (String s: roles) {
    		if (s!= null && s.length() > 0) list.add(new GrantedAuthorityImpl(s));
    	}
    	return (GrantedAuthority[])list.toArray(new GrantedAuthority[list.size()]);
    }

    
}
