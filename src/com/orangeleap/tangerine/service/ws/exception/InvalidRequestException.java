package com.orangeleap.tangerine.service.ws.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.SERVER)
public class InvalidRequestException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2907877811520467699L;

	public InvalidRequestException(String message) {
		super(message);
	}
}
