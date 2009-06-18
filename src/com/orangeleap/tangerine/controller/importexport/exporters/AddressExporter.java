package com.orangeleap.tangerine.controller.importexport.exporters;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.controller.importexport.ExportRequest;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;


public class AddressExporter extends EntityExporter {
	
    protected final Log logger = OLLogger.getLog(getClass());
    
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
		List<Constituent> constituents = constituentService.readAllConstituentsByAccountRange(er.getFromAccount(), er.getToAccount());
		// Need a separate row for all active addresses on constituent not just the original 'primary' one.
		for (Constituent constituent : constituents) {
			for (Address address: constituent.getAddresses()) {
				if (address.getAddressLine1() != null && checkNcoaAndCassDates(address)) {
					Constituent aconstituent = (Constituent)constituent.createCopy();
					aconstituent.setPrimaryAddress(address);
					result.add(aconstituent);
				}
			}
		}
		return result;
	}
	
	private boolean checkNcoaAndCassDates(Address address) {
		if (er.getExportNcoaDate() == null && er.getExportCassDate() == null) return true;
		boolean result = false;
		if (er.getExportNcoaDate() != null) {
			result = checkDate(er.getExportNcoaDate(), address.getNcoaDate());
		}
		if (er.getExportCassDate() != null) {
			result = result || checkDate(er.getExportCassDate(), address.getCassDate());
		}
		return result;
	}
	
	private boolean checkDate(Date cutoff, Date value) {
		return value == null || value.before(cutoff);
	}
	
	@Override
	protected PageType getPageType() {
	    return PageType.constituent; // need constituent info on addresses export
	}
	
	@Override
	protected String mapName(String name) {
		if (name.startsWith("primaryAddress")) {
			return name.substring(name.indexOf(".")+1);
		}
		return name;
	}

	public static boolean isConstituentReadOnlyField(String name) {
		return name.equals("firstName") || name.equals("lastName") || name.equals("organizationName") || name.equals("accountNumber");
	}

	@Override
	public List<FieldDescriptor> getExportFieldDescriptors() {
		
		List<FieldDescriptor> list = super.getExportFieldDescriptors();
		Iterator<FieldDescriptor> it = list.iterator();
		while (it.hasNext()) {
			FieldDescriptor fd = it.next();
			String name = fd.getName();
			boolean exportfield = isConstituentReadOnlyField(name) || name.startsWith("primaryAddress");
			if (!exportfield) it.remove();
		}

		// Add a column for address id
		list.add(0, getFieldDescriptor("primaryAddress.id"));
		
		// Add a column for address line 3 after address line 2
		int addr2 = 0;
		for (int i = 0; i < list.size(); i++) {
			FieldDescriptor afieldDescriptor = list.get(i);
			if (afieldDescriptor.getName().equals("primaryAddress.addressLine2")) {
				addr2 = i;
			}
		}
		list.add(addr2 + 1, getFieldDescriptor("primaryAddress.addressLine3"));

		
		return list;
		
	}
	
	public static FieldDescriptor getFieldDescriptor(String name) {
		FieldDefinition fd = new FieldDefinition();
		fd.setId(name);
		fd.setEntityType(EntityType.address);
		fd.setFieldName(name);
		fd.setFieldType(FieldType.TEXT);
		FieldDescriptor fieldDescriptor = new FieldDescriptor(name, FieldDescriptor.NATIVE, fd);
		return fieldDescriptor;
	}

}
