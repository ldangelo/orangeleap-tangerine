package com.orangeleap.tangerine.dao.util.search;

import java.util.HashMap;
import java.util.Map;

public class RecurringGiftSearchFieldMapper extends SearchFieldMapper {
	
	@Override
    public Map<String, String> getMap() {
		return MAP;
	}
	
    // These are the fields we support for searching.
    private static final Map<String, String> MAP = new HashMap<String, String>();
    static {
    	
		// Constituent
    	MAP.put("constituent.accountNumber", "ACCOUNT_NUMBER");
		MAP.put("constituent.firstName", "FIRST_NAME");
		MAP.put("constituent.lastName", "LAST_NAME");
		MAP.put("constituent.organizationName", "ORGANIZATION_NAME");

		// Address
		MAP.put("postalCode", "POSTAL_CODE");

		// Commitment
		MAP.put("referenceNumber", "RECURRING_GIFT_ID");
		MAP.put("amountPerGift", "AMOUNT_PER_GIFT");
		MAP.put("amountTotal", "AMOUNT_TOTAL");

    }
    
}

