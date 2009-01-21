package com.mpower.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.customization.FieldDao;
import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.PicklistItem;
import com.mpower.service.AuditService;
import com.mpower.service.PicklistItemService;

/*
 * Manages picklist items for site.
 */

@Service("picklistItemService")
public class PicklistItemServiceImpl implements PicklistItemService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "fieldDao")
    private FieldDao picklistItemDao;

    
	@Override
	public List<String> listPicklists(String siteName) {
		List<String> list = picklistItemDao.listPicklists(siteName);
		// TODO Need to create clones here for all site == null picklists and de-dup based on picklistName
		// Site == null items should not be allowed to be edited (and fails in audit)
		return list;
	}
	
	private Picklist createCopy(Picklist template, String siteName) {
		
		Picklist result = null;
		try {
			result = (Picklist)BeanUtils.cloneBean(template);
			List<PicklistItem> items = template.getPicklistItems();
			for (PicklistItem item: items) {
				result.getPicklistItems().add((PicklistItem)BeanUtils.cloneBean(item));
			}
		} catch (Exception e) {
			logger.error("Cannot create copy of default picklist." ,e);
    	}
		return result;
		
	}

	@Override
	public Picklist readPicklist(String picklistId) {
    	if (picklistId == null || picklistId.length() == 0) return null;
		return picklistItemDao.readPicklist(picklistId);
	}
    
    @Override
    public PicklistItem readPicklistItemById(Long id) {
        return picklistItemDao.readPicklistItem(id);
    }
    
    @Override
    public List<PicklistItem> readPicklistItems(String siteName, String picklistId) {
        return picklistItemDao.readPicklist(picklistId).getPicklistItems();
    }

    @Override
    public List<PicklistItem> readPicklistItems(String siteName, String picklistId, String startsWith) {
    	if (picklistId == null || picklistId.length() == 0) return new ArrayList<PicklistItem>();
        return picklistItemDao.readPicklistItemsByPicklistId(picklistId, startsWith, null); 
    }

    @Override
    public List<PicklistItem> readPicklistItems(String siteName, String picklistId, String startsWith, String partialDescription, Boolean inactive) {
        return picklistItemDao.readPicklistItemsByPicklistId(picklistId, startsWith, partialDescription); 
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PicklistItem maintainPicklistItem(PicklistItem picklistItem) {
    	
    	// Sanity check
    	if (picklistItem.getPicklist() == null || picklistItem.getPicklist().getSite() == null) throw new RuntimeException("Cannot update non-site-specific entry for PicklistItem "+picklistItem.getId());
    	
    	// TODO cleanup code from Code maint
    	PicklistItem oldPicklistItem = null;
        if (picklistItem.getId() != null) {
        	PicklistItem dbPicklistItem = picklistItemDao.readPicklistItem(picklistItem.getId());
            oldPicklistItem = picklistItemDao.readPicklistItem(picklistItem.getId());
            oldPicklistItem = new PicklistItem();
            try {
                BeanUtils.copyProperties(oldPicklistItem, dbPicklistItem);
                picklistItem.setOriginalObject(oldPicklistItem);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
        picklistItem = picklistItemDao.maintainPicklistItem(picklistItem);
        picklistItem.setOriginalObject(oldPicklistItem);
        auditService.auditObject(picklistItem);
        return picklistItem;
    }

}
