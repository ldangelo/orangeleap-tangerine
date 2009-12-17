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
import com.orangeleap.tangerine.util.TangerineMessageAccessor;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class ExecuteBatchController extends TangerineJsonListController {

    protected final Log logger = OLLogger.getLog(getClass());
    public static final String HAS_BATCH_ERRORS = "hasBatchErrors";

    @Resource(name = "postBatchService")
    private PostBatchService postBatchService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/executeBatch.json")
    public ModelMap executeBatch(final HttpServletRequest request, final Long batchId) {
        if (logger.isTraceEnabled()) {
            logger.trace("executeBatch: batchId = " + batchId);
        }
        checkAccess(request, PageType.executeBatch);
        final PostBatch batch = postBatchService.readBatch(batchId);

        final ModelMap model = new ModelMap();

        // check for global batch errors first before executing
        final Set<String> errorMsgs = new TreeSet<String>();
        if (batch == null) {
            errorMsgs.add(TangerineMessageAccessor.getMessage("invalidBatchId"));
        }
        else {
            postBatchService.checkPreExecuteBatchErrors(batch);
            errorMsgs.addAll(batch.getUpdateErrors());
        }

        if (errorMsgs.isEmpty()) {
            // allow execution of the batch if no errors are found
            PostBatch executedBatch = postBatchService.executeBatch(batch);
            if (executedBatch.getErrorBatchId() != null && executedBatch.getErrorBatchId() > 0) {
                model.put("errorBatchId", executedBatch.getErrorBatchId());
            }
            model.put(HAS_BATCH_ERRORS, Boolean.FALSE);
        }
        else {
            model.put("errorMsgs", errorMsgs);
            model.put(HAS_BATCH_ERRORS, Boolean.TRUE);
        }

        model.put(StringConstants.SUCCESS, Boolean.TRUE);
        return model;
    }
}