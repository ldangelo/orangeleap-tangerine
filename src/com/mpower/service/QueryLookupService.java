package com.mpower.service;

import java.util.List;
import java.util.Map;

import com.mpower.domain.QueryLookup;

public interface QueryLookupService {

    public QueryLookup readQueryLookup(String siteName, String fieldDefinitionId);

    public List<Object> executeQueryLookup(String siteName, String fieldDefinitionId, Map<String, String> params);
}
