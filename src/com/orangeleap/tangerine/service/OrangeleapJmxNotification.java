package com.orangeleap.tangerine.service;

import java.util.Map;

public interface OrangeleapJmxNotification    {
	
    public Map<String, Map<String, Long>> getAllSiteCounts();
    public Map<String, Long> getSiteCounts(String sitename);
    
}
