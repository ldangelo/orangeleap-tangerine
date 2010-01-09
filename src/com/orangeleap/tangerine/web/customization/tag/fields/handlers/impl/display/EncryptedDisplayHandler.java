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

package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.util.AES;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

public class EncryptedDisplayHandler extends ReadOnlyTextHandler {
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

	public EncryptedDisplayHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

    @Override
    protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
                             SectionDefinition sectionDefinition, List<SectionField> sectionFields, SectionField currentField,
                             TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {
        fieldValue = decryptAndMask(form, formFieldName);
        super.doHandler(request, response, pageContext, sectionDefinition, sectionFields, currentField, form, formFieldName, fieldValue, sb);
    }

    protected Object decryptAndMask(Object fieldValue, String formFieldName) {
        if (fieldValue != null) {
            try {
                fieldValue = AES.decryptAndMask(fieldValue.toString());
            }
            catch (Exception ex) {
                if (logger.isWarnEnabled()) {
                    logger.warn("decryptAndMask: could not decrypt fieldName = " + formFieldName + " value = " + AES.mask(fieldValue.toString()));
                }
            }
        }
        return fieldValue;
    }

    @Override
    public Object resolveDisplayValue(HttpServletRequest request, BeanWrapper beanWrapper, SectionField currentField, Object fieldValue) {
        return decryptAndMask(fieldValue, currentField.getFieldDefinition().getFieldName());
    }
}
