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
    	MAP.put("constituent.id", "CONSTITUENT_ID");
    	MAP.put("constituent.accountNumber", "ACCOUNT_NUMBER");
    	MAP.put("constituent.firstName", "FIRST_NAME");
    	MAP.put("constituent.lastName", "LAST_NAME");
    	MAP.put("constituent.organizationName", "ORGANIZATION_NAME");

    	// Address
    	MAP.put("postalCode", "POSTAL_CODE");
    	
    	// Gift
    	MAP.put("id", "GIFT_ID");
    	MAP.put("referenceNumber", "GIFT_ID");
    	MAP.put("amount", "AMOUNT");

    }
    
}

