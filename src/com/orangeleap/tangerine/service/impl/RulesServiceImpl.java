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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.SiteOption;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.EmailService;
import com.orangeleap.tangerine.service.ErrorLogService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.OrangeLeapRuleAgent;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.RulesService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.communication.MailService;
import com.orangeleap.tangerine.service.rule.DroolsRuleAgent;
import com.orangeleap.tangerine.service.rule.OrangeLeapRuleBase;
import com.orangeleap.tangerine.service.rule.OrangeLeapRuleSession;
import com.orangeleap.tangerine.type.RuleEventNameType;
import com.orangeleap.tangerine.type.RuleObjectType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.util.TaskStack;
import com.orangeleap.tangerine.web.common.SortInfo;

@Service("rulesService")
public class RulesServiceImpl extends AbstractTangerineService implements RulesService, ApplicationContextAware {

	/**
	 * Logger for this class and subclasses
	 */
	protected final Log logger = OLLogger.getLog(getClass());

	@Resource(name = "constituentService")
	private ConstituentService constituentService;
	
	@Resource(name = "giftService")
	private GiftService giftService;
	
    @Resource(name = "OrangeLeapRuleAgent")
    private OrangeLeapRuleAgent orangeLeapRuleAgent;


	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	private static String REINDEX_FULLTEXT = "nightly.reindex.fulltext";
	private static String RESAVE_CONSTITUENT = "nightly.resave.constituent";
	private static String SUPPRESS_GIFT_REPROCESS = "suppress.gift.reprocess";
	

	public void executeRules(String schedule, Date compareDate) {

		long time = System.currentTimeMillis();
		try {

			logger.info("Executing rules for "+schedule + ", compare date = " + compareDate + ".  Started on " + new java.util.Date());

			MailService ms = (MailService) applicationContext.getBean("mailService");
			ConstituentService ps = (ConstituentService) applicationContext.getBean("constituentService");
			GiftService gs = (GiftService) applicationContext.getBean("giftService");
			SiteService ss = (SiteService) applicationContext.getBean("siteService");
			PicklistItemService plis = (PicklistItemService)applicationContext.getBean("picklistItemService");
			TangerineUserHelper uh = (TangerineUserHelper) applicationContext.getBean("tangerineUserHelper");
	        EmailService es = (EmailService) applicationContext.getBean("emailService");
			ErrorLogService errorLogService = (ErrorLogService) applicationContext.getBean("errorLogService");

			boolean reindexFullText = "true".equalsIgnoreCase(siteService.getSiteOptionsMap().get(REINDEX_FULLTEXT));
			boolean resaveConstituent = "true".equalsIgnoreCase(siteService.getSiteOptionsMap().get(RESAVE_CONSTITUENT));

			int totalContituentCount = constituentService.getConstituentCountBySite();
			for (int start = 0; start <= totalContituentCount; start += 100) {

				SortInfo sortInfo = new SortInfo();
				sortInfo.setSort("id"); // sort by id so that new inserts will not throw off pages.
				sortInfo.setStart(start);

				// Retrieve only constituents that have been updated in the last 3 days.
				List<Constituent> peopleList = constituentService.readAllUpdatedConstituentsBySite(sortInfo, Locale.getDefault(), 3);
				if (peopleList.size() == 0) break;

				for (Constituent p : peopleList) {
					try {
						constituentService.processConstituent(schedule, compareDate, ps, gs, ms,
								ss, uh, p, plis);

						// Force a fulltext reindex of constituent after rules processing if this site option is set.
						if (reindexFullText) {
							ps.updateFullTextSearchIndex(p.getId());
						}
						
						if (resaveConstituent) {
							constituentService.maintainConstituent(constituentService.readConstituentById(p.getId()));
						}
						

					} catch (Throwable t) {
						t.printStackTrace();
						logger.error(t);
					}
				}
			}
		} catch (Throwable t) {
			logger.error(t);
			t.printStackTrace();
		}
		
		resetRecalcSiteOption();
		
		long time2 = System.currentTimeMillis();
		logger.info("Rules Processing Took: " + (time2 - time) + " ms.  Complete on " + new java.util.Date());
	}

