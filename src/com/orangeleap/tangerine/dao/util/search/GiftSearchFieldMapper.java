package com.orangeleap.tangerine.dao.util.search;

import java.util.HashMap;
import java.util.Map;

public class GiftSearchFieldMapper extends SearchFieldMapper {
	
	public Map<String, String> getMap() {
		return MAP;
	}
	
    // These are the fields we support for searching.
    private static final Map<String, String> MAP = new HashMap<String, String>();
    static {
    	
    	// Constituent
    	MAP.put("person.id", "CONSTITUENT_ID");
    	MAP.put("person.customId", "CUSTOM_ID");
    	MAP.put("person.firstName", "FIRST_NAME");
    	MAP.put("person.lastName", "LAST_NAME");
    	MAP.put("person.organizationName", "ORGANIZATION_NAME");

    	// Address
    	MAP.put("postalCode", "POSTAL_CODE");
    	
    	// Gift
    	MAP.put("id", "GIFT_ID");
    	MAP.put("referenceNumber", "GIFT_ID");
    	MAP.put("amount", "AMOUNT");

    }
    
}

