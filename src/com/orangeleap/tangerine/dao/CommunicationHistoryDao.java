package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface CommunicationHistoryDao {

    public CommunicationHistory maintainCommunicationHistory(CommunicationHistory communicationHistory);
	
	public PaginatedResult readCommunicationHistoryByConstituentId(Long constituentId, SortInfo sortinfo);
	
	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId);
	
}