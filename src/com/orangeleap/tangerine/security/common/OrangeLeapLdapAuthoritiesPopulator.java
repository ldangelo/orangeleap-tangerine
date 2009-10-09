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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.naming.directory.SearchControls;

import org.apache.commons.logging.Log;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.ldap.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;
import org.springframework.util.Assert;

/**
 * Clone of the DefaultLdapAuthoritiesPopulator class. This class copies it completely,
 * but is modified to know how to deal with usernames in the user@site format. It
 * will correctly split the individual components of the username and make use of
 * them in the authorites search in LDAP.
 * NOTE: this class was cloned from DefaultLdapAuthoritiesPopulator (aka, copy-paste)
 * due to visibility restrictions on certain methods that prevented resuse through
 * inheritance.
 */
public class OrangeLeapLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {

     //~ Static fields/initializers =====================================================================================

    protected final Log logger = OrangeLeapLogger.getLog(getClass());

    //~ Instance fields ================================================================================================

    /**
     * A default role which will be assigned to all authenticated users if set
     */
    private GrantedAuthority defaultRole;

    private SpringSecurityLdapTemplate ldapTemplate;

    /**
     * Controls used to determine whether group searches should be performed over the full sub-tree from the
     * base DN. Modified by searchSubTree property
     */
    private SearchControls searchControls = new SearchControls();

    /**
     * The ID of the attribute which contains the role name for a group
     */
    private String groupRoleAttribute = "cn";

    /**
     * The base DN from which the search for group membership should be performed
     */
    private String groupSearchBase;

    /**
     * The pattern to be used for the user search. {0} is the user's DN
     */
    private String groupSearchFilter = "(member={0})";

    /**
     * Attributes of the User's LDAP Object that contain role name information.
     */

//    private String[] userRoleAttributes = null;
    private String rolePrefix = "ROLE_";
    private boolean convertToUpperCase = true;

    //~ Constructors ===================================================================================================

    /**
     * Constructor for group search scenarios. <tt>userRoleAttributes</tt> may still be
     * set as a property.
     *
     * @param contextSource supplies the contexts used to search for user roles.
     * @param groupSearchBase          if this is an empty string the search will be performed from the root DN of the
     *                                 context factory.
     */
    public OrangeLeapLdapAuthoritiesPopulator(ContextSource contextSource, String groupSearchBase) {
        Assert.notNull(contextSource, "contextSource must not be null");
        ldapTemplate = new SpringSecurityLdapTemplate(contextSource);
        ldapTemplate.setSearchControls(searchControls);
        setGroupSearchBase(groupSearchBase);
    }

    //~ Methods ========================================================================================================

    /**
     * This method should be overridden if required to obtain any additional
     * roles for the given user (on top of those obtained from the standard
     * search implemented by this class).
     *
     * @param user the context representing the user who's roles are required
     * @return the extra roles which will be merged with those returned by the group search
     */

    protected Set getAdditionalRoles(DirContextOperations user, String username) {
        return null;
    }

    public final GrantedAuthority[] getGrantedAuthorities(DirContextOperations user, String username, String site) {
    	return getGrantedAuthorities(user, username+"@"+site);
    }

    /**
     * Obtains the authorities for the user who's directory entry is represented by
     * the supplied LdapUserDetails object.
     *
     * @param user the user who's authorities are required
     * @return the set of roles granted to the user.
     */
    public final GrantedAuthority[] getGrantedAuthorities(DirContextOperations user, String username) {
        String userDn = user.getNameInNamespace();

        if (logger.isDebugEnabled()) {
            logger.debug("Getting authorities for user " + userDn);
        }

        Set roles = getGroupMembershipRoles(userDn, username);

        Set extraRoles = getAdditionalRoles(user, username);

        if (extraRoles != null) {
            roles.addAll(extraRoles);
        }

        if (defaultRole != null) {
            roles.add(defaultRole);
        }
        
        return (GrantedAuthority[]) roles.toArray(new GrantedAuthority[roles.size()]);
    }

