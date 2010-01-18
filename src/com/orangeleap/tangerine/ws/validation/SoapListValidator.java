package com.orangeleap.tangerine.ws.validation;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SoapListValidator implements Validator {
	@Autowired
	SoapValidationManager validationManager;
	
	@Override
	public boolean supports(Class clazz) {
		if (clazz == List.class) return true;
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		List<Object> list = (List) target;
		
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			validationManager.validate(obj, errors);
		}
	}

}
