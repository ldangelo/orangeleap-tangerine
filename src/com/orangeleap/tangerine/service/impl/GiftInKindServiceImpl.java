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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import com.orangeleap.tangerine.controller.validator.CodeValidator;
import com.orangeleap.tangerine.controller.validator.EntityValidator;
import com.orangeleap.tangerine.controller.validator.GiftInKindDetailsValidator;
import com.orangeleap.tangerine.dao.GiftDao;
import com.orangeleap.tangerine.dao.GiftInKindDao;
import com.orangeleap.tangerine.domain.Constituent;
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
    
    @Resource(name="giftEntityValidator")
    protected EntityValidator entityValidator;

    @Resource(name="codeValidator")
    protected CodeValidator codeValidator;

    @Resource(name="giftInKindDetailsValidator")
    protected GiftInKindDetailsValidator giftInKindDetailsValidator;



    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
    public GiftInKind maintainGiftInKind(GiftInKind giftInKind) throws BindException {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainGiftInKind: giftInKind = " + giftInKind);
        }
        
        if (giftInKind.getFieldLabelMap() != null && !giftInKind.isSuppressValidation()) {

	        BindingResult br = new BeanPropertyBindingResult(giftInKind, "giftInKind");
	        BindException errors = new BindException(br);
	      
	        codeValidator.validate(giftInKind, errors);
	        if (errors.getAllErrors().size() > 0) throw errors;
	        giftInKindDetailsValidator.validate(giftInKind, errors);
	        if (errors.getAllErrors().size() > 0) throw errors;
	        
	        entityValidator.validate(giftInKind, errors);
	        if (errors.getAllErrors().size() > 0) throw errors;
        }

        
        maintainEntityChildren(giftInKind, giftInKind.getConstituent());
        giftInKind.setTransactionDate(Calendar.getInstance().getTime());
        Gift gift = createGiftForGiftInKind(giftInKind);
        gift = giftDao.maintainGift(gift); // save a row in the gift table
        giftInKind.setGiftId(gift.getId());
        giftInKind.filterValidDetails();
        giftInKind = giftInKindDao.maintainGiftInKind(giftInKind);
        auditService.auditObject(giftInKind, giftInKind.getConstituent());

        return giftInKind;
    }

    @Override
    public GiftInKind readGiftInKindById(Long giftInKindId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftInKindById: giftInKindId = " + giftInKindId);
        }
        return giftInKindDao.readGiftInKindById(giftInKindId);
    }

    @Override
    public List<GiftInKind> readGiftsInKindByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftsInKindByConstituentId: constituentId = " + constituentId);
        }
        return giftInKindDao.readGiftsInKindByConstituentId(constituentId);
    }
    
    @Override
    public PaginatedResult readPaginatedGiftsInKindByConstituentId(Long constituentId, SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaginatedGiftsInKindByConstituentId: constituentId = " + constituentId);
        }
        return giftInKindDao.readPaginatedGiftsInKindByConstituentId(constituentId, sortinfo);
    }
    
    @Override
    public GiftInKind readGiftInKindByIdCreateIfNull(String giftInKindId, Constituent constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftInKindByIdCreateIfNull: giftInKindId = " + giftInKindId + " constituentId = " + (constituent == null ? null : constituent.getId()));
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
    
    private GiftInKind createDefaultGiftInKind(Constituent constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("createDefaultGiftInKind: constituent = " + (constituent == null ? null : constituent.getId()));
        }
        GiftInKind giftInKind = new GiftInKind();
        giftInKind.setConstituent(constituent);
        List<GiftInKindDetail> details = new ArrayList<GiftInKindDetail>(1);
        GiftInKindDetail detail = new GiftInKindDetail();
        details.add(detail);
        giftInKind.setDetails(details);
        return giftInKind;
    }
    
    private Gift createGiftForGiftInKind(GiftInKind giftInKind) {
        if (logger.isTraceEnabled()) {
            logger.trace("createGiftForGiftInKind: giftInKind.giftId = " + giftInKind.getGiftId());
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
