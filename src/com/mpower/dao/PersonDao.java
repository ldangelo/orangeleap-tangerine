package com.mpower.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mpower.domain.Person;
import com.mpower.domain.Site;

public interface PersonDao {

    public Person savePerson(Person person);

    public Person readPerson(Long id);

    public List<Person> readPersons(String siteName, Map<String, Object> params);

    public List<Person> readPersons(String siteName, Map<String, Object> params, List<Long> ignoreIds);

    public List<Person> analyzeLapsedDonor(Date beginDate, Date currentDate);

    public List<Person> readAllPeople();

    public void setLapsedDonor(Long personId);

    public List<Person> readAllPeopleBySite(Site site);
}