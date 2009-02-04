package com.mpower.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mpower.domain.Person;
import com.mpower.domain.Site;

public interface PersonDao {

    public Person savePerson(Person person);

    public Person readPerson(Long id);
    
    public Person readPersonByLoginId(String id, String siteName);
    
    public List<Person> readPersons(String siteName, List<Long> ids);

    public List<Person> readPersons(String siteName, Map<String, Object> params);

    public List<Person> readPersons(String siteName, Map<String, Object> params, List<Long> ignoreIds);

    public List<Person> analyzeLapsedDonor(Date beginDate, Date currentDate);

    public List<Person> readAllPeople();

    public void setLapsedDonor(Long personId);

    public List<Person> readAllPeopleBySite(Site site);

	public List<Person> readAllPeopleBySiteName(String siteName);
}