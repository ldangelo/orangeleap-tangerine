/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.simple.SimpleLdapTemplate;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.cas.CasAuthenticationToken;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;
import org.springframework.security.userdetails.ldap.LdapUserDetails;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.SessionService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.service.ldap.LdapService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.util.HttpUtil;
import com.orangeleap.tangerine.util.TangerineUserHelper;

/**
 * This security filter will decorate the web HttpSession with information needed
 * by the Tangerine Views/Pages. It makes use of several services to pull together
 * the necessary information, including an LDAP call. The need to populate this
 * extra data is determined by the absence of the <code>pageAccess</code>
 * parameter in the HttpSession.
 */
public class TangerineSessionInformationFilter extends SpringSecurityFilter {

    @Autowired
    private LdapService ldapService;

    @Autowired
    private ContextSource contextSource;

    @Autowired
    private PageCustomizationService pageCustomizationService;

    @Autowired
    private ConstituentService constituentService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private TangerineUserHelper tangerineUserHelper;

    @Override
    protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && (auth instanceof CasAuthenticationToken) && auth.isAuthenticated()) {
        	
        	// Sync due to potential multiple calls from ajax
        	synchronized (this) {

	            // Set some session values based on LDAP login information, but only do this once
	            // to save unnecessary LDAP calls
	            if (WebUtils.getSessionAttribute(request, "pageAccess") == null) {
	
	                CasAuthenticationToken token = (CasAuthenticationToken) auth;
	                loadTangerineDetails(token);
	
	                TangerineAuthenticationDetails details = (TangerineAuthenticationDetails) token.getDetails();
	
	                HttpUtil.setCookie("siteCookie", details.getSite(), Integer.MAX_VALUE, response);
	                WebUtils.setSessionAttribute(request, "pageAccess", details.getPageAccess());
	                WebUtils.setSessionAttribute(request, "passwordChangeRequired", ldapService.isPasswordChangeRequired(60));
	                WebUtils.setSessionAttribute(request, "lastLoginDate", ldapService.getLastLogin());
	                WebUtils.setSessionAttribute(request, "currentDate", new Date());
	                ldapService.setLastLogin();
	            }
            
        	}
            
        }

        chain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return FilterChainOrder.EXCEPTION_TRANSLATION_FILTER + 10;
    }

    
    
    /**
     * Initialize the TangerineAuthenticationDetails object inside the Authentication Token.
     * This method will make use of the ConstituentService and PageCustomizationService
     * to load the needed information about the constituent.
     *
     * @param token the Authentication Token with the constituent information
     */
    public void loadTangerineDetails(Authentication token) {

    	if (!(token.getPrincipal() instanceof LdapUserDetails)) {
    		throw new RuntimeException("Missing authentication token principal LdapUserDetails.");
    	}
    	
        LdapUserDetails user = (LdapUserDetails) token.getPrincipal();
        GrantedAuthority[] authorities = token.getAuthorities();

        String username = user.getUsername();
        String sitename = "";
        if (user.getUsername().indexOf("@") > -1) {
            String[] split = username.split("@");
            username = split[0];
            sitename = split[1];
        }

        TangerineAuthenticationDetails details = (TangerineAuthenticationDetails) token.getDetails();
        if (details == null) throw new RuntimeException("Missing authentication details.");
        details.setUserName(username);
        details.setSite(sitename);
        getLdapDetails(details, user.getDn());

        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority auth : authorities) {
            roles.add(auth.getAuthority());
        }

        Map<String, AccessType> pageAccess = pageCustomizationService.readPageAccess(roles);
        details.setPageAccess(pageAccess);

        /* HACK: this method ensures the authenticated user exists in the Tangerine
        * database. It will attempt to lookup the user/site, and if not found, it will
        * create it. Placing this here avoids having to override a base Spring Security
        * just to do this.
        */
        sessionService.lookupSite();
        if (!checkSiteActive(sitename)) throw new RuntimeException("Inactive site");
        

        Constituent constituent = constituentService.readConstituentByLoginId(username);
        details.setConstituentId(constituent.getId());
        details.setLastName(constituent.getLastName());
        details.setFirstName(constituent.getFirstName());
    }
    
    protected boolean checkSiteActive(String siteName) {
        Site site = siteService.readSite(siteName);
        if (site == null) {
           return true; 
        }
        return site.isActive();
    }


    /**
     * Query LDAP to initialize the firstName and lastName fields of the details object
     * based on the sn and cn values. Needed when creating new users on first login.
     *
     * @param details the TangerineAuthenticationDetails object to populate fields
     * @param dn      the fully qualified DN for the user
     * @throws NamingException if bad things happen
     */
    protected void getLdapDetails(TangerineAuthenticationDetails details, String dn) throws NamingException {

        SimpleLdapTemplate template = new SimpleLdapTemplate(contextSource);

        // dn contains the FQDN, so strip off the dc
        int posn = dn.indexOf(",dc=");
        if (posn != -1) {
            dn = dn.substring(0, posn);
        }

        DirContextOperations user = template.lookupContext(dn);

        if (user != null) {

            Object attribute = user.getObjectAttribute("cn");
            if (attribute != null) {
                String cn = ("" + attribute).trim();
                int i = cn.indexOf(" ");
                if (i == -1) {
                    details.setLastName(cn);
                } else {
                    details.setFirstName(cn.substring(0, i));
                    details.setLastName(cn.substring(i + 1));
                }
            }
            attribute = user.getObjectAttribute("sn");
            if (attribute != null) {
                String sn = ("" + attribute).trim();
                details.setLastName(sn);
            }
        }
    }
}
