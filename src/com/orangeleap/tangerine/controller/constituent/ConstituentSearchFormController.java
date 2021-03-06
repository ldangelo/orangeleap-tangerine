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

package com.orangeleap.tangerine.controller.constituent;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.controller.TangerineFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.SessionService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConstituentSearchFormController extends TangerineFormController {

    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "constituentService")
    private ConstituentService constituentService;

    @Resource(name = "sessionService")
    private SessionService sessionService;

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        Constituent constituent = new Constituent(null, sessionService.lookupSite());
        if (mapSearchFieldToLastName(request)) {
            // searchField value is mapped to 'lastName'
            constituent.setLastName(request.getParameter(StringConstants.SEARCH_FIELD));
        }
        return constituent;
    }

    @Override
    protected void onBind(HttpServletRequest request, Object command, BindException e) throws Exception {
        TangerineForm form = (TangerineForm) command;
        request.setAttribute(StringConstants.SEARCH_TYPE, StringConstants.CONSTITUENT);
        if (mapSearchFieldToLastName(request)) {
            String lastNameValue = request.getParameter(StringConstants.SEARCH_FIELD);
            request.setAttribute(StringConstants.SEARCH_FIELD, lastNameValue);

            ServletRequestDataBinder binder = new ServletRequestDataBinder(form.getDomainObject());
            initBinder(request, binder);

            MutablePropertyValues propertyValues = new MutablePropertyValues();
            form.addField(StringConstants.LAST_NAME, lastNameValue);
            propertyValues.addPropertyValue(StringConstants.LAST_NAME, lastNameValue);
            binder.bind(propertyValues);
        }
    }

    private boolean mapSearchFieldToLastName(HttpServletRequest request) {
        return Boolean.TRUE.toString().equalsIgnoreCase(request.getParameter("autoLoad")) && StringUtils.hasText(request.getParameter(StringConstants.SEARCH_FIELD));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        return new ModelAndView(getSuccessView(), StringConstants.FORM, command);
    }
}
