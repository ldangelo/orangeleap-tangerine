package com.mpower.controller.commitment;

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

import com.mpower.controller.TangerineFormController;
import com.mpower.domain.Commitment;
import com.mpower.domain.Gift;
import com.mpower.domain.Viewable;
import com.mpower.service.CommitmentService;
import com.mpower.type.CommitmentType;
import com.mpower.util.StringConstants;

public class CommitmentFormController extends TangerineFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected CommitmentService commitmentService;
    protected CommitmentType commitmentType;
    protected String formUrl;

    public void setCommitmentService(CommitmentService commitmentService) {
        this.commitmentService = commitmentService;
    }

    public void setCommitmentType(CommitmentType commitmentType) {
        this.commitmentType = commitmentType;
    }
    
    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }

    @Override
    protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception {
        super.onBind(request, command, errors);
        Commitment commitment = (Commitment) command;
        commitment.removeInvalidDistributionLines();
    }
    
    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
        super.onBindAndValidate(request, command, errors);
        if (errors.hasErrors()) {
            Commitment commitment = (Commitment) command;
            commitment.defaultCreateDistributionLine();
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Commitment commitment = (Commitment) command;        
        Commitment current = commitmentService.maintainCommitment(commitment);
        
        String url = current.getGifts().isEmpty() ? formUrl : getSuccessView();
        return new ModelAndView(url + "?" + StringConstants.COMMITMENT_ID + "=" + current.getId() + "&" + StringConstants.PERSON_ID + "=" + super.getPersonId(request) + "&saved=true");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = super.referenceData(request);
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
        return refData;
    }

    @Override
    protected Viewable findViewable(HttpServletRequest request) {
        return commitmentService.readCommitmentByIdCreateIfNull(request.getParameter(StringConstants.COMMITMENT_ID), super.getPerson(request), commitmentType);
    }
}
