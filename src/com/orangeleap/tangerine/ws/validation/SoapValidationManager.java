package com.orangeleap.tangerine.ws.validation;

import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.orangeleap.tangerine.service.ws.exception.InvalidRequestException;

@Service("validationManager")
public class SoapValidationManager {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SoapValidationManager.class);

	private List<Validator> validators;
	
	public SoapValidationManager() {
		validators = new LinkedList<Validator>();
	}
	
	public void validate(Object obj) throws InvalidRequestException {
		if (obj == null) {
			logger.warn("Null Object handed to validate");
			return;
		}
		Iterator<Validator> it = validators.iterator();
		
		while (it.hasNext()) {
			Validator v = it.next();
			
			if (v.supports(obj.getClass()))
			{
				BindException errors = new BindException(obj, obj.getClass().getName());
				v.validate(obj, errors);
				
				if (errors.hasErrors())
					throw new InvalidRequestException(errors.getMessage());
			}
		}
	}

	public void validate(Object obj,Errors errors) {
		if (obj == null) {
			logger.warn("Null Object handed to validate");
			return;
		}
		Iterator<Validator> it = validators.iterator();
		
		while (it.hasNext()) {
			Validator v = it.next();
			
			if (v.supports(obj.getClass()))
			{
				v.validate(obj, errors);
				return;
			}
		}
		logger.warn("No handler found for class " + obj.getClass().getName());
	}

	public List<Validator> getValidators() {
		return validators;
	}

	public void setValidators(List<Validator> validators) {
		this.validators = validators;
	}
}
