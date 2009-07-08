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

package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.PostBatchReviewSetItem;

import java.util.List;
import java.util.Map;


public interface PostBatchDao {

    public PostBatch readPostBatch(Long postBatchId);

    public PostBatch maintainPostBatch(PostBatch postBatch);

    public List<PostBatchReviewSetItem> readPostBatchReviewSetItems(Long postBatchId);

    public PostBatchReviewSetItem maintainPostBatchReviewSetItem(PostBatchReviewSetItem postBatchReviewSetItem);

    public void deletePostBatchItems(Long postBatchId);

    public void insertIntoPostBatchFromGiftSelect(PostBatch postbatch, Map<String, Object> searchmap);

    public void insertIntoPostBatchFromAdjustedGiftSelect(PostBatch postbatch, Map<String, Object> searchmap);

    public Long getReviewSetSize(Long postBatchId);

}