package com.mpower.domain.util;

public class EntityUtility {

    private EntityUtility() {
    }

    public static boolean addWhereOrAnd(boolean whereUsed, StringBuffer queryString) {
        if (whereUsed) {
            queryString.append(" AND");
            return whereUsed;
        }
        queryString.append(" WHERE");
        return true;
    }
}
