package com.orangeleap.tangerine.dao.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.PostBatchDao;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.PostBatchReviewSetItem;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("postBatchDAO")
public class IBatisPostBatchDao extends AbstractIBatisDao implements PostBatchDao {

	/** Logger for this class and subclasses */
	protected final Log logger = OLLogger.getLog(getClass());

	@Autowired
	public IBatisPostBatchDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
	}

	@Override
	public PostBatch maintainPostBatch(PostBatch postBatch) {
		if (logger.isTraceEnabled()) {
			logger.trace("maintainPostBatch: postbatchId = " + postBatch.getId());
		}
		PostBatch aPostBatch = (PostBatch) insertOrUpdate(postBatch, "POST_BATCH");
        return aPostBatch;
	}

    @Override
    public PostBatchReviewSetItem maintainPostBatchReviewSetItem(PostBatchReviewSetItem postBatchReviewSetItem) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainPostBatchReviewSetItem: maintainPostBatchReviewSetItemId = " + postBatchReviewSetItem.getId());
        }
        PostBatchReviewSetItem aPostBatchReviewSetItem = (PostBatchReviewSetItem) insertOrUpdate(postBatchReviewSetItem, "POST_BATCH_REVIEW_SET_ITEM");
        return aPostBatchReviewSetItem;
    }
}