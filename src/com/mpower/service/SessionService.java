package com.mpower.service;

import com.mpower.domain.Site;

public interface SessionService {

    public Site lookupSite();

    public String lookupSiteName();
}
