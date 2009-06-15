package com.orangeleap.tangerine.controller.constituent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 * Fairly empty controller backing the page which contains the list of all
 * users for a site. The real voodoo happens on the JavaScript side along with
 * a JSON controller
 * @version 1.0
 */
public class ConstituentListController extends ParameterizableViewController {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mav = new ModelAndView(super.getViewName());

        return mav;
    }
}
