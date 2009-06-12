package com.orangeleap.tangerine.controller.gift.commitment.pledge;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.orangeleap.tangerine.controller.TangerineListController;
import com.orangeleap.tangerine.domain.GeneratedId;
import com.orangeleap.tangerine.service.PledgeService;

public class PledgeListController extends TangerineListController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="pledgeService")
    private PledgeService pledgeService;

    @Override
    protected List<? extends GeneratedId> getList(Long constituentId) {
        return pledgeService.readPledgesForConstituent(constituentId);
    }
}
