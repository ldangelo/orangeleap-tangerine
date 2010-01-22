package com.orangeleap.tangerine.service.rule;

import org.apache.commons.logging.Log;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.agent.RuleAgent;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.event.GiftEvent;
import com.orangeleap.tangerine.event.NewGiftEvent;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.EmailService;
import com.orangeleap.tangerine.service.ErrorLogService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.OrangeLeapRuleAgent;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.RuleEventNameType;
import com.orangeleap.tangerine.type.RuleObjectType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.RuleTask;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.util.TaskStack;

public class GiftRulesInterceptor extends RulesInterceptor {

	private static final Log logger = OLLogger.getLog(GiftRulesInterceptor.class);

	private ApplicationContext applicationContext;



	public GiftRulesInterceptor(String droolsHost, String droolsPort) {
        super(droolsHost, droolsPort);
    }

	@Override
    public void doApplyRules(Gift gift) {
        
		String site = gift.getSite().getName();
		
		SiteService siteService = (SiteService)applicationContext.getBean("siteService");
		boolean useDrools = "true".equalsIgnoreCase(siteService.getSiteOptionsMap().get(RulesInterceptor.USE_DROOLS));

		StatefulSession workingMemory = null;
		OrangeLeapRuleSession olWorkingMemory = null;
		
		if (useDrools) {
			RuleAgent agent = (RuleAgent) ((DroolsRuleAgent)applicationContext.getBean("DroolsRuleAgent")).getRuleAgent(site);
			RuleBase ruleBase = agent.getRuleBase();
			workingMemory = ruleBase.newStatefulSession();
		} else {
			OrangeLeapRuleAgent olAgent = (OrangeLeapRuleAgent)applicationContext.getBean("OrangeLeapRuleAgent");
			OrangeLeapRuleBase olRuleBase = olAgent.getOrangeLeapRuleBase(RuleEventNameType.GIFT_SAVE, site, false);
			olWorkingMemory = olRuleBase.newRuleSession();
		}
		
		try {
			
			if (logger.isDebugEnabled() && useDrools ) {
				workingMemory.addEventListener (new DebugAgendaEventListener());
				workingMemory.addEventListener(new DebugWorkingMemoryEventListener());
			}


			@SuppressWarnings("unused")
			ConstituentService ps = (ConstituentService) applicationContext.getBean("constituentService");
			GiftService gs = (GiftService) applicationContext.getBean("giftService");
			SiteService ss = (SiteService) applicationContext.getBean("siteService");
			PicklistItemService plis = (PicklistItemService)applicationContext.getBean("picklistItemService");
			TangerineUserHelper uh = (TangerineUserHelper) applicationContext.getBean("tangerineUserHelper");
	        EmailService es = (EmailService) applicationContext.getBean("emailService");
			ErrorLogService errorLogService = (ErrorLogService) applicationContext.getBean("errorLogService");

			try {
				
				ss.populateDefaultEntityEditorMaps(gift);
				Constituent constituent = gift.getConstituent();
				constituent.setGifts(gs.readMonetaryGifts(constituent));
				constituent.setSite(ss.readSite(constituent.getSite().getName()));
				constituent.setEmails(es.readByConstituentId(constituent.getId()));
				ss.populateDefaultEntityEditorMaps(constituent);
				constituent.setSuppressValidation(true);
				TaskStack.push(new RuleTask(applicationContext, site + "email",constituent,gift));

				if (useDrools) {
					workingMemory.setFocus(site+"gift");
					workingMemory.setGlobal("applicationContext", applicationContext);
					workingMemory.setGlobal("constituentService", ps);
					workingMemory.setGlobal("giftService",gs);
					workingMemory.setGlobal("picklistItemService",plis);
					workingMemory.setGlobal("userHelper",uh);
					workingMemory.insert(ss.readSite(site));
					workingMemory.insert(gift);
					workingMemory.insert(constituent);
				} else {					
					olWorkingMemory.put(RuleObjectType.SITE, ss.readSite(site));
					olWorkingMemory.put(RuleObjectType.GIFT, gift);
					olWorkingMemory.put(RuleObjectType.CONSTITUENT, constituent);
				}

			} catch (Exception ex) {
				logger.error(ex.getMessage());
				errorLogService.addErrorMessage(ex.getMessage(), "gift.rule.setup");
			}

			try {
				
				logger.debug("*** firing all rules");

				if (useDrools) {
					workingMemory.fireAllRules();
				} else {
					olWorkingMemory.executeRules(); 
				}
				
			} catch (Exception e) {
				logger.error("*** Exception firing rules: "+e.getMessage(), e);
				errorLogService.addErrorMessage(e.getMessage(), "gift.rule.fire");
			}

		} finally {
			if (useDrools && workingMemory != null) workingMemory.dispose();
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