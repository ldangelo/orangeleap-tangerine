package com.orangeleap.tangerine.dao.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.PostBatchDao;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.PostBatchReviewSetItem;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("postBatchDAO")
public class IBatisPostBatchDao extends AbstractIBatisDao implements PostBatchDao {

	/** Logger for this class and subclasses */
	protected final Log logger = OLLogger.getLog(getClass());

	@Autowired
	public IBatisPostBatchDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
	}

    @Override
    public PostBatch readPostBatch(Long postBatchId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPostBatch: postBatchId = " + postBatchId);
        }
        Map<String, Object> params = setupParams();
        params.put("id", postBatchId);
        PostBatch postbatch = (PostBatch)getSqlMapClientTemplate().queryForObject("SELECT_POST_BATCH_BY_ID", params);
        readCustomFields(postbatch);
        return postbatch;
    }


	@Override
	public PostBatch maintainPostBatch(PostBatch postBatch) {
		if (logger.isTraceEnabled()) {
			logger.trace("maintainPostBatch: postbatchId = " + postBatch.getId());
		}
        setCustomFields(postBatch);
		PostBatch aPostBatch = (PostBatch) insertOrUpdate(postBatch, "POST_BATCH");
        readCustomFields(aPostBatch);
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

    

    private static final String WHERE = "where";
    private static final String UPDATE = "update";

    private void setCustomFields(PostBatch postbatch) {
        if (postbatch == null) return;
        postbatch.getCustomFieldMap().clear();
        for (Map.Entry<String, String> me : postbatch.getWhereConditions().entrySet()) {
            postbatch.setCustomFieldValue(WHERE + "." + me.getKey(), me.getValue());
        }
        for (Map.Entry<String, String> me : postbatch.getUpdateFields().entrySet()) {
            postbatch.setCustomFieldValue(UPDATE + "." + me.getKey(), me.getValue());
        }
    }

    private void readCustomFields(PostBatch postbatch) {
        if (postbatch == null) return;
        postbatch.getWhereConditions().clear();
        postbatch.getUpdateFields().clear();
        for (Map.Entry<String, CustomField> me : postbatch.getCustomFieldMap().entrySet()) {
            String key = me.getKey();
            String value = me.getValue().getValue();
            int i = key.indexOf('.');
            if (i > -1) {
                String prefix = key.substring(0,i);
                key = key.substring(i+1);
                if (prefix.equals(WHERE)) postbatch.getWhereConditions().put(key, value);
                if (prefix.equals(UPDATE)) postbatch.getUpdateFields().put(key, value);
            }
        }
    }


}