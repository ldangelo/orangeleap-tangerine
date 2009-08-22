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

package com.orangeleap.tangerine.json.controller;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.FieldHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.FieldHandlerHelper;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TangerineJsonListController {

    @Resource(name = "pageCustomizationService")
    protected PageCustomizationService pageCustomizationService;

    @Resource(name = "fieldHandlerHelper")
    protected FieldHandlerHelper fieldHandlerHelper;

    @Resource(name = "tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    public void addListFieldsToMap(HttpServletRequest request, List<SectionField> sectionFields, List entities, List<Map<String, Object>> paramMapList) {
        for (Object thisEntity : entities) {
            BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(thisEntity);

            Map<String, Object> paramMap = new HashMap<String, Object>();
            for (SectionField field : sectionFields) {
                String fieldPropertyName = field.getFieldPropertyName();
                if (beanWrapper.isReadableProperty(fieldPropertyName)) {
                    FieldHandler handler = fieldHandlerHelper.lookupFieldHandler(field.getFieldType());

                    Object displayValue = StringConstants.EMPTY;
                    if (beanWrapper.isReadableProperty(field.getFieldPropertyName())) {
                        Object fieldValue = beanWrapper.getPropertyValue(field.getFieldPropertyName());
                        if (fieldValue instanceof CustomField) {
                            fieldValue = ((CustomField) fieldValue).getValue();
                        }
                        if (field.getFieldPropertyName().equals(StringConstants.ID)) {
                            displayValue = fieldValue;
                        }
                        else {
                            displayValue = handler.resolveDisplayValue(request, PropertyAccessorFactory.forBeanPropertyAccess(thisEntity), field, fieldValue);
                        }
                    }
                    paramMap.put(TangerineForm.escapeFieldName(fieldPropertyName), displayValue);
                }
            }
            paramMapList.add(paramMap);
        }
    }

    public List<SectionField> findSectionFields(String listPageName) {
        List<SectionDefinition> sectionDefinitions = pageCustomizationService.readSectionDefinitionsByPageTypeRoles(PageType.valueOf(listPageName), tangerineUserHelper.lookupUserRoles());
        assert sectionDefinitions != null && sectionDefinitions.size() == 1;

        SectionDefinition sectionDef = sectionDefinitions.get(0);
        List<SectionField> sectionFields = pageCustomizationService.readSectionFieldsBySection(sectionDef);
        assert sectionFields != null && !sectionFields.isEmpty();
        return sectionFields;        
    }

}
