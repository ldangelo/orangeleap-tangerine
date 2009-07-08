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

package com.orangeleap.tangerine.controller.importexport.exporters;

import com.orangeleap.tangerine.controller.importexport.ExportRequest;
import com.orangeleap.tangerine.controller.importexport.fielddefs.FieldDefUtil;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class EntityExporter {

    protected final Log logger = OLLogger.getLog(getClass());

    @SuppressWarnings("unchecked")
    protected abstract List readAll();

    protected abstract PageType getPageType();

    protected ExportRequest er;
    protected ApplicationContext applicationContext;
    protected SiteService siteservice;
    protected TangerineUserHelper tangerineUserHelper;

    protected EntityExporter(ExportRequest er, ApplicationContext applicationContext) {
        this.er = er;
        this.applicationContext = applicationContext;
        siteservice = (SiteService) applicationContext.getBean("siteService");
        tangerineUserHelper = (TangerineUserHelper) applicationContext.getBean("tangerineUserHelper");
    }

    @SuppressWarnings("unchecked")
    public List<List<String>> exportAll() {

        List<List<String>> result = new ArrayList<List<String>>();

        List<FieldDescriptor> fields = getExportFieldDescriptors();
        List<String> header = new ArrayList<String>();
        header.add("action");
        for (int i = 0; i < fields.size(); i++) {
            FieldDescriptor fd = fields.get(i);
            header.add(mapName(fd.getExportFieldNameForInternalName()));
        }
        result.add(header);

        List list = readAll();
        for (Object o : list) {
            List<String> line = new ArrayList<String>();
            result.add(line);
            line.add("change");
            for (int i = 0; i < fields.size(); i++) {
                FieldDescriptor fd = fields.get(i);
                String value = getFieldValue(o, fd);
                //logger.debug("name="+fd.getName()+", value="+value);
                line.add(value);
            }
        }

        removeInvalidFields(fields, result);

        return result;

    }

    protected String mapName(String name) {
        return name;
    }

    private void removeInvalidFields(List<FieldDescriptor> fields, List<List<String>> list) {
        for (int i = fields.size() - 1; i >= 0; i--) {
            FieldDescriptor fd = fields.get(i);
            if (fd.isDisabled()) {
                for (List<String> line : list) {
                    line.remove(i + 1);
                }
            }
        }
    }

    public List<FieldDescriptor> getExportFieldDescriptors() {

        FieldDefUtil.Exclusion exclusion = new FieldDefUtil.Exclusion() {
            public boolean excludeField(String name, FieldDefinition fd) {
                return exclude(name, fd);
            }
        };

        return FieldDefUtil.getFieldDescriptors(exclusion, getPageType(), siteservice, tangerineUserHelper);

    }

    private FieldDefUtil.Exclusion defaultExclusion = FieldDefUtil.getDefaultExclusions();

    protected boolean exclude(String name, FieldDefinition fd) {
        return defaultExclusion.excludeField(name, fd);
    }

    @SuppressWarnings("unchecked")
    private String getFieldValue(Object o, FieldDescriptor fd) {

        FieldType fieldType = fd.getFieldDefinition().getFieldType();

        String result = "";
        try {
            String name = fd.getName();
            if (fd.getType() == FieldDescriptor.CUSTOM) {
                Method m = o.getClass().getMethod("getCustomFieldValue", new Class[]{String.class});
                result = (String) m.invoke(o, name);
            } else if (fd.isMap()) {
                Method m = o.getClass().getMethod("get" + fd.getMapType() + "Map");
                Map map = (Map) m.invoke(o);
                Object so = map.get(fd.getKey());
                result = getProperty(so, fd.getSubField(), fieldType);
            } else if (fd.isDependentField()) {
                String depobject = fd.getDependentObject();
                Method m = o.getClass().getMethod("get" + FieldDescriptor.toInitialUpperCase(depobject));
                Object so = m.invoke(o);
                if (so == null) {
                    return "";
                }
                result = getProperty(so, fd.getDependentField(), fieldType);
            } else {
                result = getProperty(o, name, fieldType);
            }
        } catch (Exception e) {
            // Some screen fields are not entity properties
            fd.setDisabled(true);
        }
        if (result == null) {
            result = "";
        }

        return result;
    }

    private String getProperty(Object o, String field, FieldType fieldType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (fieldType == FieldType.DATE || fieldType == FieldType.DATE_DISPLAY) {
            Object result = getObject(o, field);
            return formatDate(result, "MM/dd/yyyy");
        } else if (fieldType == FieldType.CC_EXPIRATION || fieldType == FieldType.CC_EXPIRATION_DISPLAY) {
            Object result = getObject(o, field);
            return formatDate(result, "MM/yyyy");
        } else {
            return BeanUtils.getProperty(o, field);
        }
    }

    private Object getObject(Object o, String field) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Method m = o.getClass().getMethod("get" + StringUtils.capitalize(field), new Class[0]);
        return m.invoke(o, new Object[0]);
    }

    private String formatDate(Object o, String format) {
        if (o instanceof java.util.Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format((java.util.Date) o);
        } else {
            if (o == null) return "";
            else return o.toString();
        }
    }


}
