package com.mpower.controller.phone;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.mpower.controller.TangerineFormController;
import com.mpower.domain.Phone;
import com.mpower.domain.Viewable;
import com.mpower.service.PhoneService;
import com.mpower.util.StringConstants;

public class PhoneFormController extends TangerineFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected PhoneService phoneService;

    public void setPhoneService(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addRefData(HttpServletRequest request, Long personId, Map refData) {
        List<Phone> phones = phoneService.readPhones(personId);
        refData.put("phones", phones);
        List<Phone> currentPhones = phoneService.readCurrentPhones(personId, Calendar.getInstance(), false);
        refData.put("currentPhones", currentPhones);
        List<Phone> currentCorrespondencePhones = phoneService.readCurrentPhones(personId, Calendar.getInstance(), true);
        refData.put("currentCorrespondencePhones", currentCorrespondencePhones);

        if (logger.isDebugEnabled()) {
            for (Phone p : phones) {
                logger.debug("addRefData: phone = " + p.getNumber());
            }
        }
    }

    @Override
    protected Viewable findViewable(HttpServletRequest request) {
        String phoneId = request.getParameter(StringConstants.PHONE_ID);
        Phone phone = null;
        if (phoneId == null) {
            phone = new Phone(super.getPerson(request));
        }
        else {
            phone = phoneService.readPhone(Long.valueOf(phoneId));
        }
        return phone;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        phoneService.savePhone((Phone)command);
        return super.onSubmit(request, response, command, errors);
    }
}
