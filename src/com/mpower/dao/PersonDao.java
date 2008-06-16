package com.mpower.dao;

import java.util.List;
import java.util.Map;

import com.mpower.domain.entity.Person;

public interface PersonDao {

	public Person savePerson(Person person);

	public Person readPerson(Long id);

    public List<Person> readPersons(Long siteId, Map<String, String> params);
}
