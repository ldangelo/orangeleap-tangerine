package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.SiteOption;

public interface SiteOptionDao {

    public SiteOption readSiteOptionById(Long id);
    
    public List<SiteOption> readSiteOptions();

    public SiteOption maintainSiteOption(SiteOption siteOption);

}