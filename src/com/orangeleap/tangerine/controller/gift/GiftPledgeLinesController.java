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

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.util.StringConstants;

public class GiftPledgeLinesController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name="giftService")
    private GiftService giftService;
    
    @Resource(name="pledgeService")
    private PledgeService pledgeService;
    
    @Resource(name="constituentService")
    private ConstituentService constituentService;
    
    private Person getConstituent(HttpServletRequest request) {
        return constituentService.readConstituentById(Long.parseLong(request.getParameter(StringConstants.PERSON_ID)));
    }
    
    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return new GiftPledgeLinesForm(getConstituent(request));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        GiftPledgeLinesForm form = (GiftPledgeLinesForm)command;
        List<DistributionLine> pledgeLines = null;
        int numPledges = 0;
        if (form.getPledgeIds() != null) {
            pledgeLines = pledgeService.findDistributionLinesForPledges(form.getPledgeIds());
            numPledges = form.getPledgeIds().size();
        }
        return new ModelAndView(getSuccessView(), "combinedDistributionLines", giftService.combineGiftPledgeDistributionLines(form.getMutableDistributionLines(), pledgeLines, 
                form.getEnteredAmount(), numPledges, getConstituent(request)));
    }
}
