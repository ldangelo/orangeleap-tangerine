package com.orangeleap.tangerine.controller.commitment.pledge;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.util.StringConstants;

public class PledgeViewController extends PledgeFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Pledge pledge = (Pledge)command;
        Pledge current = pledgeService.editPledge(pledge);
        return new ModelAndView(getSuccessView() + "?" + getParamId() + "=" + current.getId() + "&" + StringConstants.PERSON_ID + "=" + super.getConstituentId(request));
    }
}
