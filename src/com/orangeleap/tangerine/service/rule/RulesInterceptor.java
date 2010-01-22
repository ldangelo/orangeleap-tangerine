package com.orangeleap.tangerine.service.rule;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.OrangeLeapRuleAgent;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.RuleEventNameType;
import com.orangeleap.tangerine.type.RuleObjectType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;



public abstract class RulesInterceptor implements ApplicationContextAware, ApplicationListener {

	private static final Log logger = OLLogger.getLog(RulesInterceptor.class);
	
	public final static String USE_DROOLS = "use.drools";

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
    	
    	String site = gift.getSite().getName();

		SiteService siteService = (SiteService)applicationContext.getBean("siteService");
		boolean useDrools = "true".equalsIgnoreCase(siteService.getSiteOptionsMap().get(RulesInterceptor.USE_DROOLS));

		StatefulSession workingMemory = null;
		OrangeLeapRuleSession olWorkingMemory = null;
		
		if (useDrools) {
			RuleBase ruleBase = ((DroolsRuleAgent)applicationContext.getBean("DroolsRuleAgent")).getRuleAgent(gift.getSite().getName()).getRuleBase();
			workingMemory = ruleBase.newStatefulSession();
		} else {
			OrangeLeapRuleAgent olAgent = (OrangeLeapRuleAgent)applicationContext.getBean("OrangeLeapRuleAgent");
			OrangeLeapRuleBase olRuleBase = olAgent.getOrangeLeapRuleBase(RuleEventNameType.EMAIL, gift.getSite().getName(), false);
			olWorkingMemory = olRuleBase.newRuleSession();
		}

		try{
			if (logger.isDebugEnabled() && useDrools) {
				workingMemory.addEventListener (new DebugAgendaEventListener());
				workingMemory.addEventListener(new DebugWorkingMemoryEventListener());
			}


			@SuppressWarnings("unused")
			ConstituentService ps = (ConstituentService) applicationContext.getBean("constituentService");
			GiftService gs = (GiftService) applicationContext.getBean("giftService");
			PicklistItemService plis = (PicklistItemService) applicationContext.getBean("picklistItemService");
			TangerineUserHelper uh = (TangerineUserHelper) applicationContext.getBean("tangerineUserHelper");
			SiteService ss = (SiteService) applicationContext.getBean("siteService");

			Constituent constituent = gift.getConstituent();

			constituent.setGifts(gs.readMonetaryGifts(constituent));
			constituent.setSite(ss.readSite(constituent.getSite().getName()));

			if (useDrools) {
				workingMemory.setGlobal("applicationContext", applicationContext);
				workingMemory.setGlobal("constituentService", ps);
				workingMemory.setGlobal("giftService",gs);
				workingMemory.setGlobal("picklistItemService",plis);
				workingMemory.setGlobal("userHelper",uh);
				workingMemory.setFocus(site+"email");
				workingMemory.insert(gift);
				workingMemory.insert(constituent);
			} else {
				olWorkingMemory.put(RuleObjectType.GIFT, gift);
				olWorkingMemory.put(RuleObjectType.CONSTITUENT, constituent);
			}
			
			try {

				logger.debug("*** firing all rules");
				if (useDrools) {
					workingMemory.fireAllRules();
				} else {
					olWorkingMemory.executeRules(); 
				}
			} catch (Exception e) {
				logger.error("*** exception firing rules - make sure rule base exists and global variable is set: ");
				logger.error(e);
			}

		} finally {
			if (useDrools && workingMemory != null) workingMemory.dispose();
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