package com.mpower.controller.address;

import java.util.Calendar;
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

import com.mpower.controller.TangerineFormController;
import com.mpower.domain.Address;
import com.mpower.service.AddressService;

public class AddressFormController extends TangerineFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected AddressService addressService;

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        Address address = findAddress(request);
        super.createFieldMaps(request, address);
        return address;
    }

    protected Address findAddress(HttpServletRequest request) {
        String addressId = request.getParameter("addressId");
        Address address = null;
        if (addressId == null) {
            address = new Address(super.getPerson(request));
        }
        else {
            address = addressService.readAddress(Long.valueOf(addressId));
        }
        return address;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = new HashMap();
        Long personId = super.getPersonId(request);
        List<Address> addresses = addressService.readAddresses(personId);

        super.addPersonToReferenceData(request, refData);
        refData.put("addresses", addresses);
        List<Address> currentAddresses = addressService.readCurrentAddresses(personId, Calendar.getInstance(), false);
        refData.put("currentAddresses", currentAddresses);
        List<Address> currentCorrespondenceAddresses = addressService.readCurrentAddresses(personId, Calendar.getInstance(), true);
        refData.put("currentCorrespondenceAddresses", currentCorrespondenceAddresses);

        if (logger.isDebugEnabled()) {
            for (Address a : addresses) {
                logger.debug("referenceData: address = " + a.getAddressLine1() + ", " + a.getCity() + ", " + a.getStateProvince() + ", " + a.getPostalCode());
            }
        }

        return refData;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        addressService.saveAddress((Address)command);
        return super.redirectToView(request);
    }
}
