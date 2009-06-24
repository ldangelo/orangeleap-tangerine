package com.orangeleap.tangerine.controller.constituent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.controller.TangerineFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.service.exception.DuplicateConstituentException;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;

public class ConstituentFormController extends TangerineFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(Constituent.class, new ConstituentEditor());
    }

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        String constituentId = super.getConstituentIdString(request);
        if (constituentId == null) {
            constituentId = request.getParameter("id");
        }
        Constituent constituent = null;
        if (constituentId == null) {
            constituent = constituentService.createDefaultConstituent();
        } 
        else {
            constituent = constituentService.readConstituentById(new Long(constituentId));
        }
        return constituent;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Constituent constituent = (Constituent) command;
        if (logger.isTraceEnabled()) {
            logger.trace("onSubmit: constituent = " + constituent.getFullName());
        }
        
        boolean saved = false;
        boolean isDuplicate = false;
        try {
            constituent = constituentService.maintainConstituent(constituent);
            saved = true;
        } 
        catch (DuplicateConstituentException dce) {
        	isDuplicate = true;
        }
        catch (ConstituentValidationException e) {
            e.createMessages(errors);
        }
        catch (BindException e) {
            errors.addAllErrors(e);
        }

        ModelAndView mav = null;
        if (saved) {
            mav = new ModelAndView(super.appendSaved(new StringBuilder(super.getSuccessView()).append("?").append(StringConstants.CONSTITUENT_ID).append("=").append(constituent.getId()).toString()));
        }
        else {
            mav = showForm(request, errors, getFormView());
            
            if (isDuplicate) {
            	mav.addObject("duplicateConstituentName", constituent.getFirstLast());
            }
        }
        return mav;
    }
}
