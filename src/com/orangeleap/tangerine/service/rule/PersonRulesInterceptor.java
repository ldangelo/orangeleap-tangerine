package com.orangeleap.tangerine.service.rule;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;


public class PersonRulesInterceptor implements ApplicationContextAware, ApplicationListener {

	private static final Log logger = LogFactory.getLog(PersonRulesInterceptor.class);

	private ApplicationContext applicationContext;
	private String ruleFlowName;
	private Class  eventClass;
	
	public void doApplyRules(Gift gift) {

	
		RuleBase ruleBase = ((DroolsRuleAgent)applicationContext.getBean("DroolsRuleAgent")).getRuleAgent().getRuleBase();

		StatefulSession workingMemory = ruleBase.newStatefulSession();

		@SuppressWarnings("unused")
		ConstituentService ps = (ConstituentService) applicationContext.getBean("constituentService");
		GiftService gs = (GiftService) applicationContext.getBean("giftService");

		String site = null;
		workingMemory.setGlobal("applicationContext", applicationContext);
		workingMemory.setGlobal("personService", ps);
		workingMemory.setGlobal("giftService",gs);
			workingMemory.insert(gift);

			try {
				List<Gift> gifts = gs.readMonetaryGiftsByConstituentId(gift.getPerson().getId());
				Iterator<Gift> giftsIter = gifts.iterator();
				while (giftsIter.hasNext()) {
					workingMemory.insert(giftsIter.next());
				}

				Person person = gift.getPerson();
				workingMemory.insert(person);

			} catch (Exception ex) {
				logger.info(ex.getMessage());
			}

		if (site == null) {
			site = gift.getSite().getName();
		}

		try {
			logger.info("*** firing all rules");
			workingMemory.setFocus(site+"person");
			workingMemory.fireAllRules();
			workingMemory.dispose();
		} catch (Exception e) {
			logger.info("*** exception firing rules - make sure rule base exists and global variable is set: ");
			logger.info(e);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public String getRuleFlowName() {
		return ruleFlowName;
	}

	public void setRuleFlowName(String ruleFlowName) {
		this.ruleFlowName = ruleFlowName;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		
	}
}