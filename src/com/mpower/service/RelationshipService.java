package com.mpower.service;

import com.mpower.domain.Person;
import com.mpower.service.exception.PersonValidationException;

public interface RelationshipService {
	
	public Person maintainRelationships(Person person) throws PersonValidationException;

}
