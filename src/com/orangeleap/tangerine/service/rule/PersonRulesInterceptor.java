package com.orangeleap.tangerine.service.rule;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.spi.ConsequenceException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.ErrorLogService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.util.TangerineUserHelper;


public class PersonRulesInterceptor implements ApplicationContextAware, ApplicationListener {

	private static final Log logger = LogFactory.getLog(PersonRulesInterceptor.class);

	private ApplicationContext applicationContext;
	private String ruleFlowName;
	private Class  eventClass;
	
	public void doApplyRules(Person person) throws ConstituentValidationException {

	
		RuleBase ruleBase = ((DroolsRuleAgent)applicationContext.getBean("DroolsRuleAgent")).getRuleAgent().getRuleBase();

		StatefulSession workingMemory = ruleBase.newStatefulSession();

		@SuppressWarnings("unused")
		ConstituentService ps = (ConstituentService) applicationContext.getBean("constituentService");
		GiftService gs = (GiftService) applicationContext.getBean("giftService");
		TangerineUserHelper uh = (TangerineUserHelper) applicationContext.getBean("tangerineUserHelper");
		ErrorLogService errorLogService = (ErrorLogService) applicationContext.getBean("errorLogService");

		String site = person.getSite().getName();
		workingMemory.setFocus(site+"person");
		workingMemory.setGlobal("applicationContext", applicationContext);
		workingMemory.setGlobal("personService", ps);
		workingMemory.setGlobal("giftService",gs);
		workingMemory.setGlobal("userHelper",uh);
		
		
		person.setGifts(gs.readMonetaryGifts(person));
		workingMemory.insert(person);

		try {
			List<Gift> gifts = gs.readMonetaryGiftsByConstituentId(person.getId());
			Iterator<Gift> giftsIter = gifts.iterator();
			while (giftsIter.hasNext()) {
				workingMemory.insert(giftsIter.next());
			}
		} catch (Exception ex) {
			logger.info(ex.getMessage());
		}

		try {
			logger.info("*** firing all rules");

			workingMemory.fireAllRules();
			
		} catch (ConsequenceException ce) {
			if(ce.getCause() instanceof ConstituentValidationException) {
				ConstituentValidationException cve = (ConstituentValidationException) ce.getCause();
				throw cve;
			}
			logger.error("*** exception firing rules - make sure rule base exists and global variable is set: ");
			logger.error(ce);			
		} catch (Exception e) {
			logger.error("*** exception firing rules - make sure rule base exists and global variable is set: ");
			logger.error(e);
		} finally {
			workingMemory.dispose();			
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