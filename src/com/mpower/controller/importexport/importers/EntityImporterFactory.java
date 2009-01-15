package com.mpower.controller.importexport.importers;

import org.springframework.context.ApplicationContext;


public class EntityImporterFactory {
	
	public EntityImporter getEntityImporter(String entity, ApplicationContext applicationContext) {
		
		if ("person".equals(entity)) return new PersonImporter(applicationContext);
		
		return null;
	}

}
