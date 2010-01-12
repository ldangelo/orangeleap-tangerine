package com.orangeleap.tangerine.ws.validation;

import org.apache.log4j.Logger;

import javax.xml.bind.Marshaller.Listener;

public class OrangeLeapMarshallerListener extends Listener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OrangeLeapMarshallerListener.class);
	
	
	@Override
	public	void afterMarshal(Object source) {
		logger.debug(source.getClass().toString());
	}

}
