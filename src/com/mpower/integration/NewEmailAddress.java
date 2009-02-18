package com.mpower.integration;

import org.springframework.integration.annotation.Gateway;

import com.mpower.domain.Email;

public interface NewEmailAddress {
	@Gateway(requestChannel="newEmailAddressChannel")
	public void routeEmailAddress(Email e);
}
