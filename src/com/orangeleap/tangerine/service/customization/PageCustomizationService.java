/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.service.customization;

import com.orangeleap.tangerine.controller.customField.CustomFieldRequest;
import com.orangeleap.tangerine.domain.QueryLookup;
import com.orangeleap.tangerine.domain.QueryLookupParam;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.type.PageType;

import java.util.List;
import java.util.Map;

public interface PageCustomizationService {

    /**
     * Return a <code>List</code> of <code>SectionDefinition</code> objects given a page type and roles
     *
     * @param pageType the type of page to get <code>SectionDefinition</code> objects for
     * @param roles    the roles to use to get the <code>SectionDefinition</code> objects
     * @return
     */
    public List<SectionDefinition> readSectionDefinitionsByPageTypeRoles(PageType pageType, List<String> roles);
    
    public List<SectionDefinition> readSectionDefinitionsByPageType(PageType pageType);

    /**
     * Return a <code>List</code> of <code>SectionField</code> objects given a <code>SectionDefinition</code>
     *
     * @param sectionDefinition the <code>SectionDefinition</code> to get fields for
     * @return
     */
    public List<SectionField> readSectionFieldsBySection(SectionDefinition sectionDefinition);
    
    public List<SectionField> readSectionFieldsBySection(SectionDefinition sectionDefinition, boolean readAll);


    /**
     * Return a <code>Map</code> indicating the access the current user has to pages
     *
     * @param roles the user roles
     * @return
     */
    public Map<String, AccessType> readPageAccess(List<String> roles);

    /**
     * Returns all distinct roles (used by system processes)
     *
     * @return
     */
    public List<String> readDistintSectionDefinitionsRoles();
    
    public List<String> readDistintSectionDefinitionsPageTypes();

    public void maintainFieldDefinition(FieldDefinition fieldDefinition);

    public SectionDefinition maintainSectionDefinition(SectionDefinition sectionDefinition);

    public SectionDefinition copySectionDefinition(Long id);

    public void maintainSectionField(SectionField sectionField);

    public void maintainSectionFields(List<SectionField> sectionFields);

    public void maintainFieldValidation(FieldValidation fieldValidation);

    public void maintainCustomFieldGuruData(CustomFieldRequest customFieldRequest);

    public QueryLookup maintainQueryLookup(QueryLookup queryLookup);

    public void maintainQueryLookupParam(QueryLookupParam queryLookupParam);

}
