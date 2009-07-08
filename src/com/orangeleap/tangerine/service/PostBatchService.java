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

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.AbstractPaymentInfoEntity;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.service.relationship.ConstituentTreeNode;

public interface PostBatchService {

    public Map<String, String> readAllowedGiftSelectFields();
    public Map<String, String> readAllowedGiftUpdateFields();
    public List<PostBatch> listBatchs(PostBatch postbatch);
    public PostBatch readBatch(Long batchId);
    public PostBatch maintainBatch(PostBatch postbatch);
    public List<AbstractPaymentInfoEntity> createBatchSelectionList(PostBatch postbatch);
    public List<AbstractPaymentInfoEntity> getBatchSelectionList(PostBatch postbatch);
	public PostBatch updateBatch(PostBatch postbatch, boolean post);

}