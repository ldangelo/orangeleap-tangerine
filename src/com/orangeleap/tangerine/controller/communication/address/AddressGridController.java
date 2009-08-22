package com.orangeleap.tangerine.controller.communication.address;

import com.orangeleap.tangerine.controller.TangerineGridController;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.util.StringConstants;

import javax.servlet.http.HttpServletRequest;

public class AddressGridController extends TangerineGridController {

    @Override
    protected Object getDummyEntity(HttpServletRequest request) {
        return new Address(getIdAsLong(request, StringConstants.CONSTITUENT_ID));
    }
}
