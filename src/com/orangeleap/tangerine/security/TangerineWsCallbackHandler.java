package com.orangeleap.tangerine.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import com.orangeleap.tangerine.security.common.OrangeLeapUsernamePasswordLocal;
import com.orangeleap.tangerine.type.AccessType;

public class TangerineWsCallbackHandler extends org.springframework.ws.soap.security.callback.AbstractCallbackHandler  {
	

	private TangerineSessionInformationFilter tangerineSessionInformationFilter;
	
	public void setTangerineSessionInformationFilter(TangerineSessionInformationFilter tangerineSessionInformationFilter) {
		this.tangerineSessionInformationFilter = tangerineSessionInformationFilter;
	}
	
	@Override
	protected void handleInternal(Callback cb) throws IOException, UnsupportedCallbackException {
		
		Map<String, Object> info = OrangeLeapUsernamePasswordLocal.getOrangeLeapAuthInfo();
		Object obj = info.get(OrangeLeapUsernamePasswordLocal.AUTH_TOKEN);
		if (obj instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)obj;
			tangerineSessionInformationFilter.loadTangerineDetails(authentication);
		}
		
	}

	
}
