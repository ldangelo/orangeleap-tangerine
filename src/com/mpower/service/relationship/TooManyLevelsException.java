package com.mpower.service.relationship;

import com.mpower.service.exception.PersonValidationException;

public class TooManyLevelsException extends PersonValidationException {

	private static final long serialVersionUID = 1L;

	public TooManyLevelsException(String customFieldName) {
		String recursionError = "Relationship tree for "+customFieldName+" exceeds maximum number of levels.";
		logger.error(recursionError, this);
		this.addValidationResult("relationshipTooManyLevels", new Object[]{customFieldName}); 
	}

}
