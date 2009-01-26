package com.mpower.controller.lookup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class MultiQueryLookupController extends QueryLookupController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    private static final String SELECTED_IDS = "selectedIds";

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        final ModelAndView mav = super.onSubmit(request, response, command, errors);
        findPreviouslySelected(request, mav);
        return mav;
    }

    private void findPreviouslySelected(final HttpServletRequest request, final ModelAndView mav) {
        SelectedIds selectedIdsObj = new SelectedIds(request.getParameterValues(SELECTED_IDS));
        mav.addObject(SELECTED_IDS, selectedIdsObj);
    }
}
