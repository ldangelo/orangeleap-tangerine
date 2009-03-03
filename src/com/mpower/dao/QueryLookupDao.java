package com.mpower.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.mpower.domain.QueryLookup;

@Deprecated
public interface QueryLookupDao {

    public QueryLookup readQueryLookup(String siteName, String fieldDefinitionId);

    public List<Object> executeQueryLookup(String queryString, LinkedHashMap<String, String> parameters);
}
