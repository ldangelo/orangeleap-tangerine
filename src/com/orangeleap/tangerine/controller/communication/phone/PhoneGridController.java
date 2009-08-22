package com.orangeleap.tangerine.controller.communication.phone;

import com.orangeleap.tangerine.controller.TangerineGridController;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.util.StringConstants;

import javax.servlet.http.HttpServletRequest;

public class PhoneGridController extends TangerineGridController {

    @Override
    protected Object getDummyEntity(HttpServletRequest request) {
        return new Phone(getIdAsLong(request, StringConstants.CONSTITUENT_ID));
    }
}