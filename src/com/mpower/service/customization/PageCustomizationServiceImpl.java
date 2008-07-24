package com.mpower.service.customization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanComparator;
import org.springframework.stereotype.Service;

import com.mpower.dao.customization.PageCustomizationDAO;
import com.mpower.domain.customization.SectionDefinition;
import com.mpower.domain.customization.SectionField;
import com.mpower.type.PageType;

@Service("pageCustomizationService")
public class PageCustomizationServiceImpl implements PageCustomizationService {
    private static final Integer ZERO = Integer.valueOf(0);
    @Resource(name = "pageCustomizationDao")
    private PageCustomizationDAO pageCustomizationDao;

    @SuppressWarnings("unchecked")
    @Override
    public List<SectionDefinition> readSectionDefinitionsBySiteAndPageType(String siteName, PageType pageType) {
        List<SectionDefinition> returnSections;
        List<SectionDefinition> outOfBoxSectionDefinitions = pageCustomizationDao.readOutOfBoxSectionDefinitions(pageType);
        List<SectionDefinition> customSectionDefinitions = pageCustomizationDao.readCustomizedSectionDefinitions(siteName, pageType);
        if (customSectionDefinitions.isEmpty()) {
            returnSections = outOfBoxSectionDefinitions;
        } else {
            returnSections = new ArrayList<SectionDefinition>(outOfBoxSectionDefinitions);
            for (SectionDefinition customizedSection : customSectionDefinitions) {
                removeMatchingOutOfBoxSection(returnSections, customizedSection);
                if (!ZERO.equals(customizedSection.getSectionOrder())) {
                    returnSections.add(customizedSection);
                }
            }
        }
        Collections.sort(returnSections, new BeanComparator("sectionOrder"));
        return returnSections;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SectionField> readSectionFieldsBySiteAndSectionName(String siteName, String sectionName) {
        List<SectionField> returnFields;
        List<SectionField> outOfBoxSectionFields = pageCustomizationDao.readOutOfBoxSectionFields(sectionName);
        List<SectionField> customSectionFields = pageCustomizationDao.readCustomizedSectionFields(siteName, sectionName);
        if (customSectionFields.isEmpty()) {
            returnFields = outOfBoxSectionFields;
        } else {
            returnFields = new ArrayList<SectionField>(outOfBoxSectionFields);
            for (SectionField customizedField : customSectionFields) {
                removeMatchingOutOfBoxField(returnFields, customizedField);
                if (!ZERO.equals(customizedField.getFieldOrder())) {
                    returnFields.add(customizedField);
                }
            }
        }
        Collections.sort(returnFields, new BeanComparator("fieldOrder"));
        return returnFields;
    }

    private void removeMatchingOutOfBoxSection(List<SectionDefinition> sectionDefinitions, SectionDefinition customizedSection) {
        for (int i = 0; i < sectionDefinitions.size(); i++) {
            SectionDefinition currentSection = sectionDefinitions.get(i);
            if (customizedSection.getSectionName().equals(currentSection.getSectionName())) {
                sectionDefinitions.remove(i);
                break;
            }
        }
    }

    private void removeMatchingOutOfBoxField(List<SectionField> sectionFields, SectionField customizedField) {
        for (int i = 0; i < sectionFields.size(); i++) {
            SectionField currentField = sectionFields.get(i);
            String currentFieldName = currentField.getFieldPropertyName();
            String customFieldName = customizedField.getFieldPropertyName();
            if (currentFieldName.equals(customFieldName)) {
                sectionFields.remove(i);
                break;
            }
        }
    }
}
