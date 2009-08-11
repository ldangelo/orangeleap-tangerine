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

package com.orangeleap.tangerine.util;

import com.orangeleap.tangerine.service.rule.RulesInterceptor;
import org.apache.commons.logging.Log;

import java.util.Stack;

public class TaskStack {
    private static final Log logger = OLLogger.getLog(RulesStack.class);

    private static final ThreadLocal<Stack<RuleTask>> tl_stack = new ThreadLocal<Stack<RuleTask>>() {
        protected synchronized Stack<RuleTask> initialValue() {
            return new Stack<RuleTask>();
        }
    };

    private static Stack<RuleTask> getStack() {
        return tl_stack.get();
    }

    // Returns true if the item was already on the stack (re-entrancy problem?)
    public static boolean push(RuleTask task) {
        boolean reentrant = getStack().contains(task);

        if (!reentrant) {
            getStack().push(task);
        } else {
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
        while (!getStack().empty()) {
            RuleTask task = getStack().pop();

            //
            // run the rules assocated with this
            RulesInterceptor ri = (RulesInterceptor) task.getContext().getBean("emailInterceptor");
            ri.doApplyRules(task.getGift());
        }
    }
    
    public static void clear() {
    	getStack().clear();
    }
    
}