	private void resetRecalcSiteOption() {
		List<SiteOption> list = siteService.getSiteOptions();
		for (SiteOption siteOption: list) {
			if (siteOption.getOptionName().equals(RESAVE_CONSTITUENT)) {
				siteOption.setOptionValue("false");
				siteService.maintainSiteOption(siteOption);
			}
		}
	}

	private void executeMailRules(String schedule) {
		// TODO Auto-generated method stub
		long time = System.currentTimeMillis();
		try {

			logger.info("Executing rules for " + schedule +
					".  Started on " + new java.util.Date());

			MailService ms = (MailService) applicationContext
					.getBean("mailService");
			ConstituentService ps = (ConstituentService) applicationContext
					.getBean("constituentService");
			GiftService gs = (GiftService) applicationContext
					.getBean("giftService");
			SiteService ss = (SiteService) applicationContext
					.getBean("siteService");
			PicklistItemService plis = (PicklistItemService) applicationContext
					.getBean("picklistItemService");
			TangerineUserHelper uh = (TangerineUserHelper) applicationContext
					.getBean("tangerineUserHelper");
			EmailService es = (EmailService) applicationContext
					.getBean("emailService");
			ErrorLogService errorLogService = (ErrorLogService) applicationContext
					.getBean("errorLogService");

			boolean reindexFullText = "true".equalsIgnoreCase(siteService
					.getSiteOptionsMap().get(REINDEX_FULLTEXT));

			try {
				TaskStack.clear();

				Site site = ss.readSite(uh.lookupUserSiteName());

				RuleBase ruleBase = ((DroolsRuleAgent) applicationContext
						.getBean("DroolsRuleAgent")).getRuleAgent(
						uh.lookupUserSiteName()).getRuleBase();
				
				RuleEventNameType ruleEventNameType = null;
				if (schedule.contains("daily")) ruleEventNameType = RuleEventNameType.EMAIL_SCHEDULED_DAILY;
				if (schedule.contains("weekly")) ruleEventNameType = RuleEventNameType.EMAIL_SCHEDULED_WEEKLY;
				if (schedule.contains("monthly")) ruleEventNameType = RuleEventNameType.EMAIL_SCHEDULED_MONTHLY;

				OrangeLeapRuleBase olRuleBase = orangeLeapRuleAgent.getOrangeLeapRuleBase(ruleEventNameType, site.getName(), false);
				OrangeLeapRuleSession olWorkingMemory = olRuleBase.newRuleSession();

				StatefulSession workingMemory = ruleBase.newStatefulSession();
				try {
					if (logger.isInfoEnabled()) {
						workingMemory
								.addEventListener(new DebugAgendaEventListener());
						workingMemory
								.addEventListener(new DebugWorkingMemoryEventListener());
					}

					workingMemory.setFocus(getSiteName() + schedule);
					workingMemory.setGlobal("applicationContext",
							applicationContext);
					workingMemory.setGlobal("constituentService", ps);
					workingMemory.setGlobal("giftService", gs);
					workingMemory.setGlobal("picklistItemService", plis);
					workingMemory.setGlobal("userHelper", uh);

					workingMemory.insert(site);
					olWorkingMemory.put(RuleObjectType.SITE, site);

					workingMemory.fireAllRules();
					olWorkingMemory.executeRules(); 

					TaskStack.execute();
					TaskStack.clear();

				} finally {
					if (workingMemory != null)
						workingMemory.dispose();
				}

			} catch (Throwable t) {
				t.printStackTrace();
				logger.error(t);
			}
		} catch (Throwable t) {
			logger.error(t);
			t.printStackTrace();
		}
		long time2 = System.currentTimeMillis();
		logger.info("Rules Processing for " + schedule + "Took: " + (time2 - time)
				+ " ms.  Complete on " + new java.util.Date());
	}

