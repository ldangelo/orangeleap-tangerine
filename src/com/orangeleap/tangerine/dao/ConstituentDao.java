package com.orangeleap.tangerine.dao;

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.Person;

public interface ConstituentDao {

    public Person readConstituentById(Long id);

    public Person readConstituentByAccountNumber(String accountNumber);

    public List<Person> readAllConstituentsBySite();

    public List<Person> readAllConstituentsBySite(String sortColumn, String dir, int start, int limit);

    public int getConstituentCountBySite();

    public Person readConstituentByLoginId(String loginId);

    public List<Person> readConstituentsByIds(List<Long> ids);

    public Person maintainConstituent(Person constituent);

    public List<Person> searchConstituents(Map<String, Object> params);

    public List<Person> searchConstituents(Map<String, Object> params, List<Long> ignoreIds);

	public List<Person> readAllConstituentsByAccountRange(Long fromId, Long toId);

	public List<Person> findConstituents(Map<String, Object> params,
			List<Long> ignoreIds);

}
