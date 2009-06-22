package com.orangeleap.tangerine.service;

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.RelationshipCustomField;
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
    
    public Map<String, Object> readRelationshipFieldDefinitions(String constituentId);
    
    public List<FieldDefinition> readMasterRelationshipFieldDefinitions();

	List<CustomField> findCustomFieldsForRelationship(Constituent constituent, FieldDefinition fieldDef);

	String resolveConstituentRelationship(CustomField customField);

	Map<String, String> validateConstituentRelationshipCustomFields(Long constituentId, List<RelationshipCustomField> newRelationshipCustomFields, String fieldDefinitionId);

	void maintainRelationshipCustomFields(Long constituentId, String fieldDefinitionId, List<CustomField> oldCustomFields, 
    		List<RelationshipCustomField> newRelationshipCustomFields, String masterFieldDefinitionId);
}
