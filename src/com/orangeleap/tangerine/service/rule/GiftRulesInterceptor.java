package com.orangeleap.tangerine.service.rule;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.WorkingMemory;
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
import com.orangeleap.tangerine.service.*;
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
        String site = null;
        site =gift.getSite().getName();

        RuleAgent agent = (RuleAgent) ((DroolsRuleAgent)applicationContext.getBean("DroolsRuleAgent")).getRuleAgent(site);
		RuleBase ruleBase = agent.getRuleBase();

		StatefulSession workingMemory = ruleBase.newStatefulSession();
		try{
			if (logger.isDebugEnabled()) {
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

					workingMemory.setFocus(site+"gift");
					workingMemory.setGlobal("applicationContext", applicationContext);
					workingMemory.setGlobal("constituentService", ps);
					workingMemory.setGlobal("giftService",gs);
					workingMemory.setGlobal("picklistItemService",plis);
					workingMemory.setGlobal("userHelper",uh);

					workingMemory.insert(ss.readSite(site));

					ss.populateDefaultEntityEditorMaps(gift);
					workingMemory.insert(gift);

					Constituent constituent = gift.getConstituent();

					constituent.setGifts(gs.readMonetaryGifts(constituent));
					constituent.setSite(ss.readSite(constituent.getSite().getName()));
					constituent.setEmails(es.readByConstituentId(constituent.getId()));
					ss.populateDefaultEntityEditorMaps(constituent);
					constituent.setSuppressValidation(true);

					TaskStack.push(new RuleTask(applicationContext,site + "email",constituent,gift));

					workingMemory.insert(constituent);

				} catch (Exception ex) {
					logger.error(ex.getMessage());
					errorLogService.addErrorMessage(ex.getMessage(), "gift.rule.setup");
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
				errorLogService.addErrorMessage(e.getMessage(), "gift.rule.fire");
			}

		}finally{
			if (workingMemory != null)
				workingMemory.dispose();
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