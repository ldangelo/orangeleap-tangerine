package com.orangeleap.tangerine.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import com.orangeleap.tangerine.controller.validator.EntityValidator;
import com.orangeleap.tangerine.dao.CommunicationHistoryDao;
import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.AuditService;
import com.orangeleap.tangerine.service.CommunicationHistoryService;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@Service("communicationHistoryService")
@Transactional(propagation = Propagation.REQUIRED)
public class CommunicationHistoryServiceImpl extends AbstractTangerineService implements CommunicationHistoryService {

	/** Logger for this class and subclasses */
	protected final Log logger = OLLogger.getLog(getClass());

	@Resource(name = "communicationHistoryDAO")
	private CommunicationHistoryDao communicationHistoryDao;
	
	@Resource(name = "tangerineUserHelper")
	private TangerineUserHelper tangerineUserHelper;
	
    @Resource(name = "auditService")
    protected AuditService auditService;
    
    @Resource(name="communicationHistoryEntityValidator")
    protected EntityValidator entityValidator;



	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
	public CommunicationHistory maintainCommunicationHistory(CommunicationHistory communicationHistory) throws BindException {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainCommunicationHistory: communicationHistory = " + communicationHistory);
        }
		if (communicationHistory.getConstituent() == null) {
            return null;
        }
		
        if (communicationHistory.getFieldLabelMap() != null && !communicationHistory.isSuppressValidation()) {

	        BindingResult br = new BeanPropertyBindingResult(communicationHistory, "communicationHistory");
	        BindException errors = new BindException(br);
	      
	        entityValidator.validate(communicationHistory, errors);
	        if (errors.getAllErrors().size() > 0) throw errors;
        }


		
		Long lookupUserId = tangerineUserHelper.lookupUserId();
		if (lookupUserId != null) {
		    communicationHistory.setCustomFieldValue("recordedBy", "" + tangerineUserHelper.lookupUserId());
		}
		CommunicationHistory savedHistory = communicationHistoryDao.maintainCommunicationHistory(communicationHistory);
		auditService.auditObject(savedHistory, communicationHistory.getConstituent());
		return savedHistory;
	}

	@Override
	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCommunicationHistoryById: communicationHistoryId = " + communicationHistoryId);
        }
		return communicationHistoryDao.readCommunicationHistoryById(communicationHistoryId);
	}
	
	@Override
	public PaginatedResult readCommunicationHistoryByConstituent(Long constituentId, SortInfo sortInfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCommunicationHistoryByConstituent: constituentId = " + constituentId);
        }
		return communicationHistoryDao.readCommunicationHistoryByConstituentId(constituentId, sortInfo);
	}

	@Override
	public CommunicationHistory readCommunicationHistoryByIdCreateIfNull(String communicationHistoryId, Constituent constituent) {
		if (logger.isTraceEnabled()) {
			logger.trace("readCommunicationHistoryByIdCreateIfNull: communicationHistoryId = " + communicationHistoryId + " constituentId = " + (constituent == null ? null : constituent.getId()));
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

	private CommunicationHistory createCommunicationHistory(Constituent constituent) {
		CommunicationHistory communicationHistory = new CommunicationHistory();
		communicationHistory.setConstituent(constituent);
		return communicationHistory;
	}
}
