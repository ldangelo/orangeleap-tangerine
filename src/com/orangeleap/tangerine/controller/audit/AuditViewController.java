package com.orangeleap.tangerine.controller.audit;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
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

public class AuditViewController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="auditService")
    private AuditService auditService;

    @Resource(name="constituentService")
    private ConstituentService constituentService;

	@SuppressWarnings("unchecked")
	public static boolean accessAllowed(HttpServletRequest request) {
		Map<String, AccessType> pageAccess = (Map<String, AccessType>)WebUtils.getSessionAttribute(request, "pageAccess");
		return pageAccess.get("/audit.htm") == AccessType.ALLOWED;
	}


    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	if (!accessAllowed(request)) return null; 
    	
        String entityType = request.getParameter("object");
        String objectId = request.getParameter("id");
        List<Audit> audits = null;
        ModelAndView mav = new ModelAndView(super.getViewName());
        if (GenericValidator.isBlankOrNull(entityType) || GenericValidator.isBlankOrNull(objectId)) {
            audits = auditService.allAuditHistoryForSite();
        } else {
            if (EntityType.valueOf(entityType) == EntityType.constituent) {
                Constituent constituent = constituentService.readConstituentById(Long.valueOf(objectId));
                mav.addObject("constituent", constituent);
                audits = auditService.auditHistoryForConstituent(constituent.getId());
            } else {
                audits = auditService.auditHistoryForEntity(EntityType.valueOf(entityType).name(), Long.valueOf(objectId));
            }
        }

        mav.addObject("audits", audits);
        return mav;
    }
}
