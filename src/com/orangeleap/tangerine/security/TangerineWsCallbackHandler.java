package com.orangeleap.tangerine.security;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.security.common.OrangeLeapRequestLocal;
import com.orangeleap.tangerine.security.common.OrangeLeapUsernamePasswordLocal;

public class TangerineWsCallbackHandler extends org.springframework.ws.soap.security.callback.AbstractCallbackHandler  {
	

	private TangerineSessionInformationFilter tangerineSessionInformationFilter;
	
	public void setTangerineSessionInformationFilter(TangerineSessionInformationFilter tangerineSessionInformationFilter) {
		this.tangerineSessionInformationFilter = tangerineSessionInformationFilter;
	}
	
	@Override
	protected void handleInternal(Callback cb) throws IOException, UnsupportedCallbackException {
		
		// Previous filter failed if this is null.  This is required for tangerineUserHelper.
		if (SecurityContextHolder.getContext() == null) {
			return;
		}
		
		Object obj = OrangeLeapUsernamePasswordLocal.getOrangeLeapAuthInfo().get(OrangeLeapUsernamePasswordLocal.AUTH_TOKEN);
		if (obj instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)obj;
			TangerineAuthenticationDetails tad = new TangerineAuthenticationDetails();
			tad.setSessionId(WebUtils.getSessionId(OrangeLeapRequestLocal.getRequest()));
			token.setDetails(tad);
			tangerineSessionInformationFilter.loadTangerineDetails(token);
		}
		
	}

	
}
