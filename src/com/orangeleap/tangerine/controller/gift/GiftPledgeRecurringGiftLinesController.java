package com.orangeleap.tangerine.controller.gift;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.util.StringConstants;

public class GiftPledgeRecurringGiftLinesController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name="giftService")
    private GiftService giftService;
    
    @Resource(name="pledgeService")
    private PledgeService pledgeService;
    
    @Resource(name="recurringGiftService")
    private RecurringGiftService recurringGiftService;
    
    @Resource(name="constituentService")
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
        GiftPledgeRecurringGiftLinesForm form = (GiftPledgeRecurringGiftLinesForm)command;
        List<DistributionLine> commitmentLines = null;
        int numCommitments = 0;
        boolean isPledge = false;
        if (form.getPledgeIds() != null) {
            commitmentLines = pledgeService.findDistributionLinesForPledges(form.getPledgeIds());
            numCommitments = form.getPledgeIds().size();
            isPledge = true;
        }
        else if (form.getRecurringGiftIds() != null) {
            commitmentLines = recurringGiftService.findDistributionLinesForRecurringGifts(form.getRecurringGiftIds());
            numCommitments = form.getRecurringGiftIds().size();
            isPledge = false;
        }
        return new ModelAndView(getSuccessView(), "combinedDistributionLines", giftService.combineGiftCommitmentDistributionLines(form.getMutableDistributionLines(), commitmentLines, 
                form.getEnteredAmount(), numCommitments, getConstituent(request), isPledge));
    }
}
