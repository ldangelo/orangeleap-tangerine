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
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
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

import com.orangeleap.tangerine.controller.validator.CodeValidator;
import com.orangeleap.tangerine.controller.validator.DistributionLinesValidator;
import com.orangeleap.tangerine.controller.validator.EntityValidator;
import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.dao.GiftDao;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.PaymentHistory;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.integration.NewGift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.service.ErrorLogService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.OrangeleapJmxNotificationBean;
import com.orangeleap.tangerine.service.PaymentHistoryService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.service.customization.FieldService;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.service.rollup.RollupHelperService;
import com.orangeleap.tangerine.type.PaymentHistoryType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.RulesStack;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@Service("giftService")
@Transactional(propagation = Propagation.REQUIRED)
public class GiftServiceImpl extends AbstractPaymentService implements GiftService, ApplicationContextAware {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "paymentHistoryService")
    private PaymentHistoryService paymentHistoryService;

    @Resource(name = "pledgeService")
    private PledgeService pledgeService;

    @Resource(name = "recurringGiftService")
    private RecurringGiftService recurringGiftService;

    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

	@Resource(name = "fieldService")
	private FieldService fieldService;

	@Resource(name = "rollupHelperService")
	private RollupHelperService rollupHelperService;

	@Resource(name = "pageCustomizationService")
	private PageCustomizationService pageCustomizationService;

    @Resource(name = "giftDAO")
    private GiftDao giftDao;

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;

    @Resource(name = "errorLogService")
    private ErrorLogService errorLogService;

    @Resource(name = "giftEntityValidator")
    protected EntityValidator entityValidator;

    @Resource(name = "codeValidator")
    protected CodeValidator codeValidator;

    @Resource(name = "distributionLinesValidator")
    protected DistributionLinesValidator distributionLinesValidator;

    @Resource(name = "OrangeleapJmxNotificationBean")
    protected OrangeleapJmxNotificationBean orangeleapJmxNotificationBean;

    private ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    private final static String MAINTAIN_METHOD = "GiftServiceImpl.maintainGift";

    // Used for create only.
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
    public Gift maintainGift(Gift gift) throws BindException {
    	
        boolean reentrant = RulesStack.push(MAINTAIN_METHOD);
        try {
            if (logger.isTraceEnabled()) {
                logger.trace("maintainGift: gift = " + gift);
            }

			validateGift(gift, true);

            long t0 = System.currentTimeMillis(); // start counting after validated

            setDefaultDates(gift);
            gift = saveAuditGift(gift);

            
            // this needs to go last because we need the gift in the database
            // in order for rules to work properly.
            if (!reentrant) {
                
            	routeGift(gift);
                paymentHistoryService.addPaymentHistory(createPaymentHistoryForGift(gift));

                long t1 = System.currentTimeMillis();
                orangeleapJmxNotificationBean.incrementStat(this.getSiteName(), "maintainGiftTime", (t1-t0));
                orangeleapJmxNotificationBean.incrementStatCount(this.getSiteName(), "maintainGiftCount");
            	
            }

            return gift;
        }
        finally {
            RulesStack.pop(MAINTAIN_METHOD);
        }
    }

    private void validateGift(Gift gift, boolean doValidateDistributionLines)
            throws BindException {
	    if (gift.getFieldLabelMap() != null && !gift.isSuppressValidation()) {
			BindingResult br = new BeanPropertyBindingResult(gift, StringConstants.GIFT);
			BindException errors = new BindException(br);

			entityValidator.validate(gift, errors);
			codeValidator.validate(gift, errors);
			if (doValidateDistributionLines) {
				distributionLinesValidator.validate(gift, errors);
			}

			if (errors.hasErrors()) {
				throw errors;
			}
	    }
    }

    private Gift saveAuditGift(Gift gift) throws BindException {
        // set the scale to 2 to make sure numbers like 5.3 will be set to 5.30 and 55.222 to 55.22
        if (gift.getAmount() != null) {
            gift.setAmount(gift.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
        }

        maintainEntityChildren(gift, gift.getConstituent());
        Gift originalGift = null;
        if (!gift.isNew()) {
            originalGift = giftDao.readGiftById(gift.getId());
        } 
        
        gift = giftDao.maintainGift(gift);
        updateAdjustedAmount(gift);
        pledgeService.updatePledgeForGift(originalGift, gift);
        recurringGiftService.updateRecurringGiftForGift(originalGift, gift);
        auditService.auditObject(gift, gift.getConstituent());
        rollupHelperService.updateRollupsForConstituentRollupValueSource(gift);
        return gift;
    }
    
    @Override
    public void updateAdjustedAmount(Gift gift) {
    	BigDecimal giftamt = gift.getAmount();
    	if (giftamt == null) giftamt = new BigDecimal("0.00");
    	BigDecimal adjamt = adjustedGiftService.findCurrentTotalPaidAdjustedAmount(gift.getId());
    	
    	BigDecimal total = giftamt.add(adjamt);
    	
    	gift.setAdjustedAmount(total);
    	
    	if (gift.getDistributionLines() != null && gift.getDistributionLines().size() > 0) {
        	BigDecimal deductibleAmount = null;
	    	for (DistributionLine dl : gift.getDistributionLines()) {
	    		if (dl != null) {
	    			String value = dl.getCustomFieldValue("taxDeductible"); // null if custom fields not loaded
	    			if (value != null && "true".equals(value)) {
	    				if (deductibleAmount == null) deductibleAmount = BigDecimal.ZERO;
	    				deductibleAmount = deductibleAmount.add(dl.getAmount());
	    			}
	    		}
	    	}
    		if (deductibleAmount != null) gift.setDeductibleAmount(deductibleAmount);
    	}
    	
    	giftDao.maintainGift(gift);
    }
    
    private void setDefaultDates(Gift gift) {
        if (gift.getId() == null) {
            Calendar transCal = Calendar.getInstance();
            gift.setTransactionDate(transCal.getTime());
        }
    }

    private final static String EDIT_METHOD = "GiftServiceImpl.editGift";

    // Used for update only.
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
    public Gift editGift(Gift gift) throws BindException {
        return editGift(gift, false);
    }

    // Used for update only.
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
    public Gift editGift(Gift gift, boolean doValidateDistributionLines) throws BindException {
        boolean reentrant = RulesStack.push(EDIT_METHOD);
        try {
            if (logger.isTraceEnabled()) {
                logger.trace("editGift: giftId = " + gift.getId());
            }

			validateGift(gift, doValidateDistributionLines);

            gift = saveAuditGift(gift);

            if (!reentrant) {
                routeGift(gift);
            }

            return gift;
        }
        finally {
            RulesStack.pop(EDIT_METHOD);
        }
    }


    private final static String REPROCESS_METHOD = "GiftServiceImpl.reprocessGift";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
    public Gift reprocessGift(Gift gift) throws BindException {
        if (logger.isTraceEnabled()) {
            logger.trace("reprocessGift: giftId = " + gift.getId());
        }
        boolean reentrant = RulesStack.push(REPROCESS_METHOD);
        try {
            validateGift(gift, true);
            gift.clearPaymentStatusInfo();
            gift = saveAuditGift(gift);

            if (!reentrant) {
                routeGift(gift);
                // paymentHistoryService.addPaymentHistory(createPaymentHistoryForGift(gift));  This is already done in Payment Processing
            }
            return gift;
        }
        finally {
            RulesStack.pop(REPROCESS_METHOD);
        }
    }

    private final static String ROUTE_METHOD = "GiftServiceImpl.routeGift";

    private void routeGift(Gift gift) {

    	boolean wasRollbackOnly = OLLogger.isCurrentTransactionMarkedRollbackOnly(context);
    	
        RulesStack.push(ROUTE_METHOD);
        try {

            try {
                NewGift newGift = (NewGift) context.getBean("newGift");
                newGift.routeGift(gift);
            }
            catch (Exception ex) {
                logger.error("RULES_FAILURE: " + ex.getMessage(), ex);
                writeRulesFailureLog(ex.getMessage() + "\r\n" + gift);
            }

        }
        finally {
            RulesStack.pop(ROUTE_METHOD);
        }
        
    	boolean isRollbackOnly = OLLogger.isCurrentTransactionMarkedRollbackOnly(context);
    	
    	if (!wasRollbackOnly && isRollbackOnly) {
    		logger.error("Rules processing caused transaction rollback for gift "+gift.getId());
    	}

    }

    private synchronized void writeRulesFailureLog(String message) {
        try {

            errorLogService.addErrorMessage(message, "gift.rules");

//    		FileWriter out = new FileWriter("rules-errors.log");
//    		try {
//    			out.write(message);
//    		} finally {
//    			out.close();
//    		}

        } catch (Exception e) {
            logger.error("Unable to write to rules error log file: " + message);
        }
    }

    private PaymentHistory createPaymentHistoryForGift(Gift gift) {
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setAmount(gift.getAmount());
        paymentHistory.setCurrencyCode(gift.getCurrencyCode());
        paymentHistory.setGiftId(gift.getId());
        paymentHistory.setConstituent(gift.getConstituent());
        paymentHistory.setPaymentHistoryType(PaymentHistoryType.GIFT);
        paymentHistory.setPaymentType(gift.getPaymentType());
        paymentHistory.setTransactionDate(gift.getTransactionDate());
        paymentHistory.setTransactionId(StringConstants.EMPTY);
        paymentHistory.setPaymentStatus(gift.getPaymentStatus());
        String desc = getPaymentDescription(gift);
        paymentHistory.setDescription(desc);
        return paymentHistory;
    }

    @Override
    public Gift readGiftById(Long giftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftById: giftId = " + giftId);
        }
        return giftDao.readGiftById(giftId);
    }

    @Override
    public List<Gift> readGiftsByIds(Set<Long> giftIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftsByIds: giftIds = " + giftIds);
        }
        return giftDao.readGiftsByIds(giftIds);
    }

    @Override
    public List<Gift> readLimitedGiftsByIds(Set<Long> giftIds, SortInfo sortInfo, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readLimitedGiftsByIds: giftIds = " + giftIds + " sortInfo = " + sortInfo);
        }
        return giftDao.readLimitedGiftsByIds(giftIds, sortInfo.getSort(), sortInfo.getDir(), sortInfo.getStart(),
                sortInfo.getLimit(), locale);
    }

    @Override
    public Gift readGiftByIdCreateIfNull(Constituent constituent, String giftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftByIdCreateIfNull: giftId = " + giftId +
                    " constituentId = " + (constituent == null ? null : constituent.getId()));
        }
        Gift gift = null;
        if (giftId == null) {
            if (constituent != null) {
                gift = this.createDefaultGift(constituent);
            }
        } else {
            gift = this.readGiftById(Long.valueOf(giftId));
        }
        return gift;
    }

    @Override
    public List<Gift> readMonetaryGifts(Constituent constituent) {
        return readMonetaryGifts(constituent.getId());
    }

    @Override
    public List<Gift> readMonetaryGifts(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readMonetaryGifts: constituentId = " + constituentId);
        }
        return giftDao.readMonetaryGiftsByConstituentId(constituentId);
    }

    @Override
    public Map<String, Object> readNumGiftsTotalAmount(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readNumGiftsTotalAmount: constituentId = " + constituentId);
        }
        List<Gift> gifts = readMonetaryGifts(constituentId);
        int giftCount = 0;
        BigDecimal amount = BigDecimal.ZERO;
        if (gifts != null) {
            for (Gift gift : gifts) {
                if (Gift.STATUS_PAID.equals(gift.getGiftStatus()) && gift.getAmount() != null) {
                    giftCount++;
                    amount = amount.add(gift.getAmount());
                }
            }
        }
        BigDecimal adjustedAmt = adjustedGiftService.readTotalAdjustedAmountByConstituentId(constituentId);
        if (adjustedAmt != null) {
            amount = amount.add(adjustedAmt);
        }

        Map<String, Object> returnMap = new HashMap<String, Object>(2);
        returnMap.put("giftCount", giftCount);
        returnMap.put(StringConstants.AMOUNT, amount);
        return returnMap;
    }

    @Override
    public PaginatedResult readPaginatedGiftList(Long constituentId, SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaginatedMonetaryGifts: constituentId = " + constituentId);
        }
        return giftDao.readPaginatedGiftListByConstituentId(constituentId, sortinfo);
    }

    @Override
    public List<Gift> searchGifts(Map<String, Object> params) {
        if (logger.isTraceEnabled()) {
            logger.trace("searchGifts: params = " + params);
        }
        return giftDao.searchGifts(params);
    }

    @Override
    public List<Gift> searchGifts(Map<String, Object> params, SortInfo sort, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("searchGifts: params = " + params + " sort = " + sort);
        }
        return giftDao.searchGifts(params, sort.getSort(), sort.getDir(), sort.getStart(),
                sort.getLimit(), locale);
    }

    @Override
    public Gift createDefaultGift(Constituent constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("createDefaultGift: constituent = " + (constituent == null ? null : constituent.getId()));
        }
        Gift gift = new Gift();
        gift.setConstituent(constituent);
        List<DistributionLine> lines = new ArrayList<DistributionLine>(1);
        DistributionLine line = new DistributionLine(constituent);
        line.setGiftId(gift.getId());
        lines.add(line);
        gift.setDistributionLines(lines);

        // TODO: consider caching techniques for the default Gift
        return gift;
    }

    @Override
    public double analyzeMajorDonor(Long constituentId, Date beginDate, Date currentDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("analyzeMajorDonor: constituentId = " + constituentId + " beginDate = " + beginDate + " currentDate = " + currentDate);
        }
        return giftDao.analyzeMajorDonor(constituentId, beginDate, currentDate);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Gift> readMonetaryGiftsByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readMonetaryGiftsByConstituentId: constituentId = " + constituentId);
        }
        return giftDao.readMonetaryGiftsByConstituentId(constituentId);
    }

    @Override
    public List<Gift> readAllGiftsByDateRange(Date fromDate, Date toDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllGiftsByDateRange:");
        }
        List<Gift> list = giftDao.readAllGiftsByDateRange(fromDate, toDate);
        // Need to ensure address and payment source are populated for export.
        for (int i = 0; i < list.size(); i++) {
            Gift g = readGiftById(list.get(i).getId());
            g.setAddress(g.getAddress());
            g.setPaymentSource(g.getPaymentSource());
            list.set(i, g);
        }
        return list;
    }

    @Override
    public List<Gift> readAllGiftsBySiteName() {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllGiftsBySiteName:");
        }
        return giftDao.readAllGiftsBySite();
    }

    @Override
    public List<DistributionLine> combineGiftCommitmentDistributionLines(List<DistributionLine> giftDistributionLines, List<DistributionLine> commitmentLines,
                                                                         DistributionLine defaultDistributionLine, BigDecimal amount,
                                                                         int numCommitments, Constituent constituent, boolean isPledge) {
        if (logger.isTraceEnabled()) {
            logger.trace("combineGiftPledgeDistributionLines: amount = " + amount + " numCommitments = " + numCommitments + " isPledge = " + isPledge);
        }
        List<DistributionLine> returnLines = new ArrayList<DistributionLine>();

        giftDistributionLines = removeDefaultDistributionLine(giftDistributionLines, defaultDistributionLine, amount);

        String associatedIdName = isPledge ? StringConstants.ASSOCIATED_PLEDGE_ID : StringConstants.ASSOCIATED_RECURRING_GIFT_ID;

        if ((commitmentLines == null || commitmentLines.isEmpty()) && giftDistributionLines != null) {
            // NO pledge/recurringGift distribution lines associated with this gift; only add gift distribution lines that have no pledge/recurringGift associations
            for (DistributionLine giftLine : giftDistributionLines) {
                if (giftLine != null && giftLine.isFieldEntered()) {
                    if (!org.springframework.util.StringUtils.hasText(giftLine.getCustomFieldValue(associatedIdName))) {
                        returnLines.add(giftLine);
                    }
                }
            }
        }
        else if ((giftDistributionLines == null || giftDistributionLines.isEmpty()) && commitmentLines != null) {
            initCommitmentDistributionLine(commitmentLines, returnLines, numCommitments, amount, isPledge);
        }
        else if (commitmentLines != null && giftDistributionLines != null) {
            for (DistributionLine aLine : giftDistributionLines) {
                if (aLine != null && aLine.isFieldEntered()) {
                    String associatedCommitmentId = aLine.getCustomFieldValue(associatedIdName);
                    if (!org.springframework.util.StringUtils.hasText(associatedCommitmentId)) {
                        returnLines.add(aLine); // No associated pledge/recurringGiftIds, so just copy the line
                    }
                    else {
                        for (Iterator<DistributionLine> commitmentLineIter = commitmentLines.iterator(); commitmentLineIter.hasNext();) {
                            DistributionLine commitmentLine = commitmentLineIter.next();
                            Long associatedCommitmentIdLong = Long.parseLong(associatedCommitmentId);

                            Long idToCheck = isPledge ? commitmentLine.getPledgeId() : commitmentLine.getRecurringGiftId();

                            if (associatedCommitmentIdLong.equals(idToCheck)) {
                                commitmentLineIter.remove();
                                returnLines.add(aLine); // Has an existing pledge/recurringGiftId, so copy the line; gift distribution lines with an associated pledge/recurringGiftId that is not in the list of pledge/recurringGiftLines will not be copied
                                break;
                            }
                        }
                    }
                }
            }
            initCommitmentDistributionLine(commitmentLines, returnLines, numCommitments, amount, isPledge);
        }
        return returnLines;
    }

    /**
     * Check if the distribution line associated with the gift should be removed if it has only the default values
     * @param giftDistributionLines
     * @param defaultLine
     * @param amount
     */
    private List<DistributionLine> removeDefaultDistributionLine(List<DistributionLine> giftDistributionLines,
                                                                 DistributionLine defaultLine, BigDecimal amount) {
        if (logger.isTraceEnabled()) {
            logger.trace("removeDefaultDistributionLine: amount = " + amount);
        }
        int count = 0;
        DistributionLine enteredLine = null;
        if (giftDistributionLines != null) {
            for (DistributionLine aLine : giftDistributionLines) {
                if (aLine != null) {
                    enteredLine = aLine;
                    count++;
                }
            }
        }
        /* If only 1 line is entered, check if it is the default */
        if (count == 1) {
            if (enteredLine.usesDefaultValues(amount, defaultLine)) {
                giftDistributionLines = new ArrayList<DistributionLine>();
            }
        }
        return giftDistributionLines;
    }

    private void initCommitmentDistributionLine(List<DistributionLine> commitmentLines, List<DistributionLine> returnLines, int numCommitments, BigDecimal amount, boolean isPledge) {
        BigDecimal splitCommitmentAmount = BigDecimal.ZERO;
        if (numCommitments > 0) {
            splitCommitmentAmount = amount.divide(new BigDecimal(numCommitments), 10, BigDecimal.ROUND_HALF_EVEN);
        }
        for (DistributionLine cLine : commitmentLines) {
            if (isPledge) {
                cLine.addCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID, cLine.getPledgeId().toString());
                cLine.setPledgeId(null);
            }
            else {
                cLine.addCustomFieldValue(StringConstants.ASSOCIATED_RECURRING_GIFT_ID, cLine.getRecurringGiftId().toString());
                cLine.setRecurringGiftId(null);
            }
            cLine.setAmount(cLine.getPercentage().multiply(splitCommitmentAmount).divide(new BigDecimal("100"), 10, BigDecimal.ROUND_HALF_EVEN).setScale(2, BigDecimal.ROUND_HALF_EVEN));

            // find the new percentage (line percentage / numCommitments)
            cLine.setPercentage(cLine.getPercentage().divide(new BigDecimal(numCommitments), 2, BigDecimal.ROUND_HALF_EVEN));
        }
        returnLines.addAll(commitmentLines); // Add the remaining pledge/recurringGift lines; these are the pledge/recurringGift distribution lines not already assigned to the gift
    }

    @Override
    public void checkAssociatedPledgeIds(Gift gift) {
        Set<Long> linePledgeIds = new HashSet<Long>();
        for (DistributionLine line : gift.getDistributionLines()) {
            if (line != null) {
                String associatedPledgeId = line.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID);
                if (NumberUtils.isDigits(associatedPledgeId)) {
                    Long thisPledgeId = Long.parseLong(associatedPledgeId);
                    linePledgeIds.add(thisPledgeId);
                    if (gift.getAssociatedPledgeIds() == null || !gift.getAssociatedPledgeIds().contains(thisPledgeId)) {
                        gift.addAssociatedPledgeId(thisPledgeId);
                    }
                }
            }
        }
        if (gift.getAssociatedPledgeIds() != null) {
            for (Iterator<Long> iter = gift.getAssociatedPledgeIds().iterator(); iter.hasNext();) {
                Long id = iter.next();
                if (!linePledgeIds.contains(id)) {
                    iter.remove();
                }
            }
        }
    }

    @Override
    public void checkAssociatedRecurringGiftIds(Gift gift) {
        Set<Long> lineRecurringGiftIds = new HashSet<Long>();
        for (DistributionLine line : gift.getDistributionLines()) {
            if (line != null) {
                String associatedRecurringGiftId = line.getCustomFieldValue(StringConstants.ASSOCIATED_RECURRING_GIFT_ID);
                if (NumberUtils.isDigits(associatedRecurringGiftId)) {
                    Long thisRecurringGiftId = Long.parseLong(associatedRecurringGiftId);
                    lineRecurringGiftIds.add(thisRecurringGiftId);
                    if (gift.getAssociatedRecurringGiftIds() == null || !gift.getAssociatedRecurringGiftIds().contains(thisRecurringGiftId)) {
                        gift.addAssociatedRecurringGiftId(thisRecurringGiftId);
                    }
                }
            }
        }
        if (gift.getAssociatedRecurringGiftIds() != null) {
            for (Iterator<Long> iter = gift.getAssociatedRecurringGiftIds().iterator(); iter.hasNext();) {
                Long id = iter.next();
                if (!lineRecurringGiftIds.contains(id)) {
                    iter.remove();
                }
            }
        }
    }

    @Override
    public List<Gift> readGiftsBySegmentationReportIds(final Set<Long> reportIds, SortInfo sort, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftsBySegmentationReportIds: reportIds = " + reportIds + " sort = " + sort);
        }
        return giftDao.readGiftsBySegmentationReportIds(reportIds, sort.getSort(), sort.getDir(), sort.getStart(),
                sort.getLimit(), locale);
    }

    @Override
    public int readCountGiftsBySegmentationReportIds(final Set<Long> reportIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountGiftsBySegmentationReportIds: reportIds = " + reportIds);
        }
        return giftDao.readCountGiftsBySegmentationReportIds(reportIds);
    }

    @Override
    public List<Gift> readAllGiftsBySegmentationReportIds(final Set<Long> reportIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllGiftsBySegmentationReportIds: reportIds = " + reportIds);
        }
        return giftDao.readAllGiftsBySegmentationReportIds(reportIds);
    }

    @Override
    public List<Gift> readAllGiftsByConstituentId(Long constituentId, SortInfo sort, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllGiftsByConstituentId: constituentId = " + constituentId + " sort = " + sort);
        }
        return giftDao.readAllGiftsByConstituentId(constituentId, sort.getSort(), sort.getDir(), sort.getStart(),
                sort.getLimit(), locale);
    }

    @Override
    public List<Gift> readGiftDistroLinesByConstituentId(Long constituentId, String constituentReferenceCustomField, SortInfo sort, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftDistroLinesByConstituentId: constituentId = " + constituentId + " sort = " + sort);
        }
        return giftDao.readGiftDistroLinesByConstituentId(constituentId, constituentReferenceCustomField, sort.getSort(), sort.getDir(), sort.getStart(),
                sort.getLimit(), locale);
    }

    @Override
    public int readCountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentId: constituentId = " + constituentId);
        }
        return giftDao.readCountByConstituentId(constituentId);
    }

    @Override
    public int readGiftDistroLinesCountByConstituentId(Long constituentId, String constituentReferenceCustomField) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftDistroLinesCountByConstituentId: constituentId = " + constituentId);
        }
        return giftDao.readGiftDistroLinesCountByConstituentId(constituentId, constituentReferenceCustomField);
    }

}
