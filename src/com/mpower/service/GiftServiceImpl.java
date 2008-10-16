package com.mpower.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.RandomStringUtils;
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
import com.mpower.domain.Commitment;
import com.mpower.domain.DistributionLine;
import com.mpower.domain.Gift;
import com.mpower.domain.PaymentSource;
import com.mpower.domain.Person;
import com.mpower.domain.customization.EntityDefault;
import com.mpower.type.EntityType;
import com.mpower.type.GiftEntryType;

@Service("giftService")
public class GiftServiceImpl implements GiftService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

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
     * @Resource(name = "creditGateway") private MPowerCreditGateway creditGateway;
     */

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Gift maintainGift(Gift gift) {
        if ("Credit Card".equals(gift.getPaymentType()) || "ACH".equals(gift.getPaymentType())) {
            gift.setAuthCode(RandomStringUtils.randomNumeric(6));
            gift.getPaymentSource().setType(gift.getPaymentType());
        } else {
            gift.setPaymentSource(null);
        }
        gift = giftDao.maintainGift(gift);

        /*
         * this was a part of our JMS/MOM poc creditGateway.sendGiftInfo(gift); logger.info("*** sending msg to queue");
         */

        auditService.auditObject(gift);
        return gift;
    }

    @Override
    public Gift readGiftById(Long giftId) {
        return giftDao.readGift(giftId);
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
        return paymentSourceDao.readPaymentSources(personId);
    }

    public void deletePaymentSource(Long paymentSourceId) {
        paymentSourceDao.readPaymentSources(paymentSourceId);
    }

    @Override
    public Gift createDefaultGift(Person person) {
        // get initial gift with built-in defaults
        Gift gift = new Gift();
        BeanWrapper personBeanWrapper = new BeanWrapperImpl(gift);

        List<EntityDefault> entityDefaults = siteDao.readEntityDefaults(person.getSite().getName(), Arrays.asList(new EntityType[] { EntityType.gift }));
        for (EntityDefault ed : entityDefaults) {
            personBeanWrapper.setPropertyValue(ed.getEntityFieldName(), ed.getDefaultValue());
        }

        gift.setPaymentSources(readPaymentSources(person.getId()));

        gift.addDistributionLine(new DistributionLine(gift));

        // TODO: consider caching techniques for the default Gift
        return gift;
    }

    @Override
    public Gift createGift(Commitment commitment) {
        Gift gift = new Gift();
        gift.setPerson(commitment.getPerson());
        gift.setCommitment(commitment);
        gift.setComments(commitment.getComments());
        gift.setDeductible(commitment.isDeductible());
        gift.setValue(commitment.getAmountPerGift());
        gift.setPaymentType(commitment.getPaymentType());
        gift.setPaymentSource(commitment.getPaymentSource());
        gift.setEntryType(GiftEntryType.AUTO);
        DistributionLine dl = new DistributionLine(gift);
        dl.setCommitmentCode(commitment.getCommitmentCode());
        dl.setProjectCode(commitment.getProjectCode());
        dl.setMotivationCode(commitment.getMotivationCode());
        dl.setAmount(commitment.getAmountPerGift());
        gift.addDistributionLine(dl);
        return gift;
    }

    @Override
    public double analyzeMajorDonor(Long personId, Date beginDate, Date currentDate) {
        return giftDao.analyzeMajorDonor(personId, beginDate, currentDate);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Gift refundGift(Long giftId) {
        Gift originalGift = giftDao.readGift(giftId);
        try {
            Gift refundGift = (Gift) BeanUtils.cloneBean(originalGift);
            refundGift.setId(null);
            refundGift.setTransactionDate(null);
            refundGift.getPaymentSource().setCreditCardExpiration(null);
            refundGift.setValue(originalGift.getValue().negate());
            refundGift.setOriginalGiftId(originalGift.getId());
            refundGift = giftDao.maintainGift(refundGift);
            refundGift.setDistributionLines(null);
            List<DistributionLine> lines = originalGift.getDistributionLines();
            for (DistributionLine line : lines) {
                BigDecimal negativeAmount = line.getAmount() == null ? null : line.getAmount().negate();
                refundGift.addDistributionLine(new DistributionLine(refundGift, negativeAmount, line.getProjectCode(), line.getMotivationCode()));
            }
            originalGift.setRefundGiftId(refundGift.getId());
            originalGift.setRefundGiftTransactionDate(refundGift.getTransactionDate());
            giftDao.maintainGift(originalGift);
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
}
