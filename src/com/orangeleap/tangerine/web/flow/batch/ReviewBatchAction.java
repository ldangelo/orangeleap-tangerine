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

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
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
        if (batch != null) {
            final Map<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("reviewBatchDesc", batch.getBatchDesc());
            dataMap.put("reviewBatchType", TangerineMessageAccessor.getMessage(batch.getBatchType()));
            model.put(StringConstants.DATA, dataMap);
        }
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
        final ModelMap model = new ModelMap();

        final List<Map<String, Object>> rowValues = new ArrayList<Map<String, Object>>();

        final Map<String, Object> metaDataMap = initStep5MetaData(batch, sortInfo.getSort(), sortInfo.getDir(),
                sortInfo.getStart(), sortInfo.getLimit());
        model.put(StringConstants.META_DATA, metaDataMap);

        unescapeSortField(sortInfo);
        
        List<? extends AbstractCustomizableEntity> entities = null;
        int totalRows = 0;
        if (StringConstants.GIFT.equals(batch.getBatchType())) {
            Set<Long> giftIds = batch.getEntryGiftIds();
            if ( ! giftIds.isEmpty()) {
                entities = giftService.readLimitedGiftsByIds(giftIds, sortInfo, getRequest(flowRequestContext).getLocale());
                totalRows = giftIds.size();
            }
        }
        else if (StringConstants.ADJUSTED_GIFT.equals(batch.getBatchType())) {
            Set<Long> adjustedGiftIds = batch.getEntryAdjustedGiftIds();
            if ( ! adjustedGiftIds.isEmpty()) {
                entities = adjustedGiftService.readLimitedAdjustedGiftsByIds(adjustedGiftIds, sortInfo, getRequest(flowRequestContext).getLocale());
                totalRows = adjustedGiftIds.size();
            }
        }
        if (entities != null && ! entities.isEmpty()) {
            for (AbstractCustomizableEntity entity : entities) {
                final BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(entity);
                final Map<String, Object> rowMap = new HashMap<String, Object>();
                rowMap.put(StringConstants.DISPLAYED_ID, bw.getPropertyValue(StringConstants.ID));
                rowMap.put(StringConstants.TYPE, TangerineMessageAccessor.getMessage(batch.getBatchType()));

                for (Map.Entry<String, String> fieldEntry : batch.getUpdateFields().entrySet()) {
                    String key = fieldEntry.getKey();
                    // the batchType + key is the fieldDefinitionId; we need to resolve the fieldName
                    String fieldDefinitionId = new StringBuilder(batch.getBatchType()).append(".").append(key).toString();
                    FieldDefinition fieldDef = fieldService.readFieldDefinition(fieldDefinitionId);
                    String fieldName = fieldDef.getFieldName();
                    String propertyName = fieldName;
                    if (bw.getPropertyValue(propertyName) instanceof CustomField) {
                        propertyName += StringConstants.DOT_VALUE;
                    }
                    String escapedFieldName = TangerineForm.escapeFieldName(fieldName);

                    if (fieldDef.getFieldType().equals(FieldType.PICKLIST)) {   // TODO: MULTI_PICKLIST, CODE, etc?
                        String newVal = fieldEntry.getValue();
                        final Picklist referencedPicklist = picklistItemService.getPicklist(fieldDef.getFieldName());
                        if (referencedPicklist != null) {
                            for (PicklistItem referencedItem : referencedPicklist.getActivePicklistItems()) {
                                if (referencedItem.getItemName().equals(newVal)) {
                                    newVal = referencedItem.getDefaultDisplayValue();
                                }
                            }
                        }
                        rowMap.put(escapedFieldName, newVal);

                    }
                    else {
                        rowMap.put(escapedFieldName, fieldEntry.getValue());
                    }
                }
                rowValues.add(rowMap);
            }
        }
        else {
            // if no entities updated, show the update fields instead
        }
        model.put(StringConstants.ROWS, rowValues);
        model.put(StringConstants.TOTAL_ROWS, totalRows);
        return model;
    }
}
