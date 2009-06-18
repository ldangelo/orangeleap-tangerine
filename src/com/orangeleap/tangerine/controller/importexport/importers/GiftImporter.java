package com.orangeleap.tangerine.controller.importexport.importers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindException;

import com.orangeleap.tangerine.controller.importexport.ImportRequest;
import com.orangeleap.tangerine.controller.importexport.exporters.FieldDescriptor;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.type.PageType;


public class GiftImporter extends EntityImporter {
	
    protected final Log logger = OLLogger.getLog(getClass());

    private final ConstituentService constituentService;
    private final GiftService giftservice;

	public GiftImporter(ImportRequest importRequest, ApplicationContext applicationContext) {
		super(importRequest, applicationContext);
		constituentService = (ConstituentService)applicationContext.getBean("constituentService");
		giftservice = (GiftService)applicationContext.getBean("giftService");
	}
	
	@Override
	public String getIdField() {
		return "accountNumber";
	}
	
	@Override
	protected PageType getPageType() {
	    return PageType.gift;
	}

	@Override
	protected List<FieldDescriptor> getFieldDescriptors() {

		List<FieldDescriptor> list = super.getFieldDescriptors();
		
		list.add(new FieldDescriptor("paymentSource.creditCardType", FieldDescriptor.PAYMENTTYPE, fieldDao.readFieldDefinition("gift.paymentSource.creditCardType")));
		list.add(new FieldDescriptor("paymentSource.creditCardHolderName", FieldDescriptor.PAYMENTTYPE, fieldDao.readFieldDefinition("gift.paymentSource.creditCardHolderName")));
		list.add(new FieldDescriptor("paymentSource.creditCardNumber", FieldDescriptor.PAYMENTTYPE, fieldDao.readFieldDefinition("gift.paymentSource.creditCardNumber")));
		list.add(new FieldDescriptor("paymentSource.creditCardExpiration", FieldDescriptor.PAYMENTTYPE, fieldDao.readFieldDefinition("gift.paymentSource.creditCardExpiration")));
		list.add(new FieldDescriptor("paymentSource.creditCardSecurityCode", FieldDescriptor.PAYMENTTYPE, fieldDao.readFieldDefinition("gift.paymentSource.creditCardSecurityCode")));

		list.add(new FieldDescriptor("paymentSource.achHolderName", FieldDescriptor.PAYMENTTYPE, fieldDao.readFieldDefinition("gift.paymentSource.achHolderName")));
		list.add(new FieldDescriptor("paymentSource.achRoutingNumber", FieldDescriptor.PAYMENTTYPE, fieldDao.readFieldDefinition("gift.paymentSource.achRoutingNumber")));
		list.add(new FieldDescriptor("paymentSource.achAccountNumber", FieldDescriptor.PAYMENTTYPE, fieldDao.readFieldDefinition("gift.paymentSource.achAccountNumber")));

		return list;
	}
	
	@Override
	public void importValueMap(String action, Map<String, String> values) throws ConstituentValidationException, BindException {
		
		if (!action.equals(EntityImporter.ACTION_ADD)) {
			throw new RuntimeException("Gifts can only be ADDed.");
		} 
		
		String paymentType = values.get("paymentType");
		if (!PaymentSource.CASH.equals(paymentType) 
			&& !PaymentSource.CHECK.equals(paymentType)
			&& !PaymentSource.CREDIT_CARD.equals(paymentType)
			&& !PaymentSource.ACH.equals(paymentType)
				) {
			throw new RuntimeException("Invalid Gift payment type for import: "+paymentType);
		}
		
		String id = values.get(getIdField());
		if (id == null) {
            throw new RuntimeException(getIdField() + " field is required.");
        }
	    Constituent constituent = constituentService.readConstituentByAccountNumber(id);
		if (constituent == null) {
            throw new RuntimeException(getIdField() + " " + id + " not found.");
        }
		logger.debug("Importing gift for constituent "+id+"...");
		
		Gift gift = giftservice.readGiftByIdCreateIfNull(constituent, null);
		
		values.remove(getIdField());
		mapValuesToObject(values, gift);
		
		if (
				PaymentSource.CASH.equals(paymentType) ||
				PaymentSource.CHECK.equals(paymentType)
		) {
			if (gift.getGiftStatus() == null || gift.getGiftStatus().length() == 0) {
				gift.setGiftStatus("Processed");
			}
		}
		
		DistributionLine dl = new DistributionLine();
		dl.setAmount(gift.getAmount());
		dl.setPercentage(new BigDecimal("100.00"));
		gift.getMutableDistributionLines().add(dl);
		gift.filterValidDistributionLines();
		
		
		gift.setSelectedAddress(gift.getAddress());
		gift.setSelectedPhone(gift.getPhone());
		gift.setSelectedPaymentSource(gift.getPaymentSource());
		
		
		gift.setPaymentSourceType(FormBeanType.NEW);
		gift.setAddressType(FormBeanType.NEW);
		gift.setPhoneType(FormBeanType.NEW);
		
		giftservice.maintainGift(gift);
		
	}
	
	
	
}
