package com.orangeleap.tangerine.security.common;

import org.springframework.security.ui.cas.CasProcessingFilter;

import com.jaspersoft.jasperserver.irplugin.JServer;

public class CasUtil {
	
	public static void populateJserverWithCasCredentials(JServer jserver, String baseUrl) {
		
		if (!"true".equalsIgnoreCase(System.getProperty("use.cas"))) return; // TODO remove
		
    	// CAS login
    	// CasAuthenticationProvider can use key for username and password for (proxy) ticket
		jserver.setUsername(CasProcessingFilter.CAS_STATELESS_IDENTIFIER);
		jserver.setPassword(CasCookieLocal.getProxyTicketFor(baseUrl)); 

	}

}
