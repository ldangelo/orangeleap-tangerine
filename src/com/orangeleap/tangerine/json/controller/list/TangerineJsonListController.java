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

package com.orangeleap.tangerine.json.controller.list;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.HttpUtil;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.FieldHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.FieldHandlerHelper;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.ExtTypeHandler;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.apache.commons.lang.math.NumberUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TangerineJsonListController {

    @Resource(name = "pageCustomizationService")
    protected PageCustomizationService pageCustomizationService;

    @Resource(name = "fieldHandlerHelper")
    protected FieldHandlerHelper fieldHandlerHelper;

    @Resource(name = "tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    private static final String SORT_KEY_PREFIX = "a";
    private static final String PARENT_NODE = "_parent";
    private static final String LEAF_NODE = "_is_leaf";

    public void addListFieldsToMap(HttpServletRequest request, List<SectionField> sectionFields, List entities, List<Map<String, Object>> paramMapList, boolean useAliasName) {
        for (Object thisEntity : entities) {
            BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(thisEntity);

            Map<String, Object> paramMap = new HashMap<String, Object>();

            int x = 0;
            for (SectionField field : sectionFields) {
                String fieldPropertyName = field.getFieldPropertyName();
                if (beanWrapper.isReadableProperty(fieldPropertyName)) {
                    FieldHandler handler = fieldHandlerHelper.lookupFieldHandler(field.getFieldType());

                    Object displayValue = StringConstants.EMPTY;
                    if (beanWrapper.isReadableProperty(field.getFieldPropertyName())) {
                        Object fieldValue = beanWrapper.getPropertyValue(field.getFieldPropertyName());
                        if (fieldValue instanceof CustomField) {
                            fieldValue = ((CustomField) fieldValue).getValue();
                        }
                        if (field.getFieldPropertyName().equals(StringConstants.ID)) {
                            displayValue = fieldValue;
                        }
                        else {
                            displayValue = handler.resolveDisplayValue(request, PropertyAccessorFactory.forBeanPropertyAccess(thisEntity), field, fieldValue);
                        }
                    }
                    if (displayValue instanceof String) {
                        displayValue = HttpUtil.jsEscape((String) displayValue);

                        String extType = ExtTypeHandler.findExtType(beanWrapper.getPropertyType(field.getFieldPropertyName()));
                        if (ExtTypeHandler.EXT_BOOLEAN.equals(extType) && ("Y".equalsIgnoreCase((String) displayValue) ||
                                "yes".equalsIgnoreCase((String) displayValue) ||
                                "T".equalsIgnoreCase((String) displayValue) ||
                                "true".equalsIgnoreCase((String) displayValue))) {
                            displayValue = "true";
                        }
                    }
                    String key = useAliasName && ! StringConstants.ID.equals(fieldPropertyName) ? new StringBuilder(SORT_KEY_PREFIX).append(x++).toString() : TangerineForm.escapeFieldName(fieldPropertyName);
                    paramMap.put(key, displayValue);
                }
            }
            paramMapList.add(paramMap);
        }
    }

    public List<SectionField> findSectionFields(String listPageName) {
        List<SectionDefinition> sectionDefinitions = pageCustomizationService.readSectionDefinitionsByPageTypeRoles(PageType.valueOf(listPageName), tangerineUserHelper.lookupUserRoles());
        assert sectionDefinitions != null && sectionDefinitions.size() == 1;

        SectionDefinition sectionDef = sectionDefinitions.get(0);
        List<SectionField> sectionFields = pageCustomizationService.readSectionFieldsBySection(sectionDef);
        assert sectionFields != null && !sectionFields.isEmpty();
        return sectionFields;
    }

    protected void setParentNodeAttributes(List<Map<String, Object>> list, Map<Long,Long> parentToChildNodeCountMap, String parentPrefix) {
        for (Map<String, Object> objectMap : list) {
            if ( ! objectMap.isEmpty()) {
                objectMap.put(PARENT_NODE, null);
                if (parentToChildNodeCountMap.get((Long) objectMap.get(StringConstants.ID)) == null || parentToChildNodeCountMap.get((Long) objectMap.get(StringConstants.ID)) == 0) {
                    objectMap.put(LEAF_NODE, true);
                }
                else {
                    objectMap.put(LEAF_NODE, false);
                }
                objectMap.put(StringConstants.ID, new StringBuilder(parentPrefix).append("-").append(objectMap.get(StringConstants.ID)).toString());
            }
        }
    }

    protected void setChildNodeAttributes(List<Map<String, Object>> list, Long parentId, String parentPrefix, String childPrefix) {
        for (Map<String, Object> objectMap : list) {
            if ( ! objectMap.isEmpty()) {
                objectMap.put(PARENT_NODE, new StringBuilder(parentPrefix).append("-").append(parentId).toString());
                objectMap.put(LEAF_NODE, true);
                objectMap.put(StringConstants.ID, new StringBuilder(childPrefix).append("-").append(objectMap.get(StringConstants.ID)).toString());
            }
        }        
    }

    protected void resolveSortFieldName(List<SectionField> allFields, SortInfo sort) {
        List<SectionField> sectionFields = pageCustomizationService.getFieldsExceptId(allFields);
        String sortKey = sort.getSort();
        if (sortKey != null) {
            String sortIndex = sortKey.replaceFirst(SORT_KEY_PREFIX, StringConstants.EMPTY);
            if (NumberUtils.isNumber(sortIndex)) {
                int index = Integer.parseInt(sortIndex);
                if (index < sectionFields.size()) {
                    String fieldName = sectionFields.get(index).getFieldPropertyName();
                    sortKey = TangerineForm.escapeFieldName(fieldName);
                    sort.setSort(sortKey);
                }
            }
        }
    }

    protected long getNodeId(HttpServletRequest request) {
        String aNode = request.getParameter("anode");
        long id = 0;
        if (aNode != null) {
            String aNodeID = aNode.replaceAll(StringConstants.GIFT + "-", StringConstants.EMPTY);
            if (NumberUtils.isNumber(aNodeID)) {
                id = Long.parseLong(aNodeID);
            }
        }
        return id;
    }
}
