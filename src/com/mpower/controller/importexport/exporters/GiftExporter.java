package com.mpower.controller.importexport.exporters;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.customization.FieldDefinition;
import com.mpower.service.GiftService;
import com.mpower.service.impl.SessionServiceImpl;
import com.mpower.type.EntityType;
import com.mpower.type.FieldType;
import com.mpower.type.PageType;


public class GiftExporter extends EntityExporter {
	
    protected final Log logger = LogFactory.getLog(getClass());
    
    private GiftService giftservice;

	public GiftExporter(String entity, ApplicationContext applicationContext) {
		super(entity, applicationContext);
		giftservice = (GiftService)applicationContext.getBean("giftService");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected List readAll() {
		return giftservice.readAllGiftsBySiteName(SessionServiceImpl.lookupUserSiteName());
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

		// Add a column for person id
		FieldDefinition fd = new FieldDefinition();
		fd.setId("gift.personId");
		fd.setEntityType(EntityType.gift);
		fd.setFieldName("person.accountNumber");
		fd.setFieldType(FieldType.TEXT);
		
		FieldDescriptor fieldDescriptor = new FieldDescriptor("person.accountNumber", FieldDescriptor.NATIVE, fd);
		list.add(0, fieldDescriptor);
		
		return list;
		
	}

}
