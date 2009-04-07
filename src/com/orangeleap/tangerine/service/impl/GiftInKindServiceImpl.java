package com.orangeleap.tangerine.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.GiftDao;
import com.orangeleap.tangerine.dao.GiftInKindDao;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKindDetail;
import com.orangeleap.tangerine.service.GiftInKindService;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@Service("giftInKindService")
@Transactional(propagation = Propagation.REQUIRED)
public class GiftInKindServiceImpl extends AbstractPaymentService implements GiftInKindService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "giftInKindDAO")
    private GiftInKindDao giftInKindDao;
    
    @Resource(name = "giftDAO")
    private GiftDao giftDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public GiftInKind maintainGiftInKind(GiftInKind giftInKind) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainGiftInKind: giftInKind = " + giftInKind);
        }
        maintainEntityChildren(giftInKind, giftInKind.getPerson());
        giftInKind.setTransactionDate(Calendar.getInstance().getTime());
        Gift gift = createGiftForGiftInKind(giftInKind);
        gift = giftDao.maintainGift(gift); // save a row in the gift table
        giftInKind.setGiftId(gift.getId());
        giftInKind.filterValidDetails();
        giftInKind = giftInKindDao.maintainGiftInKind(giftInKind);
        auditService.auditObject(giftInKind, giftInKind.getPerson());

        return giftInKind;
    }

    @Override
    public GiftInKind readGiftInKindById(Long giftInKindId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readGiftInKindById: giftInKindId = " + giftInKindId);
        }
        return giftInKindDao.readGiftInKindById(giftInKindId);
    }

    @Override
    public List<GiftInKind> readGiftsInKindByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readGiftsInKindByConstituentId: constituentId = " + constituentId);
        }
        return giftInKindDao.readGiftsInKindByConstituentId(constituentId);
    }
    
    @Override
    public PaginatedResult readPaginatedGiftsInKindByConstituentId(Long constituentId, SortInfo sortinfo) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPaginatedGiftsInKindByConstituentId: constituentId = " + constituentId);
        }
        return giftInKindDao.readPaginatedGiftsInKindByConstituentId(constituentId, sortinfo);
    }
    
    @Override
    public GiftInKind readGiftInKindByIdCreateIfNull(String giftInKindId, Person constituent) {
        if (logger.isDebugEnabled()) {
            logger.debug("readGiftInKindByIdCreateIfNull: giftInKindId = " + giftInKindId + " constituentId = " + (constituent == null ? null : constituent.getId()));
        }
        GiftInKind giftInKind = null;
        if (giftInKindId == null) {
            giftInKind = createDefaultGiftInKind(constituent); 
        }
        else {
            giftInKind = readGiftInKindById(Long.valueOf(giftInKindId));
        }
        return giftInKind;
    }
    
    private GiftInKind createDefaultGiftInKind(Person constituent) {
        if (logger.isDebugEnabled()) {
            logger.debug("createDefaultGiftInKind: constituent = " + (constituent == null ? null : constituent.getId()));
        }
        GiftInKind giftInKind = new GiftInKind();
        giftInKind.setPerson(constituent);
        List<GiftInKindDetail> details = new ArrayList<GiftInKindDetail>(1);
        GiftInKindDetail detail = new GiftInKindDetail();
        details.add(detail);
        giftInKind.setDetails(details);
        return giftInKind;
    }
    
    private Gift createGiftForGiftInKind(GiftInKind giftInKind) {
        if (logger.isDebugEnabled()) {
            logger.debug("createGiftForGiftInKind: giftInKind.giftId = " + giftInKind.getGiftId());
        }
        Gift gift = null;
        if (giftInKind.getGiftId() == null || giftInKind.getGiftId() <= 0) {
            gift = new Gift(giftInKind);
        }
        else {
            gift = giftDao.readGiftById(giftInKind.getGiftId());
            gift.setGiftForGiftInKind(giftInKind);
        }
        return gift;
    }
}
