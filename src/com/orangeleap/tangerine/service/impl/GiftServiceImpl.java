package com.orangeleap.tangerine.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessorFactory;
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
import com.orangeleap.tangerine.dao.GiftDao;
import com.orangeleap.tangerine.dao.SiteDao;
import com.orangeleap.tangerine.domain.PaymentHistory;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.customization.EntityDefault;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.integration.NewGift;
import com.orangeleap.tangerine.service.ErrorLogService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PaymentHistoryService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.PaymentHistoryType;
import com.orangeleap.tangerine.util.RulesStack;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@Service("giftService")
@Transactional(propagation = Propagation.REQUIRED)
public class GiftServiceImpl extends AbstractPaymentService implements GiftService, ApplicationContextAware {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "paymentHistoryService")
    private PaymentHistoryService paymentHistoryService;

    @Resource(name = "pledgeService")
    private PledgeService pledgeService;

    @Resource(name = "recurringGiftService")
    private RecurringGiftService recurringGiftService;

    @Resource(name = "giftDAO")
    private GiftDao giftDao;

    @Resource(name = "siteDAO")
    private SiteDao siteDao;
    
    @Resource(name = "errorLogService")
    private ErrorLogService errorLogService;
    
    @Resource(name="giftEntityValidator")
    protected EntityValidator entityValidator;

    @Resource(name="codeValidator")
    protected CodeValidator codeValidator;
    
    @Resource(name="distributionLinesValidator")
    protected DistributionLinesValidator distributionLinesValidator;

    private ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    private final static String MAINTAIN_METHOD = "GiftServiceImpl.maintainGift";
    
    // Used for create only.
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
    public Gift maintainGift(Gift gift) throws BindException
    {

    	boolean reentrant = RulesStack.push(MAINTAIN_METHOD);
        try {
        	
            if (logger.isTraceEnabled()) {
                logger.trace("maintainGift: gift = " + gift);
            }
            
            
            if (gift.getFieldLabelMap() != null && !gift.isSuppressValidation()) {

    	        BindingResult br = new BeanPropertyBindingResult(gift, "gift");
    	        BindException errors = new BindException(br);
    	      
    	        codeValidator.validate(gift, errors);
    	        if (errors.getAllErrors().size() > 0) throw errors;
    	        distributionLinesValidator.validate(gift, errors);
    	        if (errors.getAllErrors().size() > 0) throw errors;
    	        
    	        entityValidator.validate(gift, errors);
    	        if (errors.getAllErrors().size() > 0) throw errors;
            }

            
            
	        maintainEntityChildren(gift, gift.getPerson());
	        setDefaultDates(gift);
	        gift = giftDao.maintainGift(gift);
	        pledgeService.updatePledgeForGift(gift);
	        recurringGiftService.updateRecurringGiftForGift(gift);
	        auditService.auditObject(gift, gift.getPerson());
	
	        //
	        // this needs to go last because we need the gift in the database
	        // in order for rules to work properly.
	        if (!reentrant) {
	        	routeGift(gift);
	        	paymentHistoryService.addPaymentHistory(createPaymentHistoryForGift(gift));
	        }
	        
	        return gift;
	        
        } finally {
        	RulesStack.pop(MAINTAIN_METHOD);
        }

    }
    
    private void setDefaultDates(Gift gift) {
        if (gift.getId() == null) {
            Calendar transCal = Calendar.getInstance();
            gift.setTransactionDate(transCal.getTime());
            if (gift.getPostmarkDate() == null) {
                Calendar postCal = new GregorianCalendar(transCal.get(Calendar.YEAR), transCal.get(Calendar.MONTH), transCal.get(Calendar.DAY_OF_MONTH));
                gift.setPostmarkDate(postCal.getTime());
            }
        }
    }

    private final static String EDIT_METHOD = "GiftServiceImpl.editGift";
    
    // Used for update only.
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Gift editGift(Gift gift) {

    	boolean reentrant = RulesStack.push(EDIT_METHOD);
        try {
        	
	        if (logger.isTraceEnabled()) {
	            logger.trace("editGift: giftId = " + gift.getId());
	        }
	        
	        maintainEntityChildren(gift, gift.getPerson());
	        gift = giftDao.maintainGift(gift);
            pledgeService.updatePledgeForGift(gift);
            recurringGiftService.updateRecurringGiftForGift(gift);
	        if (!reentrant) {
	        	routeGift(gift);
	        }
	        auditService.auditObject(gift, gift.getPerson());
	
	        return gift;
        
        } finally {
        	RulesStack.pop(EDIT_METHOD);
        }

    }
    
    private final static String ROUTE_METHOD = "GiftServiceImpl.routeGift";
    
    private void routeGift(Gift gift) {
    
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

        } finally {
        	RulesStack.pop(ROUTE_METHOD);
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
    		logger.error("Unable to write to rules error log file: "+message);
    	}
    }
    
    private PaymentHistory createPaymentHistoryForGift(Gift gift) {
    	PaymentHistory paymentHistory = new PaymentHistory();
    	paymentHistory.setAmount(gift.getAmount());
    	paymentHistory.setCurrencyCode(gift.getCurrencyCode());
    	paymentHistory.setGiftId(gift.getId());
    	paymentHistory.setPerson(gift.getPerson());
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
    public Gift readGiftByIdCreateIfNull(Person constituent, String giftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftByIdCreateIfNull: giftId = " + giftId +  
                    " constituentId = " + (constituent == null ? null : constituent.getId()));
        }
        Gift gift = null;
        if (giftId == null) {
            if (constituent != null) {
                gift = this.createDefaultGift(constituent);
            }
        }
        else {
            gift = this.readGiftById(Long.valueOf(giftId));
        }
        return gift;
    }

    @Override
    public List<Gift> readMonetaryGifts(Person constituent) {
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
    public PaginatedResult readPaginatedGiftList(Long constituentId, SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaginatedMonetaryGifts: constituentId = " + constituentId);
        }
        return giftDao.readPaginatedGiftListByConstituentId(constituentId, sortinfo);
    }

    @Override
    public List<Gift> searchGifts(Map<String, Object> params) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGifts: params = " + params);
        }
        return giftDao.searchGifts(params);
    }

    @Override
    public Gift createDefaultGift(Person constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("createDefaultGift: constituent = " + (constituent == null ? null : constituent.getId()));
        }
        // get initial gift with built-in defaults
        Gift gift = new Gift();
        BeanWrapper giftBeanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(gift);

        List<EntityDefault> entityDefaults = siteDao.readEntityDefaults(Arrays.asList(new EntityType[] { EntityType.gift }));
        for (EntityDefault ed : entityDefaults) {
            giftBeanWrapper.setPropertyValue(ed.getEntityFieldName(), ed.getDefaultValue());
        }
        gift.setPerson(constituent);
        List<DistributionLine> lines = new ArrayList<DistributionLine>(1);
        DistributionLine line = new DistributionLine(constituent);
        line.setDefaults();
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
        	g.setAddress(g.getSelectedAddress());
        	g.setPaymentSource(g.getSelectedPaymentSource());
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
	public List<DistributionLine> combineGiftCommitmentDistributionLines(List<DistributionLine> giftDistributionLines, List<DistributionLine> commitmentLines, BigDecimal amount, 
	        int numCommitments, Person constituent, boolean isPledge) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("combineGiftPledgeDistributionLines: amount = " + amount + " numCommitments = " + numCommitments + " isPledge = " + isPledge);
	    }
        List<DistributionLine> returnLines = new ArrayList<DistributionLine>();
        
        giftDistributionLines = removeDefaultDistributionLine(giftDistributionLines, amount, constituent);
        
        String associatedIdName = isPledge ? StringConstants.ASSOCIATED_PLEDGE_ID : StringConstants.ASSOCIATED_RECURRING_GIFT_ID;
        
        if ((commitmentLines == null || commitmentLines.isEmpty()) && giftDistributionLines != null) {
            // NO pledge/recurringGift distribution lines associated with this gift; only add gift distribution lines that have no pledge/recurringGift associations
            for (DistributionLine giftLine : giftDistributionLines) {
                if (giftLine != null && giftLine.isFieldEntered()) {
                    if (org.springframework.util.StringUtils.hasText(giftLine.getCustomFieldValue(associatedIdName)) == false) {
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
                    if (org.springframework.util.StringUtils.hasText(associatedCommitmentId) == false) {
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
	 * Check if a default distribution line was created; remove if necessary
	 * @param giftDistributionLines
	 */
	public List<DistributionLine> removeDefaultDistributionLine(List<DistributionLine> giftDistributionLines, BigDecimal amount, Person constituent) {
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
            DistributionLine defaultLine = new DistributionLine(constituent);
            defaultLine.setDefaults();
            
            if (amount.equals(enteredLine.getAmount()) && new BigDecimal("100").equals(enteredLine.getPercentage())) {
                if (org.springframework.util.StringUtils.hasText(enteredLine.getProjectCode()) || org.springframework.util.StringUtils.hasText(enteredLine.getMotivationCode()) || 
                        org.springframework.util.StringUtils.hasText(enteredLine.getOther_motivationCode())) {
                    // do nothing
                }
                else {
                    boolean isSame = true;
                    Set<String> keys = enteredLine.getCustomFieldMap().keySet();
                    for (String aKey : keys) {
                        // Empty string and null are considered equivalent values for custom fields
                        if ((enteredLine.getCustomFieldValue(aKey) == null || StringConstants.EMPTY.equals(enteredLine.getCustomFieldValue(aKey))) 
                                && (defaultLine.getCustomFieldValue(aKey) == null || StringConstants.EMPTY.equals(defaultLine.getCustomFieldValue(aKey)))) {
                            // do nothing
                        }
                        else if (enteredLine.getCustomFieldValue(aKey) != null && enteredLine.getCustomFieldValue(aKey).equals(defaultLine.getCustomFieldValue(aKey))) {
                            // do nothing
                        }
                        else {
                            isSame = false;
                            break;
                        }
                    }
                    if (isSame) {
                        return new ArrayList<DistributionLine>();
                    }
                }
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
        for (DistributionLine line : gift.getMutableDistributionLines()) {
            if (line != null) {
                String associatedPledgeId = line.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID);
                if (NumberUtils.isDigits(associatedPledgeId)) {
                    Long thisPledgeId = Long.parseLong(associatedPledgeId);
                    linePledgeIds.add(thisPledgeId);
                    if (gift.getAssociatedPledgeIds() == null || gift.getAssociatedPledgeIds().contains(thisPledgeId) == false) {
                        gift.addAssociatedPledgeId(thisPledgeId);
                    }
                }
            }
        }
        if (gift.getAssociatedPledgeIds() != null) {
            for (Iterator<Long> iter = gift.getAssociatedPledgeIds().iterator(); iter.hasNext();) {
                Long id = iter.next();
                if (linePledgeIds.contains(id) == false) {
                    iter.remove();
                }
            }
        }
    }
    
    @Override
    public void checkAssociatedRecurringGiftIds(Gift gift) {
        Set<Long> lineRecurringGiftIds = new HashSet<Long>();
        for (DistributionLine line : gift.getMutableDistributionLines()) {
            if (line != null) {
                String associatedRecurringGiftId = line.getCustomFieldValue(StringConstants.ASSOCIATED_RECURRING_GIFT_ID);
                if (NumberUtils.isDigits(associatedRecurringGiftId)) {
                    Long thisRecurringGiftId = Long.parseLong(associatedRecurringGiftId);
                    lineRecurringGiftIds.add(thisRecurringGiftId);
                    if (gift.getAssociatedRecurringGiftIds() == null || gift.getAssociatedRecurringGiftIds().contains(thisRecurringGiftId) == false) {
                        gift.addAssociatedRecurringGiftId(thisRecurringGiftId);
                    }
                }
            }
        }
        if (gift.getAssociatedRecurringGiftIds() != null) {
            for (Iterator<Long> iter = gift.getAssociatedRecurringGiftIds().iterator(); iter.hasNext();) {
                Long id = iter.next();
                if (lineRecurringGiftIds.contains(id) == false) {
                    iter.remove();
                }
            }
        }
    }
}
