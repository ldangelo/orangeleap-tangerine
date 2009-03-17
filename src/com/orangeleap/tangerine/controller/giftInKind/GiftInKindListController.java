package com.orangeleap.tangerine.controller.giftInKind;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.orangeleap.tangerine.controller.TangerineListController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.service.GiftInKindService;

public class GiftInKindListController extends TangerineListController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="giftInKindService")
    private GiftInKindService giftInKindService;
    
    @Override
    protected List<? extends AbstractEntity> getList(Long constituentId) {
        return giftInKindService.readGiftsInKindByConstituentId(Long.valueOf(constituentId));
    }
}
