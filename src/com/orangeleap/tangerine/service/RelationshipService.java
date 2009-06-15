package com.orangeleap.tangerine.service;

import java.util.List;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.service.relationship.ConstituentTreeNode;

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

    public List<Constituent> executeRelationshipQueryLookup(String fieldType, String searchOption, String searchValue);
    
    public String isIndividualOrganizationRelationship(String fieldDefinitionId);
}
