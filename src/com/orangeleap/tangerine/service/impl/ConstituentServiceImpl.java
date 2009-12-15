/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.service.impl;

import com.orangeleap.tangerine.controller.validator.ConstituentValidator;
import com.orangeleap.tangerine.controller.validator.EntityValidator;
import com.orangeleap.tangerine.dao.ConstituentDao;
import com.orangeleap.tangerine.dao.EntitySearchDao;
import com.orangeleap.tangerine.dao.GiftDao;
import com.orangeleap.tangerine.dao.SiteDao;
import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.integration.NewConstituent;
import com.orangeleap.tangerine.service.AddressService;
import com.orangeleap.tangerine.service.AuditService;
import com.orangeleap.tangerine.service.CommunicationHistoryService;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.EmailService;
import com.orangeleap.tangerine.service.ErrorLogService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PhoneService;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.RelationshipService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.communication.MailService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.service.exception.DuplicateConstituentException;
import com.orangeleap.tangerine.service.rule.DroolsRuleAgent;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.RulesStack;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.util.TaskStack;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.drools.FactHandle;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.WorkingMemory;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service("constituentService")
@Transactional(propagation = Propagation.REQUIRED)
public class ConstituentServiceImpl extends AbstractTangerineService implements ConstituentService, ApplicationContextAware {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "errorLogService")
    private ErrorLogService errorLogService;

    @Resource(name = "constituentEntityValidator")
    protected EntityValidator entityValidator;

    @Resource(name = "constituentValidator")
    protected ConstituentValidator constituentValidator;

    @Resource(name = "tangerineUserHelper")
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

	@Resource(name = "entitySearchDAO")
	private EntitySearchDao entitySearchDao;

    @Resource(name = "giftDAO")
    private GiftDao giftDao;

	@Resource(name = "giftService")
	private GiftService giftService;

    @Resource(name = "communicationHistoryService")
    private CommunicationHistoryService communicationHistoryService;

    private ApplicationContext context;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstituentValidationException.class, BindException.class})
    public Constituent maintainConstituent(Constituent constituent) throws ConstituentValidationException, BindException {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainConstituent: constituent = " + constituent);
        }
        if (constituent.getSite() == null || !tangerineUserHelper.lookupUserSiteName().equals(constituent.getSite().getName())) {
            throw new ConstituentValidationException();
        }

        setOptInPrefs(constituent);

        if (constituent.getFieldLabelMap() != null && !constituent.isSuppressValidation()) {

            setPicklistDefaultsForRequiredFields(constituent, PageType.constituent, tangerineUserHelper.lookupUserRoles());

            BindingResult br = new BeanPropertyBindingResult(constituent, "constituent");
            BindException errors = new BindException(br);

	        entityValidator.validate(constituent, errors);
            constituentValidator.validate(constituent, errors);

            if (errors.hasErrors()) {
                throw errors;
            }
        }

        constituent = constituentDao.maintainConstituent(constituent);
        constituent.setAccountNumber(constituentDao.readConstituentById(constituent.getId()).getAccountNumber()); // read generated acct no
        maintainCorrespondence(constituent);

        Address address = constituent.getPrimaryAddress();
        Phone phone = constituent.getPrimaryPhone();
        Email email = constituent.getPrimaryEmail();

        if (address != null && address.isAddressEntered()) {
            address.setConstituentId(constituent.getId());
            addressService.save(address);
        }
        if (phone != null && phone.isPhoneEntered()) {
            phone.setConstituentId(constituent.getId());
            phoneService.save(phone);
        }
        if (email != null && email.isEmailEntered()) {
            email.setConstituentId(constituent.getId());
            emailService.save(email);
        }

        relationshipService.maintainRelationships(constituent);
        auditService.auditObject(constituent, constituent);

        routeConstituent(constituent);

        entitySearchDao.updateFullTextIndex(readConstituentById(constituent.getId()));

        return constituent;
    }


    private void setOptInPrefs(Constituent constituent) {
        String communicationPreferences = constituent.getCustomFieldValue("communicationPreferences");
        String communicationOptInPreferences = constituent.getCustomFieldValue("communicationOptInPreferences");
        if ("Opt In".equals(communicationPreferences) && StringUtils.trimToNull(communicationOptInPreferences) == null) {
    		Picklist picklist = picklistItemService.getPicklist("customFieldMap[communicationOptInPreferences]");
    		String defaultValue = (picklist == null || picklist.getActivePicklistItems().size() == 0) ? "Unknown" : picklist.getActivePicklistItems().get(0).getItemName();
            constituent.setCustomFieldValue("communicationOptInPreferences", defaultValue);
        }
    }


    private final static String ROUTE_METHOD = "ConstituentServiceImpl.routeConstituent";

    void routeConstituent(Constituent constituent) throws ConstituentValidationException {

    	boolean wasRollbackOnly = OLLogger.isCurrentTransactionMarkedRollbackOnly(context);

        boolean reentrant = RulesStack.push(ROUTE_METHOD);
        try {

        	if (!reentrant){
            	try {

                    NewConstituent newConstituent = (NewConstituent) context.getBean("newConstituent");
                    newConstituent.routeConstituent(constituent);
                }
                catch (DuplicateConstituentException dce) {
                    throw dce;
                }
                catch (ConstituentValidationException cve) {
                    throw cve;
                }
                catch (Exception ex) {
                    logger.error("RULES_FAILURE: " + ex.getMessage(), ex);
                    // Cannot start new transaction to record error when current transaction has timed out waiting on external connection issue.
                    String msg = "" + ex.getMessage();
                    if (!msg.contains("timeout") && !msg.contains("Connection refused")) {
                        writeRulesFailureLog(ex.getMessage() + "\r\n" + constituent);
                    }
                }
        	}
        } finally {
            RulesStack.pop(ROUTE_METHOD);
        }

    	boolean isRollbackOnly = OLLogger.isCurrentTransactionMarkedRollbackOnly(context);

    	if (!wasRollbackOnly && isRollbackOnly) {
    		logger.error("Rules processing caused transaction rollback for constituent "+constituent.getId());
    	}

    }

    private synchronized void writeRulesFailureLog(String message) {
        try {

            errorLogService.addErrorMessage(message, "gift.rules");

        } catch (Exception e) {
            logger.error("Unable to write to rules error log file: " + message);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void maintainCorrespondence(Constituent constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainCorrespondence: constituent.id = " + constituent.getId());
        }
        String communicationPref = constituent.getCustomFieldValue(StringConstants.COMMUNICATION_PREFERENCES);
        if (StringConstants.OPT_OUT_ALL.equals(communicationPref)) {
            addressService.maintainResetReceiveCorrespondence(constituent.getId());
            phoneService.maintainResetReceiveCorrespondence(constituent.getId());
            phoneService.maintainResetReceiveCorrespondenceText(constituent.getId());
            emailService.maintainResetReceiveCorrespondence(constituent.getId());

            addressService.resetReceiveCorrespondence(constituent.getPrimaryAddress());
            phoneService.resetReceiveCorrespondence(constituent.getPrimaryPhone());
            phoneService.resetReceiveCorrespondenceText(constituent.getPrimaryPhone());
            emailService.resetReceiveCorrespondence(constituent.getPrimaryEmail());
        } else if (StringConstants.OPT_IN.equals(communicationPref)) {
            // Mail
            if (constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.MAIL_CAMEL_CASE) ||
                    constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.ANY_CAMEL_CASE) ||
                    constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.UNKNOWN_CAMEL_CASE)) {
                if (constituent.getPrimaryAddress() != null) {
                    constituent.getPrimaryAddress().setReceiveCorrespondence(true);
                }
            } else {
                addressService.maintainResetReceiveCorrespondence(constituent.getId());
                addressService.resetReceiveCorrespondence(constituent.getPrimaryAddress());
            }

            // Phone (Call)
            if (constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.PHONE_CAMEL_CASE) ||
                    constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.ANY_CAMEL_CASE) ||
                    constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.UNKNOWN_CAMEL_CASE)) {
                if (constituent.getPrimaryPhone() != null) {
                    constituent.getPrimaryPhone().setReceiveCorrespondence(true);
                }
            } else {
                phoneService.maintainResetReceiveCorrespondence(constituent.getId());
                phoneService.resetReceiveCorrespondence(constituent.getPrimaryPhone());
            }

            // Phone Text (SMS)
            if (constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.TEXT_CAMEL_CASE) ||
                    constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.ANY_CAMEL_CASE) ||
                    constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.UNKNOWN_CAMEL_CASE)) {
                if (constituent.getPrimaryPhone() != null) {
                    constituent.getPrimaryPhone().setReceiveCorrespondenceText(true);
                }
            } else {
                phoneService.maintainResetReceiveCorrespondenceText(constituent.getId());
                phoneService.resetReceiveCorrespondenceText(constituent.getPrimaryPhone());
            }

            // Email
            if (constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.EMAIL_CAMEL_CASE) ||
                    constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.ANY_CAMEL_CASE) ||
                    constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.UNKNOWN_CAMEL_CASE)) {
                if (constituent.getPrimaryEmail() != null) {
                    constituent.getPrimaryEmail().setReceiveCorrespondence(true);
                }
            } else {
                emailService.maintainResetReceiveCorrespondence(constituent.getId());
                emailService.resetReceiveCorrespondence(constituent.getPrimaryEmail());
            }
        }
    }


    @Override
    public Constituent readConstituentById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readConstituentById: id = " + id);
        }
        Constituent constituent = constituentDao.readConstituentById(id);
        addCommunicationEntities(constituent);
        return constituent;
    }


    @Override
    public Constituent readConstituentByAccountNumber(String accountNumber) {
        if (logger.isTraceEnabled()) {
            logger.trace("readConstituentByAccountNumber: accountNumber = " + accountNumber);
        }
        Constituent constituent = constituentDao.readConstituentByAccountNumber(accountNumber);
        addCommunicationEntities(constituent);
        return constituent;
    }

    private void addCommunicationEntities(Constituent constituent) {
        if (constituent != null) {
            constituent.setAddresses(addressService.readByConstituentId(constituent.getId()));
            constituent.setPhones(phoneService.readByConstituentId(constituent.getId()));
            constituent.setEmails(emailService.readByConstituentId(constituent.getId()));
        }
    }


    @Override
    public Constituent readConstituentByLoginId(String loginId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readConstituentByLoginId: loginId = " + loginId);
        }
        return constituentDao.readConstituentByLoginId(loginId);
    }

    @Override
    public List<Constituent> searchConstituents(Map<String, Object> params) {
        if (logger.isTraceEnabled()) {
            logger.trace("searchConstituents: params = " + params);
        }
        String fullText = (String) params.get(StringConstants.FULLTEXT);
        if (fullText != null) {
        	return constituentDao.fullTextSearchConstituents(fullText);
        } else {
        	return constituentDao.searchConstituents(params);
        }
    }

    @Override
    public List<Constituent> searchConstituents(Map<String, Object> params, SortInfo sort, Locale locale) {
        return searchConstituents(params, false, sort, locale);    }

    @Override
    public List<Constituent> searchConstituents(Map<String, Object> params, boolean parametersStartWith, SortInfo sort, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("searchConstituents: params = " + params + " sort = " + sort);
        }
        String fullText = (String) params.get(StringConstants.FULLTEXT);
        if (fullText != null) {
        	return constituentDao.fullTextSearchConstituents(fullText);
        } else {
        	return constituentDao.searchConstituents(params, parametersStartWith, sort.getSort(), sort.getDir(), sort.getStart(),
                sort.getLimit(), locale);
        }
    }



    @Override
    public List<Constituent> findConstituents(Map<String, Object> params, List<Long> ignoreIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("findConstituents: params = " + params + " ignoreIds = " + ignoreIds);
        }
        return constituentDao.findConstituents(params, ignoreIds);
    }

    @Override
    public List<Constituent> readAllConstituentsByAccountRange(Long fromId, Long toId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllConstituentsByIdRange: " + fromId + " " + toId);
        }
        return constituentDao.readAllConstituentsByAccountRange(fromId, toId);
    }


    @Override
    public Constituent createDefaultConstituent() {
        if (logger.isTraceEnabled()) {
            logger.trace("createDefaultConstituent:");
        }
        Constituent constituent = new Constituent();
        constituent.setSite(siteDao.readSite(tangerineUserHelper.lookupUserSiteName()));

        return constituent;
    }

    @Override
    public List<Constituent> analyzeLapsedDonor(Date beginDate, Date currentDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("analyzeLapsedDonor: beginDate = " + beginDate + " currentDate = " + currentDate);
        }
        return giftDao.analyzeLapsedDonor(beginDate, currentDate);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void setLapsedDonor(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("setLapsedDonor: constituentId = " + constituentId);
        }
        Constituent constituent = readConstituentById(constituentId);
        if (constituent != null) {
            constituent.addCustomFieldValue(Constituent.DONOR_PROFILES, "lapsedDonor");
        }
        constituentDao.maintainConstituent(constituent);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Constituent> readAllConstituentsBySite() {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllConstituentsBySite:");
        }
        return constituentDao.readAllConstituentsBySite();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Constituent> readAllConstituentsBySite(SortInfo sort, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllConstituentsBySite:" + sort);
        }
        return constituentDao.readAllConstituentsBySite(sort.getSort(), sort.getDir(), sort.getStart(), sort.getLimit(), locale);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int getConstituentCountBySite() {

        return constituentDao.getConstituentCountBySite();
    }

    /*   @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean hasReceivedCommunication(Long constituentId, String commType) {
        SortInfo sortInfo = new SortInfo();
        PaginatedResult results = communicationHistoryService.readCommunicationHistoryByConstituent(constituentId, sortInfo);
        List<CommunicationHistory> list = results.getRows();

        for (CommunicationHistory ch: list) {
            if (ch.getCustomFieldValue("template").compareTo(commType) == 0) return true;
        }

        return false;
    }*/
    // @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean hasReceivedCommunication(Constituent c, Gift g, String commType) {
        SortInfo sortInfo = new SortInfo();
        sortInfo.setSort("p.CONSTITUENT_ID");

        PaginatedResult results = communicationHistoryService.readCommunicationHistoryByConstituent(c.getId(), sortInfo);
        List<CommunicationHistory> list = results.getRows();

        while (list != null && list.size() > 0) {
            for (CommunicationHistory ch : list) {
            	if ((ch.getCustomFieldValue("template") != null) && (ch.getGiftId() != null) && (ch.getCustomFieldValue("template").compareTo(commType) == 0) && (ch.getGiftId().equals(g.getId()))) {
                		return true;
                	}
            }
            sortInfo.setStart(sortInfo.getStart() + sortInfo.getLimit());
            results = communicationHistoryService.readCommunicationHistoryByConstituent(c.getId(), sortInfo);
            list = results.getRows();
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean hasReceivedCommunication(Long constituentId, String commType, int number, String timeUnit) {
        SortInfo sortInfo = new SortInfo();
        Calendar cal = Calendar.getInstance();
        StringBuilder args = new StringBuilder(timeUnit.toUpperCase());
        if (args.toString().equals("DAYS") || args.toString().equals("WEEKS") || args.toString().equals("MONTHS") || args.toString().equals("YEARS")) {
            args.deleteCharAt(args.length() - 1);
        }
        if (args.toString().equals("DAY")) {
            cal.add(Calendar.DAY_OF_YEAR, -(number));
        }
        if (args.toString().equals("WEEK")) {
            cal.add(Calendar.WEEK_OF_YEAR, -(number));
        }
        if (args.toString().equals("MONTH")) {
            cal.add(Calendar.MONTH, -(number));
        }
        if (args.toString().equals("YEAR")) {
            cal.add(Calendar.YEAR, -(number));
        }

        sortInfo.setSort("p.CONSTITUENT_ID");

        PaginatedResult results = communicationHistoryService.readCommunicationHistoryByConstituent(constituentId, sortInfo);
        List<CommunicationHistory> list = results.getRows();


        while (list != null && list.size() > 0) {
            for (CommunicationHistory ch : list) {
                if (ch.getCustomFieldValue("template").compareTo(commType) == 0 &&
                        ch.getCreateDate().compareTo(cal.getTime()) > 0) {
                    return true;
                }
            }
            sortInfo.setStart(sortInfo.getStart() + sortInfo.getLimit());
            results = communicationHistoryService.readCommunicationHistoryByConstituent(constituentId, sortInfo);
            list = results.getRows();
        }

        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.context = applicationContext;

    }

    @Override
    public void updateFullTextSearchIndex(Long constituentId) {
	     entitySearchDao.updateFullTextIndex(readConstituentById(constituentId));
    }

    // Used by nightly rules processing
    @Override
	public void processConstituent(String schedule, Date compareDate,
			ConstituentService ps, GiftService gs, MailService ms,
			SiteService ss, TangerineUserHelper uh,
			Constituent p, PicklistItemService plis) {

		TaskStack.clear();

		RuleBase ruleBase = ((DroolsRuleAgent) context
				.getBean("DroolsRuleAgent")).getRuleAgent(uh.lookupUserSiteName())
				.getRuleBase();

		StatefulSession workingMemory = ruleBase.newStatefulSession();
		try{
			if (logger.isInfoEnabled()) {
				workingMemory.addEventListener(new DebugAgendaEventListener());
				workingMemory.addEventListener(new DebugWorkingMemoryEventListener());
			}

			workingMemory.setFocus(getSiteName() + schedule);
			workingMemory.setGlobal("applicationContext", context);
			workingMemory.setGlobal("constituentService", ps);
			workingMemory.setGlobal("giftService",gs);
			workingMemory.setGlobal("picklistItemService",plis);
			workingMemory.setGlobal("userHelper",uh);

			Site s = ss.readSite(uh.lookupUserSiteName());
			workingMemory.insert(s);

			Boolean updated = false;

			//
			// if the constituent has been updated or one of their
			// gifts have been updated
			if (p.getUpdateDate().compareTo(compareDate) > 0) updated = true;

			List<Gift> giftList = giftService.readMonetaryGiftsByConstituentId(p.getId());

			//
			// if the constituent has not been updated check to see if any of their
			// gifts have been...
			for (Gift g : giftList) {
				if (g.getUpdateDate() != null && g.getUpdateDate().compareTo(compareDate) > 0) {
					updated = true;
					workingMemory.insert(g);
				}

			}
			if (updated) {
				p.setGifts(giftList);
				ss.populateDefaultEntityEditorMaps(p);
				p.setSite(s);
				FactHandle pfh = workingMemory.insert(p);
			}

			workingMemory.fireAllRules();

			TaskStack.execute();
			TaskStack.clear();

		}
		finally{
			if (workingMemory != null)
				workingMemory.dispose();
		}
    }
}
