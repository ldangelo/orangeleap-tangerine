package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.CacheGroup;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.PostBatchReviewSetItem;
import com.orangeleap.tangerine.type.CacheGroupType;


public interface PostBatchDao {

    public PostBatch readPostBatch(Long postBatchId);
    public PostBatch maintainPostBatch(PostBatch postBatch);
    public PostBatchReviewSetItem maintainPostBatchReviewSetItem(PostBatchReviewSetItem postBatchReviewSetItem);
    
}