package com.mpower.controller.address;

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
import com.mpower.domain.model.communication.Address;
import com.mpower.util.StringConstants;

public class AddressFormController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        String addressId = request.getParameter(StringConstants.ADDRESS_ID);
        Address address = null;
        if (addressId == null) {
            address = new Address(super.getConstituentId(request));
        }
        else {
            address = addressService.read(Long.valueOf(addressId));
        }
        return address;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = super.referenceData(request);
        List<Address> addresses = addressService.readByConstituentId(super.getConstituentId(request));
        refData.put("addresses", addresses);
        List<Address> currentAddresses = addressService.readCurrent(super.getConstituentId(request), false);
        refData.put("currentAddresses", currentAddresses);
        List<Address> currentCorrespondenceAddresses = addressService.readCurrent(super.getConstituentId(request), true);
        refData.put("currentCorrespondenceAddresses", currentCorrespondenceAddresses);

        if (logger.isDebugEnabled()) {
            for (Address a : addresses) {
                logger.debug("addRefData: address = " + a.getAddressLine1() + ", " + a.getCity() + ", " + a.getStateProvince() + ", " + a.getPostalCode());
            }
        }
        return refData;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        addressService.save((Address)command);
        return super.onSubmit(request, response, command, errors);
    }
}
