package com.mpower.controller.email;

import javax.servlet.http.HttpServletRequest;

import com.mpower.domain.Viewable;
import com.mpower.service.CloneService;

public class EmailManagerEditFormController extends EmailFormController {

    @Override
    protected Viewable findViewable(HttpServletRequest request) {
        return ((CloneService)emailService).clone(super.findViewable(request));
    }
}
