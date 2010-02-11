package com.orangeleap.tangerine.controller.background;

import javax.servlet.http.HttpServletRequest;

import com.orangeleap.tangerine.controller.TangerineGridController;
import com.orangeleap.tangerine.domain.Background;

public class BackgroundGridController extends TangerineGridController {

    @Override
    protected Object getDummyEntity(HttpServletRequest request) {
        return new Background(getConstituent(request));
    }
}