package com.mpower.service;

import com.mpower.domain.model.Person;
import com.mpower.service.exception.PersonValidationException;
import com.mpower.service.relationship.PersonTreeNode;

public interface RelationshipService {
	
	public Person maintainRelationships(Person person) throws PersonValidationException;
	
	public PersonTreeNode getTree(Person person, String parentCustomFieldName, boolean oneLevelOnly, boolean fromHeadOfTree) throws PersonValidationException;

}
