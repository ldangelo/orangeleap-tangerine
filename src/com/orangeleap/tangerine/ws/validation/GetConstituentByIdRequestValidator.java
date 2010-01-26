package com.orangeleap.tangerine.ws.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.orangeleap.tangerine.ws.schema.v2.GetConstituentByIdRequest;

public class GetConstituentByIdRequestValidator implements Validator {

	@Override
	public boolean supports(Class arg0) {
		if (arg0 == GetConstituentByIdRequest.class) return true; 
		return false;
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		GetConstituentByIdRequest request = (GetConstituentByIdRequest) arg0;
		
		if (request.getId() <= 0)
			errors.reject("Invalid constituent id.");

	}

}
