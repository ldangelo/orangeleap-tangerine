package com.orangeleap.tangerine.controller.importexport.exporters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.controller.importexport.ExportRequest;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;


public class AddressExporter extends EntityExporter {
	
    protected final Log logger = LogFactory.getLog(getClass());
    
    private ConstituentService constituentService;

	public AddressExporter(ExportRequest er, ApplicationContext applicationContext) {
		super(er, applicationContext);
		constituentService = (ConstituentService)applicationContext.getBean("constituentService");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected List readAll() {
		// Export as a constituent to get the extra readonly info fields and import as an address to avoid updating the constituent.
		List result = new ArrayList();
		List<Person> constituents = constituentService.readAllConstituentsByIdRange(er.getFromId(), er.getToId());
		// Need a separate row for all active addresses on person not just the original 'primary' one.
		for (Person constituent : constituents) {
			for (Address address: constituent.getAddresses()) {
				Person aconstituent = (Person)constituent.createCopy();
				aconstituent.setPrimaryAddress(address);
				result.add(aconstituent);
			}
		}
		return result;
	}


	@Override
	protected PageType getPageType() {
	    return PageType.person; // need person info on addresses export
	}

	@Override
	public List<FieldDescriptor> getExportFieldDescriptors() {
		
		List<FieldDescriptor> list = super.getExportFieldDescriptors();

		// Add a column for person id
		FieldDefinition fd = new FieldDefinition();
		fd.setId("person.id");
		fd.setEntityType(EntityType.person);
		fd.setFieldName("id");
		fd.setFieldType(FieldType.TEXT);
		
		FieldDescriptor fieldDescriptor = new FieldDescriptor("id", FieldDescriptor.NATIVE, fd);
		list.add(0, fieldDescriptor);

		// Add a column for address id
		fd = new FieldDefinition();
		fd.setId("address.id");
		fd.setEntityType(EntityType.address);
		fd.setFieldName("addressId");
		fd.setFieldType(FieldType.TEXT);
		
		fieldDescriptor = new FieldDescriptor("addressId", FieldDescriptor.NATIVE, fd);
		list.add(0, fieldDescriptor);

		
		return list;
		
	}

}
