package com.mpower.controller.address;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.controller.constituent.RequiresConstituentEditor;
import com.mpower.domain.Address;
import com.mpower.service.AddressService;
import com.mpower.service.PersonService;

public class AddressEditor extends RequiresConstituentEditor {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private AddressService addressService;

    public AddressEditor() {
        super();
    }

    public AddressEditor(AddressService addressService, PersonService personService, String personId) {
        super(personService, personId);
        setAddressService(addressService);
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (NumberUtils.isDigits(text)) {
            Long addressId = NumberUtils.createLong(text);
            Address a = addressService.readAddress(addressId);
            setValue(a);
        }
        else if ("new".equals(text)){
            Address a = new Address(super.getPerson());
            a.setActivationStatus("permanent");
            a.setAddressType("home");
            setValue(a);
        }
    }
}
