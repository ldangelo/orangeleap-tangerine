package com.mpower.service;

import java.util.List;
import java.util.Map;

import com.mpower.domain.Site;
import com.mpower.type.EntityType;

public interface SiteService {

    public List<Site> readSites();

    public Map<String, Boolean> readRequiredFields(String siteName, EntityType entityType);

    public Map<String, String> readValidations(String siteName, EntityType entityType);
}
