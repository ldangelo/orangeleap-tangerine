package com.mpower.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.mpower.domain.Site;
import com.mpower.type.PageType;

public interface SiteService {

    public List<Site> readSites();

    /**
     * Return the key of fields that are required
     * @param siteName the site name to search
     * @param pageType the page type to search
     * @param roles the roles of the current user
     * @return
     */
    public Set<String> readRequiredFields(String siteName, PageType pageType, List<String> roles);

    /**
     * Return field labels
     * @param siteName the site name to search
     * @param pageType the page type to search
     * @param roles the roles of the current user
     * @param locale the user locale
     * @return
     */
    public Map<String, String> readFieldLabels(String siteName, PageType pageType, List<String> roles, Locale locale);

    /**
     * Return field validations
     * @param siteName the site name to search
     * @param pageType the page type to search
     * @param roles the roles of the current user
     * @return
     */
    public Map<String, String> readFieldValidations(String siteName, PageType pageType, List<String> roles);

    /**
     * Return field values
     * @param siteName the site name to search
     * @param pageType the page type to search
     * @param roles the roles of the current user
     * @param object
     * @return
     */
    public Map<String, Object> readFieldValues(String siteName, PageType pageType, List<String> roles, Object object);
}
