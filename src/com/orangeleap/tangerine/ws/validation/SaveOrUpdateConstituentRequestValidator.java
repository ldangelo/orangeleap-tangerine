package com.orangeleap.tangerine.ws.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.orangeleap.tangerine.service.ws.exception.InvalidRequestException;
import com.orangeleap.tangerine.ws.schema.v2.Constituent;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdateConstituentRequest;

public class SaveOrUpdateConstituentRequestValidator implements Validator {
	@Autowired
	private SoapValidationManager validationManager;

	@Override
	public boolean supports(Class clazz) {
		if (clazz == SaveOrUpdateConstituentRequest.class) return true;
		
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		SaveOrUpdateConstituentRequest request = (SaveOrUpdateConstituentRequest) target;
		Constituent c = request.getConstituent();
		
		if (c == null) errors.reject("SAVEORUPDATECONSTITUENTREQUEST_NULL","SaveOrUpdateconstituentRequest must contain a constituent");
		validationManager.validate(c,errors);
	}

}
