package com.mpower.controller.communication.address;

import com.mpower.controller.communication.CommunicationEditor;
import com.mpower.domain.model.communication.Address;
import com.mpower.service.AddressService;
import com.mpower.service.CommunicationService;

public class AddressEditor extends CommunicationEditor<Address> {

    private AddressService addressService;

    public AddressEditor() {
        super();
    }

    public AddressEditor(AddressService addressService, String constituentId) {
        super(constituentId);
        this.addressService = addressService;
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
