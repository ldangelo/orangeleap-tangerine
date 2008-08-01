package com.mpower.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EntityUtility {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    private EntityUtility() {
    }

    public static boolean addWhereOrAnd(boolean whereUsed, StringBuilder queryString) {
        if (whereUsed) {
            queryString.append(" AND");
            return whereUsed;
        }
        queryString.append(" WHERE");
        return true;
    }
}
