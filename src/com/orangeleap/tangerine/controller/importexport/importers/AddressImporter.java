package com.orangeleap.tangerine.controller.importexport.importers;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.controller.importexport.exporters.FieldDescriptor;
import com.orangeleap.tangerine.controller.importexport.fielddefs.FieldDefUtil;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.AddressService;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.type.PageType;


public class AddressImporter extends EntityImporter {
	
    protected final Log logger = LogFactory.getLog(getClass());

    private AddressService addressService;
    private ConstituentService constituentService;

	public AddressImporter(String entity, ApplicationContext applicationContext) {
		super(entity, applicationContext);
		addressService = (AddressService)applicationContext.getBean("addressService");
		constituentService = (ConstituentService)applicationContext.getBean("constituentService");
	}
	
	@Override
	public String getIdField() {
		return "addressId";
	}
	
	@Override
	protected PageType getPageType() {
	    return PageType.address;
	}

	@Override
	protected List<FieldDescriptor> getFieldDescriptors() {
		
		FieldDefUtil.Exclusion exclusion = new FieldDefUtil.Exclusion() {
			public boolean excludeField(String name, FieldDefinition fd) {
				return exclude(name, fd);
			}
		};
		
		return FieldDefUtil.getFieldDescriptors(exclusion, getPageType(), siteservice, tangerineUserHelper);
	}

	private static FieldDefUtil.Exclusion defaultExclusion = FieldDefUtil.getDefaultExclusions();
	protected boolean exclude(String name, FieldDefinition fd) {
		return defaultExclusion.excludeField(name, fd);
	}

	@Override
	public void importValueMap(String action, Map<String, String> values) throws ConstituentValidationException {
		
		Address address;
		
		String id = values.get(getIdField());
		if (action.equals(EntityImporter.ACTION_ADD)) {
			address = new Address();
			logger.debug("Adding new entity...");
		} else {
			if (id == null) {
                throw new RuntimeException(getIdField() + " field is required for CHANGE or DELETE action.");
            }
			Long lid;
			try {
				lid = new Long(id);
			} catch (Exception e) {
				throw new RuntimeException("Invalid id value "+id);
			}
		    address = addressService.readById(lid);
			if (address == null || constituentService.readConstituentById(address.getPersonId()) == null) {
                throw new RuntimeException(getIdField() + " " + id + " not found.");
			}
			logger.debug("Importing constituent "+id+"...");
		}
		

		if (action.equals(EntityImporter.ACTION_DELETE)) {
			// TODO How to delete or set to inactive?
			address.setInactive(true);
		} else {
			mapValuesToObject(values, address);
		}
		
		addressService.save(address);
		
	}
	
	
	
}
