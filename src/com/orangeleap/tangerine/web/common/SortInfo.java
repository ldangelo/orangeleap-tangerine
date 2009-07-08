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

package com.orangeleap.tangerine.web.common;

import com.orangeleap.tangerine.dao.ibatis.AbstractIBatisDao;

import java.util.Map;
import java.util.Set;

/**
 * Simple object representing the sort parameters which ExtJS sends back
 * with a JSON request for a server-controlled Grid component
 */
public class SortInfo {

    /**
     * Minimum size of the SQL 'LIMIT' option, currently
     * set to 10 rows minimum
     */
    public final static int MIN_ROWS = 10;
    private String sort = null;
    private String sortDir = "ASC";
    private int limit = 100; // default is to return 100 rows at a time
    private int start = 0;


    public String getDir() {
        return sortDir;
    }

    public void setDir(String dir) {

        if (dir != null) {
            if (dir.trim().equalsIgnoreCase("DESC")) {
                sortDir = "DESC";
            }
        }
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim().toLowerCase();
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= MIN_ROWS) {
            this.limit = limit;
        }
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        if (start >= 0) {
            this.start = start;
        }
    }

    /**
     * Safety measure. Since Sort retains a raw value coming back
     * from the browser, call this method with an array of the valid
     * column values accepted in this context. Return true if the
     * value for sort matches one of the columns, false if otherwise
     *
     * @param columnNames array of valid column names for the context
     * @return true if the sort parameter is a valid column name
     */
    public boolean validateSortField(Set<String> columnNames) {

        for (String name : columnNames) {
            if (name.equalsIgnoreCase(sort)) {
                return true;
            }
        }

        return false;
    }

    /*
    * Convenience method to add sort info to query params
    */
    public void addParams(Map<String, Object> params) {
        params.put("sortColumn", AbstractIBatisDao.oneWord(getSort()));
        params.put("sortDir", AbstractIBatisDao.oneWord(getDir()));
        params.put("offset", getStart());
        params.put("limit", getLimit());
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("sort:").append(sort);
        builder.append(",dir:").append(sortDir).append(",start:").append(start);
        builder.append(",limit:").append(limit);

        return builder.toString();
    }
}
