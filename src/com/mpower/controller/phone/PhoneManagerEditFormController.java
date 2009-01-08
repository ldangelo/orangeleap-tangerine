package com.mpower.controller.phone;

import javax.servlet.http.HttpServletRequest;

import com.mpower.domain.Viewable;
import com.mpower.service.CloneService;

public class PhoneManagerEditFormController extends PhoneFormController {

    @Override
    protected Viewable findViewable(HttpServletRequest request) {
        return ((CloneService)phoneService).clone(super.findViewable(request));
    }
}
