package com.orangeleap.tangerine.service.rule;

import java.util.Iterator;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.FactHandle;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.WorkingMemory;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;


import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.event.GiftEvent;
import com.orangeleap.tangerine.event.NewGiftEvent;

public class GiftRulesInterceptor extends RulesInterceptor {

	private static final Log logger = LogFactory.getLog(RulesInterceptor.class);

	private ApplicationContext applicationContext;
	private DroolsRuleAgent    ruleAgent;
	
	@Autowired
	void setDroolsRulesAgent(DroolsRuleAgent ruleAgent) {
		this.ruleAgent = ruleAgent;
	}
	
	public void doApplyRules(Gift gift) {

		RuleBase ruleBase = ruleAgent.getRuleAgent().getRuleBase();

		StatefulSession workingMemory = ruleBase.newStatefulSession();
		workingMemory.addEventListener (new DebugAgendaEventListener());
		workingMemory.addEventListener(new DebugWorkingMemoryEventListener());
		@SuppressWarnings("unused")
		ConstituentService ps = (ConstituentService) applicationContext.getBean("constituentService");
		GiftService gs = (GiftService) applicationContext.getBean("giftService");

		String site = null;
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
				site =gift.getSite().getName();
			}

		try {
			workingMemory.setGlobal("applicationContext", applicationContext);
			logger.info("*** firing all rules");

			workingMemory.setFocus(site+"gift");
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


	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof GiftEvent) {
			NewGiftEvent nge = (NewGiftEvent) event;
			doApplyRules(nge.getGift());
		}
		
	}
}