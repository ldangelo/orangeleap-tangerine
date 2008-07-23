package com.mpower.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mpower.domain.Person;
import com.mpower.service.exception.PersonValidationException;

public interface PersonService {

    public Person maintainPerson(Person person) throws PersonValidationException;

    public Person readPersonById(Long id);

    public List<Person> readPersons(Long siteId, Map<String, Object> params);

    public Person createDefaultPerson(Long siteId);

    public List<Person> analyzeLapsedDonor(Date beginDate, Date currentDate);

    public List<Person> readAllPeople();

    public void setLapsedDonor(Long personId);

}
