package com.orangeleap.tangerine.controller.gift;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.orangeleap.tangerine.controller.TangerineListController;
import com.orangeleap.tangerine.domain.GeneratedId;
import com.orangeleap.tangerine.service.GiftService;

public class GiftListController extends TangerineListController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="giftService")
    private GiftService giftService;

    @Override
    protected List<? extends GeneratedId> getList(Long constituentId) {
        return giftService.readGifts(constituentId);
    }
}
