package com.mpower.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.mpower.domain.Site;
import com.mpower.type.PageType;

public interface SiteService {

    public List<Site> readSites();

    public Map<String, Boolean> readRequiredFields(String siteName, PageType pageType, List<String> roles);

    public Map<String, String> readFieldLabels(String siteName, PageType pageType, List<String> roles, Locale locale);

    public Map<String, String> readFieldValidations(String siteName, PageType pageType, List<String> roles);

    //    public Map<String, Boolean> readRequiredFields(String siteName, EntityType entityType);
    //
    //    public Map<String, String> readValidations(String siteName, EntityType entityType);
}
