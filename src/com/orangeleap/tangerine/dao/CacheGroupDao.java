package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.CacheGroup;
import com.orangeleap.tangerine.type.CacheGroupType;


public interface CacheGroupDao {

    public List<CacheGroup> readCacheGroups();

    public int updateCacheGroupTimestamp(CacheGroupType id);
    
}
