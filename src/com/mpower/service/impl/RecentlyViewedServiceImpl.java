package com.mpower.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.interfaces.RecentlyViewedDao;
import com.mpower.service.RecentlyViewedService;

/**
 * @version 1.0
 */
@Service("recentlyViewedService")
@Transactional(propagation = Propagation.REQUIRED)
public class RecentlyViewedServiceImpl extends AbstractTangerineService implements RecentlyViewedService {

    @Resource(name = "recentlyViewedDAO")
    private RecentlyViewedDao recentlyViewedDao;

    private int maxRows = 15; // max values in recently used

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Map> addRecentlyViewed(Long acctNumber) {

        String userName = tangerineUserHelper.lookupUserName();
        return recentlyViewedDao.addRecentlyViewed(userName, acctNumber, maxRows);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Map> getRecentlyViewed() {

        String userName = tangerineUserHelper.lookupUserName();
        return recentlyViewedDao.getRecentlyViewed(userName);

    }
}
