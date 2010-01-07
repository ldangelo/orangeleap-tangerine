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

package com.orangeleap.tangerine.service;

import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.PostBatchEntryError;
import com.orangeleap.tangerine.domain.Segmentation;
import com.orangeleap.tangerine.web.common.SortInfo;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public interface PostBatchService {

    void checkPreExecuteBatchErrors(PostBatch batch);
    
    PostBatch maintainBatch(PostBatch postbatch);

	PostBatch executeBatch(PostBatch postbatch);

	void deleteBatch(PostBatch postbatch);

    List<PostBatch> readBatchesByStatus(String showBatchStatus, SortInfo sort, Locale locale);

    int countBatchesByStatus(String showBatchStatus);

    PostBatch readBatch(Long batchId);

    PostBatch readBatchCreateIfNull(final Long batchId);

    List<Map<String, Object>> findSegmentationsForBatchType(PostBatch batch, Set<Long> pickedSegmentationIds, String batchType, String sortField, String sortDirection, int startIndex, int resultCount);
    
    List<Segmentation> findSegmentations(String batchType, String sortField, String sortDirection, int startIndex, int resultCount);

    long findTotalSegmentations(String batchType);

    List<Map<String, Object>> readPostBatchEntryErrorsByBatchId(Long postBatchId, SortInfo sortInfo);

    int countPostBatchEntryErrorsByBatchId(Long postBatchId);
}