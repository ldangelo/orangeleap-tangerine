package com.mpower.dao.interfaces;

import java.util.List;

import com.mpower.domain.model.customization.SectionDefinition;
import com.mpower.type.PageType;

public interface SectionDao {

    public List<SectionDefinition> readSectionDefinitions(String siteName, PageType pageType, List<String> roles);

//    public List<SectionField> readOutOfBoxSectionFields(PageType pageType, String sectionName);
//
//    public List<SectionField> readCustomizedSectionFields(String siteName, Long sectionDefinitionId);
}
