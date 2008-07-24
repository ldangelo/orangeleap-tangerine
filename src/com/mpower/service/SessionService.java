package com.mpower.service;

import javax.servlet.ServletRequest;

import com.mpower.domain.Site;
import com.mpower.domain.User;

public interface SessionService {

    public User lookupUser(ServletRequest request);

    public Site lookupSite(ServletRequest request);
}
