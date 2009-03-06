package com.mpower.controller.communication.phone;

import javax.annotation.Resource;

import com.mpower.controller.communication.CommunicationEditor;
import com.mpower.domain.model.communication.Phone;
import com.mpower.service.CommunicationService;
import com.mpower.service.PhoneService;

public class PhoneEditor extends CommunicationEditor<Phone> {

    @Resource(name="phoneService")
    private PhoneService phoneService;

    public PhoneEditor() {
        super();
    }

    public PhoneEditor(String constituentId) {
        super(constituentId);
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
