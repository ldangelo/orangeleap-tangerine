package com.mpower.controller.phone;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.controller.constituent.RequiresConstituentEditor;
import com.mpower.domain.Phone;
import com.mpower.service.PersonService;
import com.mpower.service.PhoneService;
import com.mpower.util.StringConstants;

public class PhoneEditor extends RequiresConstituentEditor {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PhoneService phoneService;

    public PhoneEditor() {
        super();
    }

    public PhoneEditor(PhoneService phoneService, PersonService personService, String personId) {
        super(personService, personId);
        setPhoneService(phoneService);
    }

    public void setPhoneService(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (NumberUtils.isDigits(text)) {
            Long phoneId = NumberUtils.createLong(text);
            Phone a = phoneService.readPhone(phoneId);
            setValue(a);
        }
        else if (StringConstants.NEW.equals(text)){
            Phone a = new Phone(super.getPerson());
            a.setActivationStatus("permanent");
            a.setPhoneType("home");
            a.setUserCreated(true);
            setValue(a);
        }
    }
}
