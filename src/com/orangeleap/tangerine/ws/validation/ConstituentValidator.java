package com.orangeleap.tangerine.ws.validation;

import java.util.Iterator;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.ws.schema.v2.*;

public class ConstituentValidator implements Validator {
	@Autowired
	ConstituentService constituentService;

	@Autowired
	SoapValidationManager validationManager;

	@Autowired
	ResourceBundleMessageSource bundleMessageSource;
	
	@Override
	public boolean supports(Class clazz) {
		if (clazz == Constituent.class)
			return true;
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Constituent c = (Constituent) target;

		if (c.getId() != null) {
			if (c.getId() < 0)
				errors.reject("soapInvalidConstituentId");

			if (c.getId() > 0) {
				//
				// we are going to try and update a constituent record...
				// let's see if we can find this constituent... if not add an
				// error
				com.orangeleap.tangerine.domain.Constituent lookup = constituentService
						.readConstituentById(c.getId());
				if (lookup == null)
					errors.reject("soapConstituentNotFound");
			}
		}
		if (c.getConstituentType() == null
				|| (!c.getConstituentType().equals("individual") && !c.getConstituentType().equals("organization"))) {
			errors.reject(bundleMessageSource.getMessage("soapInvalidConstituentType", null, Locale.ENGLISH));
		
		} else 	if (c.getConstituentType().equals("individual")) {
			//
			// Make sure the FirstName and LastName are set
			if (c.getFirstName() == null || c.getFirstName().isEmpty())
				errors.reject("soapInvalidConstituentIndividual");
			if (c.getLastName() == null || c.getLastName().isEmpty())
				errors.reject("soapInvalidConstituentIndividual");
		} else {
			if (c.getOrganizationName() == null || c.getOrganizationName().isEmpty())
				errors.reject("soapInvalidConstituentOrganization");
		}
		
		validationManager.validate(c.getAddresses(),errors);
		validationManager.validate(c.getEmails(),errors);
		validationManager.validate(c.getPhones(),errors);

	}

}
