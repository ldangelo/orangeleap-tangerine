package com.mpower.service.rule;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.mpower.domain.Gift;
import com.mpower.domain.Person;
import com.mpower.service.GiftService;
import com.mpower.service.PersonService;


public class DailyRuleFiringJob extends QuartzJobBean {

	public DailyRuleFiringJob() {}

	private static final String APPLICATION_CONTEXT_KEY = "applicationContext";

	@Override
	protected void executeInternal(JobExecutionContext context)
		throws JobExecutionException {

	    ApplicationContext applicationContext = null;
	    PersonService ps = null;
	    GiftService gs = null;

		try {
			applicationContext = getApplicationContext(context);
			ps = (PersonService)applicationContext.getBean("personService");
			gs = (GiftService)applicationContext.getBean("giftService");

		} catch (Exception e) {
			e.printStackTrace();
		}

    	try {
            RuleBase ruleBase = readRule();

            WorkingMemory workingMemory = ruleBase.newStatefulSession();

            List<Person> peopleList = ps.readAllPeople();
            	for (Person p : peopleList) {
            		workingMemory.insert(p);
           		}

            List<Gift> giftList = gs.readAllGifts();
            	for (Gift g : giftList) {
            		workingMemory.insert(g);
    			}

            workingMemory.setGlobal("applicationContext", applicationContext);
            workingMemory.fireAllRules();

        } catch (Throwable t) {
            t.printStackTrace();
        }
	}

	private ApplicationContext getApplicationContext(JobExecutionContext context ) throws Exception {
		ApplicationContext applicationContext = null;
		applicationContext = (ApplicationContext)context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
		if (applicationContext == null) {
			throw new JobExecutionException("No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");        }
		return applicationContext;    }

	private RuleBase readRule() throws Exception {

		PackageBuilder builder = new PackageBuilder();

		String name = "/rules/RuleSchedule.dslr";
		InputStream stream = getClass().getResourceAsStream(name);

		String dslName = "/rules/Site1Language.dsl";
		InputStream dslStream = getClass().getResourceAsStream(dslName);

		builder.addPackageFromDrl(new InputStreamReader(stream), new InputStreamReader(dslStream));

		builder.hasErrors();

		Package pkg = builder.getPackage();

		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		ruleBase.addPackage( pkg );
		return ruleBase;
	}

}
