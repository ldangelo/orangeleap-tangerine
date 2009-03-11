package com.mpower.service.ldap;

import java.util.Date;

public interface LdapService {

    public void changePassword(String oldpw, String newpw);

    public boolean isPasswordChangeRequired(int days);

    public Date getLastLogin();

    public void setLastLogin();
}
