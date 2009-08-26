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

package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.PageType;

import java.util.List;

public interface SectionDao {

    public SectionDefinition readSectionDefinition(Long id);

    public List<SectionDefinition> readSectionDefinitions(PageType pageType, List<String> roles);

    public List<SectionDefinition> readSectionDefinitions(PageType pageType);

    public List<SectionField> readOutOfBoxSectionFields(PageType pageType, String sectionName);

    public List<SectionField> readCustomizedSectionFields(Long sectionDefinitionId);

    public List<String> readDistintSectionDefinitionsRoles();

    public List<String> readDistintSectionDefinitionsPageTypes();

    public SectionField maintainSectionField(SectionField sectionField);

    public SectionDefinition maintainSectionDefinition(SectionDefinition sectionDefinition);

}
