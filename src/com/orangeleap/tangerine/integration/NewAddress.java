package com.orangeleap.tangerine.integration;

import org.springframework.integration.annotation.Gateway;

import com.orangeleap.tangerine.domain.communication.Address;

public interface NewAddress {
	@Gateway(requestChannel="newAddressChannel")
	public void routeAddress(Address a);
}
