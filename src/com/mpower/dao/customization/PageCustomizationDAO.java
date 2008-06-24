package com.mpower.dao.customization;

import java.util.List;

import com.mpower.entity.customization.SectionDefinition;
import com.mpower.entity.customization.SectionField;
import com.mpower.type.PageType;


public interface PageCustomizationDAO {
	public List<SectionDefinition> readOutOfBoxSectionDefinitions(PageType pageType);

    public List<SectionDefinition> readCustomizedSectionDefinitions(Long siteId, PageType pageType);

    public List<SectionField> readOutOfBoxSectionFields(String sectionName);

    public List<SectionField> readCustomizedSectionFields(Long siteId, String sectionName);
}