	private boolean taskValidForSite(String filter) {
		String filtervalue = System.getProperty(filter);
        if (filtervalue == null) return true;
		Pattern pattern = Pattern.compile(filtervalue);
		return pattern.matcher(getSiteName()).matches();
	}

	// If set, only process rules for sites that match these regexes.
	// For example, to only execute monthly rules for schema1, schema2, and schema3: tangerine.rules.monthly.filter=^(schema1|schema2|schema3)$
	private static final String DAILY_RULES_FILTER_PROPERTY = "tangerine.rules.daily.filter";
	private static final String WEEKLY_RULES_FILTER_PROPERTY = "tangerine.rules.weekly.filter";
	private static final String MONTHLY_RULES_FILTER_PROPERTY = "tangerine.rules.monthly.filter";

	@Override
	public void executeDailyJobRules() {

		if (!taskValidForSite(DAILY_RULES_FILTER_PROPERTY)) return;

		Calendar today = Calendar.getInstance();
		today.add(Calendar.DATE, -1);
		Date yesterday = new java.sql.Date(today.getTimeInMillis());

		executeRules("scheduledaily", yesterday);

		// execute the scheduled mail (The mail is generated from a segment not by a constituent
		//or gift being inserted into working memory.)
		executeMailRules("mailscheduledaily");
	}

	@Override
	public void executeWeeklyJobRules() {

		if (!taskValidForSite(WEEKLY_RULES_FILTER_PROPERTY)) return;

		Calendar today = Calendar.getInstance();
		today.add(Calendar.WEEK_OF_YEAR, -1);
		Date lastweek = new java.sql.Date(today.getTimeInMillis());

		executeRules("scheduleweekly", lastweek);

		// execute the scheduled mail (The mail is generated from a segment not by a constituent
		//or gift being inserted into working memory.)
		executeMailRules("mailscheduledaily");

	}

	@Override
	public void executeMonthlyJobRules() {

		if (!taskValidForSite(MONTHLY_RULES_FILTER_PROPERTY)) return;

		Calendar today = Calendar.getInstance();
		today.add(Calendar.MONTH, -1);
		Date lastmonth = new java.sql.Date(today.getTimeInMillis());

		executeRules("schedulemonthly", lastmonth);

		// execute the scheduled mail (The mail is generated from a segment not by a constituent
		//or gift being inserted into working memory.)
		executeMailRules("mailscheduledaily");

	}

	@Override
	public void reprocessGifts() {
		
		boolean suppressGiftReprocess = "true".equalsIgnoreCase(siteService.getSiteOptionsMap().get(SUPPRESS_GIFT_REPROCESS));
		if (suppressGiftReprocess) {
			logger.info("Suppressing gift reprocess.");
			return;
		}
		
		long time = System.currentTimeMillis();
		try {

			logger.info("Reprocessing gifts.  Started on " + new java.util.Date());
		
			int totalGiftReprocessCount = giftService.getGiftReprocessCount();
			logger.info("totalGiftReprocessCount = " + totalGiftReprocessCount);
			
			for (int start = 0; start <= totalGiftReprocessCount; start += 100) {
	
				SortInfo sortInfo = new SortInfo();
				sortInfo.setSort("id"); 
				sortInfo.setStart(start);
	
				List<Gift> gifts = giftService.readGiftsToReprocess(sortInfo);  
				for (Gift gift : gifts) {
					try {
						gift = giftService.readGiftById(gift.getId()); // fully populate sub-entities
						giftService.dailyReprocessGift(gift);
					} catch (Exception e) {
						logger.error(e);
					}
				}
				
			}
		
		} catch (Throwable t) {
			logger.error(t);
			t.printStackTrace();
		}
		
		long time2 = System.currentTimeMillis();
		logger.info("Gift reprocessing Took: " + (time2 - time) + " ms.  Complete on " + new java.util.Date());
	}

}