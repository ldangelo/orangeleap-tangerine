package com.orangeleap.tangerine.service;


public interface ErrorLogService {

	public void addErrorMessage(String message, String context);
	public void removeErrorMessagesOlderThanDays(int days);
	
}
