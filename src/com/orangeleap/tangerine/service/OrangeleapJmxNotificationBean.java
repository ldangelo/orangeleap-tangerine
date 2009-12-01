package com.orangeleap.tangerine.service;


public interface OrangeleapJmxNotificationBean extends OrangeleapJmxNotification {
	
	public void publishNotification(String type, String message);

	public void incrementStatCount(String sitename, String statname);

 
}
