package com.mpower.service.ldap;

import java.util.Calendar;

public interface LdapService {

    public void changePassword(String oldpw, String newpw);

    public boolean isPasswordChangeRequired(int days);

    public Calendar getLastLogin();

    public void setLastLogin();
}
