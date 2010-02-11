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

package com.orangeleap.tangerine.service;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.RelationshipCustomField;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.service.relationship.ConstituentTreeNode;

import java.util.List;
import java.util.Map;

public interface RelationshipService {

    public Constituent maintainRelationships(Constituent constituent) throws ConstituentValidationException;

    public ConstituentTreeNode getTree(Constituent constituent, String parentCustomFieldName, boolean oneLevelOnly, boolean fromHeadOfTree) throws ConstituentValidationException;

    public boolean isRelationship(FieldDefinition fd);

    public boolean isHierarchy(FieldDefinition fd);

    public ConstituentTreeNode getTree(Constituent constituent, String parentCustomFieldName,
                                       String childrenCustomFieldName, boolean oneLevelOnly,
                                       boolean fromHeadOfTree) throws ConstituentValidationException;

    public Constituent getHeadOfTree(Constituent constituent, String parentCustomFieldName)
            throws ConstituentValidationException;

    public List<CustomField> readCustomFieldsByConstituentAndFieldName(Long constituentId, String fieldName);

    public void maintainCustomFieldsByConstituentAndFieldDefinition(Long constituentId, String fieldDefinitionId, List<CustomField> list, List<Long> additionalDeletes) throws ConstituentValidationException;

	public String isIndividualOrganizationRelationship(String fieldDefinitionId);

    public Map<String, Object> readRelationshipFieldDefinitions(String constituentId);

    public List<FieldDefinition> readMasterRelationshipFieldDefinitions();

    List<CustomField> findCustomFieldsForRelationship(Constituent constituent, FieldDefinition fieldDef);

    String resolveConstituentRelationship(CustomField customField);

    Map<String, String> validateConstituentRelationshipCustomFields(Long constituentId, List<RelationshipCustomField> newRelationshipCustomFields, String fieldDefinitionId);

    void maintainRelationshipCustomFields(Long constituentId, String fieldDefinitionId, List<CustomField> oldCustomFields,
                                          List<RelationshipCustomField> newRelationshipCustomFields, String masterFieldDefinitionId);
}
