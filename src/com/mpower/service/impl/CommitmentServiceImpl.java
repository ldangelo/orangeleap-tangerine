package com.mpower.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.interfaces.CommitmentDao;
import com.mpower.dao.interfaces.GiftDao;
import com.mpower.dao.interfaces.PaymentSourceDao;
import com.mpower.dao.interfaces.SiteDao;
import com.mpower.domain.model.PaymentSource;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.communication.Address;
import com.mpower.domain.model.communication.Email;
import com.mpower.domain.model.communication.Phone;
import com.mpower.domain.model.customization.EntityDefault;
import com.mpower.domain.model.paymentInfo.Commitment;
import com.mpower.domain.model.paymentInfo.DistributionLine;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.service.AddressService;
import com.mpower.service.AuditService;
import com.mpower.service.CommitmentService;
import com.mpower.service.EmailService;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PhoneService;
import com.mpower.service.RecurringGiftService;
import com.mpower.type.CommitmentType;
import com.mpower.type.EntityType;

@Service("commitmentService")
@Transactional(propagation = Propagation.REQUIRED)
public class CommitmentServiceImpl implements CommitmentService {

    /** Logger for this class and subclasses */
    protected final static Log logger = LogFactory.getLog(CommitmentServiceImpl.class);

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "addressService")
    private AddressService addressService;

    @Resource(name = "phoneService")
    private PhoneService phoneService;

    @Resource(name = "emailService")
    private EmailService emailService;

    @Resource(name = "paymentSourceService")
    private PaymentSourceService paymentSourceService;

    @Resource(name = "recurringGiftService")
    private RecurringGiftService recurringGiftService;

    @Resource(name = "commitmentDAO")
    private CommitmentDao commitmentDao;

    @Resource(name = "giftDAO")
    private GiftDao giftDao;

    @Resource(name = "paymentSourceDAO")
    private PaymentSourceDao paymentSourceDao;

    @Resource(name = "siteDAO")
    private SiteDao siteDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Commitment maintainCommitment(Commitment commitment) {
    	
    	
    	// This needs to be a drools rule  see TANGERINE-97
//    	if (commitment.getCommitmentType() == CommitmentType.PLEDGE) {
//    		if (commitment.getGifts() == null || commitment.getGifts().size() == 0) {
//    			if (commitment.getPledgeStatus() == Commitment.PLEDGE_STATUS_FULFILLED || commitment.getPledgeStatus() == Commitment.PLEDGE_STATUS_IN_PROGRESS) {
//    				commitment.setPledgeStatus(Commitment.PLEDGE_STATUS_PENDING);
//    			}
//    		} else {
//    			if (commitment.getPledgeStatus() == Commitment.PLEDGE_STATUS_FULFILLED) {
//    				if (!commitment.getAmountPaid().equals(commitment.getAmountTotal())) {
//    					commitment.setPledgeStatus(Commitment.PLEDGE_STATUS_IN_PROGRESS);
//    				}
//    			}
//    		}
//    	}
    	
    	
    	
        if (PaymentSource.CREDIT_CARD.equals(commitment.getPaymentType()) || PaymentSource.ACH.equals(commitment.getPaymentType())) {
            commitment.getPaymentSource().setPaymentType(commitment.getPaymentType());
            List<PaymentSource> paymentSources = paymentSourceDao.readActivePaymentSources(commitment.getPerson().getId());
            if (paymentSources != null) {
                for (PaymentSource paymentSource : paymentSources) {
                    if (commitment.getPaymentSource().equals(paymentSource)) {
                        if (PaymentSource.CREDIT_CARD.equals(commitment.getPaymentType())) {
                            paymentSource.setCreditCardExpiration(commitment.getPaymentSource().getCreditCardExpiration());
                        }
                        commitment.setPaymentSource(paymentSourceDao.maintainPaymentSource(paymentSource));
                        break;
                    }
                }
            }
        } else {
            commitment.setPaymentSource(null);
        }

        if (logger.isDebugEnabled()) {
            List<Calendar> giftDates = getCommitmentGiftDates(commitment);
            if (giftDates != null) {
                int i = 1;
                for (Calendar cal : giftDates) {
                    logger.debug("Gift " + (i++) + ": scheduled for " + cal.getTime());
                }
            }
        }

        // TODO: need to see if they exist if null id
        if (commitment.getPaymentSource() != null && commitment.getPaymentSource().getId() == null) {
            commitment.setPaymentSource(paymentSourceService.maintainPaymentSource(commitment.getPaymentSource()));
        }
        if (commitment.getAddress() != null && commitment.getAddress().getId() == null) {
            commitment.setAddress(addressService.save(commitment.getAddress()));
        }
        if (commitment.getPhone() != null && commitment.getPhone().getId() == null) {
            commitment.setPhone(phoneService.save(commitment.getPhone()));
        }
        if (commitment.getEmail() != null && commitment.getEmail().getId() == null) {
            commitment.setEmail(emailService.save(commitment.getEmail()));
        }
        commitment = commitmentDao.maintainCommitment(commitment);
        commitment.setRecurringGift(recurringGiftService.maintainRecurringGift(commitment));
        auditService.auditObject(commitment);
        return commitment;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Commitment editCommitment(Commitment commitment) {
        if (logger.isDebugEnabled()) {
            logger.debug("editCommitment: commitmentId = " + commitment.getId());
        }
        // TODO: need to see if they exist if null id
        if (commitment.getPaymentSource() != null && commitment.getPaymentSource().getId() == null) {
            commitment.setPaymentSource(paymentSourceService.maintainPaymentSource(commitment.getPaymentSource()));
        }
        if (commitment.getAddress() != null && commitment.getAddress().getId() == null) {
            commitment.setAddress(addressService.save(commitment.getAddress()));
        }
        if (commitment.getPhone() != null && commitment.getPhone().getId() == null) {
            commitment.setPhone(phoneService.save(commitment.getPhone()));
        }
        if (commitment.getEmail() != null && commitment.getEmail().getId() == null) {
            commitment.setEmail(emailService.save(commitment.getEmail()));
        }
        commitment = commitmentDao.maintainCommitment(commitment);
        commitment.setRecurringGift(recurringGiftService.maintainRecurringGift(commitment));
        auditService.auditObject(commitment);
        return commitment;
    }

    @Override
    public Commitment readCommitmentById(Long commitmentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCommitmentById: commitmentId = " + commitmentId);
        }
        return normalize(commitmentDao.readCommitmentById(commitmentId));
    }
    
    @Override
    public Commitment readCommitmentByIdCreateIfNull(String commitmentId, Person constituent, CommitmentType commitmentType) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCommitmentByIdCreateIfNull: commitmentId = " + commitmentId + " commitmentType = " + commitmentType + " constituentId = " + (constituent == null ? null : constituent.getId()));
        }
        Commitment commitment = null;
        if (commitmentId == null) {
            if (constituent != null) {
                commitment = this.createDefaultCommitment(constituent, commitmentType);
                commitment.setPerson(constituent);
            }
        } 
        else {
            commitment = this.readCommitmentById(Long.valueOf(commitmentId));
        }
        return commitment;
    }
    
    // only needed for commitments not entered by the program and entered via sql.
    private Commitment normalize(Commitment commitment) {
    	if (commitment.getCommitmentType() == CommitmentType.RECURRING_GIFT) {
	    	if (commitment.getAddress() == null) {
	            commitment.setAddress(new Address(commitment.getPerson().getId()));
	        }
	    	if (commitment.getPhone() == null) {
	            commitment.setPhone(new Phone(commitment.getPerson().getId()));
	        }
	        if (commitment.getEmail() == null) {
	            commitment.setEmail(new Email(commitment.getPerson().getId()));
	        }
	        if (commitment.getPaymentSource() == null) {
	            commitment.setPaymentSource(new PaymentSource(commitment.getPerson()));
	        }
	    	commitment.getPaymentSource().setPerson(commitment.getPerson());
    	}
    	return commitment;
    }

    @Override
    public List<Commitment> readCommitments(Person constituent, CommitmentType commitmentType) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCommitments: constituent = " + constituent + " commitmentType = " + commitmentType);
        }
        return readCommitments(constituent.getId(), commitmentType);
    }

    @Override
    public List<Commitment> readCommitments(Long constituentId, CommitmentType commitmentType) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCommitments: constituentId = " + constituentId + " commitmentType = " + commitmentType);
        }
        return commitmentDao.readCommitmentsByConstituentIdType(constituentId, commitmentType);
    }

    @Override
    public List<Commitment> searchCommitments(CommitmentType commitmentType, Map<String, Object> params) {
        if (logger.isDebugEnabled()) {
            logger.debug("searchCommitments: commitmentType = " + commitmentType + " params = " + params);
        }
        return commitmentDao.searchCommitments(commitmentType, params);
    }

    @Override
    public Commitment createDefaultCommitment(Person constituent, CommitmentType commitmentType) {
        if (logger.isDebugEnabled()) {
            logger.debug("createDefaultCommitment: constituent = " + (constituent == null ? null : constituent.getId()) + " commitmentType = " + commitmentType);
        }
        // get initial commitment with built-in defaults
        Commitment commitment = new Commitment(commitmentType);
        BeanWrapper personBeanWrapper = new BeanWrapperImpl(commitment);

        List<EntityDefault> entityDefaults = siteDao.readEntityDefaults(Arrays.asList(new EntityType[] { EntityType.commitment }));
        for (EntityDefault ed : entityDefaults) {
            personBeanWrapper.setPropertyValue(ed.getEntityFieldName(), ed.getDefaultValue());
        }
        DistributionLine line = new DistributionLine();
        line.setCommitmentId(commitment.getId());
        commitment.addDistributionLine(line);

        return commitment;
    }

    @Override
    public BigDecimal getAmountReceived(Long commitmentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("getAmountReceived: commitmentId = " + commitmentId);
        }
        return giftDao.readGiftsReceivedSumByCommitmentId(commitmentId);
    }

    @Override
    public List<Calendar> getCommitmentGiftDates(Commitment commitment) {
        if (logger.isDebugEnabled()) {
            logger.debug("getCommitmentGiftDates: commitment = " + commitment);
        }
        List<Calendar> giftDates = null;
        if (commitment.getStartDate() == null) {
            logger.debug("Commitment start date is null");
            return giftDates;
        }
        if (commitment.getEndDate() == null) {
            logger.debug("Commitment end date is null");
            return giftDates;
        }
        giftDates = new ArrayList<Calendar>();
        Calendar startDateCal = new GregorianCalendar();
        startDateCal.setTimeInMillis(commitment.getStartDate().getTime());
        Calendar firstGiftCal = new GregorianCalendar(startDateCal.get(Calendar.YEAR), startDateCal.get(Calendar.MONTH), startDateCal.get(Calendar.DAY_OF_MONTH));
        giftDates.add(createGiftDate(commitment, firstGiftCal));

        if (Commitment.FREQUENCY_TWICE_MONTHLY.equals(commitment.getFrequency())) {
            Calendar secondGiftCal = new GregorianCalendar();
            secondGiftCal.setTimeInMillis(firstGiftCal.getTimeInMillis() + (1000 * 60 * 60 * 24 * 15));
            if (isPastEndDate(commitment, secondGiftCal.getTime())) {
                return giftDates;
            } else {
                giftDates.add(createGiftDate(commitment, secondGiftCal));
            }
            boolean pastEndDate = false;
            int i = 0;
            while (!pastEndDate) {
                i++;
                Calendar payment1 = getBimonthlyCalendar(firstGiftCal.get(Calendar.YEAR), firstGiftCal.get(Calendar.MONTH) + i, firstGiftCal.get(Calendar.DAY_OF_MONTH));
                if (isPastEndDate(commitment, payment1.getTime())) {
                    pastEndDate = true;
                } else {
                    giftDates.add(createGiftDate(commitment, payment1));
                }
                Calendar payment2 = getBimonthlyCalendar(secondGiftCal.get(Calendar.YEAR), secondGiftCal.get(Calendar.MONTH) + i, secondGiftCal.get(Calendar.DAY_OF_MONTH));
                if (isPastEndDate(commitment, payment2.getTime())) {
                    pastEndDate = true;
                } else {
                    giftDates.add(createGiftDate(commitment, payment2));
                }
            }
        } else {
            Calendar giftCal = firstGiftCal;
            boolean pastEndDate = false;
            while (!pastEndDate) {
                if (Commitment.FREQUENCY_WEEKLY.equals(commitment.getFrequency())) {
                    giftCal.add(Calendar.WEEK_OF_MONTH, 1);
                } else if (Commitment.FREQUENCY_MONTHLY.equals(commitment.getFrequency())) {
                    giftCal.add(Calendar.MONTH, 1);
                } else if (Commitment.FREQUENCY_QUARTERLY.equals(commitment.getFrequency())) {
                    giftCal.add(Calendar.MONTH, 3);
                } else if (Commitment.FREQUENCY_TWICE_ANNUALLY.equals(commitment.getFrequency())) {
                    giftCal.add(Calendar.MONTH, 6);
                } else if (Commitment.FREQUENCY_ANNUALLY.equals(commitment.getFrequency())) {
                    giftCal.add(Calendar.YEAR, 1);
                } else {
                    logger.debug("Unknown frequency");
                    return giftDates;
                }
                if (isPastEndDate(commitment, giftCal.getTime())) {
                    pastEndDate = true;
                } else {
                    giftDates.add(createGiftDate(commitment, giftCal));
                }
            }
        }
        return giftDates;
    }

    @Override
    public List<Gift> getCommitmentGifts(Commitment commitment) {
        if (logger.isDebugEnabled()) {
            logger.debug("getCommitmentGifts: commitment = " + commitment);
        }
        List<Calendar> giftDates = getCommitmentGiftDates(commitment);
        if (giftDates != null) {
            List<Gift> gifts = new ArrayList<Gift>();
            for (Calendar cal : giftDates) {
                gifts.add(createGift(commitment, cal));
            }
            return gifts;
        }
        return new ArrayList<Gift>();
    }

    public Calendar createGiftDate(Commitment commitment, Calendar giftCal) {
        return new GregorianCalendar(giftCal.get(Calendar.YEAR), giftCal.get(Calendar.MONTH), giftCal.get(Calendar.DAY_OF_MONTH));
    }

    public Gift createGift(Commitment commitment, Calendar giftCal) {
        logger.debug("Creating gift for " + commitment.getAmountPerGift() + ", on " + giftCal.getTime());
        return new Gift(commitment, giftCal.getTime());
    }

    // TODO: do the methods below need to be static???
    public static boolean isPastEndDate(Commitment commitment, Date date) {
        return commitment.getEndDate() == null ? false : date.after(commitment.getEndDate());
    }

    public static Calendar getBimonthlyCalendar(int year, int month, int day) {
        Calendar next = new GregorianCalendar();
        next.clear();
        if (month > 11) {
            next.set(year + 1, month - 12, 1);
        } else {
            next.set(year, month, 1);
        }
        int maxMonthDay = next.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (maxMonthDay >= day) {
            next.set(Calendar.DAY_OF_MONTH, day);
        } else {
            next.set(Calendar.DAY_OF_MONTH, maxMonthDay);
        }
        logger.debug("getBimonthlyCalendar() = " + next.getTime() + " millis=" + next.getTimeInMillis());
        return next;
    }
}
