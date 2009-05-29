package com.orangeleap.tangerine.controller.commitment;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.controller.gift.AssociationEditor;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.util.StringConstants;

public abstract class CommitmentFormController<T extends Commitment> extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

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
            mav = new ModelAndView(super.appendSaved(getReturnView(current) + "?" + getParamId() + "=" + current.getId() + "&" + StringConstants.PERSON_ID + "=" + super.getConstituentId(request)));
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
