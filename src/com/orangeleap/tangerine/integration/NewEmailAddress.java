package com.orangeleap.tangerine.integration;

import org.springframework.integration.annotation.Gateway;

import com.orangeleap.tangerine.domain.communication.Email;

public interface NewEmailAddress {
	@Gateway(requestChannel="newEmailAddressChannel")
	public void routeEmailAddress(Email e);
}
