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

package com.orangeleap.tangerine.web.flow.batch;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.webflow.execution.RequestContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("reviewBatchAction")
public class ReviewBatchAction extends EditBatchAction {

    protected final Log logger = OLLogger.getLog(getClass());

    @SuppressWarnings("unchecked")
    public ModelMap reviewStep1(final RequestContext flowRequestContext, final Long batchId) {
        if (logger.isTraceEnabled()) {
            logger.trace("reviewStep1: batchId = " + batchId);
        }
        tangerineListHelper.checkAccess(getRequest(flowRequestContext), PageType.createBatch); // TODO: do as annotation
        final PostBatch batch = postBatchService.readBatch(batchId);
        setFlowScopeAttribute(flowRequestContext, batch, StringConstants.BATCH);

        final ModelMap model = new ModelMap();
        model.put(StringConstants.SUCCESS, Boolean.TRUE);
        final Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("reviewBatchDesc", batch.getBatchDesc());
        dataMap.put("reviewBatchType", batch.getBatchType());
        model.put(StringConstants.DATA, dataMap);
        return model;
    }

    @SuppressWarnings("unchecked")
    public ModelMap reviewStep2(final RequestContext flowRequestContext) {
        if (logger.isTraceEnabled()) {
            logger.trace("reviewStep2: ");
        }
        tangerineListHelper.checkAccess(getRequest(flowRequestContext), PageType.createBatch); // TODO: do as annotation
        final PostBatch batch = getBatchFromFlowScope(flowRequestContext);
        final SortInfo sortInfo = getSortInfo(flowRequestContext);

        final List<Map<String, Object>> rowValues = new ArrayList<Map<String, Object>>();

        final Map<String, Object> metaDataMap = tangerineListHelper.initMetaData(sortInfo.getStart(),
                (sortInfo.getLimit() * 2)); // double the rows because of old & new values will be displayed

//        BeanWrapper bean = createDefaultEntity(batch);
        List<? extends AbstractCustomizableEntity> entities = null;
        if (StringConstants.GIFT.equals(batch.getBatchType())) {
            entities = giftService.readGiftsByIds(batch.getEntryGiftIds());
        }
        else if (StringConstants.ADJUSTED_GIFT.equals(batch.getBatchType())) {
            entities = adjustedGiftService.readAdjustedGiftsByIds(batch.getEntryAdjustedGiftIds());
        }

        final ModelMap model = new ModelMap();
        return model;
    }
}
