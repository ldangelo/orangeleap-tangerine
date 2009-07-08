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

package com.orangeleap.tangerine.controller.gift;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GiftPledgeRecurringGiftLinesController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "giftService")
    private GiftService giftService;

    @Resource(name = "pledgeService")
    private PledgeService pledgeService;

    @Resource(name = "recurringGiftService")
    private RecurringGiftService recurringGiftService;

    @Resource(name = "constituentService")
    private ConstituentService constituentService;

    private Constituent getConstituent(HttpServletRequest request) {
        return constituentService.readConstituentById(Long.parseLong(request.getParameter(StringConstants.CONSTITUENT_ID)));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return new GiftPledgeRecurringGiftLinesForm(getConstituent(request));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        GiftPledgeRecurringGiftLinesForm form = (GiftPledgeRecurringGiftLinesForm) command;
        List<DistributionLine> commitmentLines = null;
        int numCommitments = 0;
        boolean isPledge = false;
        if (form.getPledgeIds() != null) {
            commitmentLines = pledgeService.findDistributionLinesForPledges(form.getPledgeIds());
            numCommitments = form.getPledgeIds().size();
            isPledge = true;
        } else if (form.getRecurringGiftIds() != null) {
            commitmentLines = recurringGiftService.findDistributionLinesForRecurringGifts(form.getRecurringGiftIds());
            numCommitments = form.getRecurringGiftIds().size();
            isPledge = false;
        }
        return new ModelAndView(getSuccessView(), "combinedDistributionLines", giftService.combineGiftCommitmentDistributionLines(form.getMutableDistributionLines(), commitmentLines,
                form.getEnteredAmount(), numCommitments, getConstituent(request), isPledge));
    }
}
