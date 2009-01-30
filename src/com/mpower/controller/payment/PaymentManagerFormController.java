package com.mpower.controller.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.mpower.controller.TangerineFormController;
import com.mpower.domain.PaymentSource;
import com.mpower.domain.Viewable;

public class PaymentManagerFormController extends TangerineFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected Viewable findViewable(HttpServletRequest request) {
        return paymentSourceService.readPaymentSourceCreateIfNull(request.getParameter("paymentSourceId"), super.getPerson(request));
    }
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        paymentSourceService.maintainPaymentSource((PaymentSource)command);
        return super.onSubmit(request, response, command, errors);
    }
}
