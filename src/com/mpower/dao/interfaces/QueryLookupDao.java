package com.mpower.dao.interfaces;

import com.mpower.domain.model.QueryLookup;

public interface QueryLookupDao {
    public QueryLookup readQueryLookup(String fieldDefinitionId);
}
