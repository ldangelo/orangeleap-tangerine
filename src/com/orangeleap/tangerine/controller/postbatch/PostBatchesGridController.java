package com.orangeleap.tangerine.controller.postbatch;

import com.orangeleap.tangerine.controller.TangerineGridController;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.type.AccessType;
import org.springframework.web.util.WebUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PostBatchesGridController extends TangerineGridController {

    @SuppressWarnings("unchecked")
    public void checkAccess(HttpServletRequest request) {
        Map<String, AccessType> pageAccess = (Map<String, AccessType>) WebUtils.getSessionAttribute(request, "pageAccess");
        if (pageAccess.get("/postbatch.htm") != AccessType.ALLOWED) {
            throw new RuntimeException("You are not authorized to access this page"); // TODO: use invalid access exception and move to filter
        }
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        checkAccess(request);
        return super.handleRequestInternal(request, response);
    }

    @Override
    protected Object getDummyEntity(HttpServletRequest request) {
        return new PostBatch();
    }
}
