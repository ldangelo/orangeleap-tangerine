package com.mpower.integration;

import org.springframework.integration.annotation.Gateway;

import com.mpower.domain.model.communication.Email;

public interface NewEmailAddress {
	@Gateway(requestChannel="newEmailAddressChannel")
	public void routeEmailAddress(Email e);
}
