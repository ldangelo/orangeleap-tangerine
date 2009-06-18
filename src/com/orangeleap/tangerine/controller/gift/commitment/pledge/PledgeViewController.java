package com.orangeleap.tangerine.controller.gift.commitment.pledge;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.Errors;

import com.orangeleap.tangerine.controller.gift.commitment.CommitmentFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.util.StringConstants;

public class PledgeViewController extends CommitmentFormController<Pledge> {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="pledgeService")
    protected PledgeService pledgeService;
    
    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        return pledgeService.readPledgeById(getIdAsLong(request, StringConstants.PLEDGE_ID));
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
    protected Pledge maintainCommitment(Pledge entity) {
        return pledgeService.editPledge(entity);
    }

    @Override
    protected String getReturnView(Pledge entity) {
        return getSuccessView();
    }
}
