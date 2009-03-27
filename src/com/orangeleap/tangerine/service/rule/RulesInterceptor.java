package com.orangeleap.tangerine.service.rule;

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
import org.springframework.context.ApplicationContextAware;

import org.springframework.context.ApplicationListener;


import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.ConstituentService;



public abstract class RulesInterceptor implements ApplicationContextAware, ApplicationListener {

	private static final Log logger = LogFactory.getLog(RulesInterceptor.class);

	private ApplicationContext applicationContext;
	private String ruleFlowName;
	private Class  eventClass;
	
	public static Properties getDroolsProperties() {
		String host = System.getProperty("drools.host");
		String port = System.getProperty("drools.port");
		String url = "http://"+host+":"+port+"/drools/org.drools.brms.JBRMS/package/com.mpower/NEWEST";
		logger.debug("Setting Drools URL to "+url);
		Properties props = new Properties();
		props.put("url", url);
		props.put("newInstance", "true");
		props.put("name","testagent");
		return props;
	}


	public void doApplyRules(Gift gift) {

		Properties props = getDroolsProperties();
	
		RuleAgent agent = RuleAgent.newRuleAgent(props);
		RuleBase ruleBase = agent.getRuleBase();

		WorkingMemory workingMemory = ruleBase.newStatefulSession();

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

			String ruleflow = "com.mpower." + site + "_" + ruleFlowName;
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
	
	public ApplicationContext getApplicationContext() {
			return this.applicationContext;
	}

	public String getRuleFlowName() {
		return ruleFlowName;
	}

	public void setRuleFlowName(String ruleFlowName) {
		this.ruleFlowName = ruleFlowName;
	}

	/*@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event.getClass() == eventClass) {
			NewGiftEvent nge = (NewGiftEvent) event;
			doApplyRules(nge.getGift());
		}
		
	}*/
}