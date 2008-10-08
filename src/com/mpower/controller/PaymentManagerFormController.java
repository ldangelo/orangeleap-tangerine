package com.mpower.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.PaymentSource;
import com.mpower.domain.Person;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PersonService;

public class PaymentManagerFormController extends SimpleFormController {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private PaymentSourceService paymentSourceService;

	private PersonService personService;

	public void setPaymentSourceService(PaymentSourceService paymentSourceService) {
		this.paymentSourceService = paymentSourceService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		String personId = request.getParameter("personId");
		Person person = personService.readPersonById(Long.valueOf(personId));
		PaymentSource paymentSource = new PaymentSource();
		paymentSource.setPerson(person);
		return paymentSource;
	}
	
    @SuppressWarnings("unchecked")
	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
    	Map refData = new HashMap();
		String personId = request.getParameter("personId");
		List<PaymentSource> paymentSources = paymentSourceService.readPaymentSources(Long.valueOf(personId));
		refData.put("paymentSources", paymentSources);
    	return refData;
    }

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {

		PaymentSource paymentSource = (PaymentSource) command;
		paymentSourceService.savePaymentSource(paymentSource);
		String personId = request.getParameter("personId");
		List<PaymentSource> paymentSources = paymentSourceService.readPaymentSources(Long.valueOf(personId));
		Person person = personService.readPersonById(Long.valueOf(personId));
		ModelAndView mav = new ModelAndView("paymentManager");
		mav.addObject("paymentSources", paymentSources);
		paymentSource = new PaymentSource();
		paymentSource.setPerson(person);
		mav.addObject("paymentSource", paymentSource);
		return mav;

	}

}
