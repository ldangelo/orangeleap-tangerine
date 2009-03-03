package com.mpower.service;

import java.util.List;
import java.util.Map;

import com.mpower.domain.model.QueryLookup;

public interface QueryLookupService {

    public QueryLookup readQueryLookup(String fieldDefinitionId);

    public List<Object> executeQueryLookup(String fieldDefinitionId, Map<String, String> params);
}
