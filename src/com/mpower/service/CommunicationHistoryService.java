package com.mpower.service;

import java.util.List;

import com.mpower.domain.model.CommunicationHistory;
import com.mpower.domain.model.Person;

public interface CommunicationHistoryService {

    public CommunicationHistory maintainCommunicationHistory(CommunicationHistory communicationHistory);

    public List<CommunicationHistory> readCommunicationHistoryByConstituent(Long constituentId);

    public CommunicationHistory readCommunicationHistoryByIdCreateIfNull(String communicationHistoryId, Person constituent);

	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId);

}
