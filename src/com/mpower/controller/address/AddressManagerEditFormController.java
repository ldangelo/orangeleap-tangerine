package com.mpower.controller.address;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.Address;
import com.mpower.domain.Person;
import com.mpower.service.AddressService;
import com.mpower.service.PersonService;
import com.mpower.service.SiteService;
import com.mpower.service.impl.SessionServiceImpl;
import com.mpower.type.PageType;

// TODO: extend AddressFormController, override just formBackingObject() method
public class AddressManagerEditFormController extends SimpleFormController {

    private AddressService addressService;

    private PersonService personService;

    private SiteService siteService;

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = new HashMap();
        String personId = request.getParameter("personId");
        refData.put("person", personService.readPersonById(Long.valueOf(personId)));
        return refData;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        String personId = request.getParameter("personId");
        String addressId = request.getParameter("addressId");
        Address address = null;
        if (addressId == null) {
            Person person = personService.readPersonById(Long.valueOf(personId));
            address = new Address(person);
        } else {
            Address originalAddress = addressService.readAddress(Long.valueOf(addressId));
            try {
                address = (Address) BeanUtils.cloneBean(originalAddress);
                address.setId(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (isFormSubmission(request)) {
            Map<String, String> fieldLabelMap = siteService.readFieldLabels(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(getCommandName()), SessionServiceImpl.lookupUserRoles(), request.getLocale());
            address.setFieldLabelMap(fieldLabelMap);

            Map<String, Object> valueMap = siteService.readFieldValues(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(getCommandName()), SessionServiceImpl.lookupUserRoles(), address);
            address.setFieldValueMap(valueMap);
        }
        return address;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Address address = (Address) command;
        addressService.saveAddress(address);
        String personIdString = request.getParameter("personId");
        Long personId = Long.valueOf(personIdString);
        List<Address> addresses = addressService.readAddresses(personId);
        Person person = personService.readPersonById(personId);
        ModelAndView mav = new ModelAndView("address/addressManager"); // TODO: use redirect: & move to context XML

        // TODO: remove below
        mav.addObject("addresses", addresses);
        List<Address> currentAddresses = addressService.readCurrentAddresses(personId, Calendar.getInstance(), false);
        mav.addObject("currentAddresses", currentAddresses);
        List<Address> currentCorrespondenceAddresses = addressService.readCurrentAddresses(personId, Calendar.getInstance(), true);
        mav.addObject("currentCorrespondenceAddresses", currentCorrespondenceAddresses);
        address = new Address(person);
        mav.addObject("redirect:/addressManager.htm?personId=" + personIdString, errors.getModel());
        mav.addObject("person", person);
        mav.addObject("address", address);
        mav.addObject(personIdString);
        return mav;
    }
}
