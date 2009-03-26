package com.orangeleap.tangerine.controller.importexport.exporters;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.controller.importexport.ExportRequest;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;


public class PersonExporter extends EntityExporter {
	
    protected final Log logger = LogFactory.getLog(getClass());
    
    private ConstituentService constituentService;

	public PersonExporter(ExportRequest er, ApplicationContext applicationContext) {
		super(er, applicationContext);
		constituentService = (ConstituentService)applicationContext.getBean("constituentService");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected List readAll() {
		return constituentService.readAllConstituentsBySite();
	}

	@Override
	protected PageType getPageType() {
	    return PageType.person;
	}

	@Override
	public List<FieldDescriptor> getExportFieldDescriptors() {
		
		List<FieldDescriptor> list = super.getExportFieldDescriptors();

		// Add a column for person id
		FieldDefinition fd = new FieldDefinition();
		fd.setId("person.id");
		fd.setEntityType(EntityType.person);
		fd.setFieldName("accountNumber");
		fd.setFieldType(FieldType.TEXT);
		
		FieldDescriptor fieldDescriptor = new FieldDescriptor("accountNumber", FieldDescriptor.NATIVE, fd);
		list.add(0, fieldDescriptor);
		
		return list;
		
	}

}
