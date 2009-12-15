package com.orangeleap.tangerine.service.rule;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;

import com.orangeleap.tangerine.type.RuleEventNameType;
import com.orangeleap.tangerine.type.RuleObjectType;
import com.orangeleap.tangerine.util.OLLogger;
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

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.OrangeLeapRuleAgent;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.util.RuleTask;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.util.TaskStack;



public abstract class RulesInterceptor implements ApplicationContextAware, ApplicationListener {

	private static final Log logger = OLLogger.getLog(RulesInterceptor.class);

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

		RuleBase ruleBase = ((DroolsRuleAgent)applicationContext.getBean("DroolsRuleAgent")).getRuleAgent(gift.getSite().getName()).getRuleBase();

		OrangeLeapRuleAgent olAgent = (OrangeLeapRuleAgent)applicationContext.getBean("OrangeLeapRuleAgent");
		OrangeLeapRuleBase olRuleBase = olAgent.getOrangeLeapRuleBase(RuleEventNameType.EMAIL, gift.getSite().getName(), false);

		StatefulSession workingMemory = ruleBase.newStatefulSession();
		OrangeLeapRuleSession olWorkingMemory = olRuleBase.newRuleSession();

		try{
			if (logger.isDebugEnabled()) {
				workingMemory.addEventListener (new DebugAgendaEventListener());
				workingMemory.addEventListener(new DebugWorkingMemoryEventListener());
			}


			@SuppressWarnings("unused")
			ConstituentService ps = (ConstituentService) applicationContext.getBean("constituentService");
			GiftService gs = (GiftService) applicationContext.getBean("giftService");
			PicklistItemService plis = (PicklistItemService) applicationContext.getBean("picklistItemService");
			TangerineUserHelper uh = (TangerineUserHelper) applicationContext.getBean("tangerineUserHelper");
			SiteService ss = (SiteService) applicationContext.getBean("siteService");

			String site = null;

			workingMemory.setGlobal("applicationContext", applicationContext);
			workingMemory.setGlobal("constituentService", ps);
			workingMemory.setGlobal("giftService",gs);
			workingMemory.setGlobal("picklistItemService",plis);
			workingMemory.setGlobal("userHelper",uh);

				try {
					if (site == null) {
						site =gift.getSite().getName();
					}
					workingMemory.setFocus(site+"email");

					workingMemory.insert(gift);
					olWorkingMemory.put(RuleObjectType.GIFT, gift);

					Constituent constituent = gift.getConstituent();

					constituent.setGifts(gs.readMonetaryGifts(constituent));
					constituent.setSite(ss.readSite(constituent.getSite().getName()));

					workingMemory.insert(constituent);
					olWorkingMemory.put(RuleObjectType.CONSTITUENT, constituent);

				} catch (Exception ex) {
					logger.error(ex.getMessage());
				}

				if (site == null) {
					site =gift.getSite().getName();
				}

			try {

				logger.debug("*** firing all rules");

				workingMemory.fireAllRules();

			} catch (Exception e) {
				logger.error("*** exception firing rules - make sure rule base exists and global variable is set: ");
				logger.error(e);
			}

		}finally{
			if(workingMemory != null)
				workingMemory.dispose();
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