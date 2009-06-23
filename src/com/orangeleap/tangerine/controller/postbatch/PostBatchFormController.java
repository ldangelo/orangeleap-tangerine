package com.orangeleap.tangerine.controller.postbatch;

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PostBatchService;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.type.AccessType;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class PostBatchFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "constituentService")
    private ConstituentService constituentService;

    @Resource(name = "giftService")
    private GiftService giftService;

    @Resource(name = "postBatchService")
    private PostBatchService postBatchService;

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;

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
        mav.addObject("postbatch", getNewPostBatch());
        mav.addObject("allowedSelectFields", postBatchService.readAllowedGiftSelectFields());
        mav.addObject("allowedUpdateFields", postBatchService.readAllowedGiftUpdateFields());
        return mav;

    }

    private PostBatch getNewPostBatch() {
        PostBatch postbatch =  new PostBatch();
        postbatch.setEntity("gift");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        postbatch.setPostBatchDesc("Batch for " + formatter.format(new java.util.Date()));
        // Add some default field settings...
        postbatch.getWhereConditions().put("posted","false");
        postbatch.getUpdateFields().put("posted","true");
        return postbatch;
    }


	@Override
    public Object formBackingObject(HttpServletRequest request) throws ServletException {
        return getNewPostBatch();
    }


    // First they set the selection criteria and "save" which genertes the list of gifts matching the criteria to review.
    // Once they have the criteria they way they want it, they "post" which processes the updates to the list of selected items.
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {

        if (!accessAllowed(request)) return null;

        boolean post = "true".equals(request.getParameter("post"));

        PostBatch requestPostbatch = (PostBatch)command;
        // TODO validate
        // if any of the 'where' values start with ^(=|!=|<|>) then validate it matches one of the regexes:
        //       ^(=|!=|<|>)([\s])*([0-9]{2,2}-[0-9]{2,2}-[0-9]{4,4})$   (date)  or ^(=|!=|<|>)([\s])*-{0,1}\d*\.{0,1}\d+$   (number)   or  ^!=([\s])*null$

        // Read existing
        Long batchId = requestPostbatch.getId();
        PostBatch postbatch = postBatchService.readBatch(batchId);
        if (postbatch == null) postbatch = getNewPostBatch();

        
        // User can only edit the description and the list of select/update fields - the rest of the fields are read-only
        postbatch.setPostBatchDesc(requestPostbatch.getPostBatchDesc());

        readFields(request, postbatch.getWhereConditions(), "sf");
        readFields(request, postbatch.getUpdateFields(), "uf");

        // TODO validate fields are on allowed select lists.  May allow duplicate fields in list, for example date > and < for a range.

        String errormessage = "";
        List<Gift> gifts = new ArrayList<Gift>();
        try {
            if (post) {
                postbatch = postBatchService.postBatch(postbatch);
            } else {
                postbatch = postBatchService.maintainBatch(postbatch);
                gifts = postBatchService.createBatchSelectionList(postbatch);  // will throw exception if selection set too large.
            }
        } catch (Exception e) {
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

    private void readFields(HttpServletRequest request, Map conditions, String type) {
        conditions.clear();
        conditions.putAll(getMap(request, type));
    }

    @SuppressWarnings("unchecked")
	protected Map<String, String> getMap(HttpServletRequest request, String type) {
		Map map = new TreeMap<String, String>();
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parm = (String)e.nextElement();
			if (parm.startsWith(type+"name")) {
				String fieldnum = parm.substring(6);
				String key = request.getParameter(parm).trim();
				String value = request.getParameter(type+"value"+fieldnum).trim();
				if (key.length() > 0) map.put(key, value);
			}
		}
		return map;
	}





}