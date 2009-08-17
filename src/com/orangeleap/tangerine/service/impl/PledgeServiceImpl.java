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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import com.orangeleap.tangerine.controller.validator.CodeValidator;
import com.orangeleap.tangerine.controller.validator.DistributionLinesValidator;
import com.orangeleap.tangerine.controller.validator.EntityValidator;
import com.orangeleap.tangerine.controller.validator.PledgeValidator;
import com.orangeleap.tangerine.dao.PledgeDao;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.ScheduledItem;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.ReminderService;
import com.orangeleap.tangerine.service.ScheduledItemService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@SuppressWarnings("unchecked")
@Service("pledgeService")
@Transactional(propagation = Propagation.REQUIRED)
public class PledgeServiceImpl extends AbstractCommitmentService<Pledge> implements PledgeService {

    /** Logger for this class and subclasses */
    protected final static Log logger = OLLogger.getLog(PledgeServiceImpl.class);
    
    public static final String GIFT_AMOUNT_OVERRIDE = "giftAmountOverride";

    @Resource(name = "pledgeDAO")
    private PledgeDao pledgeDao;

	@Resource(name="pledgeValidator")
	protected PledgeValidator pledgeValidator;

    @Resource(name="pledgeEntityValidator")
    protected EntityValidator entityValidator;

    @Resource(name="codeValidator")
    protected CodeValidator codeValidator;
    
    @Resource(name="distributionLinesValidator")
    protected DistributionLinesValidator distributionLinesValidator;
    
    @Resource(name = "scheduledItemService")
    private ScheduledItemService scheduledItemService;

    @Resource(name = "reminderService")
    private ReminderService reminderService;




    // Used for create new only
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
    public Pledge maintainPledge(Pledge pledge) throws BindException {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainPledge: pledge = " + pledge);
        }
        
		validatePledge(pledge, true);
		setStartDateForReminders(pledge);

		Pledge oldPledge = getExisting(pledge);
		setStartDateForReminders(oldPledge);
		Pledge savedPledge = save(pledge);
		maintainSchedules(oldPledge, savedPledge);

