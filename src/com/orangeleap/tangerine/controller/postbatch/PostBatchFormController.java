package com.orangeleap.tangerine.controller.postbatch;

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PostBatchService;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

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
	@Override
    public ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {

		ModelAndView mav = super.showForm(request, response, errors, controlModel);
        mav.addObject("postBatch", getNewPostBatch());
        return mav;

    }

    private PostBatch getNewPostBatch() {
        PostBatch postbatch =  new PostBatch();
        postbatch.getWhereConditions().put("status","Paid");
        postbatch.getUpdateFields().put("status","Posted");
        return postbatch;
    }


	@Override
    public Object formBackingObject(HttpServletRequest request) throws ServletException {
        return "";
    }


    // First they set the selection criteria and "save" which genertes the list of gifts matching the criteria to review.
    // Once they have the criteria they way they want it, they "post" which processes the updates to the list of selected items.
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {

        boolean save = "true".equals(request.getParameter("save"));
        boolean post = "true".equals(request.getParameter("post"));

        PostBatch requestPostbatch = (PostBatch)command;
        // TODO validate
        // if any of the 'where' values start with ^(=|!=|<|>) then validate it matches one of the regexes:
        //       ^(=|!=|<|>)([\s])*([0-9]{2,2}-[0-9]{2,2}-[0-9]{4,4})$   (date)  or ^(=|!=|<|>)([\s])*-{0,1}\d*\.{0,1}\d+$   (number)   or  ^!=([\s])*null$



        PostBatch postbatch = postBatchService.readBatch(requestPostbatch.getId());

        // User can only edit the description and the list of select/update fields - the rest of the fields are read-only
        postbatch.setDescription(requestPostbatch.getDescription());
        postbatch.setUpdateFields(requestPostbatch.getUpdateFields());
        postbatch.setWhereConditions(requestPostbatch.getWhereConditions());

        String errormessage = "";
        List<Gift> gifts = new ArrayList<Gift>();
        try{
            if (save) {
                postbatch = postBatchService.maintainBatch(postbatch);
                gifts = postBatchService.createBatchSelectionList(postbatch);  // will throw exception if selection set too large.
            } else if (post) {
                postbatch = postBatchService.postBatch(postbatch);
            }
        } catch (Exception e) {
            errormessage = e.getMessage();
        }

        ModelAndView mav = new ModelAndView(getSuccessView());
        mav.addObject("errormessage", errormessage);
        mav.addObject("gifts", gifts);
        mav.addObject("postbatch", postbatch);

        return mav;

    }




}