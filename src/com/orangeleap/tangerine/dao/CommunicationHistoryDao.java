package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.CommunicationHistory;

public interface CommunicationHistoryDao {

    public CommunicationHistory maintainCommunicationHistory(CommunicationHistory communicationHistory);
	
	public List<CommunicationHistory> readCommunicationHistoryByConstituentId(Long personId);
	
	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId);
	
}