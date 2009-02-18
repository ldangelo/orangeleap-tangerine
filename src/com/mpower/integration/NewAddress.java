package com.mpower.integration;

import org.springframework.integration.annotation.Gateway;

import com.mpower.domain.Address;

public interface NewAddress {
	@Gateway(requestChannel="newAddressChannel")
	public void routeAddress(Address a);
}
