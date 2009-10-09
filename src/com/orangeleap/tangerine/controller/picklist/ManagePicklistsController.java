package com.orangeleap.tangerine.controller.picklist;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.service.PicklistItemService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ManagePicklistsController extends ParameterizableViewController {

    @Resource(name = "picklistItemService")
    private PicklistItemService picklistItemService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Picklist> picklists = picklistItemService.listPicklists();
        return new ModelAndView(getViewName(), "picklists", picklists);
    }
}
