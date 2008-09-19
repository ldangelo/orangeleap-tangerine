package com.mpower.service;

import java.util.Arrays;
import java.util.Calendar;
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

import com.mpower.dao.CommitmentDao;
import com.mpower.dao.RecurringGiftDao;
import com.mpower.dao.SiteDao;
import com.mpower.domain.Commitment;
import com.mpower.domain.Person;
import com.mpower.domain.RecurringGift;
import com.mpower.domain.customization.EntityDefault;
import com.mpower.type.EntityType;

@Service("commitmentService")
public class CommitmentServiceImpl implements CommitmentService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "commitmentDao")
    private CommitmentDao commitmentDao;

    @Resource(name = "recurringGiftDao")
    private RecurringGiftDao recurringGiftDao;

    @Resource(name = "siteDao")
    private SiteDao siteDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Commitment maintainCommitment(Commitment commitment) {
        if (commitment.isAutoPay()) {
            RecurringGift rg = commitment.getRecurringGift();
            if (commitment.getRecurringGift() == null) {
                rg = new RecurringGift(commitment);
                rg.setNextRunDate(commitment.getStartDate());
                commitment.setRecurringGift(rg);
            } else {
                if (commitment.getEndDate() != null && commitment.getEndDate().before(Calendar.getInstance().getTime())) {
                    commitment.setRecurringGift(null);
                    recurringGiftDao.remove(rg);
                }
            }
        }
        commitment = commitmentDao.maintainCommitment(commitment);
        auditService.auditObject(commitment);
        return commitment;
    }

    @Override
    public Commitment readCommitmentById(Long commitmentId) {
        return commitmentDao.readCommitment(commitmentId);
    }

    @Override
    public List<Commitment> readCommitments(Person person) {
        return readCommitments(person.getId());
    }

    @Override
    public List<Commitment> readCommitments(Long personId) {
        return commitmentDao.readCommitments(personId);
    }

    @Override
    public List<Commitment> readCommitments(String siteName, Map<String, Object> params) {
        return commitmentDao.readCommitments(siteName, params);
    }

    @Override
    public Commitment createDefaultCommitment(String siteName) {
        // get initial gift with built-in defaults
        Commitment commitment = new Commitment();
        BeanWrapper personBeanWrapper = new BeanWrapperImpl(commitment);

        List<EntityDefault> entityDefaults = siteDao.readEntityDefaults(siteName, Arrays.asList(new EntityType[] { EntityType.gift }));
        for (EntityDefault ed : entityDefaults) {
            personBeanWrapper.setPropertyValue(ed.getEntityFieldName(), ed.getDefaultValue());
        }

        // TODO: consider caching techniques for the default Gift
        return commitment;
    }

    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }
}
