package com.mpower.service;

import javax.servlet.ServletRequest;

import com.mpower.domain.Site;

public interface SessionService {

    public Site lookupSite(ServletRequest request);
}
