package com.orangeleap.tangerine.integration;

import org.springframework.integration.annotation.Gateway;

import com.orangeleap.tangerine.domain.communication.Phone;

public interface NewPhoneNumber {
	@Gateway(requestChannel="newPhoneNumberChannel")
	public void routeAddress(Phone p);
}
