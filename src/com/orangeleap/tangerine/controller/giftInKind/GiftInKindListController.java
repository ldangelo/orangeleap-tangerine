package com.orangeleap.tangerine.controller.giftInKind;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;

import com.orangeleap.tangerine.controller.TangerineListController;
import com.orangeleap.tangerine.domain.GeneratedId;
import com.orangeleap.tangerine.service.GiftInKindService;

public class GiftInKindListController extends TangerineListController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="giftInKindService")
    private GiftInKindService giftInKindService;
    
    @Override
    protected List<? extends GeneratedId> getList(Long constituentId) {
        return giftInKindService.readGiftsInKindByConstituentId(constituentId);
    }
}
