package com.mpower.controller.gift;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.mpower.domain.Commitment;
import com.mpower.domain.Gift;
import com.mpower.domain.Viewable;
import com.mpower.service.CommitmentService;
import com.mpower.util.StringConstants;

public class RecurringGiftFormController extends GiftFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private CommitmentService commitmentService;

    public void setCommitmentService(CommitmentService commitmentService) {
        this.commitmentService = commitmentService;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Commitment commitment = (Commitment) command;

        super.removeInvalidDistributionLines(commitment.getDistributionLines().iterator());
        
        // validate required fields
        Commitment current = commitmentService.maintainCommitment(commitment);

        // TODO: Adding errors.getModel() to our ModelAndView is a "hack" to allow our
        // form to post results back to the same page. We need to get the
        // command from errors and then add our search results to the model.
        String redirectView = current.getGifts().isEmpty() ? "recurringGift" : "recurringGiftView";
        setSuccessView(redirectView);
        ModelAndView mav = new ModelAndView("redirect:/" + redirectView + ".htm", errors.getModel());
        mav.addObject("commitmentId", current.getId());
        mav.addObject("saved", true);
        mav.addObject("id", current.getId());
        mav.addObject("myErrors", errors);
        return mav;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addRefData(HttpServletRequest request, Long personId, Map refData) {
        super.addRefData(request, personId, refData);
        String commitmentId = request.getParameter(StringConstants.COMMITMENT_ID);
        if (commitmentId != null) {
            Commitment commitment = commitmentService.readCommitmentById(Long.valueOf(commitmentId));
            if (commitment != null) {
                List<Gift> gifts = commitmentService.getCommitmentGifts(commitment);
                refData.put(StringConstants.GIFTS, gifts);
                Iterator<Gift> giftIter = gifts.iterator();
                BigDecimal giftSum = new BigDecimal(0);
                while (giftIter.hasNext()) {
                    giftSum = giftSum.add(giftIter.next().getAmount());
                }
                refData.put("giftSum", giftSum);
                refData.put("giftsReceivedSum", commitmentService.getAmountReceived(commitment.getId()));
            }
        }
    }

    @Override
    protected Viewable findViewable(HttpServletRequest request) {
        return commitmentService.readCommitmentByIdCreateIfNull(request.getParameter(StringConstants.COMMITMENT_ID), super.getPerson(request));
    }
}
