package com.mpower.service.rule;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.drools.RuleBase;
import org.drools.WorkingMemory;
import org.drools.agent.RuleAgent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.mpower.domain.Gift;
import com.mpower.domain.Person;
import com.mpower.domain.SiteAware;
import com.mpower.service.GiftService;
import com.mpower.service.PersonService;
import com.mpower.service.SessionServiceImpl;

@Aspect
@Component("rulesInterceptor")
public class RulesInterceptor implements ApplicationContextAware {

    private static final Log logger = LogFactory.getLog(RulesInterceptor.class);

    private ApplicationContext applicationContext;

    @Around(value = "execution(* com.mpower.service..*.maintain*(..)) " + "|| execution(* com.mpower.service..*.save*(..))" + "|| execution(* com.mpower.service..*.refund*(..))")
    public Object doApplyRules(ProceedingJoinPoint pjp) throws Throwable {

        Object[] args = pjp.getArgs();

        Properties props = new Properties();
        props.put("url", "http://localhost:8080/drools-jbrms/org.drools.brms.JBRMS/package/com.mpower/NEWEST");
        props.put("newInstance", "true");
        props.put("name", "testagent");

        RuleAgent agent = RuleAgent.newRuleAgent(props);
        RuleBase ruleBase = agent.getRuleBase();

        WorkingMemory workingMemory = ruleBase.newStatefulSession();

        @SuppressWarnings("unused")
        PersonService ps = (PersonService) applicationContext.getBean("personService");
        GiftService gs = (GiftService) applicationContext.getBean("giftService");

        String site = null;
        for (Object entity : args) {
            workingMemory.insert(entity);
            if (entity instanceof SiteAware) {
                site = ((SiteAware) entity).getSite() != null ? ((SiteAware) entity).getSite().getName() : null;
            }

            try {
                Gift gift = (Gift) entity;
                List<Gift> gifts = gs.readGiftsByPersonId(gift.getPerson().getId());
                Iterator<Gift> giftsIter = gifts.iterator();
                while (giftsIter.hasNext()) {
                    workingMemory.insert(giftsIter.next());
                }

                Person person = gift.getPerson();
                workingMemory.insert(person);

            } catch (Exception ex) {
            }
        }

        if (site == null) {
            site = SessionServiceImpl.lookupUserSiteName();
        }

        try {
            workingMemory.setGlobal("applicationContext", applicationContext);
            logger.info("*** firing all rules");

            String ruleflow = "com.mpower." + site + "_GiftDonatedRuleflow";
            workingMemory.startProcess(ruleflow);
            workingMemory.fireAllRules();
        } catch (Exception e) {
            logger.info("*** exception firing rules - make sure rule base exists and global variable is set: ");
            logger.info(e);
        }
        return pjp.proceed(args);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}