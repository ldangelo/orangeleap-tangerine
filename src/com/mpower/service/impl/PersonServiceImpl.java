package com.mpower.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.PersonDao;
import com.mpower.dao.SiteDao;
import com.mpower.domain.Person;
import com.mpower.domain.Site;
import com.mpower.domain.customization.EntityDefault;
import com.mpower.service.AuditService;
import com.mpower.service.PersonService;
import com.mpower.service.RelationshipService;
import com.mpower.service.exception.PersonValidationException;
import com.mpower.type.EntityType;

@Service("personService")
public class PersonServiceImpl implements PersonService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "relationshipService")
    private RelationshipService relationshipService;

    @Resource(name = "personDao")
    private PersonDao personDao;

    @Resource(name = "siteDao")
    private SiteDao siteDao; // TODO: replace with IBatis siteDAO

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = PersonValidationException.class)
    public Person maintainPerson(Person person) throws PersonValidationException {
    	
    	if (person.getConstituentType().equals(Person.ORGANIZATION) && StringUtils.isBlank(person.getLegalName())) {
    		person.setLegalName(person.getOrganizationName());
    	}
    	
    	if (person.getConstituentType().equals(Person.INDIVIDUAL) && StringUtils.isBlank(person.getRecognitionName())) {
    		person.setRecognitionName(person.createName(false));
    	}

    	person.setConstituentType(StringUtils.trimToEmpty(person.getConstituentType()).toLowerCase());
    	person.setConstituentIndividualRoles(StringUtils.trimToEmpty(person.getConstituentIndividualRoles()).toLowerCase());
    	person.setConstituentOrganizationRoles(StringUtils.trimToEmpty(person.getConstituentOrganizationRoles()).toLowerCase());
    	
        person = personDao.savePerson(person);
        relationshipService.maintainRelationships(person);
        auditService.auditObject(person);
        return person;
    }

    @Override
    public Person readPersonById(Long id) {
        return personDao.readPerson(id);
    }

    @Override
    public Person readPersonByLoginId(String id, String siteName) {
        return personDao.readPersonByLoginId(id, siteName);
    }

    @Override
    public List<Person> readPersons(String siteName, Map<String, Object> params) {
        return personDao.readPersons(siteName, params, null);
    }

    @Override
    public List<Person> readPersons(String siteName, Map<String, Object> params, List<Long> ignoreIds) {
        return personDao.readPersons(siteName, params, ignoreIds);
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

    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

	@Override
	public List<Person> readAllPeopleBySiteName(String siteName) {
        return personDao.readAllPeopleBySiteName(siteName);
	}
}
