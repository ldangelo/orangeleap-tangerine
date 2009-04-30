package com.orangeleap.tangerine.controller.importexport.importers;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.controller.importexport.ImportRequest;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.type.PageType;


public class GiftImporter extends EntityImporter {
	
    protected final Log logger = LogFactory.getLog(getClass());

    private final ConstituentService constituentService;
    private final GiftService giftservice;

	public GiftImporter(ImportRequest importRequest, ApplicationContext applicationContext) {
		super(importRequest, applicationContext);
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
	public void importValueMap(String action, Map<String, String> values) throws ConstituentValidationException {
		
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
	    Person constituent = constituentService.readConstituentByAccountNumber(id);
		if (constituent == null) {
            throw new RuntimeException(getIdField() + " " + id + " not found.");
        }
		logger.debug("Importing gift for constituent "+id+"...");
		
		Gift gift = giftservice.readGiftByIdCreateIfNull(constituent, null, null, null);
		
		mapValuesToObject(values, gift);
		
		if (gift.getGiftStatus() == null || gift.getGiftStatus().length() == 0) gift.setGiftStatus("Processed");
		
		DistributionLine dl = new DistributionLine();
		dl.setAmount(gift.getAmount());
		dl.setPercentage(new BigDecimal(100.00));
		gift.getMutableDistributionLines().add(dl);
		gift.filterValidDistributionLines();
		
		giftservice.maintainGift(gift);
		
	}
	
	
	
}
