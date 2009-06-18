package com.orangeleap.tangerine.service;

import com.orangeleap.tangerine.domain.ErrorLog;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

import java.util.List;


public interface ErrorLogService {

	public void addErrorMessage(String message, String context);
	public PaginatedResult readErrorMessages(SortInfo sortInfo);
	
}
