package com.orangeleap.tangerine.dao;

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.CacheGroup;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.PostBatchReviewSetItem;
import com.orangeleap.tangerine.type.CacheGroupType;


public interface PostBatchDao {

    public PostBatch readPostBatch(Long postBatchId);
    public PostBatch maintainPostBatch(PostBatch postBatch);
    public List<PostBatchReviewSetItem> readPostBatchReviewSetItems(Long postBatchId);
    public PostBatchReviewSetItem maintainPostBatchReviewSetItem(PostBatchReviewSetItem postBatchReviewSetItem);
    public void deletePostBatchItems(Long postBatchId);
    public void insertIntoPostBatchFromGiftSelect(PostBatch postbatch, Map<String, Object> searchmap);
    public void insertIntoPostBatchFromAdjustedGiftSelect(PostBatch postbatch, Map<String, Object> searchmap);
}