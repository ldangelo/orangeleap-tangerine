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
import com.orangeleap.tangerine.event.GiftEvent;
import com.orangeleap.tangerine.event.NewGiftEvent;
import com.orangeleap.tangerine.service.*;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.RuleTask;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.util.TaskStack;
import org.apache.commons.logging.Log;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.WorkingMemory;
import org.drools.agent.RuleAgent;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

public class GiftRulesInterceptor extends RulesInterceptor {

    private static final Log logger = OLLogger.getLog(GiftRulesInterceptor.class);

    private ApplicationContext applicationContext;


    public GiftRulesInterceptor(String droolsHost, String droolsPort) {
        super(droolsHost, droolsPort);
    }

    @Override
    public void doApplyRules(Gift gift) {
        String site = null;
        site = gift.getSite().getName();

        RuleAgent agent = (RuleAgent) ((DroolsRuleAgent) applicationContext.getBean("DroolsRuleAgent")).getRuleAgent(site);
        RuleBase ruleBase = agent.getRuleBase();

        StatefulSession session = ruleBase.newStatefulSession();
        WorkingMemory workingMemory = (WorkingMemory) session;

        if (logger.isDebugEnabled()) {
            workingMemory.addEventListener(new DebugAgendaEventListener());
            workingMemory.addEventListener(new DebugWorkingMemoryEventListener());
        }


        @SuppressWarnings("unused")
        ConstituentService ps = (ConstituentService) applicationContext.getBean("constituentService");
        GiftService gs = (GiftService) applicationContext.getBean("giftService");
        SiteService ss = (SiteService) applicationContext.getBean("siteService");
        TangerineUserHelper uh = (TangerineUserHelper) applicationContext.getBean("tangerineUserHelper");
        EmailService es = (EmailService) applicationContext.getBean("emailService");
        ErrorLogService errorLogService = (ErrorLogService) applicationContext.getBean("errorLogService");


        try {

            workingMemory.setFocus(site + "gift");
            workingMemory.setGlobal("applicationContext", applicationContext);
            workingMemory.setGlobal("constituentService", ps);
            workingMemory.setGlobal("giftService", gs);
            workingMemory.setGlobal("userHelper", uh);

            workingMemory.insert(ss.readSite(site));

            ss.populateDefaultEntityEditorMaps(gift);
            workingMemory.insert(gift);

            /*List<Gift> gifts = gs.readMonetaryGiftsByConstituentId(gift.getConstituent().getId());
                   Iterator<Gift> giftsIter = gifts.iterator();
                   while (giftsIter.hasNext()) {
                       workingMemory.insert(giftsIter.next());
                   }*/

            Constituent constituent = gift.getConstituent();

            constituent.setGifts(gs.readMonetaryGifts(constituent));
            constituent.setSite(ss.readSite(constituent.getSite().getName()));
            constituent.setEmails(es.readByConstituentId(constituent.getId()));
            ss.populateDefaultEntityEditorMaps(constituent);
            constituent.setSuppressValidation(true);

            TaskStack.push(new RuleTask(applicationContext, site + "email", constituent, gift));

            workingMemory.insert(constituent);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            errorLogService.addErrorMessage(ex.getMessage(), "gift.rule.setup");
        }

        if (site == null) {
            site = gift.getSite().getName();
        }

        try {
            logger.info("*** firing all rules");

            workingMemory.fireAllRules();


            session.dispose();
            workingMemory = null;

        } catch (Exception e) {
            logger.error("*** exception firing rules - make sure rule base exists and global variable is set: ");
            logger.error(e);
            errorLogService.addErrorMessage(e.getMessage(), "gift.rule.fire");
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