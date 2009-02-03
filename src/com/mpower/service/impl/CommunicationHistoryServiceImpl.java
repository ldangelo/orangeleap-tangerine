package com.mpower.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.CommunicationHistoryDao;
import com.mpower.domain.CommunicationHistory;
import com.mpower.service.CommunicationHistoryService;

@Service("communicationHistoryService")
@Transactional(propagation = Propagation.REQUIRED)
public class CommunicationHistoryServiceImpl implements CommunicationHistoryService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "communicationHistoryDao")
    private CommunicationHistoryDao communicationHistoryDao;


	@Override
	public CommunicationHistory addCommunicationHistory(CommunicationHistory communicationHistory) {
		if (communicationHistory.getPerson() == null) return null;
		return communicationHistoryDao.addCommunicationHistory(communicationHistory);
	}

	@Override
	public List<CommunicationHistory> readCommunicationHistoryByClient(Long personId) {
		return communicationHistoryDao.readCommunicationHistoryByClient(personId);
	}
	
	@Override
	public List<CommunicationHistory> readCommunicationHistoryByRepresentative(Long personId) {
		return communicationHistoryDao.readCommunicationHistoryByRepresentative(personId);
	}
	
}
