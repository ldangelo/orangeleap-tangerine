package com.mpower.service.customization;

import java.util.List;

import com.mpower.domain.entity.Site;
import com.mpower.domain.entity.customization.SectionDefinition;
import com.mpower.domain.entity.customization.SectionField;
import com.mpower.domain.type.PageType;

public interface PageCustomizationService {
    public List<SectionDefinition> readSectionDefinitionsBySiteAndPageType(Site site, PageType pageType);

    public List<SectionField> readSectionFieldsBySiteAndSectionName(Site site, String sectionName);
}
