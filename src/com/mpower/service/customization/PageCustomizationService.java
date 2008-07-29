package com.mpower.service.customization;

import java.util.List;

import com.mpower.domain.customization.SectionDefinition;
import com.mpower.domain.customization.SectionField;
import com.mpower.type.PageType;

public interface PageCustomizationService {
    public List<SectionDefinition> readSectionDefinitionsBySiteAndPageType(String siteName, PageType pageType, List<String> roles);

    public List<SectionField> readSectionFieldsBySiteAndSectionName(String siteName, SectionDefinition sectionDefinition);
}