    public Set getGroupMembershipRoles(String userDn, String username) {
        Set authorities = new HashSet();

        if (getGroupSearchBase() == null) {
            return authorities;
        }

        String[] filterArgs = null;
        String searchBase = getGroupSearchBase();
        if(username.indexOf("@") > -1) {
            String[] split = username.split("@");
            filterArgs = new String[]{userDn, split[0]};
            searchBase+= ",o=" + split[1];
        } else {
            filterArgs = new String[] {userDn, username};
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Searching for roles for user '" + username + "', DN = " + "'" + userDn + "', with filter "
                    + groupSearchFilter + " in search base '" + searchBase + "'");
        }

        Set userRoles = ldapTemplate.searchForSingleAttributeValues(searchBase, groupSearchFilter,
                filterArgs, groupRoleAttribute);

        if (logger.isDebugEnabled()) {
            logger.debug("Roles from search: " + userRoles);
        }

        Iterator it = userRoles.iterator();

        while (it.hasNext()) {
            String role = (String) it.next();

            if (convertToUpperCase) {
                role = role.toUpperCase();
            }

            authorities.add(new GrantedAuthorityImpl(rolePrefix + role));
        }

        return authorities;
    }

    protected ContextSource getContextSource() {
        return ldapTemplate.getContextSource();
    }

    /**
     * Set the group search base (name to search under)
     *
     * @param groupSearchBase if this is an empty string the search will be performed from the root DN of the context
     *                        factory.
     */
    private void setGroupSearchBase(String groupSearchBase) {
        Assert.notNull(groupSearchBase, "The groupSearchBase (name to search under), must not be null.");
        this.groupSearchBase = groupSearchBase;
        if (groupSearchBase.length() == 0) {
            logger.info("groupSearchBase is empty. Searches will be performed from the context source base");
        }
    }

    protected String getGroupSearchBase() {
        return groupSearchBase;
    }

    public void setConvertToUpperCase(boolean convertToUpperCase) {
        this.convertToUpperCase = convertToUpperCase;
    }

    /**
     * The default role which will be assigned to all users.
     *
     * @param defaultRole the role name, including any desired prefix.
     */
    public void setDefaultRole(String defaultRole) {
        Assert.notNull(defaultRole, "The defaultRole property cannot be set to null");
        this.defaultRole = new GrantedAuthorityImpl(defaultRole);
    }

    public void setGroupRoleAttribute(String groupRoleAttribute) {
        Assert.notNull(groupRoleAttribute, "groupRoleAttribute must not be null");
        this.groupRoleAttribute = groupRoleAttribute;
    }

    public void setGroupSearchFilter(String groupSearchFilter) {
        Assert.notNull(groupSearchFilter, "groupSearchFilter must not be null");
        this.groupSearchFilter = groupSearchFilter;
    }

    /**
     * Sets the prefix which will be prepended to the values loaded from the directory.
     * Defaults to "ROLE_" for compatibility with <tt>RoleVoter/tt>.
     */
    public void setRolePrefix(String rolePrefix) {
        Assert.notNull(rolePrefix, "rolePrefix must not be null");
        this.rolePrefix = rolePrefix;
    }

    /**
     * If set to true, a subtree scope search will be performed. If false a single-level search is used.
     *
     * @param searchSubtree set to true to enable searching of the entire tree below the <tt>groupSearchBase</tt>.
     */
    public void setSearchSubtree(boolean searchSubtree) {
        int searchScope = searchSubtree ? SearchControls.SUBTREE_SCOPE : SearchControls.ONELEVEL_SCOPE;
        searchControls.setSearchScope(searchScope);
    }

    public static String FIRST_NAME = "firstName";
    public static String LAST_NAME = "lastName";
    
    public Map<String, String> populateUserAttributesMapFromLdap(DirContextOperations user, String username, String site) throws javax.naming.NamingException {
    	Map<String, String> map = new HashMap<String, String>();
    	Object attribute = user.getObjectAttribute("cn");
    	if (attribute != null) {
    		String cn = ("" + attribute).trim();
    		int i = cn.indexOf(" ");
    		if (i == -1) {
            	map.put(LAST_NAME, cn);
    		} else {
    			map.put(FIRST_NAME, cn.substring(0,i));
            	map.put(LAST_NAME, cn.substring(i+1));
    		}
    	}
    	attribute = user.getObjectAttribute("sn");
    	if (attribute != null) {
    		String sn = ("" + attribute).trim();
    		map.put(LAST_NAME, sn);
    	}
    	return map;
    }
    

}
