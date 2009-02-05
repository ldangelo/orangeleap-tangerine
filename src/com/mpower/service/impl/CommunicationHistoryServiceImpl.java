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
import com.mpower.domain.Person;
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
	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId) {
		return communicationHistoryDao.readCommunicationHistoryById(communicationHistoryId);
	}
	@Override
	public List<CommunicationHistory> readCommunicationHistoryByClient(Long personId) {
		return communicationHistoryDao.readCommunicationHistoryByClient(personId);
	}

	@Override
	public List<CommunicationHistory> readCommunicationHistoryByRepresentative(Long personId) {
		return communicationHistoryDao.readCommunicationHistoryByRepresentative(personId);
	}

	@Override
	public CommunicationHistory readCommunicationHistoryByIdCreateIfNull(String communicationHistoryId, Person person) {
		if (logger.isDebugEnabled()) {
			logger.debug("readCommunicationHistoryByIdCreateIfNull: personId = " + (person == null ? null : person.getId()));
		}
		CommunicationHistory communicationHistory = null;
		if (communicationHistoryId == null) {
			communicationHistory = this.createCommunicationHistory(person);
		}
		else {
			communicationHistory = this.readCommunicationHistoryById(Long.valueOf(communicationHistoryId));
		}
		return communicationHistory;
	}

	private CommunicationHistory createCommunicationHistory(Person person) {
		CommunicationHistory communicationHistory = new CommunicationHistory();
		communicationHistory.setPerson(person);
		return communicationHistory;
	}

}
