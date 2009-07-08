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

package com.orangeleap.tangerine.controller.lookup;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

public class MultiQueryLookupController extends QueryLookupController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
    private static final String SELECTED_IDS = "selectedIds";
    private static final String SELECTED_IDS_STRING = "selectedIdsString";

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
        final ModelAndView mav = super.showForm(request, response, errors, controlModel);
        mav.addObject("init", "true");
        findPreviouslySelected(request, mav);
        return mav;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        final ModelAndView mav = super.onSubmit(request, response, command, errors);
        findPreviouslySelected(request, mav);
        return mav;
    }

    private void findPreviouslySelected(final HttpServletRequest request, final ModelAndView mav) {
        SelectedIds selectedIdsObj = new SelectedIds(HtmlUtils.htmlUnescape(request.getParameter(SELECTED_IDS)));
        mav.addObject(SELECTED_IDS, selectedIdsObj);
        mav.addObject(SELECTED_IDS_STRING, selectedIdsObj.toString());
    }
}
