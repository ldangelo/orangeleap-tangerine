package com.orangeleap.tangerine.service;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.service.relationship.PersonTreeNode;

public interface RelationshipService {
	
	public Person maintainRelationships(Person person) throws ConstituentValidationException;
	
	public PersonTreeNode getTree(Person person, String parentCustomFieldName, boolean oneLevelOnly, boolean fromHeadOfTree) throws ConstituentValidationException;

	public boolean isRelationship(FieldDefinition fd);
}
