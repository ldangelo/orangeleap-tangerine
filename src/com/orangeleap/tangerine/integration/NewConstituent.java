package com.orangeleap.tangerine.integration;

import org.springframework.integration.annotation.Gateway;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;

public interface NewConstituent {
	@Gateway(requestChannel="newConstituentChannel")
	public void routeConstituent(Constituent p) throws ConstituentValidationException;
}
