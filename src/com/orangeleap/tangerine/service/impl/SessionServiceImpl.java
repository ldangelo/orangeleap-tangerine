package com.orangeleap.tangerine.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.service.SessionService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.util.TangerineUserHelper;

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