		return savedPledge;
    }
    
    private void setStartDateForReminders(Pledge pledge) {
		if (pledge != null && !pledge.isRecurring()) {
			pledge.setStartDate(pledge.getProjectedDate());
			pledge.setEndDate(pledge.getProjectedDate());
			pledge.setFrequency(Commitment.FREQUENCY_ONE_TIME);
		}
    }

	private void validatePledge(Pledge pledge, boolean validateDistributionLines) throws BindException {
		if (pledge.getFieldLabelMap() != null && !pledge.isSuppressValidation()) {
			BindingResult br = new BeanPropertyBindingResult(pledge, "pledge");
			BindException errors = new BindException(br);

			entityValidator.validate(pledge, errors);
			pledgeValidator.validate(pledge, errors);
			codeValidator.validate(pledge, errors);

			if (validateDistributionLines) {
				distributionLinesValidator.validate(pledge, errors);
			}

			if (errors.hasErrors()) {
				throw errors;
			}
		}
	}

    // Used for modify existing only
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
    public Pledge editPledge(Pledge pledge) throws BindException {
        if (logger.isTraceEnabled()) {
            logger.trace("editPledge: pledgeId = " + pledge.getId());
        }
	    validatePledge(pledge, false);
		setStartDateForReminders(pledge);
	    
		Pledge oldPledge = getExisting(pledge);
		setStartDateForReminders(oldPledge);
		Pledge savedPledge = save(pledge);
		maintainSchedules(oldPledge, savedPledge);

		return savedPledge;
    }
    
    private Pledge save(Pledge pledge) throws BindException {
        maintainEntityChildren(pledge, pledge.getConstituent());
        pledge = pledgeDao.maintainPledge(pledge);
        auditService.auditObject(pledge, pledge.getConstituent());
        return pledge;
    }

    @Override
    public Pledge readPledgeById(Long pledgeId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPledgeById: pledgeId = " + pledgeId);
        }
        return pledgeDao.readPledgeById(pledgeId);
    }
    
    @Override
    public Pledge readPledgeByIdCreateIfNull(String pledgeId, Constituent constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPledgeByIdCreateIfNull: pledgeId = " + pledgeId + " constituentId = " + (constituent == null ? null : constituent.getId()));
        }
        Pledge pledge = null;
        if (pledgeId == null) {
            if (constituent != null) {
                pledge = this.createDefaultPledge(constituent);
            }
        } 
        else {
            pledge = this.readPledgeById(Long.valueOf(pledgeId));
        }
        return pledge;
    }

    public Pledge createDefaultPledge(Constituent constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("createDefaultPledge: constituent = " + (constituent == null ? null : constituent.getId()));
        }
        Pledge pledge = new Pledge();
        createDefault(constituent, pledge, EntityType.pledge, "pledgeId");

        return pledge;
    }
    
    @Override
    public List<Pledge> readPledgesForConstituent(Constituent constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPledges: constituent = " + constituent);
        }
        return readPledgesForConstituent(constituent.getId());
    }

    @Override
    public List<Pledge> readPledgesForConstituent(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPledges: constituentId = " + constituentId);
        }
        return pledgeDao.readPledgesByConstituentId(constituentId);
    }
    
    @Override
    public PaginatedResult readPaginatedPledgesByConstituentId(Long constituentId, SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaginatedPledgesByConstituentId: constituentId = " + constituentId);
        }
        return pledgeDao.readPaginatedPledgesByConstituentId(constituentId, sortinfo);
    }

    @Override
    public List<Pledge> searchPledges(Map<String, Object> params) {
        if (logger.isTraceEnabled()) {
            logger.trace("searchPledges: params = " + params);
        }
        return pledgeDao.searchPledges(params);
    }
    
    @Override
    public Map<String, List<Pledge>> findNotCancelledPledges(Long constituentId, String selectedPledgeIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("findNotCancelledPledges: constituentId = " + constituentId + " selectedPledgeIds = " + selectedPledgeIds);
        }
        Set<String> selectedPledgeIdsSet = StringUtils.commaDelimitedListToSet(selectedPledgeIds);
        List<Pledge> notSelectedPledges = pledgeDao.findNotCancelledPledges(constituentId);
        List<Pledge> selectedPledges = new ArrayList<Pledge>(); 
        
        if (selectedPledgeIdsSet.isEmpty() == false) {
            for (Iterator iter = notSelectedPledges.iterator(); iter.hasNext();) {
                Pledge pledge = (Pledge) iter.next();
                if (selectedPledgeIdsSet.contains(pledge.getId().toString())) {
                    selectedPledges.add(pledge);
                    iter.remove();
                }
            }
        }
        Map<String, List<Pledge>> pledgeMap = new HashMap<String, List<Pledge>>();
        pledgeMap.put("selectedPledges", selectedPledges);
        pledgeMap.put("notSelectedPledges", notSelectedPledges);
        return pledgeMap;
    }
    
    @Override
    public List<DistributionLine> findDistributionLinesForPledges(Set<String> pledgeIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("findDistributionLinesForPledges: pledgeIds = " + pledgeIds);
        }
        if (pledgeIds != null && !pledgeIds.isEmpty()) {
            return pledgeDao.findDistributionLinesForPledges(new ArrayList<String>(pledgeIds));
        }
        return null;
    }
    
    @Override
    public boolean canApplyPayment(Pledge pledge) {
        if (logger.isTraceEnabled()) {
            logger.trace("canApplyPayment: pledge.id = " + pledge.getId() + " status = " + pledge.getPledgeStatus());
        }
        return pledge.getId() != null && pledge.getId() > 0 && !Commitment.STATUS_CANCELLED.equals(pledge.getPledgeStatus());
    }
    
    @Override
    public boolean arePaymentsAppliedToPledge(Pledge pledge) {
        if (logger.isTraceEnabled()) {
            logger.trace("arePaymentsAppliedToPledge: pledge.id = " + pledge.getId());
        }
        boolean areApplied = false;
        if (pledge.getId() != null && pledge.getId() > 0) {
            Long paymentsAppliedCount = pledgeDao.readPaymentsAppliedToPledgeId(pledge.getId());
            if (paymentsAppliedCount != null && paymentsAppliedCount > 0) {
                areApplied = true;
            }
        }
        return areApplied;
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updatePledgeForGift(Gift gift) {
        if (logger.isTraceEnabled()) {
            logger.trace("updatePledgeForGift: gift.id = " + gift.getId());
        }
        updatePledgeStatusAmountPaid(gift.getDistributionLines());
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updatePledgeForAdjustedGift(AdjustedGift adjustedGift) {
        if (logger.isTraceEnabled()) {
            logger.trace("updatePledgeForAdjustedGift: adjustedGift.id = " + adjustedGift.getId());
        }
        updatePledgeStatusAmountPaid(adjustedGift.getDistributionLines());
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    private void updatePledgeStatusAmountPaid(List<DistributionLine> lines) {
        Set<Long> pledgeIds = new HashSet<Long>();
        if (lines != null) {
            for (DistributionLine thisLine : lines) {
                if (NumberUtils.isDigits(thisLine.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID))) {
                    Long pledgeId = Long.parseLong(thisLine.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID));
                    pledgeIds.add(pledgeId);
                }
            }
    
            if (pledgeIds.isEmpty() == false) {
                for (Long pledgeId : pledgeIds) {
                    Pledge pledge = readPledgeById(pledgeId);
                    if (pledge != null) {
                        BigDecimal amountPaid = pledgeDao.readAmountPaidForPledgeId(pledgeId);
                        setPledgeAmounts(pledge, amountPaid);
                        setCommitmentStatus(pledge, "pledgeStatus");
                        pledgeDao.maintainPledgeAmountPaidRemainingStatus(pledge);
                    }
                }
            }
        }
    }
    
    private void setPledgeAmounts(Pledge pledge, BigDecimal amountPaid) {
        if (amountPaid == null || amountPaid.compareTo(BigDecimal.ZERO) == -1) {
            amountPaid = BigDecimal.ZERO;
        }
        pledge.setAmountPaid(amountPaid);

        if (pledge.getAmountTotal() != null) {
            if (pledge.isRecurring() == false || (pledge.isRecurring() && pledge.getEndDate() != null)) {
                pledge.setAmountRemaining(pledge.getAmountTotal().subtract(pledge.getAmountPaid()));
            }
        }
    }
    
    // Schedule methods

    private void maintainSchedules(Pledge oldPledge, Pledge savedPledge) {
        if (!savedPledge.getPledgeStatus().equals(Commitment.STATUS_CANCELLED)) {
        	if (needToResetSchedule(oldPledge, savedPledge)) {
        		reminderService.deleteReminders(savedPledge);
        		scheduledItemService.regenerateSchedule(savedPledge);
        	} else if (needToResetReminders(oldPledge, savedPledge)) {
        		reminderService.deleteReminders(savedPledge);
        	}
    		scheduledItemService.extendSchedule(savedPledge);
    		reminderService.generateDefaultReminders(savedPledge);
        } else {
        	reminderService.deleteReminders(savedPledge);
        	scheduledItemService.deleteSchedule(savedPledge);
        }
    }
    
    private Pledge getExisting(Pledge pledge) {
	    if (pledge.getId() == null || pledge.getId().equals(0)) return null;
    	return pledgeDao.readPledgeById(pledge.getId());
    }
    
    private boolean needToResetSchedule(Pledge old, Pledge updated) {
    	
    	if (old == null) return true;
    	
		if (!compare(old.getStartDate(), updated.getStartDate())
				|| !compare(old.getEndDate(), updated.getEndDate())
				|| !old.getFrequency().equals(updated.getFrequency()))
			return true;
    	
    	return false;
    }
    
    private boolean needToResetReminders(Pledge old, Pledge updated) {
    	
    	if (old == null) return true;
    	
    	ReminderServiceImpl.ReminderInfo riold = new ReminderServiceImpl.ReminderInfo(old);
    	ReminderServiceImpl.ReminderInfo rinew = new ReminderServiceImpl.ReminderInfo(updated);
    	
		if (
				riold.isGenerateReminders() != rinew.isGenerateReminders()
				|| riold.getInitialReminder() != rinew.getInitialReminder()
				|| riold.getMaximumReminders() != rinew.getMaximumReminders()
				|| riold.getReminderIntervalDays() != rinew.getReminderIntervalDays()
			)
			return true;
    	
    	return false;
    }
    
    private boolean compare(Date d1, Date d2) {
    	if (d1 == null && d2 == null) return true;
    	if (d1 == null || d2 == null) return false;
    	return d1.equals(d2);
    }
    
  
    // Used by nightly batch
	@Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void extendPaymentSchedule(Pledge pledge) {
		scheduledItemService.extendSchedule(pledge);
		reminderService.generateDefaultReminders(pledge);
	}

    // Used by nightly batch
	@Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public ScheduledItem getNextPaymentToRun(Pledge pledge) {
		return scheduledItemService.getNextItemToRun(pledge);
	}



    
}
