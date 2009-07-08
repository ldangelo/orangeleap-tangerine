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

import org.apache.commons.logging.Log;

import java.util.Stack;

public class RulesStack {

    protected static final Log logger = OLLogger.getLog(RulesStack.class);

    private static ThreadLocal<Stack<String>> tl_stack = new ThreadLocal<Stack<String>>() {
        protected synchronized Stack<String> initialValue() {
            return new Stack<String>();
        }
    };

    public static Stack<String> getStack() {
        return tl_stack.get();
    }

    // Returns true if the item was already on the stack (re-entrancy problem?)
    public static boolean push(String operationId) {
        boolean reentrant = getStack().contains(operationId);
        getStack().push(operationId);
        if (reentrant) {
            logger.debug("Re-entrant rules stack state.");
        }
        return reentrant;
    }

    // Returns false if the expected item wasn't on the top of the stack (missing try/finally?)
    public static boolean pop(String operationId) {
        boolean expected = operationId.equals(getStack().pop());
        if (!expected) {
            logger.error("Unexpected rules stack state: " + operationId);
        }
        return expected;
    }

}
