package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.QueryLookup;
import com.orangeleap.tangerine.domain.QueryLookupParam;

public interface QueryLookupDao {
    public QueryLookup readQueryLookup(String fieldDefinitionId);

	public QueryLookup maintainQueryLookup(QueryLookup queryLookup);

	public void maintainQueryLookupParam(QueryLookupParam queryLookupParam);
}
