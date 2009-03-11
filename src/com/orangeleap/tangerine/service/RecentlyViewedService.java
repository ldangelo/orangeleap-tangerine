package com.orangeleap.tangerine.service;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 */
public interface RecentlyViewedService {

    public List<Map> addRecentlyViewed(Long acctNumber);

    public List<Map> getRecentlyViewed();
}
