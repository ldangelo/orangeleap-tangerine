package com.mpower.dao.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QueryUtil {
	
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    
    private QueryUtil() {
    }

    public static StringBuilder getCustomString(Map<String, String> customParams, LinkedHashMap<String, Object> parameterMap) {
        StringBuilder customString = new StringBuilder();
        if (customParams != null && !customParams.isEmpty()) {
            int paramCount = 1;
            for (Map.Entry<String, String> pair : customParams.entrySet()) {
                String key = pair.getKey();
                Object value = pair.getValue();
                customString.append(" AND EXISTS ( SELECT giftCustomField.id FROM com.mpower.domain.GiftCustomField giftCustomField WHERE giftCustomField.gift.id = gift.id AND (");
                customString.append("giftCustomField.customField.name = :name");
                customString.append(paramCount);
                customString.append(" AND giftCustomField.customField.value LIKE :value");
                customString.append(paramCount);
                parameterMap.put("name" + paramCount, key);
                parameterMap.put("value" + paramCount, "%" + value + "%");
                customString.append("))");
                paramCount++;
            }
        }
        return customString;
    }
}
