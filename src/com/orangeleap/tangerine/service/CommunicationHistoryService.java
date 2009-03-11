package com.orangeleap.tangerine.service;

import java.util.List;

import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.domain.Person;

public interface CommunicationHistoryService {

    public CommunicationHistory maintainCommunicationHistory(CommunicationHistory communicationHistory);

    public List<CommunicationHistory> readCommunicationHistoryByConstituent(Long constituentId);

    public CommunicationHistory readCommunicationHistoryByIdCreateIfNull(String communicationHistoryId, Person constituent);

	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId);

}
