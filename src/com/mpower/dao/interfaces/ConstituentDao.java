package com.mpower.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.mpower.domain.model.Person;
import com.mpower.web.common.Sort;

public interface ConstituentDao {

    public Person readConstituentById(Long id);

    public List<Person> readAllConstituentsBySite();

    public List<Person> readAllConstituentsBySite(String sortColumn, String dir, int start, int limit);

    public int getConstituentCountBySite();

    public Person readConstituentByLoginId(String loginId);

    public List<Person> readConstituentsByIds(List<Long> ids);

    public Person maintainConstituent(Person constituent);
    
    public void setLapsedDonor(Long constituentId);
    
    public List<Person> searchConstituents(Map<String, Object> params, List<Long> ignoreIds);

}
