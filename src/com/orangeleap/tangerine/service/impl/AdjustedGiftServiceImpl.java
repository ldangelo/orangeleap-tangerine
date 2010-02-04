package com.orangeleap.tangerine.service.impl;

import com.orangeleap.tangerine.service.validator.AdjustedDistributionLinesValidator;
import com.orangeleap.tangerine.service.validator.CodeValidator;
import com.orangeleap.tangerine.service.validator.EntityValidator;
import com.orangeleap.tangerine.dao.AdjustedGiftDao;
import com.orangeleap.tangerine.dao.GiftDao;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.PaymentHistory;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PaymentHistoryService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.service.rollup.RollupHelperService;
import com.orangeleap.tangerine.type.PaymentHistoryType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Service("adjustedGiftService")
@Transactional(propagation = Propagation.REQUIRED)
public class AdjustedGiftServiceImpl extends AbstractPaymentService implements AdjustedGiftService {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "adjustedGiftDAO")
    private AdjustedGiftDao adjustedGiftDao;

    @Resource(name = "giftDAO")
    private GiftDao giftDao;

    @Resource(name = "giftService")
    private GiftService giftService;

    @Resource(name = "paymentHistoryService")
    private PaymentHistoryService paymentHistoryService;

    @Resource(name = "pledgeService")
    private PledgeService pledgeService;

    @Resource(name = "recurringGiftService")
    private RecurringGiftService recurringGiftService;
    
	@Resource(name = "rollupHelperService")
	private RollupHelperService rollupHelperService;
	
	@Resource(name = "adjustedGiftEntityValidator")
	protected EntityValidator entityValidator;

	@Resource(name = "codeValidator")
	protected CodeValidator codeValidator;

	@Resource(name = "adjustedDistributionLinesValidator")
	protected AdjustedDistributionLinesValidator adjustedDistributionLinesValidator;

    @Override
    public AdjustedGift readAdjustedGiftByIdCreateIfNull(Constituent constituent, String adjustedGiftId, String originalGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAdjustedGiftByIdCreateIfNull: adjustedGiftId = " + adjustedGiftId + " originalGiftId = " + originalGiftId + 
                    " constituentId = " + (constituent == null ? null : constituent.getId()));
        }
        AdjustedGift adjustedGift;
        if (adjustedGiftId == null) {
            adjustedGift = this.createdDefaultAdjustedGift(Long.valueOf(originalGiftId));
        }
        else {
            adjustedGift = readAdjustedGiftById(Long.valueOf(adjustedGiftId));
        }
        adjustedGift.setConstituent(constituent);
        return adjustedGift;
    }

    @Override
    public AdjustedGift createdDefaultAdjustedGift(Long originalGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("createdDefaultAdjustedGift: originalGiftId = " + originalGiftId);
        }
        Gift originalGift = giftDao.readGiftById(originalGiftId);
        if (originalGift == null) {
            throw new IllegalArgumentException("Gift for ID = " + originalGiftId + " was not found");
        }
        AdjustedGift aAdjustedGift = new AdjustedGift(originalGift);
        aAdjustedGift.setCurrentTotalAdjustedAmount(findCurrentTotalAdjustedAmount(originalGiftId));
        return aAdjustedGift;
    }

	private void validateAdjustedGift(AdjustedGift adjustedGift, boolean doValidateDistributionLines)
	        throws BindException {
		if (adjustedGift.getFieldLabelMap() != null && !adjustedGift.isSuppressValidation()) {
			BindingResult br = new BeanPropertyBindingResult(adjustedGift, StringConstants.ADJUSTED_GIFT);
			BindException errors = new BindException(br);

			entityValidator.validate(adjustedGift, errors);
			codeValidator.validate(adjustedGift, errors);
			if (doValidateDistributionLines) {
				adjustedDistributionLinesValidator.validate(adjustedGift, errors);
			}

			if (errors.hasErrors()) {
				throw errors;
			}
		}
	}

	private AdjustedGift saveAuditAdjustedGift(AdjustedGift adjustedGift) throws BindException {
		maintainEntityChildren(adjustedGift, adjustedGift.getConstituent());
		adjustedGift = adjustedGiftDao.maintainAdjustedGift(adjustedGift);
		pledgeService.updatePledgeForAdjustedGift(adjustedGift);
		recurringGiftService.updateRecurringGiftForAdjustedGift(adjustedGift);

		if (adjustedGift.isAdjustedPaymentRequired()) {
		    paymentHistoryService.addPaymentHistory(createPaymentHistoryForAdjustedGift(adjustedGift));
		}

		
		updateGiftAdjustedAmount(adjustedGift);
		auditService.auditObject(adjustedGift, adjustedGift.getConstituent());
        rollupHelperService.updateRollupsForConstituentRollupValueSource(adjustedGift);
		return adjustedGift;
	}
	
	private void updateGiftAdjustedAmount(AdjustedGift adjustedGift) {
		if (adjustedGift.getOriginalGiftId() == null) return;
		Gift gift = giftDao.readGiftById(adjustedGift.getOriginalGiftId());
		if (gift == null) return;
		giftService.updateAdjustedAmount(gift);
	}

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
    public AdjustedGift maintainAdjustedGift(AdjustedGift adjustedGift) throws BindException {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainAdjustedGift: adjustedGiftId = " + adjustedGift.getId());
        }
	    validateAdjustedGift(adjustedGift, true);
	    adjustedGift = saveAuditAdjustedGift(adjustedGift);
        return adjustedGift;
    }

	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
	public AdjustedGift editAdjustedGift(AdjustedGift adjustedGift) throws BindException {
	    return editAdjustedGift(adjustedGift, false);
	}

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
    public AdjustedGift editAdjustedGift(AdjustedGift adjustedGift, boolean doValidateDistributionLines) throws BindException {
        if (logger.isTraceEnabled()) {
            logger.trace("editAdjustedGift: adjustedGiftId = " + adjustedGift.getId() + " doValidateDistributionLines = " + doValidateDistributionLines);
        }
        validateAdjustedGift(adjustedGift, doValidateDistributionLines);

        adjustedGift = saveAuditAdjustedGift(adjustedGift);
        return adjustedGift;
    }

    private PaymentHistory createPaymentHistoryForAdjustedGift(AdjustedGift adjustedGift) {
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setAmount(adjustedGift.getAdjustedAmount());
        paymentHistory.setCurrencyCode(adjustedGift.getCurrencyCode());
        paymentHistory.setAdjustedGiftId(adjustedGift.getId());
        paymentHistory.setConstituent(adjustedGift.getConstituent());
        paymentHistory.setPaymentHistoryType(PaymentHistoryType.ADJUSTED_GIFT);
        paymentHistory.setPaymentType(adjustedGift.getPaymentType());
        paymentHistory.setPaymentStatus(adjustedGift.getPaymentStatus());
        paymentHistory.setTransactionDate(adjustedGift.getAdjustedTransactionDate());
        paymentHistory.setTransactionId(StringConstants.EMPTY);
        paymentHistory.setDescription(getPaymentDescription(adjustedGift));
        return paymentHistory;
    }

    @Override
    public AdjustedGift readAdjustedGiftById(Long adjustedGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAdjustedGiftById: adjustedGiftId = " + adjustedGiftId);
        }
        AdjustedGift aAdjustedGift = adjustedGiftDao.readAdjustedGiftById(adjustedGiftId);
        aAdjustedGift.setCurrentTotalAdjustedAmount(findCurrentTotalAdjustedAmount(aAdjustedGift.getOriginalGiftId()));
        return aAdjustedGift;
    }

    @Override
    public List<AdjustedGift> readAdjustedGiftsByIds(final Set<Long> adjustedGiftIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAdjustedGiftsByIds: adjustedGiftIds = " + adjustedGiftIds);
        }
        return adjustedGiftDao.readAdjustedGiftsByIds(adjustedGiftIds);
    }

    @Override
    public List<AdjustedGift> readLimitedAdjustedGiftsByIds(Set<Long> adjustedGiftIds, SortInfo sortInfo, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readLimitedAdjustedGiftsByIds: adjustedGiftIds = " + adjustedGiftIds + " sortInfo = " + sortInfo);
        }
        return adjustedGiftDao.readLimitedAdjustedGiftsByIds(adjustedGiftIds, sortInfo.getSort(), sortInfo.getDir(), sortInfo.getStart(),
                sortInfo.getLimit(), locale);
    }
    
    @Override
    public List<AdjustedGift> readAdjustedGiftsForOriginalGiftId(Long originalGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAdjustedGiftsForOriginalGiftId: originalGiftId = " + originalGiftId);
        }
        return adjustedGiftDao.readAdjustedGiftsForOriginalGiftId(originalGiftId);
    }

    @Override
    public BigDecimal findCurrentTotalAdjustedAmount(Long originalGiftId, Long adjustedGiftIdToOmit) {
        if (logger.isTraceEnabled()) {
            logger.trace("findCurrentTotalAdjustedAmount: originalGiftId = " + originalGiftId + " adjustedGiftIdToOmit = " + adjustedGiftIdToOmit);
        }
        List<AdjustedGift> adjustedGifts = readAdjustedGiftsForOriginalGiftId(originalGiftId);

        if (adjustedGiftIdToOmit != null) {
            Iterator<AdjustedGift> iter = adjustedGifts.iterator();
            while (iter.hasNext()) {
                AdjustedGift adjustedGift = iter.next();
                if (adjustedGift.getId().equals(adjustedGiftIdToOmit)) {
                    iter.remove();
                }
            }
        }
        return findCurrentTotalAdjustedAmount(adjustedGifts);
    }

    @Override
    public BigDecimal findCurrentTotalAdjustedAmount(Long originalGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("findCurrentTotalAdjustedAmount: originalGiftId = " + originalGiftId);
        }
        return findCurrentTotalAdjustedAmount(readAdjustedGiftsForOriginalGiftId(originalGiftId));
    }

    @Override
    public BigDecimal findCurrentTotalPaidAdjustedAmount(Long originalGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("findCurrentTotalPaidAdjustedAmount: originalGiftId = " + originalGiftId);
        }
        return findCurrentTotalPaidAdjustedAmount(readAdjustedGiftsForOriginalGiftId(originalGiftId));
    }

    @Override
    public BigDecimal findCurrentTotalAdjustedAmount(List<AdjustedGift> adjustedGifts) {
        if (logger.isTraceEnabled()) {
            logger.trace("findCurrentTotalAdjustedAmount: ");
        }
        BigDecimal existingAmount = BigDecimal.ZERO;
        if (adjustedGifts != null) {
            for (AdjustedGift aAdjGift : adjustedGifts) {
                if (aAdjGift.getAdjustedAmount() != null) {
                    existingAmount = existingAmount.add(aAdjGift.getAdjustedAmount());
                }
            }
        }
        return existingAmount;
    }

    @Override
    public BigDecimal findCurrentTotalPaidAdjustedAmount(List<AdjustedGift> adjustedGifts) {
        if (logger.isTraceEnabled()) {
            logger.trace("findCurrentTotalAdjustedAmount: ");
        }
        BigDecimal existingAmount = BigDecimal.ZERO;
        if (adjustedGifts != null) {
            for (AdjustedGift aAdjGift : adjustedGifts) {
                if (aAdjGift.getAdjustedAmount() != null && "Paid".equals(aAdjGift.getAdjustedStatus()) ) {
                    existingAmount = existingAmount.add(aAdjGift.getAdjustedAmount());
                }
            }
        }
        return existingAmount;
    }

    @Override
    public boolean isAdjustedAmountEqualGiftAmount(Gift gift) {
        if (logger.isTraceEnabled()) {
            logger.trace("isAdjustedAmountEqualGiftAmount: gift.amount = " + gift.getAmount());
        }

        BigDecimal originalAmount = gift.getAmount();
        BigDecimal adjustedAmount = findCurrentTotalAdjustedAmount(gift.getAdjustedGifts());
        return originalAmount != null && adjustedAmount != null && originalAmount.add(adjustedAmount).floatValue() == 0;
    }

    @Override
    public boolean isAdjustedAmountEqualGiftAmount(AdjustedGift adjustedGift) {
        if (logger.isTraceEnabled()) {
            logger.trace("isAdjustedAmountEqualGiftAmount: adjustedGift.adjustedAmount = " + adjustedGift.getAdjustedAmount());
        }

        BigDecimal originalAmount = adjustedGift.getOriginalAmount();
        BigDecimal adjustedAmount = adjustedGift.getCurrentTotalAdjustedAmount();
        return originalAmount.add(adjustedAmount).floatValue() == 0;
    }

    @Override
    public Map<Long, Long> countAdjustedGiftsByOriginalGiftId(final List<Gift> gifts) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAdjustedGiftsForOriginalGiftId:");
        }
        final List<Long> giftIds = new ArrayList<Long>();
        if (gifts != null) {
            for (Gift gift : gifts) {
                giftIds.add(gift.getId());
            }
        }
        if (giftIds.isEmpty()) {
            return new HashMap<Long, Long>();
        }
        return adjustedGiftDao.countAdjustedGiftsByOriginalGiftId(giftIds);
    }

    @Override
    public List<AdjustedGift> readAllAdjustedGiftsByConstituentGiftId(Long constituentId, Long giftId, SortInfo sort, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllAdjustedGiftsByConstituentGiftId: constituentId = " + constituentId + " giftId = " + giftId + " sort = " + sort);
        }
        return adjustedGiftDao.readAllAdjustedGiftsByConstituentGiftId(constituentId, giftId, sort.getSort(), sort.getDir(), sort.getStart(),
                sort.getLimit(), locale);
    }

    @Override
    public int readCountByConstituentGiftId(Long constituentId, Long giftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentGiftId: constituentId = " + constituentId + " giftId = " + giftId);
        }
        return adjustedGiftDao.readCountByConstituentGiftId(constituentId, giftId);
    }

    @Override
    public BigDecimal readTotalAdjustedAmountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readTotalAdjustedAmountByConstituentId: constituentId = " + constituentId);
        }
        return adjustedGiftDao.readTotalAdjustedAmountByConstituentId(constituentId);
    }

    @Override
    public List<AdjustedGift> readAdjustedGiftsBySegmentationReportIds(final Set<Long> reportIds, SortInfo sort, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAdjustedGiftsBySegmentationReportIds: reportIds = " + reportIds + " sort = " + sort);
        }
        return adjustedGiftDao.readAdjustedGiftsBySegmentationReportIds(reportIds, sort.getSort(), sort.getDir(), sort.getStart(),
                sort.getLimit(), locale);
    }

    @Override
    public int readCountAdjustedGiftsBySegmentationReportIds(final Set<Long> reportIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountAdjustedGiftsBySegmentationReportIds: reportIds = " + reportIds);
        }
        return adjustedGiftDao.readCountAdjustedGiftsBySegmentationReportIds(reportIds);
    }

    @Override
    public List<AdjustedGift> readAllAdjustedGiftsBySegmentationReportIds(final Set<Long> reportIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllAdjustedGiftsBySegmentationReportIds: reportIds = " + reportIds);
        }
        return adjustedGiftDao.readAllAdjustedGiftsBySegmentationReportIds(reportIds);
    }

	@Override
	public Map<Long, Long> countAdjustedGiftDistroLinesByOriginalGiftId(
			List<Gift> gifts, String constituentReferenceCustomField) {
        if (logger.isTraceEnabled()) {
            logger.trace("countAdjustedGiftDistroLinesByOriginalGiftId");
        }
        final List<Long> giftIds = new ArrayList<Long>();
        if (gifts != null) {
            for (Gift gift : gifts) {
                giftIds.add(gift.getId());
            }
        }
        if (giftIds.isEmpty()) {
            return new HashMap<Long, Long>();
        }
        return adjustedGiftDao.countAdjustedGiftDistroLinesByOriginalGiftId(giftIds, constituentReferenceCustomField);
	}

	@Override
	public int readAdjustedGiftDistroLinesCountByConstituentGiftId(
			Long constituentId, String constituentReferenceCustomField,
			long giftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAdjustedGiftDistroLinesCountByConstituentGiftId: constituentId = " + constituentId + " giftId = " + giftId);
        }
        return adjustedGiftDao.readAdjustedGiftDistroLinesCountByConstituentGiftId(constituentId, giftId, constituentReferenceCustomField);
	}

	@Override
	public List<AdjustedGift> readAllAdjustedGiftDistroLinesByConstituentId(
			Long constituentId, String constituentReferenceCustomField,
			long giftId, SortInfo sort, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllAdjustedGiftDistroLinesByConstituentId: constituentId = " + constituentId + " sort = " + sort);
        }
        return adjustedGiftDao.readAllAdjustedGiftDistroLinesByConstituentGiftId(constituentId, giftId, constituentReferenceCustomField, sort.getSort(), sort.getDir(), sort.getStart(),
                sort.getLimit(), locale);
	}
}
