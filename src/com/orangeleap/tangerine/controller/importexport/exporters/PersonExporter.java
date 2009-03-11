package com.orangeleap.tangerine.controller.importexport.exporters;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.type.PageType;


public class PersonExporter extends EntityExporter {
	
    protected final Log logger = LogFactory.getLog(getClass());
    
    private ConstituentService constituentService;

	public PersonExporter(String entity, ApplicationContext applicationContext) {
		super(entity, applicationContext);
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


}
