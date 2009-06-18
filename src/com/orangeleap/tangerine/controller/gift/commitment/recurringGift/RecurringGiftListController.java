package com.orangeleap.tangerine.controller.gift.commitment.recurringGift;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;

import com.orangeleap.tangerine.controller.TangerineListController;
import com.orangeleap.tangerine.domain.GeneratedId;
import com.orangeleap.tangerine.service.RecurringGiftService;

public class RecurringGiftListController extends TangerineListController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="recurringGiftService")
    private RecurringGiftService recurringGiftService;

    @Override
    protected List<? extends GeneratedId> getList(Long constituentId) {
        return recurringGiftService.readRecurringGiftsForConstituent(constituentId);
    }
}
