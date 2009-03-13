package com.orangeleap.tangerine.controller.importexport.exporters;

import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.controller.importexport.ExportRequest;


public class EntityExporterFactory {
	
	public EntityExporter getEntityExporter(ExportRequest er, ApplicationContext applicationContext) {
		
		if ("person".equals(er.getEntity())) return new PersonExporter(er, applicationContext);
		if ("gift".equals(er.getEntity())) return new GiftExporter(er, applicationContext);
		
		return null;
	}

}
