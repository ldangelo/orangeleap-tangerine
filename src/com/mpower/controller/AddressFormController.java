package com.mpower.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.Address;
import com.mpower.domain.Person;
import com.mpower.service.AddressService;
import com.mpower.service.PersonService;

public class AddressFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private AddressService addressService;

    private PersonService personService;

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        String personId = request.getParameter("personId");
        String addressId = request.getParameter("addressId");
        Address address = null;
        if (addressId == null) {
            Person person = personService.readPersonById(Long.valueOf(personId));
            address = new Address();
            address.setPerson(person);
        } else {
            try {
                address = (Address) BeanUtils.cloneBean(addressService.readAddress(Long.valueOf(addressId)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return address;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = new HashMap();
        String personId = request.getParameter("personId");
        List<Address> addresses = addressService.readAddresses(Long.valueOf(personId));
        refData.put("person", personService.readPersonById(Long.valueOf(personId)));
        refData.put("addresses", addresses);
        if (logger.isDebugEnabled()) {
            for (Address a : addresses) {
                logger.debug("### address: " + a.getAddressLine1() + ", " + a.getCity() + ", " + a.getStateProvince() + ", " + a.getPostalCode());
            }
        }
        return refData;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Address address = (Address) command;
        addressService.saveAddress(address);
        String personId = request.getParameter("personId");
        List<Address> addresses = addressService.readAddresses(Long.valueOf(personId));
        Person person = personService.readPersonById(Long.valueOf(personId));
        ModelAndView mav = new ModelAndView("paymentManager");
        mav.addObject("addresses", addresses);
        address = new Address();
        // address.setPerson(person);
        mav.addObject("person", person);
        mav.addObject("address", address);
        mav.addObject(personId);
        return mav;
    }
}
