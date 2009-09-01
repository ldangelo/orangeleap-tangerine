package com.orangeleap.tangerine.controller.communicationHistory;

import com.orangeleap.tangerine.controller.TangerineGridController;
import com.orangeleap.tangerine.domain.CommunicationHistory;

import javax.servlet.http.HttpServletRequest;

public class CommunicationHistoryGridController extends TangerineGridController {

    @Override
    protected Object getDummyEntity(HttpServletRequest request) {
        return new CommunicationHistory(getConstituent(request));
    }
}