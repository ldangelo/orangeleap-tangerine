package com.orangeleap.tangerine.controller.communication.phone;

import javax.annotation.Resource;

import com.orangeleap.tangerine.controller.communication.CommunicationEditor;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.service.CommunicationService;
import com.orangeleap.tangerine.service.PhoneService;

public class PhoneEditor extends CommunicationEditor<Phone> {

    @Resource(name="phoneService")
    private PhoneService phoneService;

    public PhoneEditor() {
        super();
    }

    public PhoneEditor(PhoneService phoneService, String constituentId) {
        super(constituentId);
        this.phoneService = phoneService;
    }

    @Override
    protected Phone createEntity(Long constituentId) {
        return new Phone(constituentId);
    }

    @Override
    protected CommunicationService<Phone> getCommunicationService() {
        return phoneService;
    }
}
