package com.orangeleap.tangerine.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.dao.PledgeDao;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.type.EntityType;
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
		if (pledge.getGifts() == null || pledge.getGifts().size() == 0) {
			if (Commitment.STATUS_FULFILLED.equals(pledge.getPledgeStatus()) || Commitment.STATUS_IN_PROGRESS.equals(pledge.getPledgeStatus())) {
				pledge.setPledgeStatus(Commitment.STATUS_PENDING);
			}
		} 
		else {
			if (Commitment.STATUS_FULFILLED.equals(pledge.getPledgeStatus())) {
				if (!pledge.getAmountPaid().equals(pledge.getAmountTotal())) {
					pledge.setPledgeStatus(Commitment.STATUS_IN_PROGRESS);
				}
			}
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
}
