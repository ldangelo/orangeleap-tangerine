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

package com.orangeleap.tangerine.controller;

import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.web.common.TangerineListHelper;
import org.apache.commons.logging.Log;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;

public abstract class TangerineGridController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="constituentService")
    protected ConstituentService constituentService;

    @Resource(name = "tangerineListHelper")
    protected TangerineListHelper tangerineListHelper;

    public Long getIdAsLong(HttpServletRequest request, String id) {
        if (logger.isTraceEnabled()) {
            logger.trace("getIdAsLong: id = " + id);
        }
        String paramId = request.getParameter(id);
        if (StringUtils.hasText(paramId)) {
            return Long.valueOf(request.getParameter(id));
        }
        return null;
    }

    protected Long getConstituentId(HttpServletRequest request) {
        return this.getIdAsLong(request, StringConstants.CONSTITUENT_ID);
    }

    protected String getConstituentIdString(HttpServletRequest request) {
        return request.getParameter(StringConstants.CONSTITUENT_ID);
    }

	protected Constituent getConstituent(HttpServletRequest request) {
	    Long constituentId = getConstituentId(request);
	    Constituent constituent = null;
	    if (constituentId != null) {
	        constituent = constituentService.readConstituentById(constituentId); // TODO: do we need to check if the user can view this constituent (authorization)?
	    }
	    if (constituent == null) {
	        throw new IllegalArgumentException("The constituent ID was not found");
	    }
	    return constituent;
	}

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getParameter(StringConstants.CONSTITUENT_ID) != null) {
            request.setAttribute(StringConstants.CONSTITUENT, getConstituent(request));
        }
        return new ModelAndView(getViewName(), StringConstants.FORM, new TangerineForm(getDummyEntity(request)));
    }

    protected abstract Object getDummyEntity(HttpServletRequest request);
}
