package com.orangeleap.tangerine.service.impl;

import java.io.FileWriter;
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

import org.apache.commons.lang.StringUtils;
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

import com.orangeleap.tangerine.dao.GiftDao;
import com.orangeleap.tangerine.dao.SiteDao;
import com.orangeleap.tangerine.domain.PaymentHistory;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.domain.customization.EntityDefault;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.integration.NewGift;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PaymentHistoryService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.type.GiftEntryType;
import com.orangeleap.tangerine.type.GiftType;
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

    @Resource(name = "recurringGiftService")
    private RecurringGiftService recurringGiftService;

    @Resource(name = "pledgeService")
    private PledgeService pledgeService;

    @Resource(name = "giftDAO")
    private GiftDao giftDao;

    @Resource(name = "siteDAO")
    private SiteDao siteDao;

    private ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    private final static String MAINTAIN_METHOD = "GiftServiceImpl.maintainGift";
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Gift maintainGift(Gift gift) {

    	boolean reentrant = RulesStack.push(MAINTAIN_METHOD);
        try {
        	
            if (logger.isTraceEnabled()) {
                logger.trace("maintainGift: gift = " + gift);
            }
            
	        maintainEntityChildren(gift, gift.getPerson());
	        setDefaultDates(gift);
	        gift = giftDao.maintainGift(gift);
	        if (!reentrant) {
	        	paymentHistoryService.addPaymentHistory(createPaymentHistoryForGift(gift));
	        }
	        auditService.auditObject(gift, gift.getPerson());
	
	        //
	        // this needs to go last because we need the gift in the database
	        // in order for rules to work properly.
	        if (!reentrant) {
	        	routeGift(gift);
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
    		FileWriter out = new FileWriter("rules-errors.log");
    		try {
    			out.write(message);
    		} finally {
    			out.close();
    		}
    	} catch (Exception e) {
    		logger.error("Unable to write to rules error log file: "+message);
    	}
    }
    
    private PaymentHistory createPaymentHistoryForGift(Gift gift) {
    	PaymentHistory paymentHistory = new PaymentHistory();
    	paymentHistory.setAmount(gift.getAmount());
    	paymentHistory.setCurrencyCode(gift.getCurrencyCode());
    	paymentHistory.setGift(gift);
    	paymentHistory.setPerson(gift.getPerson());
    	paymentHistory.setPaymentHistoryType(PaymentHistoryType.GIFT);
    	paymentHistory.setPaymentType(gift.getPaymentType());
    	paymentHistory.setTransactionDate(gift.getTransactionDate());
    	paymentHistory.setTransactionId("");
    	String desc = getGiftDescription(gift);
    	paymentHistory.setDescription(desc);
    	return paymentHistory;
    }
    
    private String getGiftDescription(Gift gift) {
    	StringBuilder sb = new StringBuilder();

    	// TODO: localize
    	if (PaymentSource.ACH.equals(gift.getPaymentType())) {
    	    sb.append("ACH Number: ").append(gift.getSelectedPaymentSource().getAchAccountNumberDisplay());
    	}
    	if (PaymentSource.CREDIT_CARD.equals(gift.getPaymentType())) {
    		sb.append("Credit Card Number: ").append(gift.getSelectedPaymentSource().getCreditCardType()).append(" ").append(gift.getSelectedPaymentSource().getCreditCardNumberDisplay());
    		sb.append(" ");
    		sb.append(gift.getSelectedPaymentSource().getCreditCardExpirationMonth());
    		sb.append(" / ");
    		sb.append(gift.getSelectedPaymentSource().getCreditCardExpirationYear());
    		sb.append(" ");
    		sb.append(gift.getSelectedPaymentSource().getCreditCardHolderName());
    	}
    	if (PaymentSource.CHECK.equals(gift.getPaymentType())) {
    		sb.append("\nCheck Number: ");
    		sb.append(gift.getCheckNumber());
    	}
    	if (FormBeanType.NONE.equals(gift.getAddressType()) == false) {
        	Address address = gift.getSelectedAddress();
        	if (address != null) {
            	sb.append("\nAddress: ");
        		sb.append(StringUtils.trimToEmpty(address.getAddressLine1())); 
        		sb.append(" ").append(StringUtils.trimToEmpty(address.getAddressLine2()));
                sb.append(" ").append(StringUtils.trimToEmpty(address.getAddressLine3()));
                sb.append(" ").append(StringUtils.trimToEmpty(address.getCity()));
                String state = StringUtils.trimToEmpty(address.getStateProvince());
                sb.append((state.length() == 0  ? "" : (", " + state))).append(" ");
                sb.append(" ").append(StringUtils.trimToEmpty(address.getCountry()));
                sb.append(" ").append(StringUtils.trimToEmpty(address.getPostalCode()));
        	}
    	}
        if (FormBeanType.NONE.equals(gift.getPhoneType()) == false) {
        	Phone phone = gift.getSelectedPhone();
        	if (phone != null) {
            	sb.append("\nPhone: ");
        		sb.append(StringUtils.trimToEmpty(phone.getNumber()));
        	}
        }
    	return sb.toString();
    }

    @Override
    public Gift readGiftById(Long giftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftById: giftId = " + giftId);
        }
        return giftDao.readGiftById(giftId);
    }

    @Override
    public Gift readGiftByIdCreateIfNull(Person constituent, String giftId, String recurringGiftId, String pledgeId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftByIdCreateIfNull: giftId = " + giftId + " recurringGiftId = " + recurringGiftId + 
                    "pledgeId = " + pledgeId + " constituentId = " + (constituent == null ? null : constituent.getId()));
        }
        Gift gift = null;
        if (giftId == null) {
            if (recurringGiftId != null) {
                RecurringGift recurringGift = null;
                recurringGift = recurringGiftService.readRecurringGiftById(Long.parseLong(recurringGiftId));
                if (recurringGift == null) {
                    logger.error("readGiftByIdCreateIfNull: recurringGift not found for recurringGiftId = " + recurringGiftId);
                    return gift;
                }
                gift = this.createGift(recurringGift, GiftType.MONETARY_GIFT, GiftEntryType.MANUAL);
            }
            else if (pledgeId != null) {
                Pledge pledge = null;
                pledge = pledgeService.readPledgeById(Long.parseLong(pledgeId));
                if (pledge == null) {
                    logger.error("readGiftByIdCreateIfNull: pledge not found for pledgeId = " + pledgeId);
                    return gift;
                }
                gift = this.createGift(pledge, GiftType.MONETARY_GIFT, GiftEntryType.MANUAL);
            }
            if (gift == null) {
                if (constituent != null) {
                    gift = this.createDefaultGift(constituent);
                }
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
    public Gift createGift(Commitment commitment, GiftType giftType, GiftEntryType giftEntryType) {
        if (logger.isTraceEnabled()) {
            logger.trace("createGift: commitment = " + commitment + " giftType = " + giftType + " giftEntryType = " + giftEntryType);
        }
        Gift gift = new Gift();
        gift.setPerson(commitment.getPerson());
        if (commitment instanceof RecurringGift) {
            gift.setRecurringGiftId(commitment.getId());
        }
        else if (commitment instanceof Pledge) {
            gift.setPledgeId(commitment.getId());
        }
        gift.setComments(commitment.getComments());
        gift.setAmount(commitment.getAmountPerGift());
        gift.setPaymentType(commitment.getPaymentType());
        gift.setPaymentSource(commitment.getPaymentSource());
        gift.setGiftType(giftType);
        gift.setEntryType(giftEntryType);
        if (commitment.getDistributionLines() != null) {
            List<DistributionLine> list = new ArrayList<DistributionLine>();
            for (DistributionLine oldLine : commitment.getDistributionLines()) {
                DistributionLine newLine = new DistributionLine(oldLine, gift.getId());
                list.add(newLine);
            }
            gift.setDistributionLines(list);
        }
        gift.setAddress(commitment.getAddress());
        gift.setPhone(commitment.getPhone());
        return gift;
    }

    @Override
    public double analyzeMajorDonor(Long constituentId, Date beginDate, Date currentDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("analyzeMajorDonor: constituentId = " + constituentId + " beginDate = " + beginDate + " currentDate = " + currentDate);
        }
        return giftDao.analyzeMajorDonor(constituentId, beginDate, currentDate);
    }

//    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
//    public void adjustGift(AdjustedGift adjustedGift) {
//        if (logger.isTraceEnabled()) {
//            logger.trace("adjustGift: giftId = " + adjustedGift.getId());
//        }

//        try {
            // get the original gift to compare against
//            Gift originalGift = readGiftById(adjustedGift.getId());

            // clone the incoming gift; we'll use as a template
            //(Gift) BeanUtils.cloneBean(adjustedGift);
//            adjustedGift.resetIdToNull();

//            adjustedGift.setTransactionDate( gift.getAdjustedTransactionDate());
            // clear these from the adjustment, we'll put them on the original
//            adjustedGift.setAdjustedGiftId(null);
//            adjustedGift.setGiftType(GiftType.ADJUSTMENT);
//            adjustedGift.setDeductibleAmount(adjustedGift.getAmount());
//            adjustedGift.setOriginalGiftId(originalGift.getId());
//            adjustedGift.setPaymentStatus(null);
//            adjustedGift.setPaymentType(null);
//            adjustedGift.setPostmarkDate(null);
//            adjustedGift.setComments(null);
//
//            // make sure we really have an adjustment
//            if(adjustedGift.getAmount().equals(BigDecimal.ZERO) ) {
//                // if no amount difference, don't save anything
//                logger.debug("adjustGift: adjustment amount = 0; exiting method");
//                return;
//            }

            // remove the "null" gifts in Mutable DistributionLine
//            List<DistributionLine> mutLines = adjustedGift.getMutableDistributionLines();
//            List<DistributionLine> temp = new ArrayList<DistributionLine>();
//            for(DistributionLine line : mutLines) {
//                if(line.isValid()) {
//                    temp.add(line);
//                }
//            }
//
//            mutLines.clear();
//            mutLines.addAll(temp);
//
//            // now match up the lines and compare the values
//            List<DistributionLine> origLines = originalGift.getDistributionLines();
//            List<DistributionLine> newLines = new ArrayList<DistributionLine>();
//
//            int len = origLines.size();
//
//            for(int i=0; i<len; i++) {
//                BigDecimal amt = mutLines.get(i).getAmount();
//                if(amt != null && amt.compareTo(BigDecimal.ZERO) != 0 && !origLines.get(i).getAmount().equals(amt)) {
//                    DistributionLine dl = mutLines.get(i);
//                    dl.setPercentage(null);
//                    newLines.add( dl );
//                }
//            }
//
//            adjustedGift.setDistributionLines(newLines);
//
//            // save our adjustment, which gets us the new ID
////            adjustedGift = maintainGift(adjustedGift);
////            adjustedGift.setId(adjustedGift.getId());
//
//            // set the refund (adjustment) details on the original and save it
////            originalGift.setAdjustedGiftId(adjustedGift.getId());
////            originalGift.setAdjustedTransactionDate(adjustedGift.getTransactionDate());
//
//            maintainGift(originalGift);
//
////        } catch (IllegalAccessException e) {
////            throw new IllegalStateException(e);
////        } catch (InstantiationException e) {
////            throw new IllegalStateException(e);
////        } catch (InvocationTargetException e) {
////            throw new IllegalStateException(e);
////        } catch (NoSuchMethodException e) {
////            throw new IllegalStateException(e);
////        }
//    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Gift> readMonetaryGiftsByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readMonetaryGiftsByConstituentId: constituentId = " + constituentId);
        }
        return giftDao.readMonetaryGiftsByConstituentId(constituentId);
    }

    // THIS METHOD IS NOT USED ANYWHERE TODO: remove?
    @Override
    public List<Gift> readGiftsByRecurringGiftId(RecurringGift recurringGift) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftsByRecurringGiftId: recurringGiftId = " + recurringGift.getId());
        }
        return giftDao.readGiftsByRecurringGiftId(recurringGift.getId());
    }

    // THIS METHOD IS NOT USED ANYWHERE TODO: remove?
    @Override
    public List<Gift> readGiftsByPledgeId(Pledge pledge) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftsByPledgeId: pledgeId = " + pledge.getId());
        }
        return giftDao.readGiftsByPledgeId(pledge.getId());
    }

	@Override
	public List<Gift> readAllGiftsByDateRange(Date fromDate, Date toDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllGiftsByDateRange:");
        }
        return giftDao.readAllGiftsByDateRange(fromDate, toDate);
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
