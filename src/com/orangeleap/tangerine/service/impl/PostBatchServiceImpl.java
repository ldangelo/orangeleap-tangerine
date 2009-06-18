package com.orangeleap.tangerine.service.impl;

import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.PostBatchService;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("postBatchService")
@Transactional(propagation = Propagation.REQUIRED)
public class PostBatchServiceImpl extends AbstractTangerineService implements PostBatchService {

	public static final int MAX_TREE_DEPTH = 200;
	private static final String CONSTITUENT = "constituent";

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());


    @Override
    public List<PostBatch> listBatchs(PostBatch postbatch) {
        return new ArrayList<PostBatch>();
    }

    @Override
    public PostBatch readBatch(long batchId) {
        return null;
    }

    @Override
    public PostBatch maintainBatch(PostBatch postbatch) {
        return postbatch;
    }

    // Evaluates criteria to create list of matching gifts at this moment.
    @Override
    public List<Gift> createBatchSelectionList(PostBatch postbatch) {
         return new ArrayList<Gift>();
    }

    // Reads previous list of matched gifts. Does not re-evaluate any criteria.
    @Override
    public List<Gift> getBatchSelectionList(PostBatch postbatch) {
         return new ArrayList<Gift>();
    }

    @Override
    public PostBatch postBatch(PostBatch postbatch) {
         return postbatch;
    }
}