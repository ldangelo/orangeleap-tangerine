package com.mpower.service.customization;

import java.util.List;

import com.mpower.domain.type.PageType;
import com.mpower.entity.Site;
import com.mpower.entity.customization.SectionDefinition;
import com.mpower.entity.customization.SectionField;

public interface PageCustomizationService {
    public List<SectionDefinition> readSectionDefinitionsBySiteAndPageType(Site site, PageType pageType);

    public List<SectionField> readSectionFieldsBySiteAndSectionName(Site site, String sectionName);
}
