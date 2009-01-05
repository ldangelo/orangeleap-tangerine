package com.mpower.controller.phone;

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

import com.mpower.domain.Person;
import com.mpower.domain.Phone;
import com.mpower.service.PersonService;
import com.mpower.service.PhoneService;
import com.mpower.service.SiteService;
import com.mpower.service.impl.SessionServiceImpl;
import com.mpower.type.PageType;

public class PhoneFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PhoneService phoneService;

    private PersonService personService;

    private SiteService siteService;

    public void setPhoneService(PhoneService phoneService) {
        this.phoneService = phoneService;
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

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        String personId = request.getParameter("personId");
        String phoneId = request.getParameter("phoneId");
        Phone phone = null;
        if (phoneId == null) {
            Person person = personService.readPersonById(Long.valueOf(personId));
            phone = new Phone();
            phone.setPerson(person);
        } else {
            phone = phoneService.readPhone(Long.valueOf(phoneId));
        }
        if (isFormSubmission(request)) {
            Map<String, String> fieldLabelMap = siteService.readFieldLabels(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(getCommandName()), SessionServiceImpl.lookupUserRoles(), request.getLocale());
            phone.setFieldLabelMap(fieldLabelMap);

            Map<String, Object> valueMap = siteService.readFieldValues(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(getCommandName()), SessionServiceImpl.lookupUserRoles(), phone);
            phone.setFieldValueMap(valueMap);
        }
        return phone;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = new HashMap();
        String personIdString = request.getParameter("personId");
        Long personId = Long.valueOf(personIdString);
        List<Phone> phones = phoneService.readPhones(personId);
        refData.put("person", personService.readPersonById(personId));
        refData.put("phones", phones);
        if (logger.isDebugEnabled()) {
            for (Phone p : phones) {
                logger.debug("### phone: " + p.getNumber());
            }
        }
        List<Phone> currentPhones = phoneService.readCurrentPhones(personId, Calendar.getInstance(), false);
        refData.put("currentPhones", currentPhones);
        List<Phone> currentCorrespondencePhones = phoneService.readCurrentPhones(personId, Calendar.getInstance(), true);
        refData.put("currentCorrespondencePhones", currentCorrespondencePhones);
        return refData;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        phoneService.savePhone((Phone) command);
        String personIdString = request.getParameter("personId");
        Long personId = Long.valueOf(personIdString);
        List<Phone> phones = phoneService.readPhones(personId);
        Person person = personService.readPersonById(personId);
        ModelAndView mav = new ModelAndView("phone/phoneManager"); // TODO: Move to context XML
        mav.addObject("phones", phones);
        List<Phone> currentPhones = phoneService.readCurrentPhones(personId, Calendar.getInstance(), false);
        mav.addObject("currentPhones", currentPhones);
        List<Phone> currentCorrespondencePhones = phoneService.readCurrentPhones(personId, Calendar.getInstance(), true);
        mav.addObject("currentCorrespondencePhones", currentCorrespondencePhones);
        mav.addObject("person", person);
        mav.addObject("phone", new Phone());
        mav.addObject(personIdString);
        return mav;
    }
}
