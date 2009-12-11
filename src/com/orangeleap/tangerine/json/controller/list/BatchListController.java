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

import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.PostBatchService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;
import com.orangeleap.tangerine.web.common.SortInfo;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.ExtTypeHandler;
import org.apache.commons.collections.set.UnmodifiableSet;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class BatchListController extends TangerineJsonListController {

    @Resource(name = "postBatchService")
    private PostBatchService postBatchService;

    @Resource(name = "constituentService")
    private ConstituentService constituentService;

    public static final String BATCH_TYPE = "batchType";
    public static final String BATCH_DESC = "batchDesc";
    public static final String EXECUTED = "executed";
    public static final String EXECUTED_DATE = "executedDate";
    public static final String EXECUTED_BY_USER = "executedByUser";
    public static final String CREATE_DATE = "createDate";

    private final Set<String> openBatchFields;
    private final Set<String> executedBatchFields;
    private final Set<String> errorBatchFields;

    @SuppressWarnings("unchecked")
    public BatchListController() {
        Set<String> fields = new LinkedHashSet<String>();
        fields.add(StringConstants.ID);
        fields.add(BATCH_TYPE);
        fields.add(BATCH_DESC);
        fields.add(CREATE_DATE);
        fields.add(EXECUTED);
        openBatchFields = UnmodifiableSet.decorate(fields);

        fields = new LinkedHashSet<String>();
        fields.add(StringConstants.ID);
        fields.add(BATCH_TYPE);
        fields.add(BATCH_DESC);
        fields.add(CREATE_DATE);
        fields.add(EXECUTED_DATE);
        fields.add(EXECUTED_BY_USER);
        fields.add(EXECUTED);
        executedBatchFields = UnmodifiableSet.decorate(fields);

        fields = new LinkedHashSet<String>();
        fields.add(StringConstants.ID);
        fields.add(BATCH_TYPE);
        fields.add(BATCH_DESC);
        fields.add(CREATE_DATE);
        fields.add(StringConstants.ERRORS);
        fields.add(EXECUTED);
        errorBatchFields = UnmodifiableSet.decorate(fields);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/batchList.json")
    public ModelMap getBatchList(HttpServletRequest request, String showBatchStatus, SortInfo sortInfo) {
        checkAccess(request, PageType.batch);
        final ModelMap model = new ModelMap();
        checkSortKey(sortInfo, showBatchStatus);
        setupMetaData(model, showBatchStatus, sortInfo);
        setupRows(model, showBatchStatus, sortInfo, request.getLocale());
        return model;
    }

    @SuppressWarnings("unchecked")
    private void setupRows(ModelMap model, String showBatchStatus, SortInfo sortInfo, Locale locale) {
        model.put(StringConstants.TOTAL_ROWS, postBatchService.countBatchesByStatus(showBatchStatus));

        final List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();

    	final List<PostBatch> batches = postBatchService.readBatchesByStatus(showBatchStatus, sortInfo, locale);
    	for (PostBatch batch : batches) {
            final Map<String, Object> map = new HashMap<String, Object>();
            final Set<String> fieldNames = findFieldNamesByStatus(showBatchStatus);
            BeanWrapper bean = PropertyAccessorFactory.forBeanPropertyAccess(batch);

            for (String thisFieldName : fieldNames) {
                if (bean.isReadableProperty(thisFieldName)) {
                    map.put(thisFieldName, bean.getPropertyValue(thisFieldName));
                }
            }
            rowList.add(map);
    	}
        model.put(StringConstants.ROWS, rowList);
    }

    @SuppressWarnings("unchecked")
    private void setupMetaData(final ModelMap model, final String showBatchStatus, final SortInfo sortInfo) {
        final Map<String, Object> metaDataMap = tangerineListHelper.initMetaData(sortInfo.getStart(), sortInfo.getLimit());

        final Map<String, String> sortInfoMap = new HashMap<String, String>();
        sortInfoMap.put(StringConstants.FIELD, sortInfo.getSort());
        sortInfoMap.put(StringConstants.DIRECTION, sortInfo.getDir());
        metaDataMap.put(StringConstants.SORT_INFO, sortInfoMap);

        setupFields(metaDataMap, showBatchStatus);
        model.put(StringConstants.META_DATA, metaDataMap);
    }

    /**
     * Check that the sort key is valid for the type of batch status we are inquiring about
     * @param sort
     * @param showBatchStatus
     */
    private void checkSortKey(final SortInfo sort, final String showBatchStatus) {
        if ((StringConstants.OPEN.equals(showBatchStatus) && ! openBatchFields.contains(sort.getSort())) ||
                (StringConstants.EXECUTED.equals(showBatchStatus) && ! executedBatchFields.contains(sort.getSort())) ||
                (StringConstants.ERRORS.equals(showBatchStatus) && ! errorBatchFields.contains(sort.getSort()))) {
            sort.setSort(StringConstants.ID);
            sort.setDir("ASC");
        }
    }

    private void setupFields(final Map<String, Object> metaDataMap, final String showBatchStatus) {
        final List<Map<String, Object>> fieldList = new ArrayList<Map<String, Object>>();

        final Set<String> fieldNames = findFieldNamesByStatus(showBatchStatus);
        for (String thisFieldName : fieldNames) {
            final Map<String, Object> fieldMap = new HashMap<String, Object>();

            fieldMap.put(StringConstants.NAME, thisFieldName);
            fieldMap.put(StringConstants.MAPPING, thisFieldName);

            String extType = ExtTypeHandler.EXT_STRING;
            if (StringConstants.ID.equals(thisFieldName)) {
                extType = ExtTypeHandler.EXT_INT;
            }
            else if (EXECUTED.equals(thisFieldName)) {
                extType = ExtTypeHandler.EXT_BOOLEAN;
            }
            else if (EXECUTED_DATE.equals(thisFieldName) || CREATE_DATE.equals(thisFieldName)) {
                extType = ExtTypeHandler.EXT_DATE;
                fieldMap.put(StringConstants.DATE_FORMAT, "Y-m-d H:i:s");
            }

            fieldMap.put(StringConstants.TYPE, extType);
            fieldMap.put(StringConstants.HEADER, TangerineMessageAccessor.getMessage(thisFieldName));

            fieldList.add(fieldMap);
        }
        metaDataMap.put(StringConstants.FIELDS, fieldList);
    }

    private Set<String> findFieldNamesByStatus(String showBatchStatus) {
        return StringConstants.EXECUTED.equals(showBatchStatus) ? executedBatchFields :
                (StringConstants.ERRORS.equals(showBatchStatus) ? errorBatchFields : openBatchFields);
    }
}
