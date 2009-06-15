package com.orangeleap.tangerine.dao.util.search;

import com.orangeleap.tangerine.type.EntityType;

public class SearchFieldMapperFactory {
	public SearchFieldMapper getMapper(EntityType entitytype)  {
		
		if (entitytype == EntityType.constituent) {
            return new ConstituentSearchFieldMapper();
        }
		if (entitytype == EntityType.gift) {
            return new GiftSearchFieldMapper();
        }
		if (entitytype == EntityType.pledge) {
            return new PledgeSearchFieldMapper();
        }
        if (entitytype == EntityType.recurringGift) {
            return new RecurringGiftSearchFieldMapper();
        }
		
		throw new RuntimeException("Invalid entity.");
	}
}
