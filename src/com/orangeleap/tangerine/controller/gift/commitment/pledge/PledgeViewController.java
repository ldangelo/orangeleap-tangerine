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

package com.orangeleap.tangerine.controller.gift.commitment.pledge;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.controller.gift.AssociationEditor;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class PledgeViewController extends TangerineConstituentAttributesFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "pledgeService")
    protected PledgeService pledgeService;

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
	    return pledgeService.readPledgeById(getIdAsLong(request, StringConstants.PLEDGE_ID));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map refData = super.referenceData(request, command, errors);
        TangerineForm form = (TangerineForm) command;
        Pledge pledge = (Pledge) form.getDomainObject();
        refData.put(StringConstants.CAN_APPLY_PAYMENT, ! Commitment.STATUS_FULFILLED.equals(pledge.getPledgeStatus()) &&
                ! Commitment.STATUS_CANCELLED.equals(pledge.getPledgeStatus()));
        return refData;        
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(List.class, "associatedGiftIds", new AssociationEditor());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
                                    BindException formErrors) throws Exception {
	    TangerineForm form = (TangerineForm) command;
	    Pledge pledge = (Pledge) form.getDomainObject();

        boolean saved = true;
        try {
            pledge = pledgeService.editPledge(pledge);
        }
        catch (BindException domainErrors) {
            saved = false;
	        bindDomainErrorsToForm(request, formErrors, domainErrors, form, pledge);
        }

        ModelAndView mav;
        if (saved) {
            mav = new ModelAndView(super.appendSaved(getSuccessView() + "?" + StringConstants.PLEDGE_ID + "=" + pledge.getId() + "&" +
		            StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request)));
        }
        else {
			mav = showForm(request, formErrors, getFormView());
        }
        return mav;
    }
}