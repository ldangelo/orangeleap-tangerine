package com.orangeleap.tangerine.controller.importexport.exporters;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.controller.importexport.ExportRequest;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;


public class GiftExporter extends EntityExporter {
	
    protected final Log logger = LogFactory.getLog(getClass());
    
    private GiftService giftservice;

	public GiftExporter(ExportRequest er, ApplicationContext applicationContext) {
		super(er, applicationContext);
		giftservice = (GiftService)applicationContext.getBean("giftService");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected List readAll() {
		return giftservice.readAllGiftsByDateRange(er.getFromDate(), er.getToDate());
	}

	@Override
	protected PageType getPageType() {
	    return PageType.gift;
	}

	@Override
	protected boolean exclude(String name, FieldDefinition fd) {
		return super.exclude(name, fd) 
		|| fd.getId().startsWith("selected") 
		|| fd.getCustomFieldName().equals("reference")
		|| fd.getCustomFieldName().equals("other_reference")
		|| fd.getId().toLowerCase().contains("creditCardNumber".toLowerCase())
		|| fd.getId().toLowerCase().contains("creditCardSecurityCode".toLowerCase())
		|| fd.getId().toLowerCase().contains("achRoutingNumber".toLowerCase())
		|| fd.getId().toLowerCase().contains("achAccountNumber".toLowerCase())
		;
	}
	
	@Override
	public List<FieldDescriptor> getExportFieldDescriptors() {
		
		List<FieldDescriptor> list = super.getExportFieldDescriptors();

		// Add a column for person accountnumber
		FieldDefinition fd = new FieldDefinition();
		fd.setId("person.accountNumber");
		fd.setEntityType(EntityType.gift);
		fd.setFieldName("person.accountNumber");
		fd.setFieldType(FieldType.TEXT);
		
		FieldDescriptor fieldDescriptor = new FieldDescriptor("person.accountNumber", FieldDescriptor.NATIVE, fd);
		list.add(0, fieldDescriptor);
		
		return list;
		
	}

}
