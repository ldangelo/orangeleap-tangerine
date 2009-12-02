package com.orangeleap.tangerine.service;

import java.util.Map;

// JMX interface
public interface OrangeleapJmxNotification    {
	
	public Long getTotalLoginCount();
	public Long getTotalAuthorizeCount();
	public Long getTotalCaptureCount();
	public Long getTotalAuthorizeAndCaptureCount();

    public Map<String, Map<String, Long>> getAllSiteCounts();
    public Map<String, Long> getSiteCounts(String sitename);
    
}
