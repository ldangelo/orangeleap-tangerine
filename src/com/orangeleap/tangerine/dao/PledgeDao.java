package com.orangeleap.tangerine.dao;

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.paymentInfo.Pledge;

public interface PledgeDao {

    public Pledge maintainPledge(Pledge pledge);

    public Pledge readPledgeById(Long pledgeId);

    public List<Pledge> readPledgesByConstituentId(Long constituentId);

    public List<Pledge> findNotCancelledPledgesByGiftId(Long giftId, Long constituentId);
    
    public List<Pledge> findNotCancelledPledges(Long constituentId);

    public List<Pledge> searchPledges(Map<String, Object> params);
}
