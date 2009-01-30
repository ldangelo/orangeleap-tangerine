package com.mpower.service;

import java.util.List;

import com.mpower.domain.CommunicationHistory;

public interface CommunicationHistoryService {

    public CommunicationHistory addCommunicationHistory(CommunicationHistory communicationHistory);

    public List<CommunicationHistory> readCommunicationHistoryByClient(Long personId);

    public List<CommunicationHistory> readCommunicationHistoryByRepresentative(Long personId);

}
