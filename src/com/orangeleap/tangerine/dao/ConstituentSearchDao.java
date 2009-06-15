package com.orangeleap.tangerine.dao;

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.Constituent;

public interface ConstituentSearchDao {

    public List<Constituent> searchConstituents(Map<String, Object> searchParams);
    
}
