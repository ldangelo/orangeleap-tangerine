package com.orangeleap.tangerine.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface ConstituentService {

    public Person maintainConstituent(Person constituent) throws ConstituentValidationException;

    public Person readConstituentById(Long id);

    public Person readConstituentByLoginId(String id);

	public List<Person> searchConstituents(Map<String, Object> params);

    public List<Person> searchConstituents(Map<String, Object> params, List<Long> ignoreIds);

    public Person createDefaultConstituent();

    public List<Person> analyzeLapsedDonor(Date beginDate, Date currentDate);

    public void setLapsedDonor(Long constituentId);

    public List<Person> readAllConstituentsBySite();

    public List<Person> readAllConstituentsBySite(SortInfo sort);

    public int getConstituentCountBySite();

	public List<Person> readAllConstituentsByIdRange(Long fromId, Long toId);
	
}
