package com.mpower.dao.customization;

import java.util.List;

import com.mpower.domain.customization.PageAccess;
import com.mpower.domain.customization.SectionDefinition;
import com.mpower.domain.customization.SectionField;
import com.mpower.type.PageType;

public interface PageCustomizationDAO {

    public List<SectionDefinition> readSectionDefinitions(String siteName, PageType pageType, List<String> roles);

    public List<SectionField> readOutOfBoxSectionFields(PageType pageType, String sectionName);

    public List<SectionField> readCustomizedSectionFields(String siteName, Long sectionDefinitionId);

    public List<PageAccess> readPageAccess(String siteName, List<String> roles);
}
