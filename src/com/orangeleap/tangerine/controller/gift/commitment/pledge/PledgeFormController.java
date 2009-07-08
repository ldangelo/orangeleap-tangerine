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

import com.orangeleap.tangerine.controller.gift.commitment.CommitmentFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PledgeFormController extends CommitmentFormController<Pledge> {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "pledgeService")
    protected PledgeService pledgeService;

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        return pledgeService.readPledgeByIdCreateIfNull(request.getParameter(StringConstants.PLEDGE_ID), super.getConstituent(request));
    }

    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
        ModelAndView mv = super.showForm(request, response, errors);
        Pledge pledge = (Pledge) formBackingObject(request);

        if (usePledgeView(pledge)) {
            mv = new ModelAndView(getSuccessView() + "?" + getParamId() + "=" + pledge.getId() + "&" + StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request));
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
        refData.put(StringConstants.CAN_APPLY_PAYMENT, pledgeService.canApplyPayment((Pledge) command));
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
