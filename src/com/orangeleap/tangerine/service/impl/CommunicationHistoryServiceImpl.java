package com.orangeleap.tangerine.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.CommunicationHistoryDao;
import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.service.CommunicationHistoryService;
import com.orangeleap.tangerine.util.TangerineUserHelper;

@Service("communicationHistoryService")
@Transactional(propagation = Propagation.REQUIRED)
public class CommunicationHistoryServiceImpl extends AbstractTangerineService implements CommunicationHistoryService {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "communicationHistoryDAO")
	private CommunicationHistoryDao communicationHistoryDao;
	
	@Resource(name = "tangerineUserHelper")
	private TangerineUserHelper tangerineUserHelper;

	@Override
	public CommunicationHistory maintainCommunicationHistory(CommunicationHistory communicationHistory) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainCommunicationHistory: communicationHistory = " + communicationHistory);
        }
		if (communicationHistory.getPerson() == null) {
            return null;
        }
        communicationHistory.setCustomFieldValue("recordedBy", "" + tangerineUserHelper.lookupUserId());
		return communicationHistoryDao.maintainCommunicationHistory(communicationHistory);
	}

	@Override
	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCommunicationHistoryById: communicationHistoryId = " + communicationHistoryId);
        }
		return communicationHistoryDao.readCommunicationHistoryById(communicationHistoryId);
	}
	
	@Override
	public List<CommunicationHistory> readCommunicationHistoryByConstituent(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCommunicationHistoryByConstituent: constituentId = " + constituentId);
        }
		return communicationHistoryDao.readCommunicationHistoryByConstituentId(constituentId);
	}

	@Override
	public CommunicationHistory readCommunicationHistoryByIdCreateIfNull(String communicationHistoryId, Person constituent) {
		if (logger.isDebugEnabled()) {
			logger.debug("readCommunicationHistoryByIdCreateIfNull: communicationHistoryId = " + communicationHistoryId + " constituentId = " + (constituent == null ? null : constituent.getId()));
		}
		CommunicationHistory communicationHistory = null;
		if (communicationHistoryId == null) {
			communicationHistory = this.createCommunicationHistory(constituent);
			communicationHistory.setRecordDate(new java.util.Date()); // default to today
		}
		else {
			communicationHistory = this.readCommunicationHistoryById(Long.valueOf(communicationHistoryId));
		}
		return communicationHistory;
	}

	private CommunicationHistory createCommunicationHistory(Person constituent) {
		CommunicationHistory communicationHistory = new CommunicationHistory();
		communicationHistory.setPerson(constituent);
		return communicationHistory;
	}
}
