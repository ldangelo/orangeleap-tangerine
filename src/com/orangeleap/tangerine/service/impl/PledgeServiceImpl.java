package com.orangeleap.tangerine.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.dao.PledgeDao;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@SuppressWarnings("unchecked")
@Service("pledgeService")
@Transactional(propagation = Propagation.REQUIRED)
public class PledgeServiceImpl extends AbstractCommitmentService<Pledge> implements PledgeService {

    /** Logger for this class and subclasses */
    protected final static Log logger = LogFactory.getLog(PledgeServiceImpl.class);

    @Resource(name = "pledgeDAO")
    private PledgeDao pledgeDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Pledge maintainPledge(Pledge pledge) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainPledge: pledge = " + pledge);
        }
        pledge.filterValidDistributionLines();
        return save(pledge);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Pledge editPledge(Pledge pledge) {
        if (logger.isTraceEnabled()) {
            logger.trace("editPledge: pledgeId = " + pledge.getId());
        }
        return save(pledge);
    }
    
    private Pledge save(Pledge pledge) {
        maintainEntityChildren(pledge, pledge.getPerson());
        pledge = pledgeDao.maintainPledge(pledge);
        auditService.auditObject(pledge, pledge.getPerson());
        return pledge;
    }

    @Override
    public Pledge readPledgeById(Long pledgeId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPledgeById: pledgeId = " + pledgeId);
        }
        return pledgeDao.readPledgeById(pledgeId);
    }
    
    @Override
    public Pledge readPledgeByIdCreateIfNull(String pledgeId, Person constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPledgeByIdCreateIfNull: pledgeId = " + pledgeId + " constituentId = " + (constituent == null ? null : constituent.getId()));
        }
        Pledge pledge = null;
        if (pledgeId == null) {
            if (constituent != null) {
                pledge = this.createDefaultPledge(constituent);
            }
        } 
        else {
            pledge = this.readPledgeById(Long.valueOf(pledgeId));
        }
        return pledge;
    }

    public Pledge createDefaultPledge(Person constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("createDefaultPledge: constituent = " + (constituent == null ? null : constituent.getId()));
        }
        Pledge pledge = new Pledge();
        createDefault(constituent, pledge, EntityType.pledge, "pledgeId");

        return pledge;
    }
    
    @Override
    public List<Pledge> readPledgesForConstituent(Person constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPledges: constituent = " + constituent);
        }
        return readPledgesForConstituent(constituent.getId());
    }

    @Override
    public List<Pledge> readPledgesForConstituent(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPledges: constituentId = " + constituentId);
        }
        return pledgeDao.readPledgesByConstituentId(constituentId);
    }
    
    @Override
    public PaginatedResult readPaginatedPledgesByConstituentId(Long constituentId, SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaginatedPledgesByConstituentId: constituentId = " + constituentId);
        }
        return pledgeDao.readPaginatedPledgesByConstituentId(constituentId, sortinfo);
    }

    @Override
    public List<Pledge> searchPledges(Map<String, Object> params) {
        if (logger.isTraceEnabled()) {
            logger.trace("searchPledges: params = " + params);
        }
        return pledgeDao.searchPledges(params);
    }
    
    @Override
    public Map<String, List<Pledge>> findNotCancelledPledges(Long constituentId, String selectedPledgeIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("findNotCancelledPledges: constituentId = " + constituentId + " selectedPledgeIds = " + selectedPledgeIds);
        }
        Set<String> selectedPledgeIdsSet = StringUtils.commaDelimitedListToSet(selectedPledgeIds);
        List<Pledge> notSelectedPledges = pledgeDao.findNotCancelledPledges(constituentId);
        List<Pledge> selectedPledges = new ArrayList<Pledge>(); 
        
        if (selectedPledgeIdsSet.isEmpty() == false) {
            for (Iterator iter = notSelectedPledges.iterator(); iter.hasNext();) {
                Pledge pledge = (Pledge) iter.next();
                if (selectedPledgeIdsSet.contains(pledge.getId().toString())) {
                    selectedPledges.add(pledge);
                    iter.remove();
                }
            }
        }
        Map<String, List<Pledge>> pledgeMap = new HashMap<String, List<Pledge>>();
        pledgeMap.put("selectedPledges", selectedPledges);
        pledgeMap.put("notSelectedPledges", notSelectedPledges);
        return pledgeMap;
    }
    
    @Override
    public List<DistributionLine> findDistributionLinesForPledges(Set<String> pledgeIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("findDistributionLinesForPledges: pledgeIds = " + pledgeIds);
        }
        if (pledgeIds != null && pledgeIds.isEmpty() == false) {
            return pledgeDao.findDistributionLinesForPledges(new ArrayList<String>(pledgeIds));
        }
        return null;
    }
    
    @Override
    public boolean canApplyPayment(Pledge pledge) {
        if (logger.isTraceEnabled()) {
            logger.trace("canApplyPayment: pledge.id = " + pledge.getId() + " status = " + pledge.getPledgeStatus());
        }
        return pledge.getId() != null && pledge.getId() > 0 && Commitment.STATUS_CANCELLED.equals(pledge.getPledgeStatus()) == false;
    }
    
    @Override
    public boolean arePaymentsAppliedToPledge(Pledge pledge) {
        if (logger.isTraceEnabled()) {
            logger.trace("arePaymentsAppliedToPledge: pledge.id = " + pledge.getId());
        }
        boolean areApplied = false;
        if (pledge.getId() != null && pledge.getId() > 0) {
            Long paymentsAppliedCount = pledgeDao.readPaymentsAppliedToPledgeId(pledge.getId());
            if (paymentsAppliedCount != null && paymentsAppliedCount > 0) {
                areApplied = true;
            }
        }
        return areApplied;
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updatePledgeForGift(Gift gift) {
        if (logger.isTraceEnabled()) {
            logger.trace("updatePledgeForGift: gift.id = " + gift.getId());
        }
        updatePledgeStatusAmountPaid(gift.getDistributionLines());
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updatePledgeForAdjustedGift(AdjustedGift adjustedGift) {
        if (logger.isTraceEnabled()) {
            logger.trace("updatePledgeForAdjustedGift: adjustedGift.id = " + adjustedGift.getId());
        }
        updatePledgeStatusAmountPaid(adjustedGift.getDistributionLines());
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    private void updatePledgeStatusAmountPaid(List<DistributionLine> lines) {
        Set<Long> pledgeIds = new HashSet<Long>();
        if (lines != null) {
            for (DistributionLine thisLine : lines) {
                if (NumberUtils.isDigits(thisLine.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID))) {
                    Long pledgeId = Long.parseLong(thisLine.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID));
                    pledgeIds.add(pledgeId);
                }
            }
    
            if (pledgeIds.isEmpty() == false) {
                for (Long pledgeId : pledgeIds) {
                    Pledge pledge = readPledgeById(pledgeId);
                    if (pledge != null) {
                        BigDecimal amountPaid = pledgeDao.readAmountPaidForPledgeId(pledgeId);
                        setPledgeAmounts(pledge, amountPaid);
                        setCommitmentStatus(pledge, "pledgeStatus");
                        pledgeDao.maintainPledgeAmountPaidRemainingStatus(pledge);
                    }
                }
            }
        }
    }
    
    private void setPledgeAmounts(Pledge pledge, BigDecimal amountPaid) {
        if (amountPaid == null || amountPaid.compareTo(BigDecimal.ZERO) == -1) {
            amountPaid = BigDecimal.ZERO;
        }
        pledge.setAmountPaid(amountPaid);

        if (pledge.getAmountTotal() != null) {
            if (pledge.isRecurring() == false || (pledge.isRecurring() && pledge.getEndDate() != null)) {
                pledge.setAmountRemaining(pledge.getAmountTotal().subtract(pledge.getAmountPaid()));
            }
        }
    }
}
