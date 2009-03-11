package com.orangeleap.tangerine.integration;

import org.springframework.integration.annotation.Gateway;

import com.orangeleap.tangerine.domain.Person;

public interface NewPerson {
	@Gateway(requestChannel="newPersonChannel")
	public void routePerson(Person p);
}
