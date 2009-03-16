package com.orangeleap.tangerine.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.ConstituentDao;
import com.orangeleap.tangerine.dao.GiftDao;
import com.orangeleap.tangerine.dao.SiteDao;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.customization.EntityDefault;
import com.orangeleap.tangerine.service.AddressService;
import com.orangeleap.tangerine.service.AuditService;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.EmailService;
import com.orangeleap.tangerine.service.PhoneService;
import com.orangeleap.tangerine.service.RelationshipService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.web.common.SortInfo;

@Service("constituentService")
public class ConstituentServiceImpl extends AbstractTangerineService implements ConstituentService {

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
        constituent = constituentDao.maintainConstituent(constituent);
        relationshipService.maintainRelationships(constituent);
        auditService.auditObject(constituent, constituent);
        return constituent;
    }

    @Override
    public Person readConstituentById(Long id) {
        if (logger.isDebugEnabled()) {
            logger.debug("readConstituentById: id = " + id);
        }
        Person constituent = constituentDao.readConstituentById(id);
        if (constituent != null) {
            constituent.setAddresses(addressService.readByConstituentId(constituent.getId()));
            constituent.setPhones(phoneService.readByConstituentId(constituent.getId()));
            constituent.setEmails(emailService.readByConstituentId(constituent.getId()));
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Person> readAllConstituentsBySite(SortInfo sort) {
        if (logger.isDebugEnabled()) {
            logger.debug("readAllConstituentsBySite:" + sort);
        }

        return constituentDao.readAllConstituentsBySite(sort.getSort(), sort.getDir(), sort.getStart(), sort.getLimit());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int getConstituentCountBySite() {

        return constituentDao.getConstituentCountBySite();
    }
}
