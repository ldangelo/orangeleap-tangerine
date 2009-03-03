package com.mpower.service.rule;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.RuleBase;
import org.drools.WorkingMemory;
import org.drools.agent.RuleAgent;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

import com.mpower.domain.model.Person;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.event.GiftEvent;
import com.mpower.event.NewGiftEvent;
import com.mpower.service.GiftService;
import com.mpower.service.PersonService;
import com.mpower.util.TangerineUserHelper;

//TODO: all the interceptors are cut & paste code!!!
public class GiftRulesInterceptor extends RulesInterceptor {

	private static final Log logger = LogFactory.getLog(RulesInterceptor.class);

	private ApplicationContext applicationContext;
	
	@Override
    public void doApplyRules(Gift gift) {

		Properties props = getDroolsProperties();
	
		RuleAgent agent = RuleAgent.newRuleAgent(props);
		RuleBase ruleBase = agent.getRuleBase();

		WorkingMemory workingMemory = ruleBase.newStatefulSession();
		workingMemory.addEventListener (new DebugAgendaEventListener());
		workingMemory.addEventListener(new DebugWorkingMemoryEventListener());
		@SuppressWarnings("unused")
		PersonService ps = (PersonService) applicationContext.getBean("personService");
		GiftService gs = (GiftService) applicationContext.getBean("giftService");
		TangerineUserHelper tangerineUserHelper = (TangerineUserHelper) applicationContext.getBean("tangerineUserHelper");

		String site = tangerineUserHelper.lookupUserSiteName(); // TODO: fix for site?
			workingMemory.insert(gift);

			try {
				List<Gift> gifts = gs.readGiftsByConstituentId(gift.getPerson().getId());
				Iterator<Gift> giftsIter = gifts.iterator();
				while (giftsIter.hasNext()) {
					workingMemory.insert(giftsIter.next());
				}

				Person person = gift.getPerson();
				workingMemory.insert(person);

			} catch (Exception ex) {
				logger.info(ex.getMessage());
			}

		try {
			workingMemory.setGlobal("applicationContext", applicationContext);
			logger.info("*** firing all rules");

			String ruleflow = "com.mpower." + site + "_" + getRuleFlowName();
			workingMemory.startProcess(ruleflow);
			workingMemory.fireAllRules();
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