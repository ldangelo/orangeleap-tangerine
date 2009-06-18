package com.orangeleap.tangerine.service.ldap;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

public class LdapPerson {
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

	
    private LdapTemplate ldapTemplate;

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    @SuppressWarnings("unchecked")
    public List getAllPersonNames() {
        return ldapTemplate.search("", "(objectclass=person)", new AttributesMapper() {

            @Override
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                return attributes.get("cn").get();
            }
        });
    }
}
