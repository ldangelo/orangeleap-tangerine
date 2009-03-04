package com.mpower.controller.importexport.importers;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.model.PaymentSource;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.service.ConstituentService;
import com.mpower.service.GiftService;
import com.mpower.service.exception.PersonValidationException;
import com.mpower.type.PageType;


public class GiftImporter extends EntityImporter {
	
    protected final Log logger = LogFactory.getLog(getClass());

    private final ConstituentService constituentService;
    private final GiftService giftservice;

	public GiftImporter(String entity, ApplicationContext applicationContext) {
		super(entity, applicationContext);
		constituentService = (ConstituentService)applicationContext.getBean("constituentService");
		giftservice = (GiftService)applicationContext.getBean("giftService");
	}
	
	@Override
	public String getIdField() {
		return "person.accountNumber";
	}
	
	@Override
	protected PageType getPageType() {
	    return PageType.gift;
	}



	@Override
	public void importValueMap(String action, Map<String, String> values) throws PersonValidationException {
		
		if (!action.equals(EntityImporter.ACTION_ADD)) {
			throw new RuntimeException("Gifts can only be ADDed.");
		} 
		
		String paymentType = values.get("paymentType");
		if (!PaymentSource.CASH.equals(paymentType) && !PaymentSource.CHECK.equals(paymentType)) {
			throw new RuntimeException("Gift payment type must be Cash or Check for import.");
		}
		
		String id = values.get(getIdField());
		if (id == null) {
            throw new RuntimeException(getIdField() + " field is required.");
        }
	    Person constituent = constituentService.readConstituentById(new Long(id));
		if (constituent == null) {
            throw new RuntimeException(getIdField() + " " + id + " not found.");
        }
		logger.debug("Importing gift for constituent "+id+"...");
		
		Gift gift = giftservice.readGiftByIdCreateIfNull(null, null, constituent);
		
		mapValuesToObject(values, gift);
		
		giftservice.maintainGift(gift);
		
	}
	
	
	
}
