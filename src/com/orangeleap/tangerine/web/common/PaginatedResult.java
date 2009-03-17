package com.orangeleap.tangerine.web.common;

import java.util.List;

/**
 * This class is used for the return value from services
 * and DAOs that need to provided a paginated response back to
 * the view layer. It has fields for holding the response list
 * and total row count
 * @version 1.0
 */
public class PaginatedResult {

    private long rowCount;

    private List rows;

    public long getRowCount() {
        return rowCount;
    }

    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
