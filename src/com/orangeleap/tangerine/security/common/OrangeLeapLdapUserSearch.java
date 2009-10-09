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

package com.mpower.security.common;

import javax.naming.directory.SearchControls;

import org.apache.commons.logging.Log;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.ldap.LdapUserSearch;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

public class OrangeLeapLdapUserSearch implements LdapUserSearch {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OrangeLeapLogger.getLog(getClass());


    private ContextSource contextSource;

    /**
     * The LDAP SearchControls object used for the search. Shared between searches so shouldn't be modified once the bean has been configured.
     */
    private SearchControls searchControls = new SearchControls();

    /**
     * Context name to search in, relative to the base of the configured ContextSource.
     */
    private String searchBase = "";

    /**
     * The filter expression used in the user search. This is an LDAP search filter (as defined in 'RFC 2254') with optional arguments. See the documentation for the <tt>search</tt> methods in {@link javax.naming.directory.DirContext DirContext} for more information.
     * <p>
     * In this case, the username is the only parameter.
     * </p>
     * Possible examples are:
     * <ul>
     * <li>(uid={0}) - this would search for a username match on the uid attribute.</li>
     * </ul>
     */
    private String searchFilter;

    // ~ Constructors ===================================================================================================

    public OrangeLeapLdapUserSearch(String searchBase, String searchFilter, BaseLdapPathContextSource contextSource) {
        Assert.notNull(contextSource, "contextSource must not be null");
        Assert.notNull(searchFilter, "searchFilter must not be null.");
        Assert.notNull(searchBase, "searchBase must not be null (an empty string is acceptable).");

        this.searchFilter = searchFilter;
        this.contextSource = contextSource;
        this.searchBase = searchBase;

        setSearchSubtree(true);
    }

    public OrangeLeapLdapUserSearch(String searchFilter, BaseLdapPathContextSource contextSource) {
        Assert.notNull(contextSource, "contextSource must not be null");
        Assert.notNull(searchFilter, "searchFilter must not be null.");

        this.searchFilter = searchFilter;
        this.contextSource = contextSource;

        setSearchSubtree(true);
    }

    // ~ Methods ========================================================================================================

    /**
     * Return the LdapUserDetails containing the user's information
     *
     * @param username the username to search for.
     * @return An LdapUserDetails object containing the details of the located user's directory entry
     * @throws UsernameNotFoundException if no matching entry is found.
     */
    public DirContextOperations searchForUser(String username) {
        SpringSecurityLdapTemplate template = new SpringSecurityLdapTemplate(contextSource);

        template.setSearchControls(searchControls);

        try {
        	
        	String[] filterArgs;
        	String aSearchBase;

            if(username.indexOf("@") > -1)  {
                String[] split = username.split("@");
                filterArgs = new String[] {split[0]};
                aSearchBase = "o=" + split[1];
            } else {
                filterArgs = new String[]{username};
                aSearchBase = searchBase;
            }

            return template.searchForSingleEntry(aSearchBase, searchFilter, filterArgs);
            
        } catch (IncorrectResultSizeDataAccessException notFound) {
            if (notFound.getActualSize() == 0) {
                throw new UsernameNotFoundException("User " + username + " not found in directory.", username);
            }
            // Search should never return multiple results if properly configured, so just rethrow
            throw notFound;
        }
    }

    /**
     * Return the LdapUserDetails containing the user's information
     *
     * @param username the username to search for.
     * @return An LdapUserDetails object containing the details of the located user's directory entry
     * @throws UsernameNotFoundException if no matching entry is found.
     */
    public DirContextOperations searchForUser(String username, String site) {
        SpringSecurityLdapTemplate template = new SpringSecurityLdapTemplate(contextSource);

        template.setSearchControls(searchControls);

        try {
            String searchBase = "o=" + site;
            return template.searchForSingleEntry(searchBase, searchFilter, new String[]{username});

        } catch (IncorrectResultSizeDataAccessException notFound) {
            if (notFound.getActualSize() == 0) {
                throw new UsernameNotFoundException("User " + username + " not found in directory.", username);
            }
            // Search should never return multiple results if properly configured, so just rethrow
            throw notFound;
        }
    }

    /**
     * Sets the corresponding property on the {@link SearchControls} instance used in the search.
     *
     * @param deref the derefLinkFlag value as defined in SearchControls..
     */
    public void setDerefLinkFlag(boolean deref) {
        searchControls.setDerefLinkFlag(deref);
    }

    /**
     * If true then searches the entire subtree as identified by context, if false (the default) then only searches the level identified by the context.
     *
     * @param searchSubtree true the underlying search controls should be set to SearchControls.SUBTREE_SCOPE rather than SearchControls.ONELEVEL_SCOPE.
     */
    public void setSearchSubtree(boolean searchSubtree) {
        searchControls.setSearchScope(searchSubtree ? SearchControls.SUBTREE_SCOPE : SearchControls.ONELEVEL_SCOPE);
    }

    /**
     * The time to wait before the search fails; the default is zero, meaning forever.
     *
     * @param searchTimeLimit the time limit for the search (in milliseconds).
     */
    public void setSearchTimeLimit(int searchTimeLimit) {
        searchControls.setTimeLimit(searchTimeLimit);
    }

    /**
     * Specifies the attributes that will be returned as part of the search.
     * <p/>
     * null indicates that all attributes will be returned. An empty array indicates no attributes are returned.
     *
     * @param attrs An array of attribute names identifying the attributes that will be returned. Can be null.
     */
    public void setReturningAttributes(String[] attrs) {
        searchControls.setReturningAttributes(attrs);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("[ searchFilter: '").append(searchFilter).append("', ");
        sb.append("searchBase: '").append(searchBase).append("'");
        sb.append(", scope: ").append(searchControls.getSearchScope() == SearchControls.SUBTREE_SCOPE ? "subtree" : "single-level, ");
        sb.append(", searchTimeLimit: ").append(searchControls.getTimeLimit());
        sb.append(", derefLinkFlag: ").append(searchControls.getDerefLinkFlag()).append(" ]");
        return sb.toString();
    }
}
