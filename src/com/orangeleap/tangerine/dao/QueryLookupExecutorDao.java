package com.orangeleap.tangerine.dao;

import java.util.List;
import java.util.Map;

public interface QueryLookupExecutorDao {
    public List<Object> executeQueryLookup(String queryString, Map<String, String> parameters);
}
