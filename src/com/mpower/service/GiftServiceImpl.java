package com.mpower.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.GiftDao;
import com.mpower.dao.SiteDao;
import com.mpower.entity.Gift;
import com.mpower.entity.Person;
import com.mpower.entity.customization.EntityDefault;
import com.mpower.type.EntityType;

@Service("giftService")
public class GiftServiceImpl implements GiftService {

    @Resource(name = "giftDao")
    private GiftDao giftDao;

    @Resource(name = "siteDao")
    private SiteDao siteDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Gift maintainGift(Gift gift) {
        return giftDao.maintainGift(gift);
    }

    @Override
    public Gift readGiftById(Long giftId) {
        return giftDao.readGift(giftId);
    }

    @Override
    public List<Gift> readGifts(Person person) {
        return giftDao.readGifts(person.getId());
    }

    @Override
    public Gift createDefaultGift(Long siteId) {
        // get initial gift with built-in defaults
        Gift gift = new Gift();
        BeanWrapper personBeanWrapper = new BeanWrapperImpl(gift);

        List<EntityDefault> entityDefaults = siteDao.readEntityDefaults(siteId, Arrays.asList(new EntityType[] { EntityType.gift }));
        for (EntityDefault ed : entityDefaults) {
            personBeanWrapper.setPropertyValue(ed.getEntityFieldName(), ed.getDefaultValue());
        }

        // TODO: consider caching techniques for the default Person
        return gift;
    }
}
