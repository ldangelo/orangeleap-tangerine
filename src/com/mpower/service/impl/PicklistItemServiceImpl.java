package com.mpower.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.customization.FieldDao;
import com.mpower.domain.Site;
import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.PicklistItem;
import com.mpower.service.AuditService;
import com.mpower.service.PicklistItemService;
import com.mpower.service.SiteService;

/*
 * Manages picklist items for site.
 */

@Service("picklistItemService")
public class PicklistItemServiceImpl implements PicklistItemService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "siteService")
    private SiteService siteService;

    @Resource(name = "fieldDao")
    private FieldDao picklistItemDao;
    
	// The unique key for PICKLIST is just id, not site + id, so id's have to be kept unique
	public static String addSiteToId(String siteName, String picklistId) {
		if (picklistId.contains("-")) throw new RuntimeException("Invalid picklistId character.");
		return siteName + "-" + picklistId;
	}
	public static String removeSiteFromId(String picklistId) {
		if (!picklistId.contains("-")) throw new RuntimeException("Invalid picklistId character.");
		return picklistId.substring(picklistId.indexOf("-") + 1);
	}
	

	private boolean exclude(Picklist picklist) {
		String name = picklist.getPicklistName();
		return name == null 
			|| name.equals("frequency")
			|| name.equals("constituentType")
			|| name.equals("phoneType")  // editing the default values of these person info items may cause problems, so disable editing for now
			|| name.equals("emailType")  // editing the default values of these person info items may cause problems, so disable editing for now
			|| name.equals("addressType")  // editing the default values of these person info items may cause problems, so disable editing for now
		;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public Picklist getPicklist(String siteName, String picklistId) {
		
		if (picklistId == null || picklistId.length() == 0) return null;
		
		Picklist picklist = picklistItemDao.readPicklistById(picklistId);
		if (picklist == null) {
			picklist = picklistItemDao.readPicklistById(removeSiteFromId(picklistId));
		}
		if (picklist == null) return null;
		
		if (picklist.getSite() == null) {
			return createCopy(picklist, siteName);
		} else if (picklist.getSite().getName().equals(siteName)) {
			return picklist;
		} else {
			return null;
		}
		
	}

    
	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public List<Picklist> listPicklists(String siteName) {
		
		List<Picklist> list = picklistItemDao.listPicklists(siteName);
		Iterator<Picklist> it = list.iterator();
		while (it.hasNext()) {
			if (exclude(it.next())) it.remove();
		}
		
		// Overridden, site-specific picklists
		List<Picklist> result = new ArrayList<Picklist>();
		for (Picklist picklist : list) {
			if (picklist.getSite() != null) {
				result.add(picklist);
			}
		}
		
		// Non-overriden, global picklists
		for (Picklist picklist : list) {
			if (picklist.getSite() == null) {
				boolean found = false;
				for (Picklist apicklist : result) if (apicklist.getPicklistName().equals(picklist.getPicklistName())) found = true;
				if (!found) {
					result.add(createCopy(picklist, siteName));
				}
			}
		}
		
		return result;
	}
	
	private Picklist createCopy(Picklist template, String siteName) {
	
		// Need to create editable clones for all site == null picklists 
		// Site == null items are global and should not be allowed to be edited.
		
		Picklist result = null;
		try {
			result = (Picklist)BeanUtils.cloneBean(template);
			result.setId(addSiteToId(siteName, template.getId()));
			result.setSite(getSite(siteName));
			result.setPicklistItems(new ArrayList<PicklistItem>());
			List<PicklistItem> items = template.getPicklistItems();
			for (PicklistItem item: items) {
				PicklistItem newItem = (PicklistItem)BeanUtils.cloneBean(item);
				newItem.setId(new Long(-item.getId())); // not persisted yet, but needs unique id
				newItem.setPicklist(result);
				result.getPicklistItems().add(newItem);
			}
		} catch (Exception e) {
			logger.error("Cannot create copy of default picklist." ,e);
    	}
		return result;
		
	}
	
	private Site getSite(String siteName) {
		List<Site> sites = siteService.readSites();
	    for (Site site: sites) if (site.getName().equals(siteName)) return site;
	    throw new RuntimeException("Invalid site name: " + siteName);
	}
	
	private void removeBlankItems(Picklist picklist) {
    	Iterator<PicklistItem> it = picklist.getPicklistItems().iterator();
    	while (it.hasNext()) {
    		PicklistItem item = it.next();
    		if (item.getItemName() == null || item.getItemName().length() == 0) it.remove();
    	}
	}

	// Note caller must add siteName to picklist id before saving
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PicklistItem maintainPicklistItem(String siteName, PicklistItem picklistItem) {
    	
    	// Sanity checks
    	if (picklistItem == null || picklistItem.getItemName() == null || picklistItem.getItemName().length() == 0) {
    		throw new RuntimeException("PicklistItem is blank.");
    	}
    	Picklist picklist = picklistItem.getPicklist();
    	if (picklist == null 
    			|| !picklist.getId().contains("-")
    			|| picklist.getSite() == null 
    			|| picklist.getSite().getName().length() == 0
    			) {
    		throw new RuntimeException("Cannot update non-site-specific entry for PicklistItem "+picklistItem.getId());
    	}

    	// See if site-specific picklist already exists in db
    	Picklist dbPicklist = picklistItemDao.readPicklistById(picklist.getId());
    	if (dbPicklist == null) {
    		
    		logger.info("Creating "+picklist.getSite().getName()+" site-specific copy of picklist "+picklist.getId());
    		removeBlankItems(picklist);
    		
    		// If picklist not found it's copied from global picklist to a new site-specific picklist.
    		boolean found = false;
        	for (PicklistItem apicklistItem : picklist.getPicklistItems()) {
        	    if (apicklistItem.getItemName().equals(picklistItem.getItemName())) found = true;
        	}
        	if (!found) picklist.getPicklistItems().add(picklistItem);

        	// Set order and reset item ids.
        	for (int i = 0; i < picklist.getPicklistItems().size(); i++) {
        		PicklistItem apicklistItem = picklist.getPicklistItems().get(i);
        		apicklistItem.setItemOrder(new Integer(i+1));
        	    apicklistItem.setId(null);
        	    apicklistItem.setPicklist(picklist);
        	}
            
        	picklist = picklistItemDao.maintainPicklist(picklist);

    		removeBlankItems(picklist);
    		
            for (PicklistItem apicklistItem: picklist.getPicklistItems()) {
            	if (apicklistItem.getItemName().equals(picklistItem.getItemName())) {
            		auditService.auditObject(apicklistItem);
            		return apicklistItem;
            	}
            }

            throw new RuntimeException("Unable to update "+picklistItem.getItemName());
            
    	} else {
    	
    		logger.info("Updating "+picklist.getSite().getName()+" site-specific copy of picklist item "+picklistItem.getItemName());

    		if (picklistItem.getId() != null) {
	        	PicklistItem dbPicklistItem = picklistItemDao.readPicklistItemById(picklistItem.getId());
	        	PicklistItem oldPicklistItem = new PicklistItem();
	            try {
	                BeanUtils.copyProperties(oldPicklistItem, dbPicklistItem);
	                
	                BeanUtils.copyProperties(dbPicklistItem, picklistItem);
	                picklistItem = dbPicklistItem;
	                
	                picklistItem.setOriginalObject(oldPicklistItem);
	            } catch (IllegalAccessException e) {
	            } catch (InvocationTargetException e) {
	            }
	        }
    		
	        picklistItem = picklistItemDao.maintainPicklistItem(picklistItem);
	        auditService.auditObject(picklistItem);
    		
	        return picklistItem;
	        
    	}
    	
    }


}
