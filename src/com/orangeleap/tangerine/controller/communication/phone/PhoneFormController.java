package com.orangeleap.tangerine.controller.communication.phone;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.util.StringConstants;

public class PhoneFormController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        String phoneId = request.getParameter(StringConstants.PHONE_ID);
        return phoneService.readByIdCreateIfNull(phoneId, getConstituentId(request));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map refData = super.referenceData(request, command, errors);
        phoneService.findReferenceDataByConstituentId(refData, getConstituentId(request), "phones", "currentPhones", "currentCorrespondencePhones");
        return refData;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Phone phone = (Phone) command;
        if (phoneService.alreadyExists(phone) != null) {
            errors.reject("errorPhoneExists");
            return showForm(request, response, errors);
        }
        phoneService.save(phone);
        return super.onSubmit(request, response, command, errors);
    }
}
