package com.orangeleap.tangerine.service;

import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface CommunicationHistoryService {

    public CommunicationHistory maintainCommunicationHistory(CommunicationHistory communicationHistory);

    public PaginatedResult readCommunicationHistoryByConstituent(Long constituentId, SortInfo sortInfo);

    public CommunicationHistory readCommunicationHistoryByIdCreateIfNull(String communicationHistoryId, Person constituent);

	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId);

}
