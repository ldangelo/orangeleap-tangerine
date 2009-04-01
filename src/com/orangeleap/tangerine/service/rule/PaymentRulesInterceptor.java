package com.orangeleap.tangerine.service.rule;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.drools.FactHandle;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.WorkingMemory;
import org.drools.agent.RuleAgent;
import org.drools.base.RuleNameEqualsAgendaFilter;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.impl.SessionServiceImpl;



public class PaymentRulesInterceptor implements ApplicationContextAware, ApplicationListener {

	private static final Log logger = LogFactory.getLog(RulesInterceptor.class);

	private ApplicationContext applicationContext;
	private String ruleFlowName;
	private DroolsRuleAgent ruleAgent;
	

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
			site = gift.getSite().getName();
		}

		try {
			workingMemory.setGlobal("applicationContext", applicationContext);
			logger.info("*** firing all rules");

			workingMemory.setFocus(site+"processpayment");
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