package com.mpower.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.mpower.domain.Address;
import com.mpower.service.AddressService;

public class AddressDeleteController implements Controller {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private AddressService addressService;

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String addressId = request.getParameter("addressId");
        String personId = request.getParameter("personId");
        Address address = addressService.readAddress(Long.valueOf(addressId));
        address.setInactive(true);
        addressService.saveAddress(address);
        ModelAndView mav = new ModelAndView("redirect:/addressManager.htm?personId=" + personId);
        return mav;
    }
}
