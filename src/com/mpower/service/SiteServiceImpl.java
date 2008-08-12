package com.mpower.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Service;

import com.mpower.dao.SiteDao;
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

    private List<SectionField> sectionFields;

    @Override
    public List<Site> readSites() {
        return siteDao.readSites();
    }

    @Override
    public Map<String, Boolean> readRequiredFields(String siteName, PageType pageType, List<String> roles) {
        Map<String, Boolean> returnMap = new HashMap<String, Boolean>();
        List<SectionField> sfs = getSectionFields(siteName, pageType, roles);
        if (sfs != null) {
            for (SectionField sectionField : sectionFields) {
                boolean required = fieldService.lookupFieldRequired(siteName, sectionField);
                String key = sectionField.getFieldDefinition().getFieldName();
                if (sectionField.getSecondaryFieldDefinition() != null) {
                    key += "." + sectionField.getSecondaryFieldDefinition().getFieldName();
                }
                returnMap.put(key, required);
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
                if (locale != null){
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
        List<SectionField> sfs = getSectionFields(siteName, pageType, roles);
        if (sfs != null) {
            for (SectionField sectionField : sectionFields) {
                String regex = fieldService.lookupFieldValidation(siteName, sectionField);
                String key = sectionField.getFieldDefinition().getFieldName();
                if (sectionField.getSecondaryFieldDefinition() != null) {
                    key += "." + sectionField.getSecondaryFieldDefinition().getFieldName();
                }
                returnMap.put(key, regex);
            }
        }
        return returnMap;
    }

    private List<SectionField> getSectionFields(String siteName, PageType pageType, List<String> roles) {
        if (sectionFields == null) {
            sectionFields = new ArrayList<SectionField>();
            List<SectionDefinition> sectionDefinitions = pageCustomizationService.readSectionDefinitionsBySiteAndPageType(siteName, pageType, roles);
            if (sectionDefinitions != null) {
                for (SectionDefinition sectionDefinition : sectionDefinitions) {
                    List<SectionField> currentSectionFields = pageCustomizationService.readSectionFieldsBySiteAndSectionName(siteName, sectionDefinition);
                    if (currentSectionFields != null) {
                        sectionFields.addAll(currentSectionFields);
                    }
                }
            }
        }
        return sectionFields;
    }
}
