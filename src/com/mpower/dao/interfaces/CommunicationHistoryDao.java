package com.mpower.dao.interfaces;

import java.util.List;

import com.mpower.domain.model.CommunicationHistory;

public interface CommunicationHistoryDao {

    public CommunicationHistory maintainCommunicationHistory(CommunicationHistory communicationHistory);
	
	public List<CommunicationHistory> readCommunicationHistoryByPerson(Long personId);
	
	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId);
	
}