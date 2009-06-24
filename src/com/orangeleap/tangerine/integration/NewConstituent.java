package com.orangeleap.tangerine.integration;

import org.springframework.integration.annotation.Gateway;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.service.exception.DuplicateConstituentException;

public interface NewConstituent {
	@Gateway(requestChannel="newConstituentChannel")
	public void routeConstituent(Constituent p) throws DuplicateConstituentException, ConstituentValidationException;
}
