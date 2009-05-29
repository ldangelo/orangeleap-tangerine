package com.orangeleap.tangerine.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.BindException;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface PledgeService extends CommitmentService<Pledge> {

    public Pledge maintainPledge(Pledge pledge) throws BindException;

    public Pledge editPledge(Pledge pledge);

    public Pledge readPledgeById(Long pledgeId);
    
    public Pledge readPledgeByIdCreateIfNull(String pledgeId, Person constituent);
    
    public Pledge createDefaultPledge(Person constituent);
    
    public List<Pledge> readPledgesForConstituent(Person constituent);

    public List<Pledge> readPledgesForConstituent(Long constituentId);
    
	public PaginatedResult readPaginatedPledgesByConstituentId(Long constituentId, SortInfo sortinfo);
    
    public List<Pledge> searchPledges(Map<String, Object> params);
    
    public boolean arePaymentsAppliedToPledge(Pledge pledge);

    public Map<String, List<Pledge>> findNotCancelledPledges(Long constituentId, String selectedPledgeIds);
    
    public List<DistributionLine> findDistributionLinesForPledges(Set<String> pledgeIds);
    
    public boolean canApplyPayment(Pledge pledge);
    
    public void updatePledgeForGift(Gift gift);
    
    public void updatePledgeForAdjustedGift(AdjustedGift adjustedGift);
}
