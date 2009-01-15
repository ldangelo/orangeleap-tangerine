package com.mpower.controller.importexport.exporters;

import org.springframework.context.ApplicationContext;


public class EntityExporterFactory {
	
	public EntityExporter getEntityExporter(String entity, ApplicationContext applicationContext) {
		
		if ("person".equals(entity)) return new PersonExporter(entity, applicationContext);
		
		return null;
	}

}
