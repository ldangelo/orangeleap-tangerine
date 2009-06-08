package com.orangeleap.tangerine.service.customization;

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.controller.customField.CustomFieldRequest;
import com.orangeleap.tangerine.domain.QueryLookup;
import com.orangeleap.tangerine.domain.QueryLookupParam;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.type.PageType;

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
    
    /**
     * Returns all distinct roles (used by system processes)
     * @return
     */
    public List<String> readDistintSectionDefinitionsRoles();
    
    public void maintainFieldDefinition(FieldDefinition fieldDefinition);
    
    public SectionDefinition maintainSectionDefinition(SectionDefinition sectionDefinition);

    public void maintainSectionField(SectionField sectionField);

    public void maintainFieldValidation(FieldValidation fieldValidation);
    
    public void maintainCustomFieldGuruData(CustomFieldRequest customFieldRequest);

	public QueryLookup maintainQueryLookup(QueryLookup queryLookup);

	public void maintainQueryLookupParam(QueryLookupParam queryLookupParam);

}
