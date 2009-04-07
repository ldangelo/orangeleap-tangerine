package com.orangeleap.tangerine.service.impl;

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
import com.orangeleap.tangerine.service.rule.DroolsRuleAgent;
import com.orangeleap.tangerine.util.TangerineUserHelper;

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

			SiteService ss = (SiteService) applicationContext
					.getBean("siteService");
			TangerineUserHelper th = (TangerineUserHelper) applicationContext
					.getBean("tangerineUserHelper");
			List<Site> siteList = ss.readSites();

			for (Site s : siteList) {

				th.setSystemUserAndSiteName(s.getName());

				List<Person> peopleList = constituentService
						.readAllConstituentsBySite();
				if (peopleList.size() > 0) {
					RuleBase ruleBase = ((DroolsRuleAgent) applicationContext
							.getBean("DroolsRuleAgent")).getRuleAgent()
							.getRuleBase();

					StatefulSession workingMemory = ruleBase
							.newStatefulSession();
					workingMemory
							.addEventListener(new DebugAgendaEventListener());
					workingMemory
							.addEventListener(new DebugWorkingMemoryEventListener());

					workingMemory.setGlobal("applicationContext",
							applicationContext);
					workingMemory.setFocus(getSiteName() + "scheduled");
					for (Person p : peopleList) {

						List<Gift> giftList = giftService
								.readMonetaryGiftsByConstituentId(p.getId());
						p.setGifts(giftList);
						FactHandle pfh = workingMemory.insert(p);

					}
					workingMemory.fireAllRules();
					workingMemory.dispose();
				}
			}
		} catch (Throwable t) {
			logger.error(t);
			t.printStackTrace();
		}
	}

    
}
