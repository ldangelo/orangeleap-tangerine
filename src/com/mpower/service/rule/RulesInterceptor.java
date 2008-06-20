package com.mpower.service.rule;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.DroolsError;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderErrors;
import org.drools.rule.Package;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Aspect
@Component("rulesInterceptor")
public class RulesInterceptor implements ApplicationContextAware {

	private static final Log logger = LogFactory.getLog(RulesInterceptor.class);
	
	private ApplicationContext applicationContext;
	
	@Around(value="execution(* com.mpower.service..*.maintain*(..)) " +
			"|| execution(* com.mpower.service..*.save*(..))")
	public Object doApplyRules(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		
		RuleBase ruleBase = loadRules(args);
		if (ruleBase == null) {
			return pjp.proceed();
		}
		WorkingMemory workingMemory = ruleBase.newStatefulSession();
		for (Object entity : args) {
			workingMemory.insert(entity);
		}
		workingMemory.setGlobal("applicationContext", applicationContext);
		
		logger.info("*** firing all rules");
		workingMemory.fireAllRules();
		
		return pjp.proceed(args);
	}
	
	private RuleBase loadRules(Object[] entities) throws Exception {
		try {
			if (entities == null || entities.length == 0) {
				return null;
			}
			
			PackageBuilder builder = new PackageBuilder();
			
			boolean foundRule = false;
			
			//TODO: Need a better overall strategy for looking up a rule file
			//Consider checking the database first and then falling back to the file.
			for (Object entity : entities) {
				String name = "/rules/" + entity.getClass().getSimpleName() + "_maintain.drl";
				InputStream stream = getClass().getResourceAsStream(name);
				if (stream == null) {
					continue;
				}
				foundRule = true;
				builder.addPackageFromDrl(new InputStreamReader(stream));
				IOUtils.closeQuietly(stream);
			}
			
			if (! foundRule) {
				return null;
			}
			
			if (builder.hasErrors()) {
				PackageBuilderErrors errors = builder.getErrors();
				StringBuffer buff = new StringBuffer("An error occured loading Drools: ").append(System.getProperty("line.separator"));
				DroolsError[] drlErrors = errors.getErrors();
				for (DroolsError error : drlErrors) {
					buff.append(error.getMessage()).append(System.getProperty("line.separator"));
				}
				logger.error(buff.toString());
			}
			
			Package pkg = builder.getPackage();
			
			//add the package to a rule base.
			RuleBase ruleBase = RuleBaseFactory.newRuleBase();
			ruleBase.addPackage(pkg);
			return ruleBase;
		} finally {
			//
		}
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
}
