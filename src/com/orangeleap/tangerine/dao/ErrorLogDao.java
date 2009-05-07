package com.orangeleap.tangerine.dao;


public interface ErrorLogDao {

	public void addErrorMessage(String message, String context, Long constituentId);
	
}
