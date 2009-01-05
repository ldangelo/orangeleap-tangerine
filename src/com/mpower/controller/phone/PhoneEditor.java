package com.mpower.controller.phone;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Phone;
import com.mpower.service.PhoneService;

public class PhoneEditor extends PropertyEditorSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PhoneService phoneService;

    public PhoneEditor() {
        super();
    }

    public PhoneEditor(PhoneService phoneService) {
        super();
        setPhoneService(phoneService);
    }

    public void setPhoneService(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (NumberUtils.isDigits(text)) {
            Long phoneId = NumberUtils.createLong(text);
            Phone a = phoneService.readPhone(phoneId);
            setValue(a);
        } else {
            Phone a = new Phone();
            a.setActivationStatus("permanent");
            a.setPhoneType("home");
            setValue(a);
        }
    }
}
