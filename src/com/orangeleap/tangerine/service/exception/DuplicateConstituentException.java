package com.orangeleap.tangerine.service.exception;

public class DuplicateConstituentException extends ConstituentValidationException {

	private static final long serialVersionUID = 1L;

	private Long duplicateConstituentID;
	
	public DuplicateConstituentException() {
		super();
	}
	
	public DuplicateConstituentException(String message) {
		super(message);
	}

	public void setDuplicateConstituentID(Long duplicateConstituentID) {
		this.duplicateConstituentID = duplicateConstituentID;
	}

	public Long getDuplicateConstituentID() {
		return duplicateConstituentID;
	}
	
	
}
