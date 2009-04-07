package com.orangeleap.tangerine.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;

public interface PledgeService extends CommitmentService<Pledge> {

    public Pledge maintainPledge(Pledge pledge);

    public Pledge editPledge(Pledge pledge);

    public Pledge readPledgeById(Long pledgeId);
    
    public Pledge readPledgeByIdCreateIfNull(String pledgeId, Person constituent);
    
    public Pledge createDefaultPledge(Person constituent);
    
    public List<Pledge> readPledgesForConstituent(Person constituent);

    public List<Pledge> readPledgesForConstituent(Long constituentId);
    
    public List<Pledge> searchPledges(Map<String, Object> params);

    public Map<String, List<Pledge>> findNotCancelledPledges(Long constituentId, String selectedPledgeIds);
    
    public List<DistributionLine> findDistributionLinesForPledges(Set<String> pledgeIds);
}
