package com.mpower.dao;

import java.util.List;

import com.mpower.domain.CommunicationHistory;

public interface CommunicationHistoryDao {

    public CommunicationHistory addCommunicationHistory(CommunicationHistory communicationHistory);
	
	public List<CommunicationHistory> readCommunicationHistoryByClient(Long personId);
	
	public List<CommunicationHistory> readCommunicationHistoryByRepresentative(Long personId);

	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId);
	
}