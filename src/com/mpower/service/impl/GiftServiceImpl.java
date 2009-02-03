package com.mpower.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.GiftDao;
import com.mpower.dao.PaymentSourceDao;
import com.mpower.dao.SiteDao;
import com.mpower.domain.Address;
import com.mpower.domain.Commitment;
import com.mpower.domain.DistributionLine;
import com.mpower.domain.Email;
import com.mpower.domain.Gift;
import com.mpower.domain.PaymentHistory;
import com.mpower.domain.PaymentSource;
import com.mpower.domain.Person;
import com.mpower.domain.Phone;
import com.mpower.domain.customization.EntityDefault;
import com.mpower.service.AddressService;
import com.mpower.service.AuditService;
import com.mpower.service.CommitmentService;
import com.mpower.service.EmailService;
import com.mpower.service.GiftService;
import com.mpower.service.PaymentHistoryService;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PhoneService;
import com.mpower.type.EntityType;
import com.mpower.type.GiftEntryType;
import com.mpower.type.PaymentHistoryType;

@Service("giftService")
@Transactional(propagation = Propagation.REQUIRED)
public class GiftServiceImpl implements GiftService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "addressService")
    private AddressService addressService;

    @Resource(name = "phoneService")
    private PhoneService phoneService;

    @Resource(name = "emailService")
    private EmailService emailService;

    @Resource(name = "paymentSourceService")
    private PaymentSourceService paymentSourceService;

    @Resource(name = "paymentHistoryService")
    private PaymentHistoryService paymentHistoryService;

    @Resource(name = "commitmentService")
    private CommitmentService commitmentService;

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "giftDao")
    private GiftDao giftDao;

    @Resource(name = "paymentSourceDao")
    private PaymentSourceDao paymentSourceDao;

    @Resource(name = "siteDao")
    private SiteDao siteDao;

    /*
     * this is needed for JMS
     */
    // @Resource(name = "creditGateway")
    // private MPowerCreditGateway creditGateway;
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Gift maintainGift(Gift gift) {
        if (PaymentSource.CREDIT_CARD.equals(gift.getPaymentType()) || PaymentSource.ACH.equals(gift.getPaymentType())) {
            gift.setAuthCode(RandomStringUtils.randomNumeric(6));
            if (gift.getPaymentSource() != null && gift.getPaymentSource().getId() == null) {
                gift.getPaymentSource().setType(gift.getPaymentType());
                List<PaymentSource> paymentSources = paymentSourceDao.readActivePaymentSources(gift.getPerson().getId());
                if (paymentSources != null) {
                    for (PaymentSource paymentSource : paymentSources) {
                        if (gift.getPaymentSource().equals(paymentSource)) {
                            if (PaymentSource.CREDIT_CARD.equals(gift.getPaymentType())) {
                                paymentSource.setCreditCardExpiration(gift.getPaymentSource().getCreditCardExpiration());
                            }
                            gift.setPaymentSource(paymentSourceDao.maintainPaymentSource(paymentSource));
                            break;
                        }
                    }
                } else {
                    gift.setPaymentSource(null);
                }
            }
        }

        // TODO: need to see if they exist if null id
        if (gift.getPaymentSource() != null && gift.getPaymentSource().getId() == null) {
            gift.setPaymentSource(paymentSourceService.maintainPaymentSource(gift.getPaymentSource()));
        }
        if (gift.getAddress() != null && gift.getAddress().getId() == null) {
            gift.setAddress(addressService.saveAddress(gift.getAddress()));
        }
        if (gift.getPhone() != null && gift.getPhone().getId() == null) {
            gift.setPhone(phoneService.savePhone(gift.getPhone()));
        }
        if (gift.getEmail() != null && gift.getEmail().getId() == null) {
            gift.setEmail(emailService.saveEmail(gift.getEmail()));
        }
        gift = giftDao.maintainGift(gift);

        // this was a part of our JMS/MOM poc
        // comment it out to disable jms processing.
        // processMockTrans(gift);

        
        paymentHistoryService.addPaymentHistory(createPaymentHistoryForGift(gift));
        
        auditService.auditObject(gift);

        return gift;
    }

    /*
     * this is needed for JMS
     */
    // @Resource(name = "creditGateway")
    // private MPowerCreditGateway creditGateway;
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Gift editGift(Gift gift) {
        if (logger.isDebugEnabled()) {
            logger.debug("editGift: giftId = " + gift.getId());
        }
        if (gift.getPaymentSource() != null && gift.getPaymentSource().getId() == null) {
            gift.setPaymentSource(paymentSourceService.maintainPaymentSource(gift.getPaymentSource()));
        }
        if (gift.getAddress() != null && gift.getAddress().getId() == null) {
            gift.setAddress(addressService.saveAddress(gift.getAddress()));
        }
        if (gift.getPhone() != null && gift.getPhone().getId() == null) {
            gift.setPhone(phoneService.savePhone(gift.getPhone()));
        }
        if (gift.getEmail() != null && gift.getEmail().getId() == null) {
            gift.setEmail(emailService.saveEmail(gift.getEmail()));
        }
        gift = giftDao.maintainGift(gift);

        // this was a part of our JMS/MOM poc
        // comment it out to disable jms processing.
        // processMockTrans(gift);

        auditService.auditObject(gift);

        return gift;
    }

    // private void processMockTrans(Gift gift) {
    // // this was a part of our JMS/MOM poc
    // creditGateway.sendGiftTransaction(gift);
    // }
    
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
    	
    	if (PaymentSource.ACH.equals(gift.getPaymentType())) {
    	    sb.append("ACH Number: "+gift.getPaymentSource().getAchAccountNumberDisplay());
    	}
    	if (PaymentSource.CREDIT_CARD.equals(gift.getPaymentType())) {
    		sb.append("Credit Card Number: "+gift.getPaymentSource().getCreditCardType()+" "+gift.getPaymentSource().getCreditCardNumberDisplay());
    		sb.append(" ");
    		sb.append(gift.getPaymentSource().getCreditCardExpirationMonth());
    		sb.append(" / ");
    		sb.append(gift.getPaymentSource().getCreditCardExpirationYear());
    		sb.append(" ");
    		sb.append(gift.getPaymentSource().getCreditCardHolderName());
    	}
    	if (PaymentSource.CHECK.equals(gift.getPaymentType())) {
    		sb.append("\nCheck Number: ");
    		sb.append(gift.getCheckNumber());
    	}
    	Address address = gift.getAddress();
    	if (address != null) {
        	sb.append("\nAddress: ");
        	String state = StringUtils.trimToEmpty(address.getStateProvince());
    		sb.append(StringUtils.trimToEmpty(address.getAddressLine1()) 
    				+ " " + StringUtils.trimToEmpty(address.getAddressLine2()) 
    				+ " " + StringUtils.trimToEmpty(address.getAddressLine3()) 
    				+ " " + StringUtils.trimToEmpty(address.getCity()) 
    				+ (state.length() == 0  ? "" : (", " + state))
    				+ " " + StringUtils.trimToEmpty(address.getCountry()) 
    				+ " " + StringUtils.trimToEmpty(address.getPostalCode())
    				);
    	}
    	Phone phone = gift.getPhone();
    	if (phone != null) {
        	sb.append("\nPhone: ");
    		sb.append(StringUtils.trimToEmpty(phone.getNumber()));
    	}
    	    	
    	return sb.toString();
    	
    }

    @Override
    public Gift readGiftById(Long giftId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readGiftById: giftId = " + giftId);
        }
        return normalize(giftDao.readGift(giftId));
    }

    @Override
    public Gift readGiftByIdCreateIfNull(String giftId, String commitmentId, Person person) {
        if (logger.isDebugEnabled()) {
            logger.debug("readGiftByIdCreateIfNull: giftId = " + giftId + " commitmentId = " + commitmentId + " personId = " + (person == null ? null : person.getId()));
        }
        Gift gift = null;
        if (giftId == null) {
            Commitment commitment = null;
            if (commitmentId != null) {
                commitment = commitmentService.readCommitmentById(Long.valueOf(commitmentId));
                if (commitment == null) {
                    logger.error("readGiftByIdCreateIfNull: commitment not found for commitmentId = " + commitmentId);
                    return gift;
                }
                gift = this.createGift(commitment, GiftEntryType.MANUAL);
                gift.setPerson(commitment.getPerson());
            }
            if (gift == null) {
                if (person != null) {
                    gift = this.createDefaultGift(person);
                    gift.setPerson(person);
                }
            }
        }
        else {
            gift = this.readGiftById(Long.valueOf(giftId));
        }
        return gift;
    }

    // only needed for gifts not entered by the program and entered via sql.
    private Gift normalize(Gift gift) {
        if (gift.getAddress() == null) {
            gift.setAddress(new Address(gift.getPerson()));
        }
        if (gift.getPhone() == null) {
            gift.setPhone(new Phone(gift.getPerson()));
        }
        if (gift.getEmail() == null) {
            gift.setEmail(new Email(gift.getPerson()));
        }
        if (gift.getPaymentSource() == null) {
            gift.setPaymentSource(new PaymentSource(gift.getPerson()));
        }
        gift.getPaymentSource().setPerson(gift.getPerson());
        return gift;
    }

    @Override
    public List<Gift> readGifts(Person person) {
        return readGifts(person.getId());
    }

    @Override
    public List<Gift> readGifts(Long personId) {
        return giftDao.readGifts(personId);
    }

    @Override
    public List<Gift> readGifts(String siteName, Map<String, Object> params) {
        return giftDao.readGifts(siteName, params);
    }

    public void createPaymentSource(PaymentSource paymentSource) {
        paymentSourceDao.maintainPaymentSource(paymentSource);
    }

    public List<PaymentSource> readPaymentSources(Long personId) {
        return paymentSourceDao.readActivePaymentSources(personId);
    }

    public void deletePaymentSource(Long paymentSourceId) {
        paymentSourceDao.readActivePaymentSources(paymentSourceId);
    }

    @Override
    public Gift createDefaultGift(Person person) {
        if (logger.isDebugEnabled()) {
            logger.debug("createDefaultGift: person = " + (person == null ? null : person.getId()));
        }
        // get initial gift with built-in defaults
        Gift gift = new Gift();
        BeanWrapper personBeanWrapper = new BeanWrapperImpl(gift);

        List<EntityDefault> entityDefaults = siteDao.readEntityDefaults(person.getSite().getName(), Arrays.asList(new EntityType[] { EntityType.gift }));
        for (EntityDefault ed : entityDefaults) {
            personBeanWrapper.setPropertyValue(ed.getEntityFieldName(), ed.getDefaultValue());
        }

        gift.addDistributionLine(new DistributionLine(gift));

        // TODO: consider caching techniques for the default Gift
        return gift;
    }

    @Override
    public Gift createGift(Commitment commitment, GiftEntryType giftEntryType) {
        Gift gift = new Gift();
        gift.setPerson(commitment.getPerson());
        gift.setCommitment(commitment);
        gift.setComments(commitment.getComments());
        gift.setAmount(commitment.getAmountPerGift());
        gift.setPaymentType(commitment.getPaymentType());
        gift.setPaymentSource(commitment.getPaymentSource());
        gift.setEntryType(giftEntryType);
        for (DistributionLine dl : commitment.getDistributionLines()) {
            DistributionLine gdl = new DistributionLine(gift);
            gdl.setProjectCode(dl.getProjectCode());
            gdl.setAmount(dl.getAmount());
            gift.addDistributionLine(gdl);
        }
        gift.setAddress(commitment.getAddress());
        gift.setPhone(commitment.getPhone());
        return gift;
    }

    @Override
    public double analyzeMajorDonor(Long personId, Date beginDate, Date currentDate) {
        return giftDao.analyzeMajorDonor(personId, beginDate, currentDate);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Gift refundGift(Long giftId) {
        Gift originalGift = readGiftById(giftId);
        try {
            Gift refundGift = (Gift) BeanUtils.cloneBean(originalGift);
            refundGift.setId(null);
            refundGift.setTransactionDate(null);
            refundGift.getPaymentSource().setCreditCardExpiration(null);
            refundGift.setAmount(originalGift.getAmount().negate());
            refundGift.setOriginalGiftId(originalGift.getId());
            refundGift = maintainGift(refundGift);
            refundGift.setDistributionLines(null);
            List<DistributionLine> lines = originalGift.getDistributionLines();
            for (DistributionLine line : lines) {
                BigDecimal negativeAmount = line.getAmount() == null ? null : line.getAmount().negate();
                refundGift.addDistributionLine(new DistributionLine(refundGift, negativeAmount, line.getProjectCode(), line.getMotivationCode()));
            }
            originalGift.setRefundGiftId(refundGift.getId());
            originalGift.setRefundGiftTransactionDate(refundGift.getTransactionDate());
            maintainGift(originalGift);
            auditService.auditObject(refundGift);
            return refundGift;
        } catch (IllegalAccessException e) {
            throw new IllegalStateException();
        } catch (InstantiationException e) {
            throw new IllegalStateException();
        } catch (InvocationTargetException e) {
            throw new IllegalStateException();
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Gift> readGiftsByPersonId(Long personId) {
        return giftDao.readGiftsByPersonId(personId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Gift> readAllGifts() {
        return giftDao.readAllGifts();
    }

    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    public List<Gift> readGiftsByCommitment(Commitment commitment) {
        return giftDao.readGiftsByCommitmentId(commitment.getId());
    }
}
