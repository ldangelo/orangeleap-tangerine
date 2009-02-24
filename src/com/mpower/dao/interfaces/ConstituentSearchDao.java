package com.mpower.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.mpower.domain.model.Person;

public interface ConstituentSearchDao {

    public List<Person> searchConstituents(Map<String, Object> searchParams);
    
}
