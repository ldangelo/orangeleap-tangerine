/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.dao.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.QueryLookupDao;
import com.orangeleap.tangerine.domain.QueryLookup;
import com.orangeleap.tangerine.domain.QueryLookupParam;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Corresponds to the QUERY_LOOKUP tables
 */
@Repository("queryLookupDAO")
public class IBatisQueryLookupDao extends AbstractIBatisDao implements QueryLookupDao {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisQueryLookupDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @SuppressWarnings("unchecked")
    @Override
    public QueryLookup readQueryLookup(String fieldDefinitionId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readQueryLookup: fieldDefinitionId = " + fieldDefinitionId);
        }
        Map<String, Object> params = setupParams();
        params.put("fieldDefinitionId", fieldDefinitionId);
        // There should only be 0, 1, or 2 records (one this site and one null site) returned from this query.
        List<QueryLookup> list = getSqlMapClientTemplate().queryForList("SELECT_QUERY_LOOKUP_BY_SITE_FLD_DEF_ID", params);
        if (list.isEmpty()) {
            return null;
        }
        for (QueryLookup ql : list) {
            if (ql.getSite() != null) {
                return ql;
            }
        }
        return list.get(0);
    }

    @Override
    public QueryLookup maintainQueryLookup(QueryLookup queryLookup) {
        return (QueryLookup) insertOrUpdate(queryLookup, "QUERY_LOOKUP");
    }

    @Override
    public void maintainQueryLookupParam(QueryLookupParam queryLookupParam) {
        insertOrUpdate(queryLookupParam, "QUERY_LOOKUP_PARAM");
    }


}