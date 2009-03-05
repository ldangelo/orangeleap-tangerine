package com.mpower.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.domain.model.Site;
import com.mpower.service.SessionService;
import com.mpower.service.SiteService;
import com.mpower.util.TangerineUserHelper;

@Component("sessionService")
@Transactional(propagation = Propagation.REQUIRED)
public class SessionServiceImpl extends AbstractTangerineService implements SessionService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "siteService")
    private SiteService siteService;

    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    @Override
    public Site lookupSite() {
    	return siteService.createSiteAndUserIfNotExist(tangerineUserHelper.lookupUserSiteName());
    }
}
