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

package com.orangeleap.tangerine.json.controller.batch;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.Segmentation;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.json.controller.list.TangerineJsonListController;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.PostBatchService;
import com.orangeleap.tangerine.service.customization.FieldService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;
import com.orangeleap.tangerine.web.common.SortInfo;
import com.orangeleap.tangerine.web.customization.tag.fields.SectionFieldTag;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.ExtTypeHandler;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BatchSelectionController extends TangerineJsonListController {

    protected final Log logger = OLLogger.getLog(getClass());
    public static final String BATCH_FIELDS = "BatchFields";

    @Resource(name = "postBatchService")
    private PostBatchService postBatchService;

    @Resource(name = "giftService")
    private GiftService giftService;

    @Resource(name = "picklistItemService")
    private PicklistItemService picklistItemService;

    @Resource(name = "fieldService")
    private FieldService fieldService;

    @SuppressWarnings("unchecked")
    public void checkAccess(HttpServletRequest request) {
        Map<String, AccessType> pageAccess = (Map<String, AccessType>) WebUtils.getSessionAttribute(request, "pageAccess");
        if (pageAccess.get("/postbatch.htm") != AccessType.ALLOWED) {
            throw new RuntimeException("You are not authorized to access this page"); // TODO: use invalid access exception and move to filter
        }
    }

    private String resolveSegmentationFieldName(String key) {
        String resolvedName = key;
        if (StringConstants.NAME.equals(key)) {
            resolvedName = "reportName";
        }
        else if ("desc".equals(key)) {
            resolvedName = "reportComment";
        }
        else if ("count".equals(key)) {
            resolvedName = "resultCount";
        }
        else if ("lastDt".equals(key)) {
            resolvedName = "lastRunDateTime";
        }
        else if ("lastUser".equals(key)) {
            resolvedName = "lastRunByUserName";
        }
        return resolvedName;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping("/findSegmentations.json")
    public ModelMap findSegmentations(String batchType, SortInfo sort) {
        if (logger.isTraceEnabled()) {
            logger.trace("findSegmentations: batchType = " + batchType + " sortInfo = " + sort);
        }
        final ModelMap model = new ModelMap();

        final List<Segmentation> segmentations = postBatchService.findSegmentations(batchType, resolveSegmentationFieldName(sort.getSort()), sort.getDir(),
                sort.getStart(), sort.getLimit());
        final List<Map<String, Object>> returnList = addSegmentationsToMap(segmentations);
        model.put(StringConstants.ROWS, returnList);
        model.put(StringConstants.TOTAL_ROWS, returnList.size());
        return model;        
    }

    private List<Map<String, Object>> addSegmentationsToMap(final List<Segmentation> segmentations) {
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        if (segmentations != null) {
            final SimpleDateFormat sdf = new SimpleDateFormat(StringConstants.EXT_DATE_FORMAT);
            for (Segmentation segmentation : segmentations) {
                Map<String, Object> returnMap = new HashMap<String, Object>();
                returnMap.put(StringConstants.ID, segmentation.getId());
                returnMap.put(StringConstants.NAME, segmentation.getName());
                returnMap.put("desc", segmentation.getDescription());
                returnMap.put("count", segmentation.getCount());
                returnMap.put("lastDt", segmentation.getLastRunDate() == null ? null : sdf.format(segmentation.getLastRunDate()));
                returnMap.put("lastUser", segmentation.getLastRunByUser());
                returnMap.put("picked", Boolean.TRUE);  // TODO: fix
                returnList.add(returnMap);
            }
        }
        return returnList;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/confirmChoices.json")
    public ModelMap findRowsForSegmentations(HttpServletRequest request, String ids, String batchType, SortInfo sort) {
        if (logger.isTraceEnabled()) {
            logger.trace("findRowsForSegmentations: ids = " + ids + " batchType = " + batchType + " sort = " + sort);
        }
        final ModelMap model = new ModelMap();
        if (StringConstants.GIFT.equals(batchType)) {
            appendModelForGift(request, ids, sort, model);
        }
        model.put(StringConstants.SUCCESS, Boolean.TRUE);
        return model;
    }

    @SuppressWarnings("unchecked")
    private void appendModelForGift(HttpServletRequest request, String ids, SortInfo sort, ModelMap modelMap) {
        /* MetaData */
        final Map<String, Object> metaDataMap = new LinkedHashMap<String, Object>();
        metaDataMap.put(StringConstants.ID_PROPERTY, StringConstants.ID);
        metaDataMap.put(StringConstants.ROOT, StringConstants.ROWS);
        metaDataMap.put(StringConstants.TOTAL_PROPERTY, StringConstants.TOTAL_ROWS);
        metaDataMap.put(StringConstants.SUCCESS_PROPERTY, StringConstants.SUCCESS);
        metaDataMap.put(StringConstants.START, sort.getStart());
        metaDataMap.put(StringConstants.LIMIT, sort.getLimit());

        final List<SectionField> allFields = findSectionFields("giftList");
        final List<SectionField> fieldsExceptId = pageCustomizationService.getFieldsExceptId(allFields);

        final BeanWrapper bw = createDefaultGift();
        final List<Map<String, Object>> fieldList = new ArrayList<Map<String, Object>>();

        addIdConstituentIdFields(fieldList, bw);
        for (SectionField sectionFld : fieldsExceptId) {
            final Map<String, Object> fieldMap = new HashMap<String, Object>();
            String escapedFieldName = TangerineForm.escapeFieldName(sectionFld.getFieldPropertyName());
            fieldMap.put(StringConstants.NAME, escapedFieldName);
            fieldMap.put(StringConstants.MAPPING, escapedFieldName);
            String extType = ExtTypeHandler.findExtType(bw.getPropertyType(sectionFld.getFieldPropertyName()));
            fieldMap.put(StringConstants.TYPE, extType);
            fieldMap.put(StringConstants.HEADER, sectionFld.getFieldDefinition().getDefaultLabel());

            if (StringConstants.DATE.equals(extType)) {
                String format;
                if (FieldType.CC_EXPIRATION.equals(sectionFld.getFieldType()) || FieldType.CC_EXPIRATION_DISPLAY.equals(sectionFld.getFieldType())) {
                    format = "m-d-Y";
                }
                else {
                    format = "Y-m-d H:i:s";
                }
                fieldMap.put(StringConstants.DATE_FORMAT, format);
            }
            fieldList.add(fieldMap);
        }
        metaDataMap.put(StringConstants.FIELDS, fieldList);

        final Map<String, String> sortInfoMap = new HashMap<String, String>();
        if ( ! StringUtils.hasText(sort.getSort())) {
            sort.setSort(TangerineForm.escapeFieldName(fieldsExceptId.get(0).getFieldPropertyName()));
            sort.setDir(SectionFieldTag.getInitDirection(fieldsExceptId));
        }

        sortInfoMap.put(StringConstants.FIELD, sort.getSort());
        sortInfoMap.put(StringConstants.DIRECTION, sort.getDir());
        metaDataMap.put(StringConstants.SORT_INFO, sortInfoMap);

        modelMap.put(StringConstants.META_DATA, metaDataMap);

        final List<Gift> gifts = giftService.readGiftsBySegmentationReportIds(StringUtils.commaDelimitedListToSet(ids), sort, request.getLocale());
        modelMap.put(StringConstants.TOTAL_ROWS, gifts.size());

        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        addListFieldsToMap(request, allFields, gifts, rowList, false); // this needs to be 'allFields' to include the 'id' property
        modelMap.put(StringConstants.ROWS, rowList);
    }

    private void addIdConstituentIdFields(final List<Map<String, Object>> fieldList, final BeanWrapper bw) {
        final Map<String, Object> idMap = new HashMap<String, Object>();
        idMap.put(StringConstants.NAME, StringConstants.ID);
        idMap.put(StringConstants.MAPPING, StringConstants.ID);
        idMap.put(StringConstants.TYPE, "int");
        idMap.put(StringConstants.HEADER, TangerineMessageAccessor.getMessage(StringConstants.ID));
        fieldList.add(idMap);

        if (bw.isReadableProperty(StringConstants.CONSTITUENT) || bw.isReadableProperty(StringConstants.CONSTITUENT_ID)) {
            final Map<String, Object> constituentIdMap = new HashMap<String, Object>();
            constituentIdMap.put(StringConstants.NAME, StringConstants.CONSTITUENT_ID);
            constituentIdMap.put(StringConstants.MAPPING, StringConstants.CONSTITUENT_ID);
            constituentIdMap.put(StringConstants.TYPE, "string");
            constituentIdMap.put(StringConstants.HEADER, TangerineMessageAccessor.getMessage(StringConstants.CONSTITUENT_ID));
            fieldList.add(constituentIdMap);
        }
    }

    private BeanWrapper createDefaultGift() {
        // Mock objects for introspection
        Gift gift = new Gift();
        Constituent constituent = new Constituent(0L, new Site());
        gift.setConstituent(constituent);
        gift.setPaymentSource(new PaymentSource(constituent));
        return PropertyAccessorFactory.forBeanPropertyAccess(gift);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/findBatchUpdateFields.json")
    public ModelMap findBatchUpdateFields(final String batchType) {
        if (logger.isTraceEnabled()) {
            logger.trace("findBatchUpdateFields: batchType = " + batchType);
        }
        final String picklistNameId = new StringBuilder(batchType).append(BATCH_FIELDS).toString();
        final Picklist picklist = picklistItemService.getPicklist(picklistNameId);
        final ModelMap modelMap = new ModelMap();
        final List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        
        if (picklist != null) {
            for (PicklistItem item : picklist.getActivePicklistItems()) {
                String fieldDefinitionId = new StringBuilder(batchType).append(".").append(item.getDefaultDisplayValue()).toString();
                FieldDefinition fieldDef = fieldService.readFieldDefinition(fieldDefinitionId);
                if (fieldDef != null) {
                    FieldType fieldType = fieldDef.getFieldType();
                    if (fieldType.equals(FieldType.PICKLIST)) {
                        final Picklist referencedPicklist = picklistItemService.getPicklist(item.getDefaultDisplayValue());
                        if (referencedPicklist != null) {
                            final List<Map<String, String>> referencedItemList = new ArrayList<Map<String, String>>();
                            for (PicklistItem referencedItem : referencedPicklist.getActivePicklistItems()) {
                                final Map<String, String> referencedItemMap = new HashMap<String, String>();
                                referencedItemMap.put("itemName", referencedItem.getItemName());
                                referencedItemMap.put("displayVal", referencedItem.getDefaultDisplayValue());
                                referencedItemList.add(referencedItemMap);
                            }
                            modelMap.put(new StringBuilder(item.getDefaultDisplayValue()).append("-Data").toString(), referencedItemList);
                        }
                    }
                    final Map<String, Object> map = new HashMap<String, Object>();
                    map.put(StringConstants.NAME, item.getDefaultDisplayValue());
                    map.put(StringConstants.DESC, fieldDef.getDefaultLabel());
                    map.put(StringConstants.TYPE, fieldType.name().toLowerCase());
                    map.put(StringConstants.VALUE, StringConstants.EMPTY); // TODO: re-enter value
                    map.put(StringConstants.SELECTED, Boolean.FALSE); // TODO: redisplay for existing
                    returnList.add(map);
                }
            }
        }

        modelMap.put(StringConstants.ROWS, returnList);
        modelMap.put(StringConstants.TOTAL_ROWS, returnList.size());
        return modelMap;
    }
}
