package com.orangeleap.tangerine.service.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.RuleBase;
import org.drools.WorkingMemory;
import org.drools.agent.RuleAgent;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.SiteService;

public class DailyRuleFiringJob extends QuartzJobBean {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	public DailyRuleFiringJob() {}

	private static final String APPLICATION_CONTEXT_KEY = "applicationContext";

	@SuppressWarnings("unused")
	private String siteName;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		ApplicationContext applicationContext = null;
		ConstituentService ps = null;
		GiftService gs = null;
		SiteService ss = null;

		try {
			applicationContext = getApplicationContext(context);
			ps = (ConstituentService)applicationContext.getBean("constituentService");
			gs = (GiftService)applicationContext.getBean("giftService");
			ss = (SiteService)applicationContext.getBean("siteService");

		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Site> sites = null;
		sites = ss.readSites();

		Properties props = RulesInterceptor.getDroolsProperties();
		
		for (Site site : sites) {

			siteName = site.getName();

			try {

				RuleAgent agent = RuleAgent.newRuleAgent(props);
				RuleBase ruleBase = agent.getRuleBase();

				WorkingMemory workingMemory = ruleBase.newStatefulSession();

				List<Person> peopleList = new ArrayList<Person>(); //ps.readAllPeopleBySite(site); // TODO: fix for IBatis

				for (Person p : peopleList) {

					workingMemory.insert(p);

					List<Gift> giftList = gs.readMonetaryGiftsByConstituentId(p.getId());

					for (Gift g : giftList) {
						workingMemory.insert(g);
					}

				}

				String ruleflow = "com.orangeleap.tangerine." + site.getName() + "_ScheduledRuleflow";

				workingMemory.setGlobal("applicationContext", applicationContext);
				workingMemory.startProcess(ruleflow);
				workingMemory.fireAllRules();

			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private ApplicationContext getApplicationContext(JobExecutionContext context ) throws Exception {
		ApplicationContext applicationContext = null;
		applicationContext = (ApplicationContext)context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
		if (applicationContext == null) {
			throw new JobExecutionException("No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");        }
		return applicationContext;    }

}
