package com.mpower.controller.communication.phone;

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

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        String phoneId = request.getParameter(StringConstants.PHONE_ID);
        return phoneService.readByIdCreateIfNull(phoneId, getConstituentId(request));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = super.referenceData(request);
        phoneService.findReferenceDataByConstituentId(refData, getConstituentId(request), "phones", "currentPhones", "currentCorrespondencePhones");
        return refData;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        phoneService.save((Phone)command);
        return super.onSubmit(request, response, command, errors);
    }
}
