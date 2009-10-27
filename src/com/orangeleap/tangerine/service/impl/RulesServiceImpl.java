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

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.EmailService;
import com.orangeleap.tangerine.service.ErrorLogService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.RulesService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.communication.MailService;
import com.orangeleap.tangerine.service.rule.DroolsRuleAgent;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.util.TaskStack;
import com.orangeleap.tangerine.web.common.SortInfo;

import org.apache.commons.logging.Log;
import org.drools.FactHandle;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service("rulesService")
public class RulesServiceImpl extends AbstractTangerineService implements RulesService, ApplicationContextAware {

	/**
	 * Logger for this class and subclasses
	 */
	protected final Log logger = OLLogger.getLog(getClass());

	@Resource(name = "constituentService")
	private ConstituentService constituentService;



	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
	throws BeansException {
		this.applicationContext = applicationContext;
	}

	private static String REINDEX_FULLTEXT = "nightly.reindex.fulltext";

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

			int totalContituentCount = constituentService.getConstituentCountBySite();
			for (int start = 0; start <= totalContituentCount; start += 100){
				
				logger.info("Processing rules for constituent "+start+" out of "+totalContituentCount);
				
				SortInfo sortInfo = new SortInfo();
				sortInfo.setSort("id"); // sort by id so that new inserts will not throw off pages.
				sortInfo.setStart(start);


				List<Constituent> peopleList = constituentService.readAllConstituentsBySite(sortInfo, Locale.getDefault());

				for (Constituent p : peopleList) {
					try {
						constituentService.processConstituent(schedule, compareDate, ps, gs, ms,
								ss, uh, p, plis);
						
						// Force a fulltext reindex of constituent after rules processing if this site option is set.
						if (reindexFullText) {
							ps.maintainConstituent(ps.readConstituentById(p.getId()));
						}
						
					}catch(Throwable t) {
						t.printStackTrace();
						logger.error(t);
					}
				}
			}
		}catch (Throwable t) {
			logger.error(t);
			t.printStackTrace();
		}
		long time2 = System.currentTimeMillis();
		logger.info("Rules Processing Took: " + (time2 - time) + " ms.  Complete on " + new java.util.Date());
	}



	private boolean taskValidForSite(String filter) {
        String filtervalue = System.getProperty(filter);
        if (filtervalue == null) return true;
        Pattern pattern = Pattern.compile(filtervalue);
        return pattern.matcher(getSiteName()).matches();
	}

	// If set, only process rules for sites that match these regexes.
	// For example, to only execute monthly rules for schema1, schema2, and schema3:   tangerine.rules.monthly.filter=^(schema1|schema2|schema3)$
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
	}

	@Override
	public void executeWeeklyJobRules() {

		if (!taskValidForSite(WEEKLY_RULES_FILTER_PROPERTY)) return;

		Calendar today = Calendar.getInstance();
		today.add(Calendar.WEEK_OF_YEAR, -1);
		Date lastweek = new java.sql.Date(today.getTimeInMillis());

		executeRules("scheduleweekly", lastweek);
	}

	@Override
	public void executeMonthlyJobRules() {

		if (!taskValidForSite(MONTHLY_RULES_FILTER_PROPERTY)) return;

		Calendar today = Calendar.getInstance();
		today.add(Calendar.MONTH, -1);
		Date lastmonth = new java.sql.Date(today.getTimeInMillis());

		executeRules("schedulemonthly", lastmonth);
	}

}
