package com.mpower.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.mpower.domain.model.Person;

public interface ConstituentDao {

    public Person readConstituentById(Long id);

    public List<Person> readAllConstituentsBySite();

    public Person readConstituentByLoginId(String loginId);

    public List<Person> readConstituentsByIds(List<Long> ids);

    public Person maintainConstituent(Person constituent);
    
    public void setLapsedDonor(Long constituentId);
    
    public List<Person> searchPersons(Map<String, Object> params, List<Long> ignoreIds);

}
