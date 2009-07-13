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

package com.orangeleap.tangerine.controller.postbatch;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.service.PostBatchService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.util.OLLogger;

public class PostBatchListController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "postBatchService")
    private PostBatchService postBatchService;

    @SuppressWarnings("unchecked")
    public static boolean accessAllowed(HttpServletRequest request) {
        Map<String, AccessType> pageAccess = (Map<String, AccessType>) WebUtils.getSessionAttribute(request, "pageAccess");
        return pageAccess.get("/postbatch.htm") == AccessType.ALLOWED;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {

        if (!accessAllowed(request)) return null;

        ModelAndView mav = super.showForm(request, response, errors, controlModel);

        List<PostBatch> postbatchs = getPostBatchs(request);

        mav.addObject("postbatchs", postbatchs);
        return mav;

    }


    @Override
    public Object formBackingObject(HttpServletRequest request) throws ServletException {
        return getPostBatchs(request);
    }

    private List<PostBatch> getPostBatchs(HttpServletRequest request) {
         return postBatchService.listBatchs();
    }

    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {

        if (!accessAllowed(request)) return null;

        Long batchId = Long.parseLong(request.getParameter("postbatchid"));

        PostBatch postbatch = postBatchService.readBatch(batchId);
        if (postbatch == null) return null;

        return new ModelAndView("redirect:/postbatch.htm?id=" + batchId);
        	
    }



}