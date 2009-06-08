package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.PageType;

public interface SectionDao {

    public List<SectionDefinition> readSectionDefinitions(PageType pageType, List<String> roles);
    public List<SectionField> readOutOfBoxSectionFields(PageType pageType, String sectionName);
    public List<SectionField> readCustomizedSectionFields(Long sectionDefinitionId);
    public List<String> readDistintSectionDefinitionsRoles();
    public SectionField maintainSectionField(SectionField sectionField);
	public SectionDefinition maintainSectionDefinition(SectionDefinition sectionDefinition);

}
