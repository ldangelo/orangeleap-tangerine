package com.mpower.dao.interfaces;

import java.util.List;

import com.mpower.domain.model.Person;

public interface ConstituentDao {

    public Person readConstituentById(Long id);

    public List<Person> readAllConstituentsBySite(String siteName);

    public Person readConstituentByLoginIdSite(String loginId, String siteName);

    public List<Person> readConstituentsByIdsSite(String siteName, List<Long> ids);

}
