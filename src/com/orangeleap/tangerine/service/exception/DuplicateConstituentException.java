package com.orangeleap.tangerine.service.exception;

public class DuplicateConstituentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Long duplicateConstituentID;
	
	DuplicateConstituentException() {
		super();
	}
	
	DuplicateConstituentException(String message) {
		super();
	}

	public void setDuplicateConstituentID(Long duplicateConstituentID) {
		this.duplicateConstituentID = duplicateConstituentID;
	}

	public Long getDuplicateConstituentID() {
		return duplicateConstituentID;
	}
	
	
}
