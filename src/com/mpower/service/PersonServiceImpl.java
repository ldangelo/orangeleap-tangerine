package com.mpower.service;

import java.util.Arrays;
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
import com.mpower.domain.entity.Person;
import com.mpower.domain.entity.customization.EntityDefault;
import com.mpower.domain.type.EntityType;

@Service("personService")
public class PersonServiceImpl implements PersonService {

    @Resource(name = "personDao")
    private PersonDao personDao;

    @Resource(name = "siteDao")
    private SiteDao siteDao;

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public Person maintainPerson(Person person) {
        return personDao.savePerson(person);
    }

    @Override
    public Person readByPersonById(Long id) {
        return personDao.readPerson(id);
    }

    @Override
    public List<Person> readPersons(Long siteId, Map<String, String> params) {
        return personDao.readPersons(siteId, params);
    }

    @Override
    public Person createDefaultPerson(Long siteId) {
        // get initial person with built-in defaults
        Person person = new Person();
        person.setSite(siteDao.readSite(siteId));
        BeanWrapper personBeanWrapper = new BeanWrapperImpl(person);

        // TODO: this only works for String attributes of Person, not attributes of Address (which is an attribute of Person)
        List<EntityDefault> entityDefaults = siteDao.readEntityDefaults(siteId, Arrays.asList(new EntityType[] { EntityType.person }));
        for (EntityDefault ed : entityDefaults) {
            personBeanWrapper.setPropertyValue(ed.getEntityFieldName(), ed.getDefaultValue());
        }

        // TODO: consider caching techniques for the default Person
        return person;
    }
}
