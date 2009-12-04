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
import com.orangeleap.tangerine.domain.PostBatchSegmentation;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

import java.util.List;
import java.util.Locale;
import java.util.Map;


public interface PostBatchDao {

    public List<PostBatch> listBatches();

    public PostBatch readPostBatchById(Long postBatchId);

    public PostBatch maintainPostBatch(PostBatch batch);

    public List<PostBatchSegmentation> readPostBatchReviewSetItems(Long postBatchId);

    public PaginatedResult readPostBatchReviewSetItems(Long postBatchId, SortInfo sortInfo);

    public PostBatchSegmentation maintainPostBatchReviewSetItem(PostBatchSegmentation postBatchSegmentation);

    public void deletePostBatch(PostBatch batch);

    public void insertIntoPostBatchFromGiftSelect(PostBatch postbatch, Map<String, Object> searchmap);

    public void insertIntoPostBatchFromAdjustedGiftSelect(PostBatch postbatch, Map<String, Object> searchmap);

    public Long getReviewSetSize(Long postBatchId);

    List<PostBatch> readBatches(boolean batchUpdated, String sortPropertyName, String direction, int start, int limit, Locale locale);
}