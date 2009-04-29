package com.orangeleap.tangerine.service.rule;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.WorkingMemory;
import org.drools.agent.RuleAgent;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.util.RuleTask;
import com.orangeleap.tangerine.util.TaskStack;



public abstract class RulesInterceptor implements ApplicationContextAware, ApplicationListener {

	private static final Log logger = LogFactory.getLog(RulesInterceptor.class);

	private ApplicationContext applicationContext;
	private String agendaGroup;
	@SuppressWarnings("unchecked")
    private Class  eventClass;
	private final Properties droolsProperties;
	
	public RulesInterceptor() {
		droolsProperties = new Properties();
	}
	
	public RulesInterceptor(final String droolsHost, final String droolsPort) {
        if (logger.isDebugEnabled()) {
            logger.debug("RulesInterceptor: droolsHost = " + droolsHost + " droolsPort = " + droolsPort);
        }
        droolsProperties = new Properties();
        String url = "http://" + droolsHost + ":" + droolsPort + "/drools/org.drools.brms.JBRMS/package/com.mpower/NEWEST";
        logger.debug("Setting Drools URL to " + url);
        droolsProperties.put("url", url);
        droolsProperties.put("newInstance", "true");
        droolsProperties.put("name","testagent");
    }

	public Properties getDroolsProperties() {
        return droolsProperties;
    }

    public void doApplyRules(Gift gift) {

		RuleBase ruleBase = ((DroolsRuleAgent)applicationContext.getBean("DroolsRuleAgent")).getRuleAgent().getRuleBase();

		StatefulSession session = ruleBase.newStatefulSession();
		WorkingMemory workingMemory = (WorkingMemory) session;
		
		if (logger.isDebugEnabled()) {
			workingMemory.addEventListener (new DebugAgendaEventListener());
			workingMemory.addEventListener(new DebugWorkingMemoryEventListener());
		}
		
		
		@SuppressWarnings("unused")
		ConstituentService ps = (ConstituentService) applicationContext.getBean("constituentService");
		GiftService gs = (GiftService) applicationContext.getBean("giftService");
		SiteService ss = (SiteService) applicationContext.getBean("siteService");
		
		String site = null;


			try {
				if (site == null) {
					site =gift.getSite().getName();
				}
				workingMemory.setFocus(site+"email");
				
				workingMemory.insert(gift);
				
				Person person = gift.getPerson();
				
				person.setGifts(gs.readMonetaryGifts(person));
				person.setSite(ss.readSite(person.getSite().getName()));

				workingMemory.insert(person);

			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

			if (site == null) {
				site =gift.getSite().getName();
			}

		try {
			workingMemory.setGlobal("applicationContext", applicationContext);
			workingMemory.setGlobal("personService", ps);
			workingMemory.setGlobal("giftService",gs);

			logger.info("*** firing all rules");

			workingMemory.fireAllRules();
			
			
			session.dispose();
			workingMemory = null;

		} catch (Exception e) {
			logger.error("*** exception firing rules - make sure rule base exists and global variable is set: ");
			logger.error(e);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public ApplicationContext getApplicationContext() {
			return this.applicationContext;
	}

	public String getAgendaGroup() {
		return agendaGroup;
	}

	public void setAgendaGroup(String agendaGroup) {
		this.agendaGroup = agendaGroup;
	}

	/*@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event.getClass() == eventClass) {
			NewGiftEvent nge = (NewGiftEvent) event;
			doApplyRules(nge.getGift());
		}
		
	}*/
}