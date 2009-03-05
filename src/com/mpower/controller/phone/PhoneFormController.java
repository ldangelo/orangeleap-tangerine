package com.mpower.controller.phone;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.mpower.controller.TangerineConstituentAttributesFormController;
import com.mpower.domain.model.AbstractEntity;
import com.mpower.domain.model.communication.Phone;
import com.mpower.util.StringConstants;

public class PhoneFormController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = super.referenceData(request);
        List<Phone> phones = phoneService.readByConstituentId(super.getConstituentId(request));
        refData.put("phones", phones);
        List<Phone> currentPhones = phoneService.readCurrent(super.getConstituentId(request), false);
        refData.put("currentPhones", currentPhones);
        List<Phone> currentCorrespondencePhones = phoneService.readCurrent(super.getConstituentId(request), true);
        refData.put("currentCorrespondencePhones", currentCorrespondencePhones);

        if (logger.isDebugEnabled()) {
            for (Phone p : phones) {
                logger.debug("addRefData: phone = " + p.getNumber());
            }
        }
        return refData;
    }

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        String phoneId = request.getParameter(StringConstants.PHONE_ID);
        Phone phone = null;
        if (phoneId == null) {
            phone = new Phone(super.getConstituent(request).getId());
        }
        else {
            phone = phoneService.read(Long.valueOf(phoneId));
        }
        return phone;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        phoneService.save((Phone)command);
        return super.onSubmit(request, response, command, errors);
    }
}
