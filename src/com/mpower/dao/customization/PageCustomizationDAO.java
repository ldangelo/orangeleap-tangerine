package com.mpower.dao.customization;

import java.util.List;

import com.mpower.domain.entity.customization.SectionDefinition;
import com.mpower.domain.entity.customization.SectionField;
import com.mpower.domain.type.PageType;


public interface PageCustomizationDAO {
	public List<SectionDefinition> readOutOfBoxSectionDefinitions(PageType pageType);

    public List<SectionDefinition> readCustomizedSectionDefinitions(Long siteId, PageType pageType);

    public List<SectionField> readOutOfBoxSectionFields(String sectionName);

    public List<SectionField> readCustomizedSectionFields(Long siteId, String sectionName);
}
