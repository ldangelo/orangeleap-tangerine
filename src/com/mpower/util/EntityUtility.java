package com.mpower.util;

public class EntityUtility {

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
