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
import com.orangeleap.tangerine.service.ErrorLogService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.service.exception.DuplicateConstituentException;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import org.apache.commons.logging.Log;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.spi.ConsequenceException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.Iterator;
import java.util.List;


public class ConstituentRulesInterceptor implements ApplicationContextAware, ApplicationListener {

    private static final Log logger = OLLogger.getLog(ConstituentRulesInterceptor.class);

    private ApplicationContext applicationContext;
    private String ruleFlowName;
    private Class eventClass;

    public void doApplyRules(Constituent constituent) throws DuplicateConstituentException, ConstituentValidationException {

        String site = constituent.getSite().getName();
        RuleBase ruleBase = ((DroolsRuleAgent) applicationContext.getBean("DroolsRuleAgent")).getRuleAgent(site).getRuleBase();

        StatefulSession workingMemory = ruleBase.newStatefulSession();

        @SuppressWarnings("unused")
        ConstituentService ps = (ConstituentService) applicationContext.getBean("constituentService");
        GiftService gs = (GiftService) applicationContext.getBean("giftService");
        TangerineUserHelper uh = (TangerineUserHelper) applicationContext.getBean("tangerineUserHelper");
        ErrorLogService errorLogService = (ErrorLogService) applicationContext.getBean("errorLogService");


        workingMemory.setFocus(site + "constituent");
        workingMemory.setGlobal("applicationContext", applicationContext);
        workingMemory.setGlobal("constituentService", ps);
        workingMemory.setGlobal("giftService", gs);
        workingMemory.setGlobal("userHelper", uh);


        constituent.setGifts(gs.readMonetaryGifts(constituent));
        workingMemory.insert(constituent);

        try {
            List<Gift> gifts = gs.readMonetaryGiftsByConstituentId(constituent.getId());
            Iterator<Gift> giftsIter = gifts.iterator();
            while (giftsIter.hasNext()) {
                workingMemory.insert(giftsIter.next());
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }

        try {
            logger.info("*** firing all rules");

            workingMemory.fireAllRules();

        } catch (ConsequenceException ce) {
            if (ce.getCause() instanceof DuplicateConstituentException) {
                DuplicateConstituentException dce = (DuplicateConstituentException) ce.getCause();
                throw dce;
            }
            if (ce.getCause() instanceof ConstituentValidationException) {
                ConstituentValidationException cve = (ConstituentValidationException) ce.getCause();
                throw cve;
            }
            logger.error("*** exception firing rules - make sure rule base exists and global variable is set: ");
            logger.error(ce);
        } catch (Exception e) {
            logger.error("*** exception firing rules - make sure rule base exists and global variable is set: ");
            logger.error(e);
        } finally {
            workingMemory.dispose();
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

    }
}