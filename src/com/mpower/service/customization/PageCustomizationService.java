package com.mpower.service.customization;

import java.util.List;
import java.util.Map;

import com.mpower.domain.model.customization.SectionDefinition;
import com.mpower.domain.model.customization.SectionField;
import com.mpower.type.AccessType;
import com.mpower.type.PageType;

public interface PageCustomizationService {

    /**
     * Return a <code>List</code> of <code>SectionDefinition</code> objects given a page type and roles
     * @param pageType the type of page to get <code>SectionDefinition</code> objects for
     * @param roles the roles to use to get the <code>SectionDefinition</code> objects
     * @return
     */
    public List<SectionDefinition> readSectionDefinitionsByPageTypeRoles(PageType pageType, List<String> roles);

    /**
     * Return a <code>List</code> of <code>SectionField</code> objects given a <code>SectionDefinition</code>
     * @param sectionDefinition the <code>SectionDefinition</code> to get fields for
     * @return
     */
    public List<SectionField> readSectionFieldsBySection(SectionDefinition sectionDefinition);

    /**
     * Return a <code>Map</code> indicating the access the current user has to pages
     * @param roles the user roles
     * @return
     */
    public Map<String, AccessType> readPageAccess(List<String> roles);
}
