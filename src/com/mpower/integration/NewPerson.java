package com.mpower.integration;

import org.springframework.integration.annotation.Gateway;

import com.mpower.domain.model.Person;

public interface NewPerson {
	@Gateway(requestChannel="newPersonChannel")
	public void routePerson(Person p);
}
