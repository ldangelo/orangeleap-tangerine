package com.orangeleap.tangerine.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.PicklistDao;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.service.AuditService;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.SiteService;

/*
 * Manages picklist items for site.
 */

@Service("picklistItemService")
@Transactional(propagation = Propagation.REQUIRED)
public class PicklistItemServiceImpl extends AbstractTangerineService implements PicklistItemService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "siteService")
    private SiteService siteService;

    @Resource(name = "picklistDAO")
    private PicklistDao picklistDao;
    
	// The unique key for PICKLIST is just id, not site + id, so id's have to be kept unique
	public static String addSiteToId(String siteName, String picklistId) {
		if (picklistId.contains("-")) {
            throw new RuntimeException("Invalid picklistId character.");
        }
		return siteName + "-" + picklistId;
	}
	public static String removeSiteFromId(String picklistId) {
		if (!picklistId.contains("-")) {
            throw new RuntimeException("Invalid picklistId character.");
        }
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
	public Picklist getPicklist(String picklistId) {
		
		if (picklistId == null || picklistId.length() == 0) {
            return null;
        }
		
		Picklist picklist = picklistDao.readPicklistById(picklistId);
		if (picklist == null) {
			picklist = picklistDao.readPicklistById(removeSiteFromId(picklistId));
		}
		if (picklist == null) {
            return null;
        }
		
		if (picklist.getSite() == null) {
			picklist = createCopy(picklist);
			return picklistDao.maintainPicklist(picklist);
		} else if (picklist.getSite().getName().equals(getSiteName())) {
			return picklist;
		} else {
			return null;
		}
		
	}

    
	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public List<Picklist> listPicklists() {
		
		List<Picklist> list = picklistDao.listPicklists();
		Iterator<Picklist> it = list.iterator();
		while (it.hasNext()) {
			if (exclude(it.next())) {
                it.remove();
            }
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
				for (Picklist apicklist : result) {
                    if (apicklist.getPicklistDesc().equals(picklist.getPicklistDesc())) {
                        found = true;
                    }
                }
				if (!found) {
					result.add(createCopy(picklist));
				}
			}
		}
		
		return result;
	}
	
	private Picklist createCopy(Picklist template) {
	
		// Need to create editable clones for all site == null picklists 
		// Site == null items are global and should not be allowed to be edited.
		
		Picklist result = null;
		try {
			result = (Picklist)BeanUtils.cloneBean(template);
			result.setId(addSiteToId(getSiteName(), template.getId()));
			result.setSite(getSite(getSiteName()));
			result.setPicklistItems(new ArrayList<PicklistItem>());
			List<PicklistItem> items = template.getPicklistItems();
			for (PicklistItem item: items) {
				PicklistItem newItem = (PicklistItem)BeanUtils.cloneBean(item);
				newItem.setId(null); 
				newItem.setPicklistId(result.getId());
				result.getPicklistItems().add(newItem);
			}
		} catch (Exception e) {
			logger.error("Cannot create copy of default picklist." ,e);
    	}
		return result;
		
	}
	
	private Site getSite(String siteName) {
		List<Site> sites = siteService.readSites();
	    for (Site site: sites) {
            if (site.getName().equals(siteName)) {
                return site;
            }
        }
	    throw new RuntimeException("Invalid site name: " + siteName);
	}
	
	private void removeBlankItems(Picklist picklist) {
    	Iterator<PicklistItem> it = picklist.getPicklistItems().iterator();
    	while (it.hasNext()) {
    		PicklistItem item = it.next();
    		if (item.getItemName() == null || item.getItemName().length() == 0) {
                it.remove();
            }
    	}
	}
	
	private void checkNotDefault(PicklistItem picklistItem) {
    	// Sanity checks
    	if (picklistItem == null || picklistItem.getPicklistId() == null || picklistItem.getItemName() == null || picklistItem.getItemName().length() == 0) {
    		throw new RuntimeException("PicklistItem is blank.");
    	}
    	if (!picklistItem.getPicklistId().contains("-")) {
    		throw new RuntimeException("PicklistItem picklist id is not site-specific.");
    	}

    	Picklist dbPicklist = picklistDao.readPicklistById(picklistItem.getPicklistId());
    	boolean objectReferencesDefaultPicklist = dbPicklist != null && dbPicklist.getSite() == null;
    	
    	PicklistItem dbPicklistItem = picklistDao.readPicklistItemById(picklistItem.getId());
    	boolean picklistItemIdIsOnDefaultPicklistInDb = dbPicklistItem != null && picklistDao.readPicklistById(dbPicklistItem.getPicklistId()).getSite() == null;
    	
    	if (objectReferencesDefaultPicklist || picklistItemIdIsOnDefaultPicklistInDb) {
    		throw new RuntimeException("Cannot update non-site-specific entry for PicklistItem "+picklistItem.getId());
    	}

	}

	// Note caller must add siteName to picklist id before saving
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PicklistItem maintainPicklistItem(PicklistItem picklistItem) {
    	
    	if (StringUtils.trimToNull(picklistItem.getItemName()) == null || StringUtils.trimToNull(picklistItem.getDefaultDisplayValue()) == null) throw new RuntimeException("Blank values not allowed");
    	
    	checkNotDefault(picklistItem);

    	Picklist picklist = getPicklist(picklistItem.getPicklistId());
    	
		logger.info("Updating "+picklist.getSite().getName()+" site-specific copy of picklist item "+picklistItem.getItemName());

		boolean found = false;
		Long id = picklistItem.getId();
    	if (id != null) {
            for (PicklistItem apicklistItem : picklist.getPicklistItems()) {
                if (picklistItem.getItemName().equals(apicklistItem.getItemName())) {
                	found = true;
                    try {
                    	Long origid = apicklistItem.getId();
            			BeanUtils.copyProperties(apicklistItem, picklistItem);
            			apicklistItem.setId(origid);
            		} catch (Exception e) {
            		}
                    break;
                }
            }
        }
    	if (!found) {
            picklist.getPicklistItems().add(picklistItem);
        }

    	// Set order and reset item ids.
    	for (int i = 0; i < picklist.getPicklistItems().size(); i++) {
    		PicklistItem apicklistItem = picklist.getPicklistItems().get(i);
    		apicklistItem.setItemOrder(new Integer(i+1));
    	    apicklistItem.setPicklistId(picklist.getId());
    	}
        
    	picklist = picklistDao.maintainPicklist(picklist);

 		// Audit
        for (PicklistItem apicklistItem: picklist.getPicklistItems()) {
        	if (apicklistItem.getItemName().equals(picklistItem.getItemName())) {
        		//auditService.auditObject(apicklistItem); // TODO fix for iBatis
        		return apicklistItem;
        	}
        }

        return picklistItem;
    	
    }


}
