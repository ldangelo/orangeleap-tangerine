package com.orangeleap.tangerine.ws.validation;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.orangeleap.common.security.OrangeLeapUsernamePasswordLocal;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.ws.schema.v2.Site;

public class SoapSiteValidator implements Validator {
	@Autowired
	SoapValidationManager validationManager;
	
	@Autowired
	TangerineUserHelper tangerineUserHelper;
	
	@Override
	public boolean supports(Class clazz) {
		if (clazz == Site.class) return true;
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Site site = (Site) target;
		
		if (site == null)
			errors.reject("SITE.NULL","Site Object can not be null.");
		if (site.getName() == null || site.getName().isEmpty()) {
			errors.reject("SITENAME.NULL","Site name can not be null or empty");
		} else {
			//
			// validate the site name is the same as the authenticated site name
			Map<String,Object> authInfo = OrangeLeapUsernamePasswordLocal.getOrangeLeapAuthInfo();
			String siteName = tangerineUserHelper.lookupUserSiteName();
			
			//
			// when we are running under TestNG technically we have not authenticated so the siteName is NULL
			if (!site.getName().equals(siteName)) errors.reject("SITENAME_NOMATCH","Site name must match user credentials");
		}

	}

}
