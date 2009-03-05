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
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.interfaces.ConstituentDao;
import com.mpower.dao.interfaces.GiftDao;
import com.mpower.dao.interfaces.SiteDao;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.customization.EntityDefault;
import com.mpower.service.AddressService;
import com.mpower.service.AuditService;
import com.mpower.service.ConstituentService;
import com.mpower.service.EmailService;
import com.mpower.service.PhoneService;
import com.mpower.service.RelationshipService;
import com.mpower.service.exception.ConstituentValidationException;
import com.mpower.type.EntityType;
import com.mpower.util.TangerineUserHelper;

@Service("constituentService")
public class ConstituentServiceImpl implements ConstituentService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "addressService")
    private AddressService addressService;

    @Resource(name = "phoneService")
    private PhoneService phoneService;

    @Resource(name = "emailService")
    private EmailService emailService;

    @Resource(name = "relationshipService")
    private RelationshipService relationshipService;

    @Resource(name = "constituentDAO")
    private ConstituentDao constituentDao;

    @Resource(name = "siteDAO")
    private SiteDao siteDao;

    @Resource(name = "giftDAO")
    private GiftDao giftDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ConstituentValidationException.class)
    public Person maintainConstituent(Person constituent) throws ConstituentValidationException {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainConstituent: constituent = " + constituent);
        }
        if (constituent.getSite() == null || tangerineUserHelper.lookupUserSiteName().equals(constituent.getSite().getName()) == false) {
            throw new ConstituentValidationException(); 
        }
    	if (constituent.getConstituentType().equals(Person.ORGANIZATION) && StringUtils.isBlank(constituent.getLegalName())) {
    		constituent.setLegalName(constituent.getOrganizationName());
    	}
    	if (constituent.getConstituentType().equals(Person.INDIVIDUAL) && StringUtils.isBlank(constituent.getRecognitionName())) {
    		constituent.setRecognitionName(constituent.createName(false));
    	}
    	constituent.setConstituentType(StringUtils.trimToEmpty(constituent.getConstituentType()).toLowerCase());
    	constituent.setConstituentIndividualRoles(StringUtils.trimToEmpty(constituent.getConstituentIndividualRoles()).toLowerCase());
    	constituent.setConstituentOrganizationRoles(StringUtils.trimToEmpty(constituent.getConstituentOrganizationRoles()).toLowerCase());
    	
        constituent = constituentDao.maintainConstituent(constituent);
        relationshipService.maintainRelationships(constituent);
        auditService.auditObject(constituent);
        return constituent;
    }

    @Override
    public Person readConstituentById(Long id) {
        if (logger.isDebugEnabled()) {
            logger.debug("readConstituentById: id = " + id);
        }
        Person constituent = constituentDao.readConstituentById(id);
        if (constituent != null) {
            constituent.setAddresses(addressService.readAddressesByConstituentId(constituent.getId()));
            constituent.setPhones(phoneService.readPhonesByConstituentId(constituent.getId()));
            constituent.setEmails(emailService.readEmailsByConstituentId(constituent.getId()));
        }
        return constituent;
    }

    @Override
    public Person readConstituentByLoginId(String loginId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readConstituentByLoginId: loginId = " + loginId);
        }
        return constituentDao.readConstituentByLoginId(loginId);
    }

    @Override
    public List<Person> searchConstituents(Map<String, Object> params) {
        if (logger.isDebugEnabled()) {
            logger.debug("searchConstituents: params = " + params);
        }
        return constituentDao.searchConstituents(params, null);
    }

    @Override
    public List<Person> searchConstituents(Map<String, Object> params, List<Long> ignoreIds) {
        if (logger.isDebugEnabled()) {
            logger.debug("searchConstituents: params = " + params + " ignoreIds = " + ignoreIds);
        }
        return constituentDao.searchConstituents(params, ignoreIds);
    }

    @Override
    public Person createDefaultConstituent() {
        if (logger.isDebugEnabled()) {
            logger.debug("createDefaultConstituent:");
        }
        // get initial person with built-in defaults
        Person constituent = new Person();
        constituent.setSite(siteDao.readSite(tangerineUserHelper.lookupUserSiteName()));
        BeanWrapper personBeanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(constituent);

        List<EntityDefault> entityDefaults = siteDao.readEntityDefaults(Arrays.asList(new EntityType[] { EntityType.person }));
        for (EntityDefault ed : entityDefaults) {
            personBeanWrapper.setPropertyValue(ed.getEntityFieldName(), ed.getDefaultValue());
        }

        // TODO: consider caching techniques for the default Person
        return constituent;
    }

    @Override
    public List<Person> analyzeLapsedDonor(Date beginDate, Date currentDate) {
        if (logger.isDebugEnabled()) {
            logger.debug("analyzeLapsedDonor: beginDate = " + beginDate + " currentDate = " + currentDate);
        }
        return giftDao.analyzeLapsedDonor(beginDate, currentDate);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void setLapsedDonor(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("setLapsedDonor: constituentId = " + constituentId);
        }
        constituentDao.setLapsedDonor(constituentId);
    }

	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public List<Person> readAllConstituentsBySite() {
        if (logger.isDebugEnabled()) {
            logger.debug("readAllConstituentsBySite:");
        }
        return constituentDao.readAllConstituentsBySite();
	}
}
