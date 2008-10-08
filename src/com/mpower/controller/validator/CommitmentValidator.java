package com.mpower.controller.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mpower.domain.Commitment;
import com.mpower.service.SiteService;
import com.mpower.type.PageType;

public class CommitmentValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unused")
    private SiteService siteService;

    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }

    @SuppressWarnings("unused")
    private PageType pageType;

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return Commitment.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.debug("in CommitmentValidator");
        // Viewable viewable = (Viewable) target;
        //
        // Map<String, String> fieldLabelMap = viewable.getFieldLabelMap();
        //
        // // used as a cache to prevent having to use reflection if the value has already been read
        // Map<String, Object> fieldValueMap = new HashMap<String, Object>();
        //
        // // used to know that a field already has an error, so don't add another
        // Set<String> errorSet = new HashSet<String>();
    }
}
