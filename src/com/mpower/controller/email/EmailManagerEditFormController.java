package com.mpower.controller.email;

import javax.servlet.http.HttpServletRequest;

import com.mpower.domain.model.AbstractEntity;
import com.mpower.service.CloneService;

public class EmailManagerEditFormController extends EmailFormController {

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        return ((CloneService)emailService).clone(super.findEntity(request));
    }
}
