package com.mpower.service.rule;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component("rulesInterceptor")
public class RulesInterceptor {

	@Around(value="execution(* com.mpower.service..*.maintain*(..)) " +
			"|| execution(* com.mpower.service..*.save*(..))")
	public Object doApplyRules(ProceedingJoinPoint pjp) throws Throwable {
		
		return pjp.proceed();
	}
	
}
