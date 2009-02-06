package com.mpower.service.rule;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.drools.RuleBase;
import org.drools.WorkingMemory;
import org.drools.agent.RuleAgent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.mpower.domain.Gift;
import com.mpower.domain.Person;
import com.mpower.domain.SiteAware;
import com.mpower.service.GiftService;
import com.mpower.service.PersonService;
import com.mpower.service.impl.SessionServiceImpl;
import com.mpower.event.NewGiftEvent;
import com.mpower.event.PaymentEvent;

public class PaymentRulesInterceptor implements ApplicationContextAware, ApplicationListener {

	private static final Log logger = LogFactory.getLog(RulesInterceptor.class);

	private ApplicationContext applicationContext;
	private String ruleFlowName;
	
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

		@SuppressWarnings("unused")
		PersonService ps = (PersonService) applicationContext.getBean("personService");
		GiftService gs = (GiftService) applicationContext.getBean("giftService");

		String site = null;
			workingMemory.insert(gift);
			if (gift instanceof SiteAware) {
				site = ((SiteAware) gift).getSite() != null ? ((SiteAware) gift).getSite().getName() : null;
			}

			try {
				List<Gift> gifts = gs.readGiftsByPersonId(gift.getPerson().getId());
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
			site = SessionServiceImpl.lookupUserSiteName();
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

	public String getRuleFlowName() {
		return ruleFlowName;
	}

	public void setRuleFlowName(String ruleFlowName) {
		this.ruleFlowName = ruleFlowName;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof PaymentEvent) {
			NewGiftEvent nge = (NewGiftEvent) event;
			doApplyRules(nge.getGift());
		}
		
	}
}