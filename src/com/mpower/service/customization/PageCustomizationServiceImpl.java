package com.mpower.service.customization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.mpower.dao.customization.PageCustomizationDAO;
import com.mpower.domain.customization.SectionDefinition;
import com.mpower.domain.customization.SectionField;
import com.mpower.type.PageType;
import com.mpower.type.RoleType;

@Service("pageCustomizationService")
public class PageCustomizationServiceImpl implements PageCustomizationService {
  
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
	private static final Integer ZERO = Integer.valueOf(0);

    @Resource(name = "pageCustomizationDao")
    private PageCustomizationDAO pageCustomizationDao;

    @SuppressWarnings("unchecked")
    @Override
    public List<SectionDefinition> readSectionDefinitionsBySiteAndPageType(String siteName, PageType pageType, List<String> roles) {
        List<SectionDefinition> returnSections;
        List<SectionDefinition> outOfBoxSectionDefinitions = pageCustomizationDao.readOutOfBoxSectionDefinitions(pageType, roles);
        List<SectionDefinition> customSectionDefinitions = pageCustomizationDao.readCustomizedSectionDefinitions(siteName, pageType, roles);
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
        // filter out duplicate sections and return the section with the highest role
        returnSections = removeDuplicateSectionDefinitions(returnSections);

        Collections.sort(returnSections, new BeanComparator("sectionOrder"));

        return returnSections;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SectionField> readSectionFieldsBySiteAndSectionName(String siteName, SectionDefinition sectionDefinition) {
        List<SectionField> returnFields;
        List<SectionField> outOfBoxSectionFields = pageCustomizationDao.readOutOfBoxSectionFields(sectionDefinition.getSectionName());
        List<SectionField> customSectionFields = pageCustomizationDao.readCustomizedSectionFields(siteName, sectionDefinition.getId());
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
            if (customizedSection.getSectionName().equals(currentSection.getSectionName()) && (currentSection.getSite() == null)) {
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

    /**
     * Filter out duplicate section definitions that are defined for different roles - choose the page with the highest role user qualifies for
     * @param sectionDefinitions
     */
    private List<SectionDefinition> removeDuplicateSectionDefinitions(List<SectionDefinition> sectionDefinitions) {
        if (sectionDefinitions != null) {
            Map<String, SectionDefinition> nameDefinitionMap = new HashMap<String, SectionDefinition>();
            for (SectionDefinition sectionDefinition : sectionDefinitions) {
                SectionDefinition sd = nameDefinitionMap.get(sectionDefinition.getSectionName());
                if (sd == null || RoleType.valueOf(sd.getRole()).getRoleRank() < RoleType.valueOf(sectionDefinition.getRole()).getRoleRank()) {
                    nameDefinitionMap.put(sectionDefinition.getSectionName(), sectionDefinition);
                }
            }
            return new ArrayList<SectionDefinition>(nameDefinitionMap.values());
        }
        return null;
    }
}
