package com.mpower.dao.interfaces;

import java.util.List;
import java.util.Map;

public interface QueryLookupExecutorDao {
    public List<Object> executeQueryLookup(String queryString, Map<String, String> parameters);
}
