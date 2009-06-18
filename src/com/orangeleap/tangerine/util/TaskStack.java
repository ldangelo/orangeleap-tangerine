package com.orangeleap.tangerine.util;

import java.util.Stack;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;

import com.orangeleap.tangerine.service.rule.RulesInterceptor;

public class TaskStack {
    protected static final Log logger = OLLogger.getLog(RulesStack.class);
	
    private static ThreadLocal<Stack<RuleTask>> tl_stack = new ThreadLocal<Stack<RuleTask>>(){
        protected synchronized Stack<RuleTask> initialValue() {
              return new Stack<RuleTask>();
          }
    };
  
    public static Stack<RuleTask> getStack() {
          return tl_stack.get();
    }
    
    // Returns true if the item was already on the stack (re-entrancy problem?)
    public static boolean push(RuleTask task) {
    	boolean reentrant = getStack().contains(task);
    	getStack().push(task);
    	if (reentrant) {
    		logger.error("Re-entrant rules stack state.");
    	}
    	return reentrant;
    }
    
    // Returns false if the expected item wasn't on the top of the stack (missing try/finally?)
    public static boolean pop(RuleTask task) {
    	boolean expected = task.equals(getStack().pop());
    	if (!expected) {
    		logger.error("Unexpected rules stack state: " + task);
    	}
    	return expected;
    }
    
    public static void execute() {
    	while(!getStack().empty()) {
    		RuleTask task = getStack().pop();
    	
    		//
    		// run the rules assocated with this
    		RulesInterceptor ri = (RulesInterceptor) task.getContext().getBean("emailInterceptor");
    		ri.doApplyRules(task.getGift());
    	}
    	
    }
}
