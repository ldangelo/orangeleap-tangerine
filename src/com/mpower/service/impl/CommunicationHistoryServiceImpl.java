package com.mpower.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.CommunicationHistoryDao;
import com.mpower.domain.CommunicationHistory;
import com.mpower.domain.Person;
import com.mpower.security.MpowerAuthenticationToken;
import com.mpower.service.CommunicationHistoryService;
import com.mpower.service.PersonService;

@Service("communicationHistoryService")
@Transactional(propagation = Propagation.REQUIRED)
public class CommunicationHistoryServiceImpl implements CommunicationHistoryService {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "communicationHistoryDao")
	private CommunicationHistoryDao communicationHistoryDao;

	@Resource(name = "personService")
	private PersonService personService;

	@Override
	public CommunicationHistory maintainCommunicationHistory(CommunicationHistory communicationHistory) {
		if (communicationHistory.getPerson() == null) return null;
        MpowerAuthenticationToken authentication = (MpowerAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Person person = personService.readPersonById(authentication.getPersonId());
        communicationHistory.setRecordedBy(person);
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
			communicationHistory.setRecordDate(new java.util.Date()); // default to today
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
