/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.service.impl;

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
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public abstract class AbstractTangerineService {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    @Resource(name = "siteService")
    protected SiteService siteService;

    @Resource(name = "picklistItemService")
    protected PicklistItemService picklistItemService;


    protected String getSiteName() {
        return tangerineUserHelper.lookupUserSiteName();
    }

    protected void setPicklistDefaultsForRequiredFields(AbstractEntity entity, PageType pageType, List<String> userRoles) {

        if (entity instanceof AbstractCommunicatorEntity) {
            AbstractCommunicatorEntity ace = (AbstractCommunicatorEntity) entity;
            if (ace.getPrimaryAddress() != null && ace.getPrimaryAddress().getAddressLine1() != null && ace.getPrimaryAddress().getCustomFieldValue("addressType") == null) {
                ace.getPrimaryAddress().setCustomFieldValue("addressType", "unknown");
            }
            if (ace.getPrimaryEmail() != null && ace.getPrimaryEmail().getEmailAddress() != null && ace.getPrimaryEmail().getCustomFieldValue("emailType") == null) {
                ace.getPrimaryEmail().setCustomFieldValue("emailType", "unknown");
            }
            if (ace.getPrimaryPhone() != null && ace.getPrimaryPhone().getNumber() != null && ace.getPrimaryPhone().getCustomFieldValue("phoneType") == null) {
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
                        svalue = ((CustomField) ovalue).getValue();
                    }
                    if (svalue == null || StringUtils.trimToNull("" + svalue) == null) {
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
                                            ((CustomField) ovalue).setValue(defaultvalue);
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
