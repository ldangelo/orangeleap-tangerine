package com.mpower.controller.phone;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.controller.constituent.RequiresConstituentEditor;
import com.mpower.domain.model.communication.Phone;
import com.mpower.service.PhoneService;
import com.mpower.type.ActivationType;
import com.mpower.util.StringConstants;

public class PhoneEditor extends RequiresConstituentEditor {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="phoneService")
    private PhoneService phoneService;

    public PhoneEditor() {
        super();
    }

    public PhoneEditor(String personId) {
        super(personId);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (NumberUtils.isDigits(text)) {
            Long phoneId = NumberUtils.createLong(text);
            Phone a = phoneService.read(phoneId);
            setValue(a);
        }
        else if (StringConstants.NEW.equals(text)){
            Phone a = new Phone(super.getPerson().getId());
            a.setActivationStatus(ActivationType.permanent);
            a.setPhoneType("home");
            a.setUserCreated(true);
            setValue(a);
        }
    }
}
