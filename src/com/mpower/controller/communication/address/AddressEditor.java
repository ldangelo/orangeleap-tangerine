package com.mpower.controller.communication.address;

import javax.annotation.Resource;

import com.mpower.controller.communication.CommunicationEditor;
import com.mpower.domain.model.communication.Address;
import com.mpower.service.AddressService;
import com.mpower.service.CommunicationService;

public class AddressEditor extends CommunicationEditor<Address> {

    @Resource(name="addressService")
    private AddressService addressService;

    public AddressEditor() {
        super();
    }

    public AddressEditor(String constituentId) {
        super(constituentId);
    }

    @Override
    protected Address createEntity(Long constituentId) {
        return new Address(constituentId);
    }

    @Override
    protected CommunicationService<Address> getCommunicationService() {
        return addressService;
    }
}
