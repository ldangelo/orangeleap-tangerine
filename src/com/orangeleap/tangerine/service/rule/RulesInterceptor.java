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
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.WorkingMemory;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

import java.util.Properties;


public abstract class RulesInterceptor implements ApplicationContextAware, ApplicationListener {

    private static final Log logger = OLLogger.getLog(RulesInterceptor.class);

    private ApplicationContext applicationContext;
    private String agendaGroup;
    @SuppressWarnings("unchecked")
    private Class eventClass;
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
        droolsProperties.put("name", "testagent");
    }

    public Properties getDroolsProperties() {
        return droolsProperties;
    }

    public void doApplyRules(Gift gift) {

        RuleBase ruleBase = ((DroolsRuleAgent) applicationContext.getBean("DroolsRuleAgent")).getRuleAgent(gift.getSite().getName()).getRuleBase();

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

        String site = null;

        workingMemory.setGlobal("applicationContext", applicationContext);
        workingMemory.setGlobal("constituentService", ps);
        workingMemory.setGlobal("giftService", gs);

        try {
            if (site == null) {
                site = gift.getSite().getName();
            }
            workingMemory.setFocus(site + "email");

            workingMemory.insert(gift);

            Constituent constituent = gift.getConstituent();

            constituent.setGifts(gs.readMonetaryGifts(constituent));
            constituent.setSite(ss.readSite(constituent.getSite().getName()));

            workingMemory.insert(constituent);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
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