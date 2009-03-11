package com.orangeleap.tangerine.controller.communication.address;

import com.orangeleap.tangerine.controller.communication.CommunicationEditor;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.service.AddressService;
import com.orangeleap.tangerine.service.CommunicationService;

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
