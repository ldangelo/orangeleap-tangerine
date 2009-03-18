package com.orangeleap.tangerine.controller.communicationHistory;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.orangeleap.tangerine.controller.TangerineListController;
import com.orangeleap.tangerine.domain.GeneratedId;
import com.orangeleap.tangerine.service.CommunicationHistoryService;

public class CommunicationHistoryListController extends TangerineListController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="communicationHistoryService")
    protected CommunicationHistoryService communicationHistoryService;

    @Override
    protected List<? extends GeneratedId> getList(Long constituentId) {
        return communicationHistoryService.readCommunicationHistoryByConstituent(constituentId);
    }
}
