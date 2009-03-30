package com.orangeleap.tangerine.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.type.PageType;

public interface SiteService {

    public List<Site> readSites();
    
    public Site readSite(String siteName);

    public Site createSiteAndUserIfNotExist(String siteName);
    
    /**
     * Return field required
     * @param pageType the page type to search
     * @param roles the roles of the current user
     * @return
     */
    public Map<String, FieldRequired> readRequiredFields(PageType pageType, List<String> roles);

    /**
     * Return field labels
     * @param pageType the page type to search
     * @param roles the roles of the current user
     * @param locale the user locale
     * @return
     */
    public Map<String, String> readFieldLabels(PageType pageType, List<String> roles, Locale locale);

    /**
     * Return field validations
     * @param pageType the page type to search
     * @param roles the roles of the current user
     * @return
     */
    public Map<String, FieldValidation> readFieldValidations(PageType pageType, List<String> roles);

    /**
     * Return field values
     * @param pageType the page type to search
     * @param roles the roles of the current user
     * @param object
     * @return
     */
    public Map<String, Object> readFieldValues(PageType pageType, List<String> roles, Object object);
    
    /**
     * Return field types
     * @param pageType the page type to search
     * @param roles the roles of the current user
     * @param object
     * @return
     */
    public Map<String, FieldDefinition> readFieldTypes(PageType pageType, List<String> roles);
}
