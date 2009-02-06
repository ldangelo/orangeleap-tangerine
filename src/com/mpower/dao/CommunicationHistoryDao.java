package com.mpower.dao;

import java.util.List;

import com.mpower.domain.CommunicationHistory;

public interface CommunicationHistoryDao {

    public CommunicationHistory maintainCommunicationHistory(CommunicationHistory communicationHistory);
	
	public List<CommunicationHistory> readCommunicationHistoryByPerson(Long personId);
	
	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId);
	
}