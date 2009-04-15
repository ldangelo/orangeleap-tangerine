package com.orangeleap.tangerine.dao.util.search;

import java.util.HashMap;
import java.util.Map;

public class PledgeSearchFieldMapper extends SearchFieldMapper {
	
	@Override
    public Map<String, String> getMap() {
		return MAP;
	}
	
    // These are the fields we support for searching.
    private static final Map<String, String> MAP = new HashMap<String, String>();
    static {
    	
		// Constituent
    	MAP.put("person.accountNumber", "ACCOUNT_NUMBER");
		MAP.put("person.firstName", "FIRST_NAME");
		MAP.put("person.lastName", "LAST_NAME");
		MAP.put("person.organizationName", "ORGANIZATION_NAME");

		// Address
		MAP.put("postalCode", "POSTAL_CODE");

		// Commitment
		MAP.put("referenceNumber", "PLEDGE_ID");
		MAP.put("amountPerGift", "AMOUNT_PER_GIFT");
		MAP.put("amountTotal", "AMOUNT_TOTAL");

    }
    
}

