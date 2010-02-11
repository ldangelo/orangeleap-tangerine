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

package com.orangeleap.tangerine.controller.background;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.Background;
import com.orangeleap.tangerine.service.BackgroundService;
import com.orangeleap.tangerine.service.SessionService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BackgroundFormController extends TangerineConstituentAttributesFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "backgroundService")
    protected BackgroundService backgroundService;

    @Resource(name = "sessionService")
    private SessionService sessionService;

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        sessionService.lookupSite(); // call lookupSite to make sure TangerineAuthenticationToken has the constituentId set
        return backgroundService.readBackgroundByIdCreateIfNull(request.getParameter(StringConstants.BACKGROUND_ID), super.getConstituent(request));
    }

    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
        ModelAndView mav = super.showForm(request, response, errors);
        TangerineForm form = (TangerineForm) formBackingObject(request);
        Background background = (Background) form.getDomainObject();
        if ( ! background.isNew()) {
            mav = new ModelAndView(getRedirectUrl(request, background));
        }
        return mav;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException formErrors) throws Exception {
	    TangerineForm form = (TangerineForm) command;
        Background background = (Background) form.getDomainObject();

        boolean saved = true;
        try {
            background = backgroundService.maintainBackground(background);
        }
        catch (BindException domainErrors) {
            saved = false;
            bindDomainErrorsToForm(formErrors, domainErrors);
        }

        ModelAndView mav;
        if (saved) {
            mav = new ModelAndView(appendSaved(getRedirectUrl(request, background)));
        }
        else {
            mav = super.showForm(request, formErrors, getFormView());
        }
        return mav;
    }

    private String getRedirectUrl(HttpServletRequest request, Background background) {
        return getSuccessView() + "?" + StringConstants.COMMUNICATION_HISTORY_ID + "=" + background.getId() + "&" +
		            StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request);        
    }
}
