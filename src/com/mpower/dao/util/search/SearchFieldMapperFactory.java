package com.mpower.dao.util.search;

import com.mpower.type.EntityType;

public class SearchFieldMapperFactory {
	public SearchFieldMapper getMapper(EntityType entitytype)  {
		
		if (entitytype == EntityType.person) return new PersonSearchFieldMapper();
		if (entitytype == EntityType.gift) return new GiftSearchFieldMapper();
		if (entitytype == EntityType.commitment) return new CommitmentSearchFieldMapper();
		
		throw new RuntimeException("Invalid entity.");
	}
}
