package com.orangeleap.tangerine.dao;

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.Person;

public interface ConstituentSearchDao {

    public List<Person> searchConstituents(Map<String, Object> searchParams);
    
}
