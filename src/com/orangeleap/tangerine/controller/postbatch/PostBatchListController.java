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

import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.PostBatchService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class PostBatchListController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "postBatchService")
    private PostBatchService postBatchService;

    @Resource(name = "constituentService")
    private ConstituentService constituentService;

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

        List<PostBatch> postbatches = getPostBatchs(request);
        mav.addObject("postbatches", postbatches);
        return mav;

    }


    @Override
    public Object formBackingObject(HttpServletRequest request) throws ServletException {
        return getPostBatchs(request);
    }

    private List<PostBatch> getPostBatchs(HttpServletRequest request) {
    	List<PostBatch> list = postBatchService.listBatchs();
    	for (PostBatch b : list) {
    		Long id = b.getExecutedById();
    		if (id != null) {
    			String loginid = constituentService.readConstituentById(id).getLoginId();
    			b.setCustomFieldValue("loginid", loginid); // set just for the ui to use, not saved
    		}
    	}
    	return list;
    }

    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {

    	if (!accessAllowed(request)) return null;

        Long batchId = Long.parseLong(request.getParameter("id"));
        boolean update = "true".equals(request.getParameter("update"));
        boolean delete = "true".equals(request.getParameter("delete"));

        PostBatch postbatch = postBatchService.readBatch(batchId);
        if (postbatch == null) return null;
        
        ModelAndView mav = super.onSubmit(request, response, command, errors);
        if (update) {
        	try {
        		postBatchService.updateBatch(postbatch);
            	mav.addObject("errormessage", "PostBatch successfully updated.");
        	} catch (Exception e) {
            	mav.addObject("errormessage", e.getMessage());
        	}
        } else if (delete) {
        	try {
        		postBatchService.deleteBatch(postbatch);
            	mav.addObject("errormessage", "PostBatch successfully deleted.");
        	} catch (Exception e) {
            	mav.addObject("errormessage", e.getMessage());
        	}
        } else {
        	mav = new ModelAndView("redirect:/postbatch.htm?id=" + batchId);
        }
        
        List<PostBatch> postbatches = getPostBatchs(request);
        mav.addObject("postbatches", postbatches);
        return mav;
        	
    }



}