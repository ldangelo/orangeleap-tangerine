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

/*
Used for JSPs that use json controllers to populate themselves
 */

package com.orangeleap.tangerine.controller;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DummyController extends ParameterizableViewController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "constituentService")
    private ConstituentService constituentService;


    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView(super.getViewName());
        String id = request.getParameter(StringConstants.CONSTITUENT_ID);
        if (id != null && id.trim().length() > 0) {
            addConstituent(mav, id);
        } else {
            id = request.getParameter("id");
            String object = request.getParameter("object");
            if ("constituent".equals(object)) {
                addConstituent(mav, id);
            }
        }
        return mav;
    }

    private void addConstituent(ModelAndView mav, String id) {
        Long constituentId = Long.valueOf(id);
        Constituent constituent = constituentService.readConstituentById(Long.valueOf(constituentId));
        mav.addObject("constituent", constituent);
    }

}