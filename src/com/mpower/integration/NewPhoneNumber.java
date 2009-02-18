package com.mpower.integration;

import org.springframework.integration.annotation.Gateway;

import com.mpower.domain.Phone;

public interface NewPhoneNumber {
	@Gateway(requestChannel="newPhoneNumberChannel")
	public void routeAddress(Phone p);
}
