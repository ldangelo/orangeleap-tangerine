package com.orangeleap.tangerine.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.RecurringGiftDao;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@Service("recurringGiftService")
@Transactional(propagation = Propagation.REQUIRED)
public class RecurringGiftServiceImpl extends AbstractCommitmentService<RecurringGift> implements RecurringGiftService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "recurringGiftDAO")
    private RecurringGiftDao recurringGiftDao;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<RecurringGift> readRecurringGiftsByDateStatuses(Date date, List<String> statuses) {
        if (logger.isDebugEnabled()) {
            logger.debug("readRecurringGifts: date = " + date + " statuses = " + statuses);
        }
        return recurringGiftDao.readRecurringGifts(date, statuses);
    }

    @Override
    public RecurringGift readRecurringGiftById(Long recurringGiftId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readRecurringGiftById: recurringGiftId = " + recurringGiftId);
        }
        return recurringGiftDao.readRecurringGiftById(recurringGiftId);
    }
    
    @Override
    public RecurringGift readRecurringGiftByIdCreateIfNull(String recurringGiftId, Person constituent) {
        if (logger.isDebugEnabled()) {
            logger.debug("readRecurringGiftByIdCreateIfNull: recurringGiftId = " + recurringGiftId + " constituentId = " + (constituent == null ? null : constituent.getId()));
        }
        RecurringGift recurringGift = null;
        if (recurringGiftId == null) {
            if (constituent != null) {
                recurringGift = this.createDefaultRecurringGift(constituent);
            }
        } 
        else {
            recurringGift = this.readRecurringGiftById(Long.valueOf(recurringGiftId));
        }
        return recurringGift;
    }
    
    @Override
    public RecurringGift createDefaultRecurringGift(Person constituent) {
        if (logger.isDebugEnabled()) {
            logger.debug("createDefaultRecurringGift: constituent = " + (constituent == null ? null : constituent.getId()));
        }
        RecurringGift recurringGift = new RecurringGift();
        createDefault(constituent, recurringGift, EntityType.recurringGift, "recurringGiftId");

        return recurringGift;
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public RecurringGift maintainRecurringGift(RecurringGift recurringGift) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainRecurringGift: recurringGift = " + recurringGift);
        }
        if (recurringGift.isAutoPay()) {
            if (recurringGift == null) {
                recurringGift = new RecurringGift();
            }
            recurringGift.setNextRunDate(getNextGiftDate(recurringGift));
        } 
        else {
// TODO: is this necessary?            
//            if (recurringGift != null) {
//                recurringGiftDao.removeRecurringGift(recurringGift);
//                recurringGift = null;
//            }
        }
        recurringGift.filterValidDistributionLines();
        return save(recurringGift);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public RecurringGift editRecurringGift(RecurringGift recurringGift) {
        if (logger.isDebugEnabled()) {
            logger.debug("editRecurringGift: recurringGiftId = " + recurringGift.getId());
        }
        return save(recurringGift);
    }
    
    private RecurringGift save(RecurringGift recurringGift) {
        maintainEntityChildren(recurringGift, recurringGift.getPerson());
        recurringGift = recurringGiftDao.maintainRecurringGift(recurringGift);
        auditService.auditObject(recurringGift, recurringGift.getPerson());
        return recurringGift;
    }
    
    @Override
    public List<RecurringGift> readRecurringGiftsForConstituent(Person constituent) {
        if (logger.isDebugEnabled()) {
            logger.debug("readRecurringGifts: constituent = " + constituent);
        }
        return readRecurringGiftsForConstituent(constituent.getId());
    }

    @Override
    public List<RecurringGift> readRecurringGiftsForConstituent(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readRecurringGifts: constituentId = " + constituentId);
        }
        return recurringGiftDao.readRecurringGiftsByConstituentId(constituentId);
    }
    
    @Override
    public PaginatedResult readPaginatedRecurringGiftsByConstituentId(Long constituentId, SortInfo sortinfo) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPaginatedRecurringGiftsByConstituentId: constituentId = " + constituentId);
        }
        return recurringGiftDao.readPaginatedRecurringGiftsByConstituentId(constituentId, sortinfo);
    }



    @Override
    public List<RecurringGift> searchRecurringGifts(Map<String, Object> params) {
        if (logger.isDebugEnabled()) {
            logger.debug("searchRecurringGifts: params = " + params);
        }
        return recurringGiftDao.searchRecurringGifts(params);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    // TODO: fix IBatisRecurringDao Status, map to RecurringStatus, fix in general
    public void processRecurringGifts() {
        if (logger.isDebugEnabled()) {
            logger.debug("processRecurringGifts:");
        }
        List<RecurringGift> recurringGifts = recurringGiftDao.readRecurringGifts(Calendar.getInstance().getTime(), Arrays.asList(new String[] { Commitment.STATUS_ACTIVE /*, Commitment.STATUS_FULFILLED*/ }));
        if (recurringGifts != null) {
            for (RecurringGift recurringGift : recurringGifts) {
                logger.debug("processRecurringGifts: id =" + recurringGift.getId() + ", nextRun =" + recurringGift.getNextRunDate());
                Date nextDate = null;
                if (recurringGift.getEndDate() == null || recurringGift.getEndDate().after(getToday().getTime())) {
                    createAutoGift(recurringGift);
                    nextDate = getNextGiftDate(recurringGift);
                    if (nextDate != null) {

                        recurringGift.setNextRunDate(nextDate);
                        recurringGiftDao.maintainRecurringGift(recurringGift);
                    }
                }
                // TODO: is this necessary?
//                if (nextDate == null) {
//                    recurringGiftDao.removeRecurringGift(recurringGift);
//                    recurringGift = null;
//                }
            }
        }
    }
}
