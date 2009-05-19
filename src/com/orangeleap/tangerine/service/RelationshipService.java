package com.orangeleap.tangerine.service;

import java.util.List;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.service.relationship.PersonTreeNode;

public interface RelationshipService {
	
	public Person maintainRelationships(Person person) throws ConstituentValidationException;
	
	public PersonTreeNode getTree(Person person, String parentCustomFieldName, boolean oneLevelOnly, boolean fromHeadOfTree) throws ConstituentValidationException;

	public boolean isRelationship(FieldDefinition fd);
	
	public boolean isHierarchy(FieldDefinition fd);

	public PersonTreeNode getTree(Person person, String parentCustomFieldName,
			String childrenCustomFieldName, boolean oneLevelOnly,
			boolean fromHeadOfTree) throws ConstituentValidationException;

	public Person getHeadOfTree(Person person, String parentCustomFieldName)
			throws ConstituentValidationException;
	
	public List<CustomField> readCustomFieldsByConstituentAndFieldName(Long personId, String fieldName);
	
    public void maintainCustomFieldsByConstituentAndFieldDefinition(Long personId, String fieldDefinitionId, List<CustomField> list, List<Long> additionalDeletes) throws ConstituentValidationException;

    public List<Person> executeRelationshipQueryLookup(String fieldType, String searchOption, String searchValue);
    
    public String isIndividualOrganizationRelationship(String fieldDefinitionId);
}
