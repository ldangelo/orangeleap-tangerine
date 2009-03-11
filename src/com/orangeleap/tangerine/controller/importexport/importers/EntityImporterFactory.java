package com.orangeleap.tangerine.controller.importexport.importers;

import org.springframework.context.ApplicationContext;


public class EntityImporterFactory {
	
	public EntityImporter getEntityImporter(String entity, ApplicationContext applicationContext) {
		
		if ("person".equals(entity)) return new PersonImporter(entity, applicationContext);
		if ("gift".equals(entity)) return new GiftImporter(entity, applicationContext);
		
		return null;
	}

}
