package com.mpower.service.relationship;

import com.mpower.service.exception.ConstituentValidationException;

public class TooManyLevelsException extends ConstituentValidationException {

	private static final long serialVersionUID = 1L;

	public TooManyLevelsException(String customFieldName) {
		String recursionError = "Relationship tree for "+customFieldName+" exceeds maximum number of levels.";
		logger.error(recursionError, this);
		this.addValidationResult("relationshipTooManyLevels", new Object[]{customFieldName}); 
	}

}
