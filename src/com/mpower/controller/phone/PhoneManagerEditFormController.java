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

import org.apache.commons.beanutils.BeanUtils;
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

public class PhoneManagerEditFormController extends SimpleFormController {

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
        String phoneId = request.getParameter("phoneId");
        Phone phone = null;
        if (phoneId == null) {
            Person person = personService.readPersonById(Long.valueOf(personId));
            phone = new Phone();
            phone.setPerson(person);
        } else {
            Phone originalPhone = phoneService.readPhone(Long.valueOf(phoneId));
            try {
                phone = (Phone) BeanUtils.cloneBean(originalPhone);
                phone.setId(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (isFormSubmission(request)) {
            Map<String, String> fieldLabelMap = siteService.readFieldLabels(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(getCommandName()), SessionServiceImpl.lookupUserRoles(), request.getLocale());
            phone.setFieldLabelMap(fieldLabelMap);

            Map<String, Object> valueMap = siteService.readFieldValues(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(getCommandName()), SessionServiceImpl.lookupUserRoles(), phone);
            phone.setFieldValueMap(valueMap);
        }
        return phone;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Phone phone = (Phone) command;
        phoneService.savePhone(phone);
        String personIdString = request.getParameter("personId");
        Long personId = Long.valueOf(personIdString);
        List<Phone> phones = phoneService.readPhones(personId);
        Person person = personService.readPersonById(personId);
        ModelAndView mav = new ModelAndView("phone/phoneManager");
        mav.addObject("phones", phones);
        List<Phone> currentPhones = phoneService.readCurrentPhones(personId, Calendar.getInstance(), false);
        mav.addObject("currentPhones", currentPhones);
        List<Phone> currentCorrespondencePhones = phoneService.readCurrentPhones(personId, Calendar.getInstance(), true);
        mav.addObject("currentCorrespondencePhones", currentCorrespondencePhones);
        phone = new Phone();
        phone.setPerson(person);
        mav.addObject("redirect:/phoneManager.htm?personId=" + personIdString, errors.getModel());
        mav.addObject("person", person);
        mav.addObject("phone", phone);
        mav.addObject(personIdString);
        return mav;
    }
}
