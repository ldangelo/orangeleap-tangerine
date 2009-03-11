package com.orangeleap.tangerine.service;

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.QueryLookup;

public interface QueryLookupService {

    public QueryLookup readQueryLookup(String fieldDefinitionId);

    public List<Object> executeQueryLookup(String fieldDefinitionId, Map<String, String> params);
}
