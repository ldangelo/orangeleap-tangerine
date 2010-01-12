package com.orangeleap.tangerine.ws.validation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.xml.bind.Unmarshaller.Listener;

public class OrangeLeapUnmarshallerListener extends Listener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OrangeLeapUnmarshallerListener.class);
	private List<Validator> validators = null;
	
	public OrangeLeapUnmarshallerListener() {
		validators = new LinkedList<Validator>();
	}
	
	public List<Validator> getValidators() {
		return validators;
	}

	public void setValidators(List<Validator> validators) {
		this.validators = validators;
	}

	private Validator findValidator(Class clazz) {
		Iterator<Validator> it = validators.iterator();
		while (it.hasNext()) {
			Validator validator = it.next();
			if (validator.supports(clazz)) return validator;
		}
		return null;
	}

	public void afterUnmarshal(Object target, Object parent) {
		if (target == null) return;
		
		if (parent != null)
			logger.debug("Unmarshal target: " + target.getClass().toString() + " parent:" + parent.getClass().toString());
		else
			logger.debug("Unmarshal target: " + target.getClass().toString());
		
		Validator validator = findValidator(target.getClass());
		if (validator != null){
			BindException errors = new BindException(target,target.getClass().toString());
			validator.validate(target, errors);
			
			// if (errors.hasErrors()) throw errors;
		}
	}
}
