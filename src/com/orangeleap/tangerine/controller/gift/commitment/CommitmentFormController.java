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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.controller.gift.AssociationEditor;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.util.StringConstants;

public abstract class CommitmentFormController<T extends Commitment> extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    protected String formUrl;
    protected boolean handleEmptyDistributionLines = true;

    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }

    public void setHandleEmptyDistributionLines(boolean handleEmptyDistributionLines) {
        this.handleEmptyDistributionLines = handleEmptyDistributionLines;
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(List.class, "associatedGiftIds", new AssociationEditor());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        T commitment = (T) command;        
  
        boolean saved = true;
        T current = null;
        try {
            current = maintainCommitment(commitment);
        } 
        catch (BindException e) {
            saved = false;
            current = commitment;
            errors.addAllErrors(e);
        }

        ModelAndView mav = null;
        if (saved) {
            mav = new ModelAndView(super.appendSaved(getReturnView(current) + "?" + getParamId() + "=" + current.getId() + "&" + StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request)));
        }
        else {
            if (handleEmptyDistributionLines) {
                current.removeEmptyMutableDistributionLines();
            }
			mav = showForm(request, errors, getFormView());
        }
        return mav;
    }
    
    protected String getReturnView(T entity) {
        return formUrl;
    }
    
    protected abstract T maintainCommitment(T entity) throws BindException; 
    
    protected abstract String getParamId();
}
