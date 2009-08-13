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
import java.util.Calendar;
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
import org.joda.time.DateMidnight;
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
import com.orangeleap.tangerine.controller.validator.RecurringGiftValidator;
import com.orangeleap.tangerine.dao.RecurringGiftDao;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.ScheduledItem;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.service.ReminderService;
import com.orangeleap.tangerine.service.ScheduledItemService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@Service("recurringGiftService")
@Transactional(propagation = Propagation.REQUIRED)
public class RecurringGiftServiceImpl extends AbstractCommitmentService<RecurringGift> implements RecurringGiftService {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "recurringGiftDAO")
    private RecurringGiftDao recurringGiftDao;

	@Resource(name="recurringGiftValidator")
	protected RecurringGiftValidator recurringGiftValidator;

    @Resource(name = "recurringGiftEntityValidator")
    protected EntityValidator entityValidator;

    @Resource(name = "codeValidator")
    protected CodeValidator codeValidator;

    @Resource(name = "distributionLinesValidator")
    protected DistributionLinesValidator distributionLinesValidator;
    
    @Resource(name = "scheduledItemService")
    private ScheduledItemService scheduledItemService;

    @Resource(name = "reminderService")
    private ReminderService reminderService;



    @Override
    public RecurringGift readRecurringGiftById(Long recurringGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRecurringGiftById: recurringGiftId = " + recurringGiftId);
        }
        RecurringGift rg =  recurringGiftDao.readRecurringGiftById(recurringGiftId);
        ScheduledItem item = scheduledItemService.getNextItemToRun(rg);
        rg.setNextRunDate(item == null ? null : item.getActualScheduledDate());
        return rg;
    }

    @Override
    public RecurringGift readRecurringGiftByIdCreateIfNull(String recurringGiftId, Constituent constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRecurringGiftByIdCreateIfNull: recurringGiftId = " + recurringGiftId + " constituentId = " + (constituent == null ? null : constituent.getId()));
        }
        RecurringGift recurringGift = null;
        if (recurringGiftId == null) {
            if (constituent != null) {
                recurringGift = this.createDefaultRecurringGift(constituent);
            }
        } else {
            recurringGift = this.readRecurringGiftById(Long.valueOf(recurringGiftId));
        }
        return recurringGift;
    }

    @Override
    public RecurringGift createDefaultRecurringGift(Constituent constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("createDefaultRecurringGift: constituent = " + (constituent == null ? null : constituent.getId()));
        }
        RecurringGift recurringGift = new RecurringGift();
        createDefault(constituent, recurringGift, EntityType.recurringGift, "recurringGiftId");

        return recurringGift;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
    public RecurringGift maintainRecurringGift(RecurringGift recurringGift) throws BindException {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainRecurringGift: recurringGift = " + recurringGift);
        }
	    validateRecurringGift(recurringGift, true);

	    RecurringGift old = getExisting(recurringGift);
	    
        recurringGift.setAutoPay(true);

        RecurringGift savedRecurringGift = save(recurringGift);

        maintainSchedules(old, savedRecurringGift);

        return savedRecurringGift;
    }
    
    private void setNextRun(RecurringGift recurringGift) {
    	ScheduledItem item = scheduledItemService.getNextItemToRun(recurringGift);
        if (item != null) {
        	recurringGift.setNextRunDate(item.getActualScheduledDate());
        } else {
        	recurringGift.setNextRunDate(null);
        }
        recurringGiftDao.maintainRecurringGift(recurringGift);
    }

	private void validateRecurringGift(RecurringGift recurringGift, boolean validateDistributionLines) throws BindException {
		if (recurringGift.getFieldLabelMap() != null && !recurringGift.isSuppressValidation()) {

		    BindingResult br = new BeanPropertyBindingResult(recurringGift, "recurringGift");
		    BindException errors = new BindException(br);

			entityValidator.validate(recurringGift, errors);
			recurringGiftValidator.validate(recurringGift, errors);
		    codeValidator.validate(recurringGift, errors);
			if (validateDistributionLines) {
		        distributionLinesValidator.validate(recurringGift, errors);
			}

		    if (errors.hasErrors()) {
		        throw errors;
		    }
		}
	}
 
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
    public RecurringGift editRecurringGift(RecurringGift recurringGift) throws BindException {
    	
        if (logger.isTraceEnabled()) {
            logger.trace("editRecurringGift: recurringGiftId = " + recurringGift.getId());
        }
	    validateRecurringGift(recurringGift, false);
	    
	    RecurringGift old = getExisting(recurringGift);

        RecurringGift savedRecurringGift = save(recurringGift);

        maintainSchedules(old, savedRecurringGift);

        return savedRecurringGift;
    }
    
    private void maintainSchedules(RecurringGift old, RecurringGift savedRecurringGift) {
        if (savedRecurringGift.isActivate()) {
        	if (needToResetSchedule(old, savedRecurringGift)) {
        		reminderService.deleteReminders(savedRecurringGift);
        		scheduledItemService.regenerateSchedule(savedRecurringGift);
        	} else if (needToResetReminders(old, savedRecurringGift)) {
        		reminderService.deleteReminders(savedRecurringGift);
        	}
    		scheduledItemService.extendSchedule(savedRecurringGift);
    		reminderService.generateDefaultReminders(savedRecurringGift);
        } else {
        	reminderService.deleteReminders(savedRecurringGift);
        	scheduledItemService.deleteSchedule(savedRecurringGift);
        }
        setNextRun(savedRecurringGift);
    }

    private RecurringGift getExisting(RecurringGift recurringGift) {
	    if (recurringGift.getId() == null || recurringGift.getId().equals(0)) return null;
    	return recurringGiftDao.readRecurringGiftById(recurringGift.getId());
    }
    
    private boolean needToResetSchedule(RecurringGift old, RecurringGift updated) {
    	
    	if (old == null) return true;
    	
		if (!compare(old.getStartDate(), updated.getStartDate())
				|| !compare(old.getEndDate(), updated.getEndDate())
				|| !old.getFrequency().equals(updated.getFrequency()))
			return true;
    	
    	return false;
    }
    
    private boolean needToResetReminders(RecurringGift old, RecurringGift updated) {
    	
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

    private RecurringGift save(RecurringGift recurringGift) throws BindException {
        maintainEntityChildren(recurringGift, recurringGift.getConstituent());
        recurringGift = recurringGiftDao.maintainRecurringGift(recurringGift);
        auditService.auditObject(recurringGift, recurringGift.getConstituent());
        return recurringGift;
    }

    @Override
    public List<RecurringGift> readRecurringGiftsForConstituent(Constituent constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRecurringGifts: constituent = " + constituent);
        }
        return readRecurringGiftsForConstituent(constituent.getId());
    }

    @Override
    public List<RecurringGift> readRecurringGiftsForConstituent(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRecurringGifts: constituentId = " + constituentId);
        }
        return recurringGiftDao.readRecurringGiftsByConstituentId(constituentId);
    }

    @Override
    public PaginatedResult readPaginatedRecurringGiftsByConstituentId(Long constituentId, SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaginatedRecurringGiftsByConstituentId: constituentId = " + constituentId);
        }
        return recurringGiftDao.readPaginatedRecurringGiftsByConstituentId(constituentId, sortinfo);
    }

    @Override
    public List<RecurringGift> searchRecurringGifts(Map<String, Object> params) {
        if (logger.isTraceEnabled()) {
            logger.trace("searchRecurringGifts: params = " + params);
        }
        return recurringGiftDao.searchRecurringGifts(params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, List<RecurringGift>> findGiftAppliableRecurringGiftsForConstituent(Long constituentId, String selectedRecurringGiftIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("findGiftAppliableRecurringGiftsForConstituent: constituentId = " + constituentId + " selectedRecurringGiftIds = " + selectedRecurringGiftIds);
        }
        List<RecurringGift> rGifts = recurringGiftDao.readRecurringGiftsByConstituentId(constituentId);

        Set<String> selectedRecurringGiftIdsSet = StringUtils.commaDelimitedListToSet(selectedRecurringGiftIds);
        List<RecurringGift> notSelectedRecurringGifts = filterApplicableRecurringGiftsForConstituent(rGifts, Calendar.getInstance().getTime());
        List<RecurringGift> selectedRecurringGifts = new ArrayList<RecurringGift>();

        if (selectedRecurringGiftIdsSet.isEmpty() == false) {
            for (Iterator<RecurringGift> iter = notSelectedRecurringGifts.iterator(); iter.hasNext();) {
                RecurringGift aRecurringGift = iter.next();
                if (selectedRecurringGiftIdsSet.contains(aRecurringGift.getId().toString())) {
                    selectedRecurringGifts.add(aRecurringGift);
                    iter.remove();
                }
            }
        }
        Map<String, List<RecurringGift>> recurringGiftMap = new HashMap<String, List<RecurringGift>>();
        recurringGiftMap.put("selectedRecurringGifts", selectedRecurringGifts);
        recurringGiftMap.put("notSelectedRecurringGifts", notSelectedRecurringGifts);
        return recurringGiftMap;
    }

    @Override
    public List<RecurringGift> filterApplicableRecurringGiftsForConstituent(List<RecurringGift> gifts, Date nowDt) {
        DateMidnight now = new DateMidnight(nowDt);
        for (Iterator<RecurringGift> recIter = gifts.iterator(); recIter.hasNext();) {
            RecurringGift recurringGift = recIter.next();
            if (Commitment.STATUS_EXPIRED.equals(recurringGift.getRecurringGiftStatus()) || Commitment.STATUS_CANCELLED.equals(recurringGift.getRecurringGiftStatus())) {
                recIter.remove();
            } else {
                DateMidnight startDt = new DateMidnight(recurringGift.getStartDate());
                if (startDt.isAfter(now)) {
                    recIter.remove();
                } else if (recurringGift.getEndDate() != null) {
                    if (new DateMidnight(recurringGift.getEndDate()).isBefore(now)) {
                        recIter.remove();
                    }
                }
            }
        }
        return gifts;
    }

    @Override
    public List<DistributionLine> findDistributionLinesForRecurringGifts(Set<String> recurringGiftIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("findDistributionLinesForRecurringGifts: recurringGiftIds = " + recurringGiftIds);
        }
        if (recurringGiftIds != null && recurringGiftIds.isEmpty() == false) {
            return recurringGiftDao.findDistributionLinesForRecurringGifts(new ArrayList<String>(recurringGiftIds));
        }
        return null;
    }

    @Override
    public boolean canApplyPayment(RecurringGift recurringGift) {
        if (logger.isTraceEnabled()) {
            logger.trace("canApplyPayment: recurringGift.id = " + recurringGift.getId() + " status = " + recurringGift.getRecurringGiftStatus());
        }
        List<RecurringGift> rGifts = new ArrayList<RecurringGift>(1);
        rGifts.add(recurringGift);
        return recurringGift.getId() != null && recurringGift.getId() > 0 && filterApplicableRecurringGiftsForConstituent(rGifts, Calendar.getInstance().getTime()).size() == 1;
    }

    @Override
    public void updateRecurringGiftForGift(Gift gift) {
        if (logger.isTraceEnabled()) {
            logger.trace("updateRecurringGiftForGift: gift.id = " + gift.getId());
        }
        updateRecurringGiftStatusAmountPaid(gift.getDistributionLines());
    }

    @Override
    public void updateRecurringGiftForAdjustedGift(AdjustedGift adjustedGift) {
        if (logger.isTraceEnabled()) {
            logger.trace("updateRecurringGiftForAdjustedGift: adjustedGift.id = " + adjustedGift.getId());
        }
        updateRecurringGiftStatusAmountPaid(adjustedGift.getDistributionLines());
    }

    private void updateRecurringGiftStatusAmountPaid(List<DistributionLine> lines) {
        Set<Long> recurringGiftIds = new HashSet<Long>();
        if (lines != null) {
            for (DistributionLine thisLine : lines) {
                if (NumberUtils.isDigits(thisLine.getCustomFieldValue(StringConstants.ASSOCIATED_RECURRING_GIFT_ID))) {
                    Long recurringGiftId = Long.parseLong(thisLine.getCustomFieldValue(StringConstants.ASSOCIATED_RECURRING_GIFT_ID));
                    recurringGiftIds.add(recurringGiftId);
                }
            }

            if (recurringGiftIds.isEmpty() == false) {
                for (Long recurringGiftId : recurringGiftIds) {
                    RecurringGift recurringGift = readRecurringGiftById(recurringGiftId);
                    if (recurringGift != null) {
                        BigDecimal amountPaid = recurringGiftDao.readAmountPaidForRecurringGiftId(recurringGiftId);
                        setRecurringGiftAmounts(recurringGift, amountPaid);
                        setRecurringGiftStatus(recurringGift);
                        recurringGiftDao.maintainRecurringGiftAmountPaidRemainingStatus(recurringGift);
                    }
                }
            }
        }
    }

    private void setRecurringGiftAmounts(RecurringGift recurringGift, BigDecimal amountPaid) {
        if (amountPaid == null || amountPaid.compareTo(BigDecimal.ZERO) == -1) {
            amountPaid = BigDecimal.ZERO;
        }
        recurringGift.setAmountPaid(amountPaid);
        if (recurringGift.getAmountTotal() != null && recurringGift.getEndDate() != null) {
            recurringGift.setAmountRemaining(recurringGift.getAmountTotal().subtract(recurringGift.getAmountPaid()));
        }
    }

    @Override
    public void setRecurringGiftStatus(RecurringGift recurringGift) {
        setCommitmentStatus(recurringGift, "recurringGiftStatus");
    }

    public static final String GIFT_AMOUNT_OVERRIDE = "giftAmountOverride";
    
    protected Gift createAutoGift(RecurringGift recurringGift, ScheduledItem scheduledItem) {
    	
        Gift gift = new Gift(recurringGift);

        // Allow overriding the amount for a specific payment date.
    	String giftAmountOverride = scheduledItem.getCustomFieldValue(GIFT_AMOUNT_OVERRIDE);
    	if (giftAmountOverride != null && giftAmountOverride.trim().length() > 0) {
    		BigDecimal amount = new BigDecimal(giftAmountOverride);
    		gift.setAmount(amount);
    	}

        recurringGift.addGift(gift);
        
        gift.setSuppressValidation(true);
        try {
            gift = giftService.maintainGift(gift);
        }
        catch (BindException e) {
            // Should not happen with suppressValidation = true.
            logger.error(e);
        }
        
        return gift;

    }

    // Used by nightly batch
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processRecurringGift(RecurringGift recurringGift, ScheduledItem scheduledItem) {
    	
        Gift gift = createAutoGift(recurringGift, scheduledItem);

        /* Re-read the Recurring Gift from the DB as fields may have changed */
        recurringGift = recurringGiftDao.readRecurringGiftById(recurringGift.getId());

        scheduledItemService.completeItem(scheduledItem, gift, gift.getPaymentStatus());
        
    	ScheduledItem nextitem = scheduledItemService.getNextItemToRun(recurringGift);
        if (nextitem == null) {
            recurringGift.setRecurringGiftStatus(RecurringGift.STATUS_FULFILLED);
            recurringGift.setNextRunDate(null);
            recurringGiftDao.maintainRecurringGift(recurringGift);
        } else {
            recurringGift.setNextRunDate(nextitem.getActualScheduledDate());
        }

    }

    // Used by nightly batch
	@Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void extendPaymentSchedule(RecurringGift recurringGift) {
		scheduledItemService.extendSchedule(recurringGift);
		reminderService.generateDefaultReminders(recurringGift);
	}

    // Used by nightly batch
	@Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public ScheduledItem getNextPaymentToRun(RecurringGift recurringGift) {
		return scheduledItemService.getNextItemToRun(recurringGift);
	}

}
