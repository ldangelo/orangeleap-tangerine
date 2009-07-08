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
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;

import java.util.List;


public class ConstituentExporter extends EntityExporter {

    protected final Log logger = OLLogger.getLog(getClass());

    private ConstituentService constituentService;

    public ConstituentExporter(ExportRequest er, ApplicationContext applicationContext) {
        super(er, applicationContext);
        constituentService = (ConstituentService) applicationContext.getBean("constituentService");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List readAll() {
        return constituentService.readAllConstituentsByAccountRange(er.getFromAccount(), er.getToAccount());
    }

    @Override
    protected PageType getPageType() {
        return PageType.constituent;
    }

    @Override
    protected boolean exclude(String name, FieldDefinition fd) {
        return super.exclude(name, fd)
                || fd.getFieldType() == FieldType.QUERY_LOOKUP || fd.getFieldType() == FieldType.MULTI_QUERY_LOOKUP;
    }

    @Override
    public List<FieldDescriptor> getExportFieldDescriptors() {

        List<FieldDescriptor> list = super.getExportFieldDescriptors();

        // Add a column for constituent id
        FieldDefinition fd = new FieldDefinition();
        fd.setId("constituent.id");
        fd.setEntityType(EntityType.constituent);
        fd.setFieldName("id");
        fd.setFieldType(FieldType.TEXT);

        FieldDescriptor fieldDescriptor = new FieldDescriptor("id", FieldDescriptor.NATIVE, fd);
        list.add(0, fieldDescriptor);

        return list;

    }

}
