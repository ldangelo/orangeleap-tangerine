package com.mpower.controller.importexport.importers;

import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.mpower.service.SiteService;
import com.mpower.service.exception.PersonValidationException;

public abstract class EntityImporter {
	
	public final static String ACTION_ADD = "add";
	public final static String ACTION_CHANGE = "change";
	public final static String ACTION_DELETE = "delete";

	protected String entity;
	protected ApplicationContext applicationContext;
	protected SiteService siteservice;
	
	protected EntityImporter(String entity, ApplicationContext applicationContext) {
		this.entity = entity;
		this.applicationContext = applicationContext;
		siteservice = (SiteService)applicationContext.getBean("siteService");
	}	
	
	public abstract void importValueMap(String action, Map<String, String> m) throws PersonValidationException;
	public abstract String getIdField();
	
}
