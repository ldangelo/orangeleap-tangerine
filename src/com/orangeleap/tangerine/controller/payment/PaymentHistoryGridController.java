package com.orangeleap.tangerine.controller.payment;

import com.orangeleap.tangerine.controller.TangerineGridController;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.PaymentHistory;
import com.orangeleap.tangerine.util.StringConstants;

import javax.servlet.http.HttpServletRequest;

public class PaymentHistoryGridController extends TangerineGridController {

    @Override
    protected Object getDummyEntity(HttpServletRequest request) {
        return new PaymentHistory(getConstituent(request));
    }
}