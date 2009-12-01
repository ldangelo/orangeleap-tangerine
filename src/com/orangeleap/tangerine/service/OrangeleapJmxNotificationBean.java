package com.orangeleap.tangerine.service;

import java.util.Map;

public interface OrangeleapJmxNotificationBean  {
	
	public void publishNotification(String type, String message);

	public Map<String, Long> getLoginCounts();
	
}
