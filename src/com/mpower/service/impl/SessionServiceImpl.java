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
public class SessionServiceImpl implements SessionService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "siteService")
    private SiteService siteService;

    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    @Override
    public Site lookupSite() {
        // TODO: remove cloning logic for IBatis
    	Site site = siteService.createSiteAndUserIfNotExist(tangerineUserHelper.lookupUserSiteName());
    	Site siteClone = new Site();
    	siteClone.setName(site.getName());
    	siteClone.setMerchantNumber(site.getMerchantNumber());
    	siteClone.setCreateDate(site.getCreateDate());
    	siteClone.setUpdateDate(site.getUpdateDate());
    	if (site.getParentSite() != null) {
        	Site parentSite = new Site();
        	parentSite.setName(site.getParentSite().getName());
        	siteClone.setParentSite(parentSite);
    	}
    	return siteClone;
    }
}
