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
import com.orangeleap.tangerine.service.CommunicationHistoryService;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;
import com.orangeleap.tangerine.web.common.SortInfo;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.ExtTypeHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.FieldHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.webflow.execution.RequestContext;

@Component("reviewBatchAction")
public class ReviewBatchAction extends EditBatchAction {

    protected final Log logger = OLLogger.getLog(getClass());

	@Resource(name = "communicationHistoryService")
	protected CommunicationHistoryService communicationHistoryService;

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
            dataMap.put("reviewBatchDesc", (String) escapeStringValues(batch.getBatchDesc()));
            dataMap.put("reviewBatchType", TangerineMessageAccessor.getMessage(batch.getBatchType()));
	        dataMap.put("reviewCriteriaFields", batch.isForTouchPoints() ? TangerineMessageAccessor.getMessage("touchPointFields") : TangerineMessageAccessor.getMessage("batchTypeFields"));
	        dataMap.put("hiddenBatchType", batch.getBatchType());
	        dataMap.put("hiddenForTouchPoints", StringConstants.EMPTY + batch.isForTouchPoints());
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
        final ModelMap model = new ModelMap();
        final List<Map<String, Object>> fieldList = new ArrayList<Map<String, Object>>();

        for (Map.Entry<String, String> updateFieldEntry : batch.getUpdateFields().entrySet()) {
            final Map<String, Object> fieldMap = new HashMap<String, Object>();
	        String fieldDefinitionId = resolveFieldDefinitionId(batch, updateFieldEntry.getKey());
            FieldDefinition fieldDef = fieldService.resolveFieldDefinition(fieldDefinitionId);
            if (fieldDef != null) {
                fieldMap.put(StringConstants.NAME, escapeStringValues(fieldDef.getDefaultLabel()));

                Object value = resolveDisplayValue(fieldDef, updateFieldEntry.getValue(), true);
                fieldMap.put(StringConstants.VALUE, escapeStringValues(value));
                fieldList.add(fieldMap);
            }
        }
        model.put(StringConstants.SUCCESS, Boolean.TRUE);
        model.put(StringConstants.ROWS, fieldList);
        model.put(StringConstants.TOTAL_ROWS, fieldList.size());
        return model;
    }

	private Object resolveDisplayValue(FieldDefinition fieldDef, Object value, boolean reformatDate) {
		if (value != null) {
			if (FieldType.PICKLIST.equals(fieldDef.getFieldType())) {  // TODO: MULTI_PICKLIST, CODE, etc?
				final Picklist picklist = picklistItemService.getPicklist(fieldDef.getFieldName());
				if (picklist != null) {
					for (PicklistItem item : picklist.getActivePicklistItems()) {
						if (value.equals(item.getItemName())) {
							value = item.getDefaultDisplayValue();
							break;
						}
					}
				}
			}
			else if (FieldType.QUERY_LOOKUP.equals(fieldDef.getFieldType())) {
				FieldHandler handler = fieldHandlerHelper.lookupFieldHandler(fieldDef.getFieldType());
				value = handler.resolveDisplayValue(fieldDef, escapeStringValues(value));
			}
			else if (reformatDate) {
				if (FieldType.DATE.equals(fieldDef.getFieldType())) {
					value = formatDateToDesiredFormat(value.toString(), StringConstants.MM_DD_YYYY_FORMAT); // TODO: get date format based on locale
				}
				else if (FieldType.DATE_TIME.equals(fieldDef.getFieldType())) {
					value = formatDateToDesiredFormat(value.toString(), StringConstants.MM_DD_YYYY_HH_MM_SS_FORMAT_1); // TODO: get date format based on locale
				}
			}
		}
		return value;
	}

    private String formatDateToDesiredFormat(String value, String desiredFormat) {
        try {
            Date date = DateUtils.parseDate(value, new String[] { StringConstants.YYYY_MM_DD_HH_MM_SS_FORMAT_1,
                    StringConstants.YYYY_MM_DD_HH_MM_SS_FORMAT_2, StringConstants.YYYY_MM_DD_FORMAT,
                    StringConstants.MM_DD_YYYY_HH_MM_SS_FORMAT_1, StringConstants.MM_DD_YYYY_HH_MM_SS_FORMAT_2,
                    StringConstants.MM_DD_YYYY_FORMAT});
            value = new SimpleDateFormat(desiredFormat).format(date);
        }
        catch (Exception ex) {
            if (logger.isWarnEnabled()) {
                logger.warn("formatDateToDesiredFormat: could not parse date = " + value);
            }
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    public ModelMap reviewStep3(final RequestContext flowRequestContext) {
        if (logger.isTraceEnabled()) {
            logger.trace("reviewStep3: ");
        }
        tangerineListHelper.checkAccess(getRequest(flowRequestContext), PageType.createBatch); // TODO: do as annotation
        final PostBatch batch = getBatchFromFlowScope(flowRequestContext);
        final SortInfo sortInfo = getSortInfo(flowRequestContext);
        final ModelMap model = new ModelMap();

        final BeanWrapper bean = createDefaultEntity(batch);
        final Map<String, Object> metaDataMap = tangerineListHelper.initMetaData(sortInfo.getStart(), sortInfo.getLimit());
        initSortInfoMetaData(bean, sortInfo, metaDataMap);
        final List<Map<String, Object>> rowValues = new ArrayList<Map<String, Object>>();

        final List<Map<String, Object>> fieldList = new ArrayList<Map<String, Object>>();
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put(StringConstants.NAME, StringConstants.ID);
        fieldMap.put(StringConstants.MAPPING, StringConstants.ID);
        fieldMap.put(StringConstants.TYPE, ExtTypeHandler.EXT_INT);
        fieldMap.put(StringConstants.HEADER, escapeStringValues(TangerineMessageAccessor.getMessage(StringConstants.ID)));
        fieldList.add(fieldMap);

		initBatchUpdateFields(batch, fieldList);

        fieldMap = new HashMap<String, Object>();
        fieldMap.put(StringConstants.NAME, StringConstants.CONSTITUENT_ID);
        fieldMap.put(StringConstants.MAPPING, StringConstants.CONSTITUENT_ID);
        fieldMap.put(StringConstants.TYPE, ExtTypeHandler.EXT_INT);
        fieldMap.put(StringConstants.HEADER, escapeStringValues(TangerineMessageAccessor.getMessage(StringConstants.CONSTITUENT_ID)));
        fieldList.add(fieldMap);

        metaDataMap.put(StringConstants.FIELDS, fieldList);
        model.put(StringConstants.META_DATA, metaDataMap);

        unescapeSortField(sortInfo);
        
        List<? extends AbstractCustomizableEntity> entities = null;
        int totalRows = 0;
	    if (batch.isForTouchPoints()) {
		    entities = communicationHistoryService.readCommunicationHistoryByBatchId(batch.getId(), sortInfo, getRequest(flowRequestContext).getLocale());
		    totalRows = communicationHistoryService.readCountByBatchId(batch.getId());
	    }
	    else {
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
	    }
        if (entities != null && ! entities.isEmpty()) {
            for (AbstractCustomizableEntity entity : entities) {
                final BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(entity);
                final Map<String, Object> rowMap = new HashMap<String, Object>();
                rowMap.put(StringConstants.ID, bw.getPropertyValue(StringConstants.ID));
                rowMap.put(StringConstants.CONSTITUENT_ID, bw.getPropertyValue(StringConstants.CONSTITUENT_ID));

                for (Map.Entry<String, String> fieldEntry : batch.getUpdateFields().entrySet()) {
                    String key = fieldEntry.getKey();
                    // the batchType + key is the fieldDefinitionId; we need to resolve the fieldName
                    String fieldDefinitionId = resolveFieldDefinitionId(batch, key);
                    FieldDefinition fieldDef = fieldService.resolveFieldDefinition(fieldDefinitionId);
	                
	                if (fieldDef != null) {
						String fieldName = fieldDef.getFieldName();
						String propertyName = fieldName;
						if (bw.getPropertyValue(propertyName) instanceof CustomField) {
							propertyName += StringConstants.DOT_VALUE;
						}
						String escapedFieldName = TangerineForm.escapeFieldName(fieldName);
		                rowMap.put(escapedFieldName, escapeStringValues(resolveDisplayValue(fieldDef, fieldEntry.getValue(), false)));
	                }
                }
                rowValues.add(rowMap);
            }
            addConstituentIdsToRows(rowValues, entities);
        }
        model.put(StringConstants.ROWS, rowValues);
        model.put(StringConstants.TOTAL_ROWS, totalRows);
        return model;
    }
}
