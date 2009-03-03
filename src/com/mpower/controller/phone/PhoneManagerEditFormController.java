package com.mpower.controller.phone;

import javax.servlet.http.HttpServletRequest;

import com.mpower.domain.model.AbstractEntity;
import com.mpower.service.CloneService;

public class PhoneManagerEditFormController extends PhoneFormController {

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        return ((CloneService)phoneService).clone(super.findEntity(request));
    }
}
