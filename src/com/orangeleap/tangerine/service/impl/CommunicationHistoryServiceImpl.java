/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.service.impl;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
import com.orangeleap.tangerine.integration.NewCommunicationHistory;
import com.orangeleap.tangerine.service.AuditService;
import com.orangeleap.tangerine.service.CommunicationHistoryService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.RulesStack;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@Service("communicationHistoryService")
@Transactional(propagation = Propagation.REQUIRED)
public class CommunicationHistoryServiceImpl extends AbstractTangerineService implements CommunicationHistoryService, ApplicationContextAware {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "communicationHistoryDAO")
    private CommunicationHistoryDao communicationHistoryDao;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

    @Resource(name = "auditService")
    protected AuditService auditService;

    @Resource(name = "communicationHistoryEntityValidator")
    protected EntityValidator entityValidator;

    private ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

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
            if (errors.hasErrors()) {
	            throw errors;
            }
        }


        Long lookupUserId = tangerineUserHelper.lookupUserId();
        if (lookupUserId != null) {
            communicationHistory.setCustomFieldValue("recordedBy", "" + tangerineUserHelper.lookupUserId());
        }
        CommunicationHistory savedHistory = communicationHistoryDao.maintainCommunicationHistory(communicationHistory);
        auditService.auditObject(savedHistory, communicationHistory.getConstituent());
        
        routeCommunicationHistory(savedHistory);
        
        return savedHistory;
    }

    
    private final static String ROUTE_METHOD = "CommunicationHistoryServiceImpl.routeCommunicationHistory";

    private void routeCommunicationHistory(CommunicationHistory communicationHistory) {

    	boolean wasRollbackOnly = OLLogger.isCurrentTransactionMarkedRollbackOnly(context);

        boolean reentrant = RulesStack.push(ROUTE_METHOD);
        try {

        	if (!reentrant){
            	try {
            		NewCommunicationHistory newCommunicationHistory = (NewCommunicationHistory) context.getBean("newCommunicationHistory");
                    newCommunicationHistory.routeCommunicationHistory(communicationHistory);
                }
                catch (Exception ex) {
                    logger.error("RULES_FAILURE: CommunicationHistory -" + ex.getMessage(), ex);
                }
        	}
        } finally {
            RulesStack.pop(ROUTE_METHOD);
        }

    	boolean isRollbackOnly = OLLogger.isCurrentTransactionMarkedRollbackOnly(context);

    	if (!wasRollbackOnly && isRollbackOnly) {
    		logger.error("Rules processing caused transaction rollback for communicationHistory "+communicationHistory.getId());
    	}

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
        } else {
            communicationHistory = this.readCommunicationHistoryById(Long.valueOf(communicationHistoryId));
        }
        return communicationHistory;
    }

    private CommunicationHistory createCommunicationHistory(Constituent constituent) {
        CommunicationHistory communicationHistory = new CommunicationHistory();
        communicationHistory.setConstituent(constituent);
        return communicationHistory;
    }

    @Override
    public List<CommunicationHistory> readAllCommunicationHistoryByConstituentId(Long constituentId, SortInfo sort, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllCommunicationHistoryByConstituentId: constituentId = " + constituentId + " sort = " + sort);
        }
        return communicationHistoryDao.readAllCommunicationHistoryByConstituentId(constituentId, sort.getSort(), sort.getDir(), sort.getStart(),
                sort.getLimit(), locale);
    }

    @Override
    public int readCountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentId: constituentId = " + constituentId);
        }
        return communicationHistoryDao.readCountByConstituentId(constituentId);
    }
}
