package com.orangeleap.tangerine.controller.importexport.importers;

import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.controller.importexport.ImportRequest;


public class EntityImporterFactory {
	
	public EntityImporter getEntityImporter(ImportRequest importRequest, ApplicationContext applicationContext) {
		
		if ("person".equals(importRequest.getEntity())) return new PersonImporter(importRequest, applicationContext);
		if ("gift".equals(importRequest.getEntity())) return new GiftImporter(importRequest, applicationContext);
		if ("address".equals(importRequest.getEntity())) return new AddressImporter(importRequest, applicationContext);
		
		return null;
	}

}
