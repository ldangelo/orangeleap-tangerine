package com.mpower.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mpower.domain.Person;

public interface PersonDao {

    public Person savePerson(Person person);

    public Person readPerson(Long id);

    public List<Person> readPersons(Long siteId, Map<String, Object> params);

    public List<Person> analyzeLapsedDonor(Date beginDate, Date currentDate);

    public List<Person> readAllPeople();

    public void setLapsedDonor(Long personId);

}