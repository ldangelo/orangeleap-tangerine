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
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.SortInfo;
import com.orangeleap.tangerine.web.common.TangerineListHelper;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.math.NumberUtils;

public abstract class TangerineJsonListController {

    @Resource(name = "pageCustomizationService")
    protected PageCustomizationService pageCustomizationService;

    @Resource(name = "tangerineListHelper")
    protected TangerineListHelper tangerineListHelper;

    private static final String PARENT_NODE = "_parent";
    private static final String LEAF_NODE = "_is_leaf";

    @SuppressWarnings("unchecked")
    public boolean isAccessAllowed(HttpServletRequest request, PageType pageType) {
        return tangerineListHelper.isAccessAllowed(request, pageType);
    }

    public void checkAccess(HttpServletRequest request, PageType pageType) {
        tangerineListHelper.checkAccess(request, pageType);
    }
    
    public void addListFieldsToMap(HttpServletRequest request, List<SectionField> sectionFields, List entities,
                                   List<Map<String, Object>> paramMapList, boolean useAliasName, boolean useAliasId) {
        tangerineListHelper.addListFieldsToMap(request, sectionFields, entities, paramMapList, useAliasName, useAliasId);
    }

    public List<SectionField> findSectionFields(String listPageName) {
        return tangerineListHelper.findSectionFields(listPageName);
    }

    protected void setParentNodeAttributes(List<Map<String, Object>> list, Map<Long, Long> parentToChildNodeCountMap, String parentPrefix, boolean useAliasId) {
	    int sequence = 0;
        for (Map<String, Object> objectMap : list) {
            if ( ! objectMap.isEmpty()) {
	            Long thisId = getIdProperty(objectMap, useAliasId);

                objectMap.put(PARENT_NODE, null);
                if (parentToChildNodeCountMap.get(thisId) == null || parentToChildNodeCountMap.get(thisId) == 0) {
                    objectMap.put(LEAF_NODE, true);
                }
                else {
                    objectMap.put(LEAF_NODE, false);
                }
	            String idValue;
	            if (useAliasId) {
		            idValue = new StringBuilder(parentPrefix).append(":").append(sequence).append("-").append(thisId).toString();
	            }
	            else {
		            idValue = new StringBuilder(parentPrefix).append("-").append(thisId).toString();
	            }
                objectMap.put(StringConstants.ID, idValue);
            }
	        sequence++;
        }
    }

    protected void setChildNodeAttributes(List<Map<String, Object>> list, Long parentId, String parentPrefix, String childPrefix, boolean useAliasId) {
	    int sequence = 0;
        for (Map<String, Object> objectMap : list) {
            if ( ! objectMap.isEmpty()) {
                objectMap.put(PARENT_NODE, new StringBuilder(parentPrefix).append("-").append(parentId).toString());
                objectMap.put(LEAF_NODE, true);

	            Long thisId = getIdProperty(objectMap, useAliasId);
	            String idValue;
	            if (useAliasId) {
		            idValue = new StringBuilder(parentPrefix).append("-").append(childPrefix).append(":").append(sequence).append("-").append(thisId).toString();
	            }
	            else {
		            idValue = new StringBuilder(childPrefix).append("-").append(thisId).toString();
	            }
                objectMap.put(StringConstants.ID, idValue);
            }
	        sequence++;
        }
    }

    protected void resolveSortFieldName(List<SectionField> allFields, SortInfo sort) {
        List<SectionField> sectionFields = pageCustomizationService.getFieldsExceptId(allFields);
        String sortKey = sort.getSort();
        if (sortKey != null) {
            String sortIndex = sortKey.replaceFirst(TangerineListHelper.SORT_KEY_PREFIX, StringConstants.EMPTY);
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

    protected long getNodeId(HttpServletRequest request, String prefix, boolean useAliasId) {
        String aNode = request.getParameter("anode");
        long id = 0;
        if (aNode != null) {
	        String regEx;
	        if (useAliasId) {
		        regEx = new StringBuilder(prefix).append("\\:\\d+").append("-").toString();
	        }
	        else {
		        regEx = new StringBuilder(prefix).append("-").toString();
	        }
            String aNodeID = aNode.replaceAll(regEx, StringConstants.EMPTY);
            if (NumberUtils.isNumber(aNodeID)) {
                id = Long.parseLong(aNodeID);
            }
        }
        return id;
    }

	protected String getPrefix(HttpServletRequest request) {
		String prefix = null;
		String aNode = request.getParameter("anode");
		int index = aNode.indexOf("-");
		if (index > -1) {
			prefix = aNode.substring(0, index);
		}
		return prefix;
	}

	private Long getIdProperty(final Map<String, Object> objectMap, final boolean useAliasName) {
		String idProperty = (useAliasName ? StringConstants.ALIAS_ID : StringConstants.ID);
		return (Long) objectMap.get(idProperty);
	}
}
