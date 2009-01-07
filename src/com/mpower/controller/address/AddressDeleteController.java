package com.mpower.controller.address;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.domain.Address;
import com.mpower.service.AddressService;
import com.mpower.util.StringConstants;

public class AddressDeleteController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private AddressService addressService;

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String addressId = request.getParameter("addressId");
        String personId = request.getParameter(StringConstants.PERSON_ID);
        Address address = addressService.readAddress(Long.valueOf(addressId));
        address.setInactive(true);
        addressService.saveAddress(address);
        ModelAndView mav = new ModelAndView(super.getViewName() + "?" + StringConstants.PERSON_ID + "=" + personId);
        return mav;
    }
}
