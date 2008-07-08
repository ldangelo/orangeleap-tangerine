package com.mpower.service.rule;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DailyRuleFiringJob extends QuartzJobBean {
	public DailyRuleFiringJob() {}

	@Override
	protected void executeInternal(JobExecutionContext jobContext)
		throws JobExecutionException {
    	try {
            RuleBase ruleBase = readRule();

            WorkingMemory workingMemory = ruleBase.newStatefulSession();

            workingMemory.insert(new ScheduledMaintenance(true));
            workingMemory.fireAllRules();

        } catch (Throwable t) {
            t.printStackTrace();
        }
	}

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
