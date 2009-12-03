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
import com.orangeleap.tangerine.domain.paymentInfo.AbstractPaymentInfoEntity;
import com.orangeleap.tangerine.service.PostBatchService;
import com.orangeleap.tangerine.service.impl.PostBatchServiceImpl;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PostBatchFormController extends SimpleFormController {

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

        List<AbstractPaymentInfoEntity> gifts = new ArrayList<AbstractPaymentInfoEntity>();
        PostBatch postbatch = getPostBatch(request);
        if (!postbatch.getBatchType().equals("gift")) {
        	gifts = postBatchService.getBatchSelectionList(postbatch);
        }

        mav.addObject("gifts", gifts);
        mav.addObject("postbatch", postbatch);
        mav.addObject("allowedSelectFields", postBatchService.readAllowedGiftSelectFields());
        mav.addObject("allowedUpdateFields", postBatchService.readAllowedGiftUpdateFields());
        return mav;

    }

    private PostBatch getNewPostBatch() {
        PostBatch postbatch = new PostBatch();
        postbatch.setBatchType("gift");
        DateFormat formatter = new SimpleDateFormat(PostBatchServiceImpl.DATE_FORMAT);
        String sdate = formatter.format(new java.util.Date());
        postbatch.setBatchDesc("PostBatch for " + sdate);
        checkDefaults(postbatch);
        return postbatch;
    }
    
    // Add some default field settings...
    private void checkDefaults(PostBatch postbatch) {
//        if (postbatch.getWhereConditions().size() == 0) {
//        	postbatch.getWhereConditions().put(PostBatchServiceImpl.POSTED, "false");
//        	if (postbatch.getEntity().equals("gift")) postbatch.getWhereConditions().put(PostBatchServiceImpl.STATUS, "Paid");
//        }
        if (postbatch.getUpdateFields().size() == 0) {
	        DateFormat formatter = new SimpleDateFormat(PostBatchServiceImpl.DATE_FORMAT);
	        String sdate = formatter.format(new java.util.Date());
	        postbatch.getUpdateFields().put(PostBatchServiceImpl.POSTED_DATE, sdate);
        }
    }


    @Override
    public Object formBackingObject(HttpServletRequest request) throws ServletException {
        return getPostBatch(request);
    }

    private PostBatch getPostBatch(HttpServletRequest request) {
        String sid = request.getParameter("id");
        PostBatch postbatch = null;
        if (sid != null && sid.trim().length() > 0) {
            postbatch = postBatchService.readBatch(new Long(sid));
        }
        if (postbatch == null) postbatch = getNewPostBatch();
        return postbatch;
    }


    // First they set the selection criteria and "save" which generates the list of gifts matching the criteria to review.
    // Once they have the criteria they way they want it, they "post" which processes the updates to the list of selected items.
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {

        if (!accessAllowed(request)) return null;

        PostBatch requestPostbatch = (PostBatch) command;
        // Read existing
        Long batchId = requestPostbatch.getId();
        PostBatch postbatch = postBatchService.readBatch(batchId);
        if (postbatch == null) postbatch = getNewPostBatch();
        if (postbatch.isExecuted()) return null; // selection criteria and record set editing not allowed once updated.

        boolean isGift = PostBatchServiceImpl.GIFT.equals(postbatch.getBatchType());
        String errormessage = "";
        List<AbstractPaymentInfoEntity> gifts = new ArrayList<AbstractPaymentInfoEntity>();

        try {

            // User can only edit the description/type and the list of select/update fields - the rest of the fields are read-only
            postbatch.setBatchDesc(requestPostbatch.getBatchDesc());
//            postbatch.setEntity(requestPostbatch.getBatch());

//            readFields(request, postbatch.getWhereConditions(), "sf");
            readFields(request, postbatch.getUpdateFields(), "uf");
            validateFields(postbatch);
            checkDefaults(postbatch); // make sure they didnt delete all selection criteria or update fields.
            postbatch = postBatchService.maintainBatch(postbatch);
            gifts = postBatchService.createBatchSelectionList(postbatch);  // throw exception if selection set too large.
            if (!isGift && gifts.size() == 0) errormessage = "No matches.";
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            errormessage = e.getMessage();
        }

        ModelAndView mav = new ModelAndView(getSuccessView());
        mav.addObject("errormessage", errormessage);
        mav.addObject("gifts", gifts);
        mav.addObject("postbatch", postbatch);
        mav.addObject("allowedSelectFields", postBatchService.readAllowedGiftSelectFields());
        mav.addObject("allowedUpdateFields", postBatchService.readAllowedGiftUpdateFields());

        return mav;

    }

    // Validate fields are on allowed select lists.  May allow duplicate fields in list, for example date > and < for a range.
    private void validateFields(PostBatch postbatch) {
        Map<String, String> select = postBatchService.readAllowedGiftSelectFields();
        Map<String, String> update = postBatchService.readAllowedGiftUpdateFields();
//        for (Map.Entry<String, String> me : postbatch.getWhereConditions().entrySet()) {
//            if (select.get(me.getKey()) == null)
//                throw new RuntimeException("Invalid request."); // this would require a hacked form field.
//        }
//        for (Map.Entry<String, String> me : postbatch.getUpdateFields().entrySet()) {
//            if (update.get(me.getKey()) == null)
//                throw new RuntimeException("Invalid request."); // this would require a hacked form field.
//        }
    }

  	private void readFields(HttpServletRequest request, Map<String, String> conditions, String type) {
        conditions.clear();
        conditions.putAll(getMap(request, type));
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getMap(HttpServletRequest request, String type) {
        Map map = new TreeMap<String, String>();
        Enumeration e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String parm = (String) e.nextElement();
            if (parm.startsWith(type + "name")) {
                String fieldnum = parm.substring(6);
                String key = request.getParameter(parm).trim();
                String value = request.getParameter(type + "value" + fieldnum).trim();
                if (key.length() > 0 && value.length() > 0) map.put(key, value);
            }
        }
        return map;
    }


}