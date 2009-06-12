package com.orangeleap.tangerine.controller.gift.commitment.pledge;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.controller.gift.commitment.CommitmentFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.util.StringConstants;

public class PledgeFormController extends CommitmentFormController<Pledge> {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="pledgeService")
    protected PledgeService pledgeService;
    
    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        return pledgeService.readPledgeByIdCreateIfNull(request.getParameter(StringConstants.PLEDGE_ID), super.getConstituent(request));
    }

    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
        ModelAndView mv = super.showForm(request, response, errors);
        Pledge pledge = (Pledge)formBackingObject(request);
        
        if (usePledgeView(pledge)) {
            mv = new ModelAndView(getSuccessView() + "?" + getParamId() + "=" + pledge.getId() + "&" + StringConstants.PERSON_ID + "=" + super.getConstituentId(request));
        }
        return mv;
    }

    @Override
    protected Pledge maintainCommitment(Pledge entity) throws BindException {
        return pledgeService.maintainPledge(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map refData = super.referenceData(request, command, errors);
        refData.put(StringConstants.CAN_APPLY_PAYMENT, pledgeService.canApplyPayment((Pledge)command));
        return refData;
    }

    @Override
    protected String getParamId() {
        return StringConstants.PLEDGE_ID;
    }
    
    @Override
    protected String getReturnView(Pledge pledge) {
        String url = formUrl;
        if (usePledgeView(pledge)) {
            url = getSuccessView();
        }
        return url;
    }
    
    protected boolean usePledgeView(Pledge pledge) {
        return pledgeService.arePaymentsAppliedToPledge(pledge);
    }
}
