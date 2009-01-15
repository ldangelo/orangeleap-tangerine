package com.mpower.controller.importexport.exporters;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.mpower.service.GiftService;
import com.mpower.type.PageType;


public class GiftExporter extends EntityExporter {
	
    protected final Log logger = LogFactory.getLog(getClass());
    
    private GiftService giftservice;

	public GiftExporter(String entity, ApplicationContext applicationContext) {
		super(entity, applicationContext);
		giftservice = (GiftService)applicationContext.getBean("giftService");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected List readAll() {
		return giftservice.readAllGifts();
	}

	@Override
	protected PageType getPageType() {
	    return PageType.gift;
	}


}
