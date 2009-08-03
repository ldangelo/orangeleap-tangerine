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

package com.orangeleap.tangerine.controller.communication.email;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class EmailFormController extends TangerineConstituentAttributesFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        String emailId = request.getParameter(StringConstants.EMAIL_ID);
        return emailService.readByIdCreateIfNull(emailId, getConstituentId(request));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map refData = super.referenceData(request, command, errors);
        emailService.findReferenceDataByConstituentId(refData, getConstituentId(request), "emails", "currentEmails", "currentCorrespondenceEmails");
        return refData;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
                                    Object command, BindException formErrors) throws Exception {
        TangerineForm form = (TangerineForm) command;
        Email email = (Email) form.getDomainObject();
        if (emailService.alreadyExists(email) != null) {
            formErrors.reject("errorEmailExists");
            return showForm(request, response, formErrors);
        }
        ModelAndView mav;
        try {
            email = emailService.save(email);
            mav = new ModelAndView(super.appendSaved(getSuccessView() + "?" + StringConstants.EMAIL_ID + "=" + email.getId() +
                    "&" + StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request)));
        }
        catch (BindException domainErrors) {
            bindDomainErrorsToForm(request, formErrors, domainErrors, form, email);
            mav = showForm(request, formErrors, getFormView());
        }
        return mav;
    }
}
