package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.QueryLookup;

public interface QueryLookupDao {
    public QueryLookup readQueryLookup(String fieldDefinitionId);
}
