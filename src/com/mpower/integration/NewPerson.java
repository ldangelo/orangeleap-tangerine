package com.mpower.integration;

import org.springframework.integration.annotation.Gateway;

import com.mpower.domain.Person;

public interface NewPerson {
	@Gateway(requestChannel="newPersonChannel")
	public void routePerson(Person p);
}
