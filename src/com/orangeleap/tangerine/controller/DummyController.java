/*
Used for JSPs that use json controllers to populate themselves
 */

package com.orangeleap.tangerine.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.domain.Audit;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.AuditService;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.type.EntityType;

public class DummyController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="constituentService")
    private ConstituentService constituentService;


    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView(super.getViewName());
        String id = request.getParameter(StringConstants.CONSTITUENT_ID);
        if (id != null && id.trim().length() > 0) {
            Long constituentId = Long.valueOf(id);
            Constituent constituent = constituentService.readConstituentById(Long.valueOf(constituentId));
            mav.addObject("constituent", constituent);
        }
        return mav;
    }
}