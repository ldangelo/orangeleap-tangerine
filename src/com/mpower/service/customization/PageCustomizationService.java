package com.mpower.service.customization;

import java.util.List;

import com.mpower.domain.Site;
import com.mpower.domain.customization.SectionDefinition;
import com.mpower.domain.customization.SectionField;
import com.mpower.type.PageType;

public interface PageCustomizationService {
    public List<SectionDefinition> readSectionDefinitionsBySiteAndPageType(Site site, PageType pageType);

    public List<SectionField> readSectionFieldsBySiteAndSectionName(Site site, String sectionName);
}
