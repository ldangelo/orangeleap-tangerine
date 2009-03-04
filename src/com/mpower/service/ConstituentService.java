package com.mpower.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mpower.domain.model.Person;
import com.mpower.service.exception.PersonValidationException;

public interface ConstituentService {

    public Person maintainConstituent(Person constituent) throws PersonValidationException;

    public Person readConstituentById(Long id);

    public Person readConstituentByLoginId(String id);

	public List<Person> searchConstituents(Map<String, Object> params);

    public List<Person> searchConstituents(Map<String, Object> params, List<Long> ignoreIds);

    public Person createDefaultConstituent();

    public List<Person> analyzeLapsedDonor(Date beginDate, Date currentDate);

    public void setLapsedDonor(Long constituentId);

    public List<Person> readAllConstituentsBySite();
}
