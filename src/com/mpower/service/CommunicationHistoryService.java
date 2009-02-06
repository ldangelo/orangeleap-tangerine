package com.mpower.service;

import java.util.List;

import com.mpower.domain.CommunicationHistory;
import com.mpower.domain.Gift;
import com.mpower.domain.Person;

public interface CommunicationHistoryService {

    public CommunicationHistory maintainCommunicationHistory(CommunicationHistory communicationHistory);

    public List<CommunicationHistory> readCommunicationHistoryByPerson(Long personId);

    public CommunicationHistory readCommunicationHistoryByIdCreateIfNull(String communicationHistoryId, Person person);

	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId);

}
