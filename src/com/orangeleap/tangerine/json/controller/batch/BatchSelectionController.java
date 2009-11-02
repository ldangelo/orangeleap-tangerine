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

import com.orangeleap.tangerine.domain.Segmentation;
import com.orangeleap.tangerine.service.PostBatchService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BatchSelectionController {

    protected final Log logger = OLLogger.getLog(getClass());
    
    @Resource(name = "postBatchService")
    private PostBatchService postBatchService;

    @SuppressWarnings("unchecked")
    public void checkAccess(HttpServletRequest request) {
        Map<String, AccessType> pageAccess = (Map<String, AccessType>) WebUtils.getSessionAttribute(request, "pageAccess");
        if (pageAccess.get("/postbatch.htm") != AccessType.ALLOWED) {
            throw new RuntimeException("You are not authorized to access this page"); // TODO: use invalid access exception and move to filter
        }
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping("/findSegmentations.json")
    public ModelMap findSegmentations(String batchType) {
        if (logger.isTraceEnabled()) {
            logger.trace("findSegmentations: batchType = " + batchType);
        }
        final ModelMap model = new ModelMap();
        final List<Segmentation> segmentations = postBatchService.findSegmentations(batchType);
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
                returnList.add(returnMap);
            }
        }
        return returnList;
    }
}
