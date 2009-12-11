package com.orangeleap.tangerine.service.rule;

public class OrangeLeapConsequenceRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Exception cause;
	
	public OrangeLeapConsequenceRuntimeException(Exception cause) {
		this.cause = cause;
	}
	public Exception getCause() {return cause;}
}
