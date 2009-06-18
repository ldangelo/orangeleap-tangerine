package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.ErrorLog;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

import java.util.List;


public interface ErrorLogDao {

	public void addErrorMessage(String message, String context, Long constituentId);
    public PaginatedResult readErrorMessages(String sortColumn, String dir, int start, int limit);
	
}
