package com.orangeleap.tangerine.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.RuleBase;
import org.drools.WorkingMemory;
import org.drools.agent.RuleAgent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.RulesService;
import com.orangeleap.tangerine.service.rule.DroolsRuleAgent;

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

			RuleAgent agent = RuleAgent.newRuleAgent(DroolsRuleAgent.getDroolsProperties());
			
 			RuleBase ruleBase = agent.getRuleBase();

			WorkingMemory workingMemory = ruleBase.newStatefulSession();

			List<Person> peopleList = constituentService.readAllConstituentsBySite(); 

			for (Person p : peopleList) {

				workingMemory.insert(p);

				List<Gift> giftList = giftService.readMonetaryGiftsByConstituentId(p.getId());

				for (Gift g : giftList) {
					workingMemory.insert(g);
				}

			}

			String ruleflow = "com.orangeleap.tangerine." + getSiteName() + "_ScheduledRuleflow";

			workingMemory.setGlobal("applicationContext", applicationContext);
			workingMemory.startProcess(ruleflow);
			workingMemory.fireAllRules();

		} catch (Throwable t) {
			logger.error(t);
			t.printStackTrace();
		}
	}

    
}
