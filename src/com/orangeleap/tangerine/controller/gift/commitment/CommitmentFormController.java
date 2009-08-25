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

package com.orangeleap.tangerine.controller.gift.commitment;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.controller.gift.AbstractMutableGridFormController;
import com.orangeleap.tangerine.controller.gift.AssociationEditor;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public abstract class CommitmentFormController<T extends Commitment> extends AbstractMutableGridFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    protected String formUrl;

    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }

	@Resource(name = "picklistItemService")
	protected PicklistItemService picklistItemService;

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
	    return readCommitmentCreateIfNull(request);
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
	    T commitment = (T) form.getDomainObject();

        boolean saved = true;
        try {
            commitment = maintainCommitment(commitment);
        }
        catch (BindException domainErrors) {
            saved = false;
	        bindDomainErrorsToForm(request, formErrors, domainErrors, form, commitment);
        }

        ModelAndView mav;
        if (saved) {
            mav = new ModelAndView(super.appendSaved(getReturnView(commitment) + "?" + getParamId() + "=" + commitment.getId() + "&" +
		            StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request)));
        }
        else {
			mav = showForm(request, formErrors, getFormView());
        }
        return mav;
    }

    protected String getReturnView(T entity) {
        return formUrl;
    }

	protected abstract T readCommitmentCreateIfNull(HttpServletRequest request);

    protected abstract T maintainCommitment(T entity) throws BindException;

    protected abstract String getParamId();
}