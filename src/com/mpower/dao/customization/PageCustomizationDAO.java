package com.mpower.dao.customization;

import java.util.List;

import com.mpower.domain.customization.SectionDefinition;
import com.mpower.domain.customization.SectionField;
import com.mpower.type.PageType;


public interface PageCustomizationDAO {
	public List<SectionDefinition> readOutOfBoxSectionDefinitions(PageType pageType);

    public List<SectionDefinition> readCustomizedSectionDefinitions(String siteName, PageType pageType);

    public List<SectionField> readOutOfBoxSectionFields(String sectionName);

    public List<SectionField> readCustomizedSectionFields(String siteName, String sectionName);
}
