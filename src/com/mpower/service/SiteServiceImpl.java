package com.mpower.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.mpower.dao.SiteDao;
import com.mpower.domain.Site;

@Service("siteService")
public class SiteServiceImpl implements SiteService {
   
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
	@Resource(name = "siteDao")
    private SiteDao siteDao;
    
	@Override
	public List<Site> readSites() {
		return siteDao.readSites();		
	}

}
