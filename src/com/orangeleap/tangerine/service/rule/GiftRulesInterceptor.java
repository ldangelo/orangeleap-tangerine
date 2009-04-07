package com.orangeleap.tangerine.service.rule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.WorkingMemory;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.event.GiftEvent;
import com.orangeleap.tangerine.event.NewGiftEvent;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.SiteService;

public class GiftRulesInterceptor extends RulesInterceptor {

	private static final Log logger = LogFactory.getLog(RulesInterceptor.class);

	private ApplicationContext applicationContext;
	
	public GiftRulesInterceptor(String droolsHost, String droolsPort) {
        super(droolsHost, droolsPort);
    }
	
	@Override
    public void doApplyRules(Gift gift) {

		RuleBase ruleBase = ((DroolsRuleAgent)applicationContext.getBean("DroolsRuleAgent")).getRuleAgent().getRuleBase();

		StatefulSession session = ruleBase.newStatefulSession();
		WorkingMemory workingMemory = (WorkingMemory) session;
		workingMemory.addEventListener (new DebugAgendaEventListener());
		workingMemory.addEventListener(new DebugWorkingMemoryEventListener());

		
		
		@SuppressWarnings("unused")
		ConstituentService ps = (ConstituentService) applicationContext.getBean("constituentService");
		GiftService gs = (GiftService) applicationContext.getBean("giftService");
		SiteService ss = (SiteService) applicationContext.getBean("siteService");
		
		String site = null;


			try {
				if (site == null) {
					site =gift.getSite().getName();
				}
				workingMemory.setFocus(site+"gift");
				
				workingMemory.insert(gift);
				
				/*List<Gift> gifts = gs.readMonetaryGiftsByConstituentId(gift.getPerson().getId()); 
				Iterator<Gift> giftsIter = gifts.iterator();
				while (giftsIter.hasNext()) {
					workingMemory.insert(giftsIter.next());
				}*/

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


	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof GiftEvent) {
			NewGiftEvent nge = (NewGiftEvent) event;
			doApplyRules(nge.getGift());
		}
		
	}
}