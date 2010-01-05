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

import java.util.List;
import java.util.Locale;
import java.util.Map;


public interface PostBatchDao {

    public PostBatch readPostBatchById(Long postBatchId);

    public PostBatch maintainPostBatch(PostBatch batch);

    public void deletePostBatch(PostBatch batch);

    List<PostBatch> readBatchesByStatus(String showBatchStatus, String sortPropertyName, String direction, int start, int limit, Locale locale);

    int countBatchesByStatus(String showBatchStatus);

    List<Map<String, Object>> readPostBatchEntryErrorsByBatchId(Long postBatchId, String sortPropertyName, String dir, int start, int limit);

    int countPostBatchEntryErrorsByBatchId(Long postBatchId);
}