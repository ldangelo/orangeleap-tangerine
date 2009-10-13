package com.orangeleap.tangerine.security.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.Authentication;
import org.springframework.security.context.HttpSessionContextIntegrationFilter;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.security.providers.AbstractAuthenticationToken;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.cas.CasAuthenticationToken;
import org.springframework.security.ui.cas.CasProcessingFilter;

import com.jaspersoft.jasperserver.irplugin.JServer;

public class CasUtil {
	
	public static void populateJserverWithCasCredentials(JServer jserver, String baseUrl) {
		
		if (!"true".equalsIgnoreCase(System.getProperty("use.cas"))) return; // TODO remove
		
        AbstractAuthenticationToken authentication = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

		if (authentication instanceof UsernamePasswordAuthenticationToken) {
			
			// LDAP authenticated
			// Use username and password to login to target service
			
			Map<String, Object> info = OrangeLeapUsernamePasswordLocal.getOrangeLeapAuthInfo();
			String site = (String)info.get(OrangeLeapUsernamePasswordLocal.SITE);
			String username = (String)info.get(OrangeLeapUsernamePasswordLocal.USER_NAME);
			String password = (String)info.get(OrangeLeapUsernamePasswordLocal.PASSWORD);

			jserver.setUsername(username+"@"+site);
			jserver.setPassword(password);
			
		} else if (authentication instanceof CasAuthenticationToken) {
			
	    	// CAS login
	    	// CasAuthenticationProvider can use key for username and password for (proxy) ticket
			
			jserver.setUsername(CasProcessingFilter.CAS_STATELESS_IDENTIFIER);
			jserver.setPassword(getProxyTicketFor(baseUrl)); 
			
		}

	}
	
    public static Authentication getAuthenticationToken() {
    	HttpServletRequest request = OrangeLeapRequestLocal.getRequest();
    	if (request == null) return null;
    	HttpSession session = request.getSession();
    	if (session == null) return null;
    	SecurityContextImpl si = (SecurityContextImpl)session.getAttribute(HttpSessionContextIntegrationFilter.SPRING_SECURITY_CONTEXT_KEY);
    	if (si == null) return null;
    	return  si.getAuthentication();
    }
    
    public static String getProxyTicketFor(String baseUrl) {
		Authentication token = getAuthenticationToken();
		if (token == null) return null;
		String serviceUrl = baseUrl + "/j_acegi_cas_security_check";
		return ((CasAuthenticationToken)token).getAssertion().getPrincipal().getProxyTicketFor(serviceUrl);
    }



}
