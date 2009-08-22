package com.orangeleap.tangerine.controller.communication.email;

import com.orangeleap.tangerine.controller.TangerineGridController;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.util.StringConstants;

import javax.servlet.http.HttpServletRequest;

public class EmailGridController extends TangerineGridController {

    @Override
    protected Object getDummyEntity(HttpServletRequest request) {
        return new Email(getIdAsLong(request, StringConstants.CONSTITUENT_ID));
    }
}