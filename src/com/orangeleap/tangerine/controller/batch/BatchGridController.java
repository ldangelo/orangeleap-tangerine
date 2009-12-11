package com.orangeleap.tangerine.controller.batch;

import com.orangeleap.tangerine.controller.TangerineGridController;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.type.PageType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class BatchGridController extends TangerineGridController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        tangerineListHelper.checkAccess(request, PageType.batch);
        ModelAndView mav = super.handleRequestInternal(request, response);
        mav.addObject("allowCreate", tangerineListHelper.isAccessAllowed(request, PageType.createBatch));
        mav.addObject("allowExecute", tangerineListHelper.isAccessAllowed(request, PageType.executeBatch));
        mav.addObject("allowDelete", tangerineListHelper.isAccessAllowed(request, PageType.deleteBatch));
        return mav;
    }

    @Override
    protected Object getDummyEntity(HttpServletRequest request) {
        return new PostBatch();
    }
}
