package com.orangeleap.tangerine.controller.commitment;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.orangeleap.tangerine.controller.TangerineListController;
import com.orangeleap.tangerine.domain.GeneratedId;
import com.orangeleap.tangerine.service.CommitmentService;
import com.orangeleap.tangerine.type.CommitmentType;

public class CommitmentListController extends TangerineListController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="commitmentService")
    private CommitmentService commitmentService;
    
    private CommitmentType commitmentType;

    public void setCommitmentType(CommitmentType commitmentType) {
        this.commitmentType = commitmentType;
    }

    @Override
    protected List<? extends GeneratedId> getList(Long constituentId) {
        return commitmentService.readCommitments(constituentId, commitmentType);
    }
}
