package com.mpower.controller.address;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Viewable;
import com.mpower.service.CloneService;

public class AddressManagerEditFormController extends AddressFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected Viewable findViewable(HttpServletRequest request) {
        return ((CloneService)addressService).clone(super.findViewable(request));
    }
}
