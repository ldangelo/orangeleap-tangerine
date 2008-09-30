package com.mpower.service.ldap;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import com.mpower.service.SessionServiceImpl;

@Service("ldapService")
public class LdapServiceImpl implements LdapService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public LdapServiceImpl() {
    }

    private LdapTemplate ldapTemplate;

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public void changePassword(String oldpw, String newpw) {
        String siteName = SessionServiceImpl.lookupUserSiteName();
        String userName = SessionServiceImpl.lookupUserName();
        ModificationItem[] modificationItemArray = new ModificationItem[1];
        String newpassword = null;
        try {
            newpassword = getSHA1(newpw);
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Attribute userPasswordAttribute = new BasicAttribute("userPassword", newpassword);
        ModificationItem newPassword = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, userPasswordAttribute);
        modificationItemArray[0] = newPassword;
        String dn = "uid=" + userName + ", ou=users, o=" + siteName;
        logger.debug("dn = " + dn);
        ldapTemplate.modifyAttributes(dn, modificationItemArray);
    }

    private String getSHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] bytes = MessageDigest.getInstance("SHA-1").digest(text.getBytes());
        logger.debug("sha1 = " + bytes);
        return "{SHA}" + new String(Base64.encodeBase64(bytes));
    }
}