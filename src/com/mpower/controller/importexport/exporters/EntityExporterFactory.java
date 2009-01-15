package com.mpower.controller.importexport.exporters;

import org.springframework.context.ApplicationContext;


public class EntityExporterFactory {
	
	public EntityExporter getEntityExporter(String entity, ApplicationContext applicationContext) {
		
		if ("person".equals(entity)) return new PersonExporter(entity, applicationContext);
		if ("gift".equals(entity)) return new GiftExporter(entity, applicationContext);
		
		return null;
	}

}
