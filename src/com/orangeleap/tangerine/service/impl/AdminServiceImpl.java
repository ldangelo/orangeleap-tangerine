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

package com.orangeleap.tangerine.service.impl;

import com.orangeleap.tangerine.service.AdminService;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of the AdminService interface. Currently only used for
 * changing passwords
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {

    private LdapTemplate ldapTemplate;
    @Autowired
    private TangerineUserHelper helper;

    @Autowired
    public AdminServiceImpl(ContextSource adminContextSource) {
        this.ldapTemplate = new LdapTemplate(adminContextSource);
    }

    @Override
    public void setPassword(String password) {

        String user = helper.lookupUserName();
        String site = helper.lookupUserSiteName();

        DistinguishedName dn = new DistinguishedName("ou=users,o=" + site);
        dn.add("uid", user);

        DirContextOperations context = ldapTemplate.lookupContext(dn);

        String encPassword = encryptPassword(password);
        context.setAttributeValue("userPassword", encPassword);
        context.setAttributeValue("passwordchangedate", createLdapDateString(null));
        ldapTemplate.modifyAttributes(context);
    }

    // Encrypt any clear-text password with SHA
    protected String encryptPassword(String text) {
        byte[] bytes = null;
        try {
            bytes = MessageDigest.getInstance("SHA-1").digest(text.getBytes());
        } catch (Exception ex) {
            // ignore, since should never happen
        }

        return "{SHA}" + new String(Base64.encodeBase64(bytes));
    }

    // Helper to create the String containing a password change date of now
    protected String createLdapDateString(Date date) {

        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cal = Calendar.getInstance();

        if (date != null) {
            cal.setTime(date);
        }

        cal.add(Calendar.MILLISECOND, -cal.get(Calendar.DST_OFFSET) - cal.get(Calendar.ZONE_OFFSET));

        return format.format(cal.getTime()) + "Z";
    }

}
