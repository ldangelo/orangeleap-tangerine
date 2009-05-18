package com.orangeleap.tangerine.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.FactHandle;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.WorkingMemory;
import org.drools.agent.RuleAgent;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.RulesService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.communication.MailService;
import com.orangeleap.tangerine.service.rule.DroolsRuleAgent;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.web.common.SortInfo;

@Service("rulesService")
public class RulesServiceImpl extends AbstractTangerineService implements RulesService, ApplicationContextAware {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name = "constituentService")
    private ConstituentService constituentService;
    
    @Resource(name = "giftService")
    private GiftService giftService;
    
    
    private ApplicationContext applicationContext;
    
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
    	this.applicationContext = applicationContext;
	}
    
	@Override
	public void executeDailyJobRules() {

		try {

			ConstituentService ps = (ConstituentService) applicationContext.getBean("constituentService");
			GiftService   gs = (GiftService) applicationContext.getBean("giftService");
			MailService   ms = (MailService) applicationContext.getBean("mailService");
			SiteService ss = (SiteService) applicationContext.getBean("siteService");

			Calendar today = Calendar.getInstance();
			today.add(Calendar.DATE, -1);
			Date yesterday = new java.sql.Date(today.getTimeInMillis());
			


				
				

				List<Person> peopleList = constituentService.readAllConstituentsBySite();
				

					RuleBase ruleBase = ((DroolsRuleAgent) applicationContext
							.getBean("DroolsRuleAgent")).getRuleAgent()
							.getRuleBase();

					StatefulSession workingMemory = ruleBase
							.newStatefulSession();
					workingMemory
							.addEventListener(new DebugAgendaEventListener());
					workingMemory
							.addEventListener(new DebugWorkingMemoryEventListener());

					workingMemory.setGlobal("giftService", gs);
					workingMemory.setGlobal("personService", ps);
					workingMemory.setGlobal("mailService",ms);
					workingMemory.setGlobal("applicationContext",
							applicationContext);
					workingMemory.setFocus(getSiteName() + "scheduleddaily");
					for (Person p : peopleList) {
						Boolean updated = false;

						//
						// if the person has been updated or one of their
						// gifts have been updated
						if (p.getUpdateDate().compareTo(yesterday) > 0) updated = true;
						
						List<Gift> giftList = giftService
								.readMonetaryGiftsByConstituentId(p.getId());
						
						//
						// if the person has not been updated check to see if any of their
						// gifts have been...
						if (!updated) {
							for (Gift g: giftList) {
								if (g.getCreateDate() != null && g.getCreateDate().compareTo(yesterday) > 0) {
									updated = true;
									break;
								}
							
							}
						}
						if (updated) {
							p.setGifts(giftList);
							ss.populateDefaultEntityEditorMaps(p);
							FactHandle pfh = workingMemory.insert(p);
						}

					}
					workingMemory.fireAllRules();
					workingMemory.dispose();

		} catch (Throwable t) {
			logger.error(t);
			t.printStackTrace();
		}
	}

	@Override
	public void executeWeeklyJobRules() {

		try {

			SiteService ss = (SiteService) applicationContext
					.getBean("siteService");
			ConstituentService ps = (ConstituentService) applicationContext.getBean("constituentService");
			GiftService   gs = (GiftService) applicationContext.getBean("giftService");
			MailService   ms = (MailService) applicationContext.getBean("mailService");
			TangerineUserHelper th = (TangerineUserHelper) applicationContext
					.getBean("tangerineUserHelper");
			List<Site> siteList = ss.readSites();
			Calendar today = Calendar.getInstance();
			today.add(Calendar.WEEK_OF_YEAR, -1);
			Date lastweek = new java.sql.Date(today.getTimeInMillis());
			
			for (Site s : siteList) {
				SortInfo si = new SortInfo();
				
				
				th.setSystemUserAndSiteName(s.getName());
				List<Person> peopleList = constituentService.readAllConstituentsBySite();
				

					RuleBase ruleBase = ((DroolsRuleAgent) applicationContext
							.getBean("DroolsRuleAgent")).getRuleAgent()
							.getRuleBase();

					StatefulSession workingMemory = ruleBase
							.newStatefulSession();
					workingMemory
							.addEventListener(new DebugAgendaEventListener());
					workingMemory
							.addEventListener(new DebugWorkingMemoryEventListener());

					workingMemory.setGlobal("giftService", gs);
					workingMemory.setGlobal("personService", ps);
					workingMemory.setGlobal("mailService",ms);
					workingMemory.setGlobal("applicationContext",
							applicationContext);
					workingMemory.setFocus(getSiteName() + "scheduledweekly");
					for (Person p : peopleList) {
						Boolean updated = false;

						//
						// if the person has been updated or one of their
						// gifts have been updated
						if (p.getCreateDate().compareTo(lastweek) > 0) updated = true;
						
						List<Gift> giftList = giftService
								.readMonetaryGiftsByConstituentId(p.getId());
						
						//
						// if the person has not been updated check to see if any of their
						// gifts have been...
						if (!updated) {
							for (Gift g: giftList) {
								if (g.getUpdateDate().compareTo(lastweek) > 0) {
									updated = true;
									break;
								}
							
							}
						}
						if (updated) {
							p.setGifts(giftList);
							ss.populateDefaultEntityEditorMaps(p);
							FactHandle pfh = workingMemory.insert(p);
						}

					}
					workingMemory.fireAllRules();
					workingMemory.dispose();
				}
		} catch (Throwable t) {
			logger.error(t);
			t.printStackTrace();
		}
	}
 
	@Override
	public void executeMonthlyJobRules() {

		try {

			SiteService ss = (SiteService) applicationContext
					.getBean("siteService");
			ConstituentService ps = (ConstituentService) applicationContext.getBean("constituentService");
			GiftService   gs = (GiftService) applicationContext.getBean("giftService");
			MailService   ms = (MailService) applicationContext.getBean("mailService");
			TangerineUserHelper th = (TangerineUserHelper) applicationContext
					.getBean("tangerineUserHelper");
			List<Site> siteList = ss.readSites();
			Calendar today = Calendar.getInstance();
			today.add(Calendar.MONTH, -1);
			Date lastmonth = new java.sql.Date(today.getTimeInMillis());
			
			for (Site s : siteList) {
				SortInfo si = new SortInfo();
				
				
				th.setSystemUserAndSiteName(s.getName());
				List<Person> peopleList = constituentService.readAllConstituentsBySite();
				

					RuleBase ruleBase = ((DroolsRuleAgent) applicationContext
							.getBean("DroolsRuleAgent")).getRuleAgent()
							.getRuleBase();

					StatefulSession workingMemory = ruleBase
							.newStatefulSession();
					workingMemory
							.addEventListener(new DebugAgendaEventListener());
					workingMemory
							.addEventListener(new DebugWorkingMemoryEventListener());

					workingMemory.setGlobal("giftService", gs);
					workingMemory.setGlobal("personService", ps);
					workingMemory.setGlobal("mailService",ms);
					workingMemory.setGlobal("applicationContext",
							applicationContext);
					workingMemory.setFocus(getSiteName() + "scheduledmonthly");
					for (Person p : peopleList) {
						Boolean updated = false;

						//
						// if the person has been updated or one of their
						// gifts have been updated
						if (p.getCreateDate().compareTo(lastmonth) > 0) updated = true;
						
						List<Gift> giftList = giftService
								.readMonetaryGiftsByConstituentId(p.getId());
						
						//
						// if the person has not been updated check to see if any of their
						// gifts have been...
						if (!updated) {
							for (Gift g: giftList) {
								if (g.getUpdateDate().compareTo(lastmonth) > 0) {
									updated = true;
									break;
								}
							
							}
						}
						if (updated) {
							p.setGifts(giftList);
							ss.populateDefaultEntityEditorMaps(p);
							FactHandle pfh = workingMemory.insert(p);
						}

					}
					workingMemory.fireAllRules();
					workingMemory.dispose();
				}
		} catch (Throwable t) {
			logger.error(t);
			t.printStackTrace();
		}
	}

}
