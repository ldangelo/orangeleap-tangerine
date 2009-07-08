/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.controller.siteSettings;

import com.orangeleap.tangerine.dao.SiteDao;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class SiteSettingsController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "siteDAO")
    private SiteDao siteDao;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;


    @SuppressWarnings("unchecked")
    public static boolean accessAllowed(HttpServletRequest request) {
        Map<String, AccessType> pageAccess = (Map<String, AccessType>) WebUtils.getSessionAttribute(request, "pageAccess");
        return pageAccess.get("/siteSettings.htm") == AccessType.ALLOWED;
    }

    private final static String MASK = "*****";

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {

        if (!accessAllowed(request)) return null;

        ModelAndView mav = super.showForm(request, response, errors, controlModel);
        return mav;
    }

    private void maskPasswords(Site site) {
        site.setSmtpPassword(MASK);
        site.setJasperPassword(MASK);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        Site site = siteDao.readSite(tangerineUserHelper.lookupUserSiteName());
        maskPasswords(site);
        return site;
    }

    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {

        if (!accessAllowed(request)) return null;

        Site originalSite = siteDao.readSite(tangerineUserHelper.lookupUserSiteName());
        Site site = (Site) command;

        // They obviously can't change these properties:

        site.setName(originalSite.getName());
        site.setActive(originalSite.isActive());
        site.setCreateDate(originalSite.getCreateDate());


        // Update passwords only if they tried to change them:

        if (MASK.equals(site.getSmtpPassword())) {
            site.setSmtpPassword(originalSite.getSmtpPassword());
        }
        if (MASK.equals(site.getJasperPassword())) {
            site.setJasperPassword(originalSite.getJasperPassword());
        }


        String message = "";
        String errormessage = "";

        try {

            siteDao.updateSite(site);

            message = "Site information updated.";

        } catch (Exception e) {
            e.printStackTrace();
            errormessage = e.getMessage();
            errors.reject(errormessage, errormessage);
        }

        maskPasswords(site);

        ModelAndView mav = super.onSubmit(command, errors);
        mav.setViewName(super.getFormView());
        mav.addObject("message", message);
        mav.addObject("errormessage", errormessage);
        return mav;

    }


}
