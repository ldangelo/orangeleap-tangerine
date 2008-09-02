package com.mpower.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import com.mpower.dao.SiteDao;
import com.mpower.domain.Phone;
import com.mpower.domain.Site;
import com.mpower.domain.customization.SectionDefinition;
import com.mpower.domain.customization.SectionField;
import com.mpower.service.customization.FieldService;
import com.mpower.service.customization.MessageService;
import com.mpower.service.customization.PageCustomizationService;
import com.mpower.type.MessageResourceType;
import com.mpower.type.PageType;

@Service("siteService")
public class SiteServiceImpl implements SiteService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "fieldService")
    private FieldService fieldService;

    @Resource(name = "messageService")
    private MessageService messageService;

    @Resource(name = "pageCustomizationService")
    private PageCustomizationService pageCustomizationService;

    @Resource(name = "siteDao")
    private SiteDao siteDao;

    private Map<PageType, List<SectionField>> sectionFieldsMap = new HashMap<PageType, List<SectionField>>();

    @Override
    public List<Site> readSites() {
        return siteDao.readSites();
    }

    @Override
    public Set<String> readRequiredFields(String siteName, PageType pageType, List<String> roles) {
        Set<String> returnMap = new HashSet<String>();
        List<SectionField> fields = getSectionFields(siteName, pageType, roles);
        if (fields != null) {
            for (SectionField sectionField : fields) {
                boolean required = fieldService.lookupFieldRequired(siteName, sectionField);
                String key = sectionField.getFieldDefinition().getFieldName();
                if (sectionField.getSecondaryFieldDefinition() != null) {
                    key += "." + sectionField.getSecondaryFieldDefinition().getFieldName();
                }
                if (required) {
                    returnMap.add(key);
                }
            }
        }
        return returnMap;
    }

    @Override
    public Map<String, String> readFieldLabels(String siteName, PageType pageType, List<String> roles, Locale locale) {
        Map<String, String> returnMap = new HashMap<String, String>();
        List<SectionField> sfs = getSectionFields(siteName, pageType, roles);
        if (sfs != null) {
            for (SectionField sectionField : sfs) {
                String labelText = null;
                // TODO: find out how to get current locale
                if (locale != null) {
                    labelText = messageService.lookupMessage(siteName, MessageResourceType.FIELD_LABEL, sectionField.getFieldLabelName(), locale);
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
    public Map<String, String> readFieldValidations(String siteName, PageType pageType, List<String> roles) {
        Map<String, String> returnMap = new HashMap<String, String>();
        List<SectionField> fields = getSectionFields(siteName, pageType, roles);
        if (fields != null) {
            for (SectionField sectionField : fields) {
                String regex = fieldService.lookupFieldValidation(siteName, sectionField);
                String key = sectionField.getFieldDefinition().getFieldName();
                if (sectionField.getSecondaryFieldDefinition() != null) {
                    key += "." + sectionField.getSecondaryFieldDefinition().getFieldName();
                }
                if (!StringUtils.isEmpty(regex)) {
                    returnMap.put(key, regex);
                }
            }
        }
        return returnMap;
    }

    private List<SectionField> getSectionFields(String siteName, PageType pageType, List<String> roles) {
        if (sectionFieldsMap.get(pageType) == null) {
            List<SectionField> fields = new ArrayList<SectionField>();
            List<SectionDefinition> sectionDefinitions = pageCustomizationService.readSectionDefinitionsBySiteAndPageType(siteName, pageType, roles);
            if (sectionDefinitions != null) {
                for (SectionDefinition sectionDefinition : sectionDefinitions) {
                    List<SectionField> currentSectionFields = pageCustomizationService.readSectionFieldsBySiteAndSectionName(siteName, sectionDefinition);
                    if (currentSectionFields != null) {
                        fields.addAll(currentSectionFields);
                    }
                }
            }
            sectionFieldsMap.put(pageType, fields);
        }
        return sectionFieldsMap.get(pageType);
    }

    @Override
    public Map<String, Object> readFieldValues(String siteName, PageType pageType, List<String> roles, Object object) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<SectionField> sfs = getSectionFields(siteName, pageType, roles);
        if (sfs != null) {
            BeanWrapperImpl bean = new BeanWrapperImpl(object);
            for (SectionField sectionField : sfs) {
                String key = sectionField.getFieldDefinition().getFieldName();
                if (sectionField.getSecondaryFieldDefinition() != null) {
                    key += "." + sectionField.getSecondaryFieldDefinition().getFieldName();
                }
                Object value = bean.getPropertyValue(key);
                if (value instanceof Phone) {
                    value = bean.getPropertyValue(key + ".number");
                }
                returnMap.put(key, value);
            }
            returnMap.put("id", bean.getPropertyValue("id"));
        }
        return returnMap;
    }
}
