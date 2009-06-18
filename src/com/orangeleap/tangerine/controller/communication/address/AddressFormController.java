package com.orangeleap.tangerine.controller.communication.address;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.util.StringConstants;

public class AddressFormController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        String addressId = request.getParameter(StringConstants.ADDRESS_ID);
        return addressService.readByIdCreateIfNull(addressId, getConstituentId(request));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map refData = super.referenceData(request, command, errors);
        addressService.findReferenceDataByConstituentId(refData, getConstituentId(request), "addresses", "currentAddresses", "currentCorrespondenceAddresses");
        return refData;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Address address = (Address) command;
        if (addressService.alreadyExists(address) != null) {
            errors.reject("errorAddressExists");
            return showForm(request, response, errors);
        }
        addressService.save(address);
        return super.onSubmit(request, response, command, errors);
    }
}
