package com.mpower.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.PersonDao;
import com.mpower.dao.SiteDao;
import com.mpower.dao.customization.FieldDao;
import com.mpower.domain.Person;
import com.mpower.domain.Site;
import com.mpower.domain.customization.EntityDefault;
import com.mpower.domain.customization.FieldValidation;
import com.mpower.domain.customization.RequiredField;
import com.mpower.service.exception.PersonValidationException;
import com.mpower.service.validation.PersonValidator;
import com.mpower.type.EntityType;

@Service("personService")
public class PersonServiceImpl implements PersonService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "fieldDao")
    private FieldDao fieldDao;

    @Resource(name = "personDao")
    private PersonDao personDao;

    @Resource(name = "siteDao")
    private SiteDao siteDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
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
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Person> readAllPeople() {
        return personDao.readAllPeople();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void setLapsedDonor(Long personId) {
        personDao.setLapsedDonor(personId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Person> readAllPeopleBySite(Site site) {
        return personDao.readAllPeopleBySite(site);
    }

    @Override
    public Map<String, Boolean> readPersonRequireds(String siteName, EntityType entityType) {
        Map<String, Boolean> returnMap = new HashMap<String, Boolean>();
        List<RequiredField> requiredFields = fieldDao.readRequiredFields(siteName, entityType);
        if (requiredFields != null) {
            for (RequiredField rf : requiredFields) {
                String key = rf.getFieldDefinition().getFieldName();
                if (rf.getSecondaryFieldDefinition() != null) {
                    key += "." + rf.getSecondaryFieldDefinition().getFieldName();
                }
                if (returnMap.get(key) == null || rf.getSiteName() != null) {
                    returnMap.put(key, rf.isRequired());
                }
            }
        }
        return returnMap;
    }

    @Override
    public Map<String, String> readPersonValidations(String siteName, EntityType entityType) {
        Map<String, String> returnMap = new HashMap<String, String>();
        List<FieldValidation> validations = fieldDao.readFieldValidations(siteName, entityType);
        if (validations != null) {
            for (FieldValidation v : validations) {
                String key = v.getFieldDefinition().getFieldName();
                if (v.getSecondaryFieldDefinition() != null) {
                    key += "." + v.getSecondaryFieldDefinition().getFieldName();
                }
                if (returnMap.get(key) == null || v.getSiteName() != null) {
                    returnMap.put(key, v.getRegex());
                }
            }
        }
        return returnMap;
    }
}
