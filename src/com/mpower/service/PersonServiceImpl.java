package com.mpower.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.PersonDao;
import com.mpower.dao.SiteDao;
import com.mpower.domain.Person;
import com.mpower.domain.customization.EntityDefault;
import com.mpower.service.exception.PersonValidationException;
import com.mpower.service.validation.PersonValidator;
import com.mpower.type.EntityType;

@Service("personService")
public class PersonServiceImpl implements PersonService {

    @Resource(name = "personDao")
    private PersonDao personDao;

    @Resource(name = "siteDao")
    private SiteDao siteDao;

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public Person maintainPerson(Person person) throws PersonValidationException {
        PersonValidator.validatePerson(person);
        return personDao.savePerson(person);
    }

    @Override
    public Person readPersonById(Long id) {
        return personDao.readPerson(id);
    }

    @Override
    public List<Person> readPersons(String siteName, Map<String, Object> params) {
        return personDao.readPersons(siteName, params);
    }

    @Override
    public Person createDefaultPerson(String siteName) {
        // get initial person with built-in defaults
        Person person = new Person();
        person.setSite(siteDao.readSite(siteName));
        BeanWrapper personBeanWrapper = new BeanWrapperImpl(person);

        List<EntityDefault> entityDefaults = siteDao.readEntityDefaults(siteName, Arrays.asList(new EntityType[] { EntityType.person }));
        for (EntityDefault ed : entityDefaults) {
            personBeanWrapper.setPropertyValue(ed.getEntityFieldName(), ed.getDefaultValue());
        }

        // TODO: consider caching techniques for the default Person
        return person;
    }

    @Override
    public List<Person> analyzeLapsedDonor(Date beginDate, Date currentDate) {
    	return personDao.analyzeLapsedDonor(beginDate, currentDate);
    }

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Person> readAllPeople() {
		return personDao.readAllPeople();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void setLapsedDonor(Long personId) {
		personDao.setLapsedDonor(personId);
	}
}
