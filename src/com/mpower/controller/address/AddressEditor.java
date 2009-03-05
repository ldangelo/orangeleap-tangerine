package com.mpower.controller.address;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.controller.constituent.RequiresConstituentEditor;
import com.mpower.domain.model.communication.Address;
import com.mpower.service.AddressService;
import com.mpower.type.ActivationType;

public class AddressEditor extends RequiresConstituentEditor {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="addressService")
    private AddressService addressService;

    public AddressEditor() {
        super();
    }

    public AddressEditor(String personId) {
        super(personId);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (NumberUtils.isDigits(text)) {
            Long addressId = NumberUtils.createLong(text);
            Address a = addressService.read(addressId);
            setValue(a);
        }
        else if ("new".equals(text)){
            Address a = new Address(super.getPerson().getId());
            a.setUserCreated(true);
            a.setActivationStatus(ActivationType.permanent);
            a.setAddressType("home");
            setValue(a);
        }
    }
}
