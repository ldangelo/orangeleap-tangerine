package com.orangeleap.tangerine.controller.importexport.exporters;

import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.controller.importexport.ExportRequest;


public class EntityExporterFactory {
	
	public EntityExporter getEntityExporter(ExportRequest er, ApplicationContext applicationContext) {
		
		if ("constituent".equals(er.getEntity())) return new ConstituentExporter(er, applicationContext);
		if ("gift".equals(er.getEntity())) return new GiftExporter(er, applicationContext);
		if ("address".equals(er.getEntity())) return new AddressExporter(er, applicationContext);
		
		return null;
	}

}
