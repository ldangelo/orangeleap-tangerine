package com.orangeleap.tangerine.controller.payment;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.PaymentHistoryService;

public class PaymentHistoryListController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="paymentHistoryService")
    private PaymentHistoryService paymentHistoryService;
    
    @Resource(name="constituentService")
    private ConstituentService constituentService;

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("handleRequestInternal:");
        }
        String personId = request.getParameter("personId");
        Person constituent = constituentService.readConstituentById(Long.valueOf(personId));
        
        ModelAndView mav = new ModelAndView(super.getViewName());
        if (constituent != null) {
            mav.addObject("person", constituent);
        }
        return mav;
    }
}
