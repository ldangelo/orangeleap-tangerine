package com.mpower.controller.constituent;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Fairly empty controller backing the page which contains the list of all
 * users for a site. The real voodoo happens on the JavaScript side along with
 * a JSON controller
 * @version 1.0
 */
public class PersonListController extends ParameterizableViewController {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mav = new ModelAndView(super.getViewName());

        return mav;
    }
}
