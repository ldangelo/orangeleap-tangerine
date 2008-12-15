package com.mpower.service;

import com.mpower.domain.Person;
import com.mpower.service.exception.PersonValidationException;
import com.mpower.service.relationship.PersonTreeNode;

public interface RelationshipService {
	
	public Person maintainRelationships(Person person) throws PersonValidationException;
	
	public PersonTreeNode getEntireTree(Person person, String parentCustomFieldName) throws PersonValidationException;


}
