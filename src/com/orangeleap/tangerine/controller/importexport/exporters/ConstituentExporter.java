package com.orangeleap.tangerine.controller.importexport.exporters;

import java.util.List;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.controller.importexport.ExportRequest;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;


public class ConstituentExporter extends EntityExporter {
	
    protected final Log logger = OLLogger.getLog(getClass());
    
    private ConstituentService constituentService;

	public ConstituentExporter(ExportRequest er, ApplicationContext applicationContext) {
		super(er, applicationContext);
		constituentService = (ConstituentService)applicationContext.getBean("constituentService");
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
