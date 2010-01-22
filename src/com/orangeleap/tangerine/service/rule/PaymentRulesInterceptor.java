/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.service.rule;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
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
import com.orangeleap.tangerine.util.TangerineUserHelper;

import org.apache.commons.logging.Log;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class PaymentRulesInterceptor implements ApplicationContextAware,
        ApplicationListener {

    private static final Log logger = OLLogger.getLog(PaymentRulesInterceptor.class);

    private ApplicationContext applicationContext;

    public void doApplyRules(Gift gift) {
        String site = gift.getSite().getName();

		SiteService siteService = (SiteService)applicationContext.getBean("siteService");
		boolean useDrools = "true".equalsIgnoreCase(siteService.getSiteOptionsMap().get(RulesInterceptor.USE_DROOLS));
        
		StatefulSession workingMemory = null;
		OrangeLeapRuleSession olWorkingMemory = null;
		
		if (useDrools) {
	        RuleBase ruleBase = ((DroolsRuleAgent) applicationContext.getBean("DroolsRuleAgent")).getRuleAgent(site).getRuleBase();
	        workingMemory = ruleBase.newStatefulSession();
		} else {
	        OrangeLeapRuleAgent olAgent = (OrangeLeapRuleAgent)applicationContext.getBean("OrangeLeapRuleAgent");
			OrangeLeapRuleBase olRuleBase = olAgent.getOrangeLeapRuleBase(RuleEventNameType.PAYMENT_PROCESSING, site, false);
			olWorkingMemory = olRuleBase.newRuleSession();
		}

		try{
        	if (logger.isDebugEnabled() && useDrools) {
                workingMemory.addEventListener(new DebugAgendaEventListener());
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
            	
		        Constituent constituent = gift.getConstituent();
		        constituent.setGifts(gs.readMonetaryGifts(constituent));

        		if (useDrools) {
	                workingMemory.setFocus(site + "processpayment");
					workingMemory.setGlobal("applicationContext", applicationContext);
					workingMemory.setGlobal("constituentService", ps);
					workingMemory.setGlobal("giftService",gs);
					workingMemory.setGlobal("picklistItemService",plis);
					workingMemory.setGlobal("userHelper",uh);
	                workingMemory.insert(gift.getSite());
	                workingMemory.insert(gift);
	                workingMemory.insert(constituent);
        		} else {
	                olWorkingMemory.put(RuleObjectType.SITE, ss.readSite(site));
					olWorkingMemory.put(RuleObjectType.GIFT, gift);
					olWorkingMemory.put(RuleObjectType.CONSTITUENT, constituent);
        		}

            } catch (Exception ex) {
                logger.info(ex.getMessage());
                errorLogService.addErrorMessage(ex.getMessage(), "payment.rule.setup");
            }

            try {

                logger.debug("*** firing all rules");
                if (useDrools) {
                	workingMemory.fireAllRules();
                } else {
                	olWorkingMemory.executeRules(); 
                }
                
            } catch (Exception e) {
                logger.info("*** exception firing rules: "+e.getMessage(), e);
                errorLogService.addErrorMessage(e.getMessage(), "payment.rule.fire");
            }
        } finally {
        	if (useDrools && workingMemory != null) workingMemory.dispose();
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