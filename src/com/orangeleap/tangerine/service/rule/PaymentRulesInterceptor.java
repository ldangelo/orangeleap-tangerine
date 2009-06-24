package com.orangeleap.tangerine.service.rule;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.ErrorLogService;
import com.orangeleap.tangerine.service.GiftService;

public class PaymentRulesInterceptor implements ApplicationContextAware,
		ApplicationListener {

	private static final Log logger = OLLogger.getLog(PaymentRulesInterceptor.class);

	private ApplicationContext applicationContext;
	
	public void doApplyRules(Gift gift) {
		String site = gift.getSite().getName();
		RuleBase ruleBase = ((DroolsRuleAgent)applicationContext.getBean("DroolsRuleAgent")).getRuleAgent(site).getRuleBase();

		StatefulSession workingMemory = ruleBase.newStatefulSession();
		if (logger.isDebugEnabled()) {
			workingMemory.addEventListener(new DebugAgendaEventListener());
			workingMemory.addEventListener(new DebugWorkingMemoryEventListener());
		}
		@SuppressWarnings("unused")
		ConstituentService ps = (ConstituentService) applicationContext
				.getBean("constituentService");
		GiftService gs = (GiftService) applicationContext
				.getBean("giftService");
		ErrorLogService errorLogService = (ErrorLogService) applicationContext.getBean("errorLogService");


		try {

			workingMemory.setGlobal("applicationContext", applicationContext);
			workingMemory.setGlobal("constituentService", ps);
			workingMemory.setGlobal("giftService",gs);

			workingMemory.setFocus(site + "processpayment");

			workingMemory.insert(gift.getSite());
			workingMemory.insert(gift);

/*			List<Gift> gifts = gs.readMonetaryGiftsByConstituentId(gift
					.getConstituent().getId());
			Iterator<Gift> giftsIter = gifts.iterator();
			while (giftsIter.hasNext()) {
				workingMemory.insert(giftsIter.next());
			}*/

			Constituent constituent = gift.getConstituent();
			constituent.setGifts(gs.readMonetaryGifts(constituent));
			workingMemory.insert(constituent);

		} catch (Exception ex) {
			logger.info(ex.getMessage());
			errorLogService.addErrorMessage(ex.getMessage(), "payment.rule.setup");
		}

		try {
			
			logger.info("*** firing all rules");

			workingMemory.fireAllRules();

			workingMemory.dispose();
		} catch (Exception e) {
			logger
					.info("*** exception firing rules - make sure rule base exists and global variable is set: ");
			logger.info(e);
			errorLogService.addErrorMessage(e.getMessage(), "payment.rule.fire");
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {

	}
}