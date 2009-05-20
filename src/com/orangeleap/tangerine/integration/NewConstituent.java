package com.orangeleap.tangerine.integration;

import org.springframework.integration.annotation.Gateway;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;

public interface NewConstituent {
	@Gateway(requestChannel="newConstituentChannel")
	public void routePerson(Person p) throws ConstituentValidationException;
}
