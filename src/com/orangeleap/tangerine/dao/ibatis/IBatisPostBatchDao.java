/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
import java.util.List;

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
    public List<PostBatchReviewSetItem> readPostBatchReviewSetItems(Long postBatchId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPostBatch: postBatchId = " + postBatchId);
        }
        Map<String, Object> params = setupParams();
        params.put("postBatchId", postBatchId);
        List<PostBatchReviewSetItem> result = (List<PostBatchReviewSetItem>)getSqlMapClientTemplate().queryForList("SELECT_POST_BATCH_REVIEW_SET_ITEMS", params);
        return result;
    }


    @Override
    public PostBatchReviewSetItem maintainPostBatchReviewSetItem(PostBatchReviewSetItem postBatchReviewSetItem) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainPostBatchReviewSetItem: maintainPostBatchReviewSetItemId = " + postBatchReviewSetItem.getId());
        }
        PostBatchReviewSetItem aPostBatchReviewSetItem = (PostBatchReviewSetItem) insertOrUpdate(postBatchReviewSetItem, "POST_BATCH_REVIEW_SET_ITEM");
        return aPostBatchReviewSetItem;
    }

    @Override
    public void deletePostBatchItems(Long postBatchId) {
        if (logger.isTraceEnabled()) {
            logger.trace("deletePostBatchItems: postBatchId = " + postBatchId);
        }
        Map<String, Object> params = setupParams();
        params.put("postBatchId", postBatchId);
        getSqlMapClientTemplate().delete("DELETE_POST_BATCH_REVIEW_SET_ITEMS", params);
    }

    public void insertIntoPostBatchFromGiftSelect(PostBatch postbatch, Map<String, Object> searchmap) {
        Map<String, Object> params = setupParams();
        params.put("postBatchId", postbatch.getId());
        // TODO translate map for various supported params with numeric/date ranges
        params.putAll(searchmap);
        getSqlMapClientTemplate().insert("INSERT_POST_BATCH_REVIEW_SET_ITEM_FROM_GIFT_SELECT", params);
    }
    
    public void insertIntoPostBatchFromAdjustedGiftSelect(PostBatch postbatch, Map<String, Object> searchmap) {
        Map<String, Object> params = setupParams();
        params.put("postBatchId", postbatch.getId());
        // TODO translate map for various supported params with numeric/date ranges
        params.putAll(searchmap);
        getSqlMapClientTemplate().insert("INSERT_POST_BATCH_REVIEW_SET_ITEM_FROM_ADJUSTED_GIFT_SELECT", params);
    }

    public Long getReviewSetSize(Long postBatchId) {
        Map<String, Object> params = setupParams();
        params.put("postBatchId", postBatchId);
        return (Long)getSqlMapClientTemplate().queryForObject("SELECT_POST_BATCH_REVIEW_SET_SIZE", params);
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