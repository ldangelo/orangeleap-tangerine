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

import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.json.controller.list.TangerineJsonListController;
import com.orangeleap.tangerine.service.PostBatchService;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class DeleteBatchController extends TangerineJsonListController {

    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "postBatchService")
    private PostBatchService postBatchService;
    
    @SuppressWarnings("unchecked")
    @RequestMapping("/deleteBatch.json")
    public ModelMap deleteBatch(final HttpServletRequest request, final Long batchId) {
        if (logger.isTraceEnabled()) {
            logger.trace("deleteBatch: batchId = " + batchId);
        }
        checkAccess(request, PageType.deleteBatch);
        PostBatch batch = postBatchService.readBatch(batchId);
        postBatchService.deleteBatch(batch);
        final ModelMap model = new ModelMap();
        model.put(StringConstants.SUCCESS, Boolean.TRUE);
        return model;
    }
}
