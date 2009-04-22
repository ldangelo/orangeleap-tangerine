package com.orangeleap.tangerine.controller.commitment.pledge;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;

import com.orangeleap.tangerine.controller.commitment.CommitmentFormController;
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
    protected Pledge maintainCommitment(Pledge entity) {
        return pledgeService.maintainPledge(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map refData = super.referenceData(request, command, errors);
        pledgeService.findGiftSum(refData, (Pledge)command);
        return refData;
    }

    @Override
    protected String getParamId() {
        return StringConstants.PLEDGE_ID;
    }
}
