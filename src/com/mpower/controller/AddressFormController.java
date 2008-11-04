package com.mpower.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.mpower.service.SessionServiceImpl;
import com.mpower.service.SiteService;
import com.mpower.type.PageType;

public class AddressFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

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

    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
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
            address = addressService.readAddress(Long.valueOf(addressId));
        }
        if (isFormSubmission(request)) {
            Map<String, String> fieldLabelMap = siteService.readFieldLabels(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(getCommandName()), SessionServiceImpl.lookupUserRoles(), request.getLocale());
            address.setFieldLabelMap(fieldLabelMap);

            Map<String, Object> valueMap = siteService.readFieldValues(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(getCommandName()), SessionServiceImpl.lookupUserRoles(), address);
            address.setFieldValueMap(valueMap);
        }
        return address;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = new HashMap();
        String personIdString = request.getParameter("personId");
        Long personId = Long.valueOf(personIdString);
        List<Address> addresses = addressService.readAddresses(personId);
        refData.put("person", personService.readPersonById(personId));
        refData.put("addresses", addresses);
        if (logger.isDebugEnabled()) {
            for (Address a : addresses) {
                logger.debug("### address: " + a.getAddressLine1() + ", " + a.getCity() + ", " + a.getStateProvince() + ", " + a.getPostalCode());
            }
        }
        List<Address> currentAddresses = addressService.readCurrentAddresses(personId, Calendar.getInstance(), false);
        refData.put("currentAddresses", currentAddresses);
        List<Address> currentCorrespondenceAddresses = addressService.readCurrentAddresses(personId, Calendar.getInstance(), true);
        refData.put("currentCorrespondenceAddresses", currentCorrespondenceAddresses);
        return refData;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        addressService.saveAddress((Address) command);
        String personIdString = request.getParameter("personId");
        Long personId = Long.valueOf(personIdString);
        List<Address> addresses = addressService.readAddresses(personId);
        Person person = personService.readPersonById(personId);
        ModelAndView mav = new ModelAndView("addressManager");
        mav.addObject("addresses", addresses);
        List<Address> currentAddresses = addressService.readCurrentAddresses(personId, Calendar.getInstance(), false);
        mav.addObject("currentAddresses", currentAddresses);
        List<Address> currentCorrespondenceAddresses = addressService.readCurrentAddresses(personId, Calendar.getInstance(), true);
        mav.addObject("currentCorrespondenceAddresses", currentCorrespondenceAddresses);
        mav.addObject("person", person);
        mav.addObject("address", new Address());
        mav.addObject(personIdString);
        return mav;
    }
}
