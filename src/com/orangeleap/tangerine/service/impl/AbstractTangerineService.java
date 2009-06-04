package com.orangeleap.tangerine.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.communication.AbstractCommunicatorEntity;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public abstract class AbstractTangerineService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;
    
    @Resource(name="siteService")
    protected SiteService siteService;
   
    @Resource(name="picklistItemService")
    protected PicklistItemService picklistItemService;
   


    protected String getSiteName() {
        return tangerineUserHelper.lookupUserSiteName();
    }

	protected void setPicklistDefaultsForRequiredFields(AbstractEntity entity, PageType pageType, List<String> userRoles) {
		
		if (entity instanceof AbstractCommunicatorEntity) {
			AbstractCommunicatorEntity ace = (AbstractCommunicatorEntity)entity;
			if (ace.getPrimaryAddress() != null && ace.getPrimaryAddress().getCustomFieldValue("addressType") == null) {
				ace.getPrimaryAddress().setCustomFieldValue("addressType", "unknown");
			}
			if (ace.getPrimaryEmail() != null && ace.getPrimaryEmail().getCustomFieldValue("emailType") == null) {
				ace.getPrimaryEmail().setCustomFieldValue("emailType", "unknown");
			}
			if (ace.getPrimaryPhone() != null && ace.getPrimaryPhone().getCustomFieldValue("phoneType") == null) {
				ace.getPrimaryPhone().setCustomFieldValue("phoneType", "unknown");
			}
		}
		
		Map<String, FieldRequired> requiredFieldMap = siteService.readRequiredFields(pageType, userRoles);
		if (requiredFieldMap != null) {
			BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(entity);
			for (String key : requiredFieldMap.keySet()) {
				if (bw.isReadableProperty(key)) {
					Object ovalue = bw.getPropertyValue(key);
					String svalue = ovalue == null ? null : ovalue.toString();
					if (ovalue instanceof CustomField) {
						svalue = ((CustomField)ovalue).getValue();
					}
					if (svalue == null || StringUtils.trimToNull(""+svalue) == null) {		                			
						FieldRequired fr = requiredFieldMap.get(key);
						if (fr.isRequired()) {
							FieldType ft = fr.getFieldDefinition().getFieldType();
							if (ft.equals(FieldType.PICKLIST) || ft.equals(FieldType.MULTI_PICKLIST)) {
								Picklist picklist = picklistItemService.getPicklist(fr.getFieldDefinition().getFieldName());
								if (picklist != null) {
									List<PicklistItem> items = picklist.getActivePicklistItems();
									if (items.size() > 0) {
										String defaultvalue = items.get(0).getItemName();
										if (ovalue instanceof CustomField) {
											((CustomField)ovalue).setValue(defaultvalue);
										} else {
											bw.setPropertyValue(key, defaultvalue);
										}
									}
								}
							}
						}
					}
				}
			}
		}

	}
	


    
    
}
