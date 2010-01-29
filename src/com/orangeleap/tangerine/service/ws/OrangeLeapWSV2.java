package com.orangeleap.tangerine.service.ws;


import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.providers.ldap.LdapAuthenticationProvider;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.service.AddressService;
import com.orangeleap.tangerine.service.CommunicationHistoryService;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.EmailService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PaymentSourceService;
import com.orangeleap.tangerine.service.PhoneService;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;
import com.orangeleap.tangerine.service.ws.exception.*;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.ws.schema.v2.AddCommunicationHistoryRequest;
import com.orangeleap.tangerine.ws.schema.v2.AddPickListItemByNameRequest;
import com.orangeleap.tangerine.ws.schema.v2.AddPickListItemByNameResponse;
import com.orangeleap.tangerine.ws.schema.v2.AddPickListItemResponse;
import com.orangeleap.tangerine.ws.schema.v2.BulkAddCommunicationHistoryRequest;
import com.orangeleap.tangerine.ws.schema.v2.CommunicationHistory;
import com.orangeleap.tangerine.ws.schema.v2.Constituent;
import com.orangeleap.tangerine.ws.schema.v2.CreateDefaultConstituentRequest;
import com.orangeleap.tangerine.ws.schema.v2.CreateDefaultConstituentResponse;
import com.orangeleap.tangerine.ws.schema.v2.FindConstituentsRequest;
import com.orangeleap.tangerine.ws.schema.v2.FindConstituentsResponse;
import com.orangeleap.tangerine.ws.schema.v2.GetCommunicationHistoryRequest;
import com.orangeleap.tangerine.ws.schema.v2.GetCommunicationHistoryResponse;
import com.orangeleap.tangerine.ws.schema.v2.GetConstituentByIdRequest;
import com.orangeleap.tangerine.ws.schema.v2.GetConstituentByIdResponse;
import com.orangeleap.tangerine.ws.schema.v2.GetConstituentGiftRequest;
import com.orangeleap.tangerine.ws.schema.v2.GetConstituentGiftResponse;
import com.orangeleap.tangerine.ws.schema.v2.GetConstituentPledgeRequest;
import com.orangeleap.tangerine.ws.schema.v2.GetConstituentPledgeResponse;
import com.orangeleap.tangerine.ws.schema.v2.GetConstituentRecurringGiftRequest;
import com.orangeleap.tangerine.ws.schema.v2.GetConstituentRecurringGiftResponse;
import com.orangeleap.tangerine.ws.schema.v2.GetPaymentSourcesByConstituentIdRequest;
import com.orangeleap.tangerine.ws.schema.v2.GetPaymentSourcesByConstituentIdResponse;
import com.orangeleap.tangerine.ws.schema.v2.GetPickListByNameRequest;
import com.orangeleap.tangerine.ws.schema.v2.GetPickListByNameResponse;
import com.orangeleap.tangerine.ws.schema.v2.GetPickListsRequest;
import com.orangeleap.tangerine.ws.schema.v2.GetPickListsResponse;
import com.orangeleap.tangerine.ws.schema.v2.Gift;
import com.orangeleap.tangerine.ws.schema.v2.Pledge;
import com.orangeleap.tangerine.ws.schema.v2.RecurringGift;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdateConstituentRequest;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdateConstituentResponse;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdateGiftRequest;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdateGiftResponse;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdatePledgeRequest;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdatePledgeResponse;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdateRecurringGiftRequest;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdateRecurringGiftResponse;
import com.orangeleap.tangerine.ws.schema.v2.SearchConstituentsRequest;
import com.orangeleap.tangerine.ws.schema.v2.SearchConstituentsResponse;
import com.orangeleap.tangerine.ws.util.v2.ObjectConverter;
import com.orangeleap.tangerine.ws.validation.SoapValidationManager;
import com.orangeleap.theguru.client.GetSegmentationByNameRequest;
import com.orangeleap.theguru.client.GetSegmentationByNameResponse;
import com.orangeleap.theguru.client.Segmentation;
import com.orangeleap.theguru.client.Theguru;
import com.orangeleap.theguru.client.WSClient;

@Endpoint
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class OrangeLeapWSV2 {
	private static final Log logger = LogFactory.getLog(OrangeLeapWSV2.class);

	ApplicationContext applicationContext;

	@Autowired
	private GiftService giftService;

	@Autowired
	private PledgeService pledgeService;

	@Autowired
	private ConstituentService cs;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private PhoneService phoneService;

	@Autowired
	private PicklistItemService picklistItemService;

	@Autowired
	private RecurringGiftService recurringGiftService;
	
	@Resource(name = "communicationHistoryService")
	private CommunicationHistoryService communicationHistory;

	@Autowired
	private SoapValidationManager validationManager;
	
	@Autowired
	private PaymentSourceService paymentSourceService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private TangerineUserHelper tangerineUserHelper;
	
	/**
	 * Creates a new <code>OrangeLeapWS</code> instance.
	 */
	public OrangeLeapWSV2() {
		cs = null;
	}

	/**
	 * Creates a new <code>OrangeLeapWS</code> instance.
	 * 
	 * @param service
	 *            a <code>ConstituentService</code> value
	 */
	public OrangeLeapWSV2(ConstituentService service, GiftService gs,
			CommunicationHistoryService chs, PicklistItemService plis) {
		cs = service;
		giftService = gs;
		communicationHistory = chs;
		picklistItemService = plis;
	}

	/**
	 * Describe <code>getCs</code> method here.
	 * 
	 * @return a
	 *         <code>com.orangeleap.tangerine.service.ConstituentService</code>
	 *         value
	 */
	public com.orangeleap.tangerine.service.ConstituentService getCs() {
		return cs;
	}

	/**
	 * Describe <code>setCs</code> method here.
	 * 
	 * @param cs
	 *            a
	 *            <code>com.orangeleap.tangerine.service.ConstituentService</code>
	 *            value
	 */
	public void setCs(com.orangeleap.tangerine.service.ConstituentService cs) {
		this.cs = cs;
	}

	@PayloadRoot(localPart = "GetConstituentByIdRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public GetConstituentByIdResponse getConstituentById(
			GetConstituentByIdRequest request) {
		GetConstituentByIdResponse response = new GetConstituentByIdResponse();
		ObjectConverter converter = new ObjectConverter();
		com.orangeleap.tangerine.domain.Constituent co = cs
				.readConstituentById(request.getId());
		Constituent wsco = new Constituent();
		converter.ConvertToJAXB(co, wsco);
		response.setConstituent(wsco);

		return response;
	}

	@PayloadRoot(localPart = "CreateDefaultConstituentRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	/**
	 * Describe <code>createDefaultConstituent</code> method here.
	 *
	 * @param cdcr a <code>CreateDefaultConstituentRequest</code> value
	 * @return a <code>CreateDefaultConstituentResponse</code> value
	 */
	public CreateDefaultConstituentResponse createDefaultConstituent(
			CreateDefaultConstituentRequest cdcr) throws InvalidRequestException {
		CreateDefaultConstituentResponse cr = new CreateDefaultConstituentResponse();
		
		validationManager.validate(cdcr);
		
		com.orangeleap.tangerine.domain.Constituent p = cs
				.createDefaultConstituent();
		com.orangeleap.tangerine.ws.schema.v2.Constituent c = new com.orangeleap.tangerine.ws.schema.v2.Constituent();

		cr.getConstituent().add(c);

		return cr;
	}

	@PayloadRoot(localPart = "SaveOrUpdateConstituentRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public SaveOrUpdateConstituentResponse maintainConstituent(
			SaveOrUpdateConstituentRequest p)
			throws BindException, InvalidRequestException, ConstituentValidationException {
		
		validationManager.validate(p);
		
		
		com.orangeleap.tangerine.domain.Constituent c = null;
		//
		// if the request has a constituent id then get the constituent
		// otherwise create a constituent
		if (p.getConstituent().getId() == null || p.getConstituent().getId() == 0) {
			c = cs.createDefaultConstituent();
		} else {
			c = cs.readConstituentById(p.getConstituent().getId());
			
			//
			// we did not find a constituent so throw an error
			if (c == null) {
				throw new ConstituentValidationException("Could not find constituent with id = " + p.getConstituent().getId().toString());
			}
		}
		
		ObjectConverter converter = new ObjectConverter();

		converter.ConvertFromJAXB(p.getConstituent(), c,siteService.readSite(tangerineUserHelper.lookupUserSiteName()));

		cs.maintainConstituent(c);

		//
		// if we have e-mail's then we should save them as well
		Iterator<com.orangeleap.tangerine.domain.communication.Email> emailIt = c.getEmails().iterator();
		while(emailIt.hasNext()) {
			com.orangeleap.tangerine.domain.communication.Email email = emailIt.next();
			email.setConstituentId(c.getConstituentId());
			emailService.save(email);
		}
		
		//
		// if we have addresses then we should save them as well
		Iterator<com.orangeleap.tangerine.domain.communication.Address> addressIt = c.getAddresses().iterator();
		while (addressIt.hasNext()) {
			com.orangeleap.tangerine.domain.communication.Address address = addressIt.next();
			address.setConstituentId(c.getConstituentId());
			addressService.save(address);
		}
		
		//
		// if we have phoneNumbers then we should save them as well
		Iterator<com.orangeleap.tangerine.domain.communication.Phone> phoneIt = c.getPhones().iterator();
		while (phoneIt.hasNext()) {
			com.orangeleap.tangerine.domain.communication.Phone phone = phoneIt.next();
			phone.setConstituentId(c.getConstituentId());
			phoneService.save(phone);
		}
		
		Constituent responseConstituent = new Constituent();
		SaveOrUpdateConstituentResponse response = new SaveOrUpdateConstituentResponse();
		converter.ConvertToJAXB(c, responseConstituent);
		responseConstituent.setAccountNumber(c.getId());
		response.setConstituent(responseConstituent);
		return response;
	}

	@PayloadRoot(localPart = "FindConstituentsRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public FindConstituentsResponse findConstituent(
			FindConstituentsRequest request) throws InvalidRequestException {
		FindConstituentsResponse cr = new FindConstituentsResponse();
		ObjectConverter converter = new ObjectConverter();

		validationManager.validate(request);
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (request.getFirstName() != null && !request.getFirstName().isEmpty())
			params.put("firstName", request.getFirstName());
		if (request.getLastName() != null && !request.getLastName().isEmpty())
			params.put("lastName", request.getLastName());
		if (request.getPrimaryAddress() != null) {
			com.orangeleap.tangerine.ws.schema.v2.Address primaryAddress = request
					.getPrimaryAddress();
			if (primaryAddress.getAddressLine1() != null
					&& !primaryAddress.getAddressLine1().isEmpty())
				params.put("primaryAddress.addressLine1", primaryAddress
						.getAddressLine1());
			if (primaryAddress.getAddressLine2() != null
					&& !primaryAddress.getAddressLine2().isEmpty())
				params.put("primaryAddress.addressLine2", primaryAddress
						.getAddressLine2());
			if (primaryAddress.getAddressLine3() != null
					&& !primaryAddress.getAddressLine3().isEmpty())
				params.put("primaryAddress.addressLine3", primaryAddress
						.getAddressLine3());
			if (primaryAddress.getCity() != null
					&& !primaryAddress.getCity().isEmpty())
				params.put("primaryAddress.city", primaryAddress.getCity());
			if (primaryAddress.getStateProvince() != null
					&& !primaryAddress.getStateProvince().isEmpty())
				params.put("primaryAddress.state", primaryAddress
						.getStateProvince());
			if (primaryAddress.getCountry() != null
					&& !primaryAddress.getCountry().isEmpty())
				params.put("primaryAddress.country", primaryAddress
						.getCountry());
		}

		if (request.getPrimaryEmail() != null) {
			com.orangeleap.tangerine.ws.schema.v2.Email primaryEmail = request
					.getPrimaryEmail();
			if (primaryEmail.getEmailAddress() != null
					&& !primaryEmail.getEmailAddress().isEmpty())
				params.put("primaryEmail.emailAddress", primaryEmail
						.getEmailAddress());
		}

		if (request.getPrimaryPhone() != null) {
			com.orangeleap.tangerine.ws.schema.v2.Phone primaryPhone = request
					.getPrimaryPhone();
			if (primaryPhone.getNumber() != null
					&& !primaryPhone.getNumber().isEmpty())
				params.put("primaryPhone.number", primaryPhone.getNumber());
		}

		if (params.size() == 0 ) {
			// no parameters where defined for the search throw an exception
			throw new InvalidRequestException("Must supply paramaters for findRequest");
		}
		
		List<com.orangeleap.tangerine.domain.Constituent> constituents = cs
				.findConstituents(params, null);
		for (com.orangeleap.tangerine.domain.Constituent co : constituents) {
			com.orangeleap.tangerine.ws.schema.v2.Constituent sc = new Constituent();
			converter.ConvertToJAXB(co, sc);

			cr.getConstituent().add(sc);
		}
		return cr;
	}

	@PayloadRoot(localPart = "SearchConstituentsRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public SearchConstituentsResponse searchConstituent(
			SearchConstituentsRequest request) throws InvalidRequestException {
		SearchConstituentsResponse cr = new SearchConstituentsResponse();
		ObjectConverter converter = new ObjectConverter();

		validationManager.validate(request);
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (request.getFirstName() != null && !request.getFirstName().isEmpty())
			params.put("firstName", request.getFirstName());
		if (request.getLastName() != null && !request.getLastName().isEmpty())
			params.put("lastName", request.getLastName());
		if (request.getPrimaryAddress() != null) {
			com.orangeleap.tangerine.ws.schema.v2.Address primaryAddress = request
					.getPrimaryAddress();
			if (primaryAddress.getAddressLine1() != null
					&& !primaryAddress.getAddressLine1().isEmpty())
				params.put("primaryAddress.addressLine1", primaryAddress
						.getAddressLine1());
			if (primaryAddress.getAddressLine2() != null
					&& !primaryAddress.getAddressLine2().isEmpty())
				params.put("primaryAddress.addressLine2", primaryAddress
						.getAddressLine2());
			if (primaryAddress.getAddressLine3() != null
					&& !primaryAddress.getAddressLine3().isEmpty())
				params.put("primaryAddress.addressLine3", primaryAddress
						.getAddressLine3());
			if (primaryAddress.getCity() != null
					&& !primaryAddress.getCity().isEmpty())
				params.put("primaryAddress.city", primaryAddress.getCity());
			if (primaryAddress.getStateProvince() != null
					&& !primaryAddress.getStateProvince().isEmpty())
				params.put("primaryAddress.state", primaryAddress
						.getStateProvince());
			if (primaryAddress.getCountry() != null
					&& !primaryAddress.getCountry().isEmpty())
				params.put("primaryAddress.country", primaryAddress
						.getCountry());
		}

		if (request.getPrimaryEmail() != null) {
			com.orangeleap.tangerine.ws.schema.v2.Email primaryEmail = request
					.getPrimaryEmail();
			if (primaryEmail.getEmailAddress() != null
					&& !primaryEmail.getEmailAddress().isEmpty())
				params.put("primaryEmail.emailAddress", primaryEmail
						.getEmailAddress());
		}

		if (request.getPrimaryPhone() != null) {
			com.orangeleap.tangerine.ws.schema.v2.Phone primaryPhone = request
					.getPrimaryPhone();
			if (primaryPhone.getNumber() != null
					&& !primaryPhone.getNumber().isEmpty())
				params.put("primaryPhone.number", primaryPhone.getNumber());
		}

		List<com.orangeleap.tangerine.domain.Constituent> constituents = cs
				.searchConstituents(params);
		for (com.orangeleap.tangerine.domain.Constituent co : constituents) {
			com.orangeleap.tangerine.ws.schema.v2.Constituent sc = new Constituent();
			converter.ConvertToJAXB(co, sc);

			cr.getConstituent().add(sc);
		}
		return cr;
	}

	void validateRecurringGiftInformation(SaveOrUpdateRecurringGiftRequest request) throws InvalidRequestException {
		if (request.getConstituentId() == 0)
			throw new InvalidRequestException("SaveOrUpdateRecurringGift requires a constituent id");
		
	}
	
	@PayloadRoot(localPart = "SaveOrUpdateRecurringGiftRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public SaveOrUpdateRecurringGiftResponse maintainRecurringGift(
			SaveOrUpdateRecurringGiftRequest request) throws InvalidRequestException {
		validateRecurringGiftInformation(request);
		
		validationManager.validate(request);
		
		com.orangeleap.tangerine.domain.Constituent c = cs
				.readConstituentById(request.getConstituentId());
		if (c == null) throw new InvalidRequestException("Unable to locate constituent by id");
		
		// 
		// if we are updating a pledge we should get it first and then do the conversion...
		// this way we don't clobber data by saving a new pledge on top of it..
		com.orangeleap.tangerine.domain.paymentInfo.RecurringGift rg = null;
		
		if (request.getRecurringgift().getId() != null && request.getRecurringgift().getId() > 0) {
			rg = recurringGiftService.readRecurringGiftById(request.getRecurringgift().getId());
			
			
			if (rg == null)
				throw new InvalidRequestException("Faild to locate pledge with id = " + request.getRecurringgift().getId().toString());
		} else {
			rg= recurringGiftService.createDefaultRecurringGift(c);
		}
		
		ObjectConverter converter = new ObjectConverter();

		converter.ConvertFromJAXB(request.getRecurringgift(), rg,siteService.readSite(tangerineUserHelper.lookupUserSiteName()));

		try {
			recurringGiftService.maintainRecurringGift(rg);
		} catch (BindException e) {
			logger.error(e.getMessage());
			throw new InvalidRequestException(e.getMessage());
		}

		rg.setConstituentId(rg.getConstituent().getId());
		
		SaveOrUpdateRecurringGiftResponse response = new SaveOrUpdateRecurringGiftResponse();
		RecurringGift responceRGift = new RecurringGift();

		converter.ConvertToJAXB(rg, responceRGift);
		response.setRecurringgift(responceRGift);
		return response;
	}
	
	@PayloadRoot(localPart = "SaveOrUpdatePledgeRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public SaveOrUpdatePledgeResponse maintainPledge(
			SaveOrUpdatePledgeRequest request) throws InvalidRequestException {
		
		validationManager.validate(request);
		
		com.orangeleap.tangerine.domain.Constituent c = cs
				.readConstituentById(request.getConstituentId());
		if (c == null) throw new InvalidRequestException("Unable to locate constituent by id");
		
		// 
		// if we are updating a pledge we should get it first and then do the conversion...
		// this way we don't clobber data by saving a new pledge on top of it..
		com.orangeleap.tangerine.domain.paymentInfo.Pledge p = null;
		
		if (request.getPledge().getId() != null && request.getPledge().getId() > 0) {
			p = pledgeService.readPledgeById(request.getPledge().getId());
			
			if (p == null)
				throw new InvalidRequestException("Faild to locate pledge with id = " + request.getPledge().getId().toString());
		} else {
			p= pledgeService.createDefaultPledge(c);
		}
		
		ObjectConverter converter = new ObjectConverter();

		converter.ConvertFromJAXB(request.getPledge(), p,siteService.readSite(tangerineUserHelper.lookupUserSiteName()));

		try {
			pledgeService.maintainPledge(p);
		} catch (BindException e) {
			logger.error(e.getMessage());
		}

		SaveOrUpdatePledgeResponse response = new SaveOrUpdatePledgeResponse();
		Pledge responsePledge = new Pledge();
		converter.ConvertToJAXB(p, responsePledge);
		response.setPledge(responsePledge);
		return response;
	}

	private void validatePaymentInformation(SaveOrUpdateGiftRequest request)
			throws InvalidRequestException {
		Gift g = request.getGift();
		
		if (g == null)
			throw new InvalidRequestException("Invalid request gift can not be null!");
		com.orangeleap.tangerine.ws.schema.v2.PaymentSource paymentSource = g
				.getPaymentSource();

		if (paymentSource == null)
			throw new InvalidRequestException(
					"Payment Source is required!");
		if (paymentSource.getConstituentId() <= 0)
			throw new InvalidRequestException(
					"Constituent id is required for Payment Source");
		if (paymentSource.getPaymentType() == null)
			throw new InvalidRequestException("Payment type is required");
		if (paymentSource.getPaymentType() == com.orangeleap.tangerine.ws.schema.v2.PaymentType.CREDIT_CARD) {
			// Validate Credit Card infomation
			if (paymentSource.getCreditCardExpirationMonth() == null
					|| paymentSource.getCreditCardExpirationMonth() == 0)
				throw new InvalidRequestException(
						"Credit Card Expiration Month is required for PaymentType CREDIT_CARD");
			if (paymentSource.getCreditCardExpirationYear() == null
					|| paymentSource.getCreditCardExpirationYear() == 0)
				throw new InvalidRequestException(
						"Credit Card Expiration Year is required for PaymentType CREDIT_CARD");
			if (paymentSource.getCreditCardHolderName() == null
					|| paymentSource.getCreditCardHolderName().equals(""))
				throw new InvalidRequestException(
						"Credit Card Holder Name is required for PaymentType CREDIT_CARD");
			if (paymentSource.getCreditCardNumber() == null
					|| paymentSource.getCreditCardNumber().equals(""))
				throw new InvalidRequestException(
						"Credit Card Number is required for PaymentType CREDIT_CARD");
			if ((paymentSource.getCreditCardType() == null || paymentSource.getCreditCardType().equals("")))
				throw new InvalidRequestException(
						"Credit Card type is required for PaymentType CREDIT_CARD");
			if (!paymentSource.getCreditCardType().equals(com.orangeleap.tangerine.domain.PaymentSource.VISA) && 
				!paymentSource.getCreditCardType().equals(com.orangeleap.tangerine.domain.PaymentSource.MASTER_CARD) && 
				!paymentSource.getCreditCardType().equals(com.orangeleap.tangerine.domain.PaymentSource.AMERICAN_EXPRESS) &&
				!paymentSource.getCreditCardType().equals(com.orangeleap.tangerine.domain.PaymentSource.DISCOVER))
				throw new InvalidRequestException(
						"Invalid Credit Card type for PaymentType CREDIT_CARD");
		} else if (paymentSource.getPaymentType() == com.orangeleap.tangerine.ws.schema.v2.PaymentType.ACH) {
			// Validate ACH Information
			if (paymentSource.getAchAccountNumber() == null
					|| paymentSource.getAchAccountNumber().equals(""))
				throw new InvalidRequestException(
						"ACH Account Number is required for PaymentType ACH");
			if (paymentSource.getAchHolderName() == null
					|| paymentSource.getAchHolderName().equals(""))
				throw new InvalidRequestException(
						"ACH Holder Name is required for PaymentType ACH");
			if (paymentSource.getAchRoutingNumber() == null
					|| paymentSource.getAchRoutingNumber().equals(""))
				throw new InvalidRequestException(
						"ACH Routing Number is required for PaymentType ACH");
		} else if (paymentSource.getPaymentType() == com.orangeleap.tangerine.ws.schema.v2.PaymentType.CHECK) {
			throw new InvalidRequestException(
					"CHECK PaymentType is not supported by OrangeLeap API");
		} else if (paymentSource.getPaymentType() == com.orangeleap.tangerine.ws.schema.v2.PaymentType.CASH) {
			throw new InvalidRequestException(
					"CASH PaymentType is not supported by OrangeLeap API");
		} else {
			throw new InvalidRequestException(
					"Invalid Payment Type supplied for PaymentSource");
		}
	}

	void validateGiftInformation(SaveOrUpdateGiftRequest request) throws InvalidRequestException {
		Gift g = request.getGift();
		Long id = request.getConstituentId();

		if (g.getAmount().doubleValue() <= 0.0) throw new InvalidRequestException("Gift amount must be greater than 0.0");
		if (id == 0) throw new InvalidRequestException("SaveOrUpdateGiftRequest must contain a valid constituentId");		
		if (g.getPaymentType() != com.orangeleap.tangerine.ws.schema.v2.PaymentType.CREDIT_CARD &&
			g.getPaymentType() != com.orangeleap.tangerine.ws.schema.v2.PaymentType.ACH) throw new InvalidRequestException("Gift contains invalid PaymentType");
	}
	
	@PayloadRoot(localPart = "SaveOrUpdateGiftRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public SaveOrUpdateGiftResponse maintainGift(SaveOrUpdateGiftRequest request)
			throws InvalidRequestException {
		SaveOrUpdateGiftResponse response = new SaveOrUpdateGiftResponse();

		validationManager.validate(request);
		
		validatePaymentInformation(request);
		validateGiftInformation(request);
		
		com.orangeleap.tangerine.domain.Constituent c = cs
				.readConstituentById(request.getConstituentId());

		if (c != null) {
			com.orangeleap.tangerine.domain.paymentInfo.Gift g = null;

			//
			// if this gift has an id already then we are doing an update
			// we need to get the existing gift so we don't clobber data that
			// is not exposed via the API
			if (request.getGift().getId() != null && request.getGift().getId() > 0) {
				g = giftService.readGiftById(request.getGift().getId());

				if (g == null) {
					throw new InvalidRequestException(
							"Failed to find gift with id = "
									+ request.getGift().getId().toString());
				}
			} else {
				g = new com.orangeleap.tangerine.domain.paymentInfo.Gift();
			}

			ObjectConverter converter = new ObjectConverter();

			converter.ConvertFromJAXB(request.getGift(), g,siteService.readSite(tangerineUserHelper.lookupUserSiteName()));
			g.setConstituent(c);

			try {
				g = giftService.maintainGift(g);
			} catch (BindException e) {
				logger.error(e.getMessage());
			}

			Gift responseGift = new Gift();
			converter.ConvertToJAXB(g, responseGift);
			if (responseGift.getPaymentSource() != null) {
				responseGift.getPaymentSource().setCreditCardNumber("");
				responseGift.getPaymentSource().setAchAccountNumber("");
				responseGift.getPaymentSource().setAchRoutingNumber("");
			}
			response.setGift(responseGift);
		} else {
			throw new InvalidRequestException("Unable to locate constituent by constituentId");
		}
		return response;
	}

	@PayloadRoot(localPart = "GetConstituentPledgeRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public GetConstituentPledgeResponse getConstituentsPledges(
			GetConstituentPledgeRequest request) throws InvalidRequestException {
		
		validationManager.validate(request);
		
		if (request.getConstituentId() == 0)
			throw new InvalidRequestException("Must supply a valid constituent id");

		List<com.orangeleap.tangerine.domain.paymentInfo.Pledge> pledges = pledgeService
				.readPledgesForConstituent(request.getConstituentId());

		GetConstituentPledgeResponse response = new GetConstituentPledgeResponse();
		ObjectConverter converter = new ObjectConverter();

		for (com.orangeleap.tangerine.domain.paymentInfo.Pledge p : pledges) {
			Pledge sp = new Pledge();

			converter.ConvertToJAXB(p, sp);
			response.getPledge().add(sp);
		}

		return response;
	}

	@PayloadRoot(localPart = "GetConstituentRecurringGiftRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public GetConstituentRecurringGiftResponse getConstituentsRecurringGifts(
			GetConstituentRecurringGiftRequest request) throws InvalidRequestException {
		
		validationManager.validate(request);
		
		if (request.getConstituentId() == 0)
			throw new InvalidRequestException("Must supply a valid constituent id");
		
		List<com.orangeleap.tangerine.domain.paymentInfo.RecurringGift> rgifts = recurringGiftService.readRecurringGiftsForConstituent(request.getConstituentId());

		GetConstituentRecurringGiftResponse response = new GetConstituentRecurringGiftResponse();
		ObjectConverter converter = new ObjectConverter();

		for (com.orangeleap.tangerine.domain.paymentInfo.RecurringGift rg : rgifts) {
			RecurringGift srg = new RecurringGift();

			converter.ConvertToJAXB(rg, srg);
			response.getRecurringgift().add(srg);
		}

		return response;
	}
	
	@PayloadRoot(localPart = "GetConstituentGiftRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public GetConstituentGiftResponse getConstituentsGifts(
			GetConstituentGiftRequest request) throws InvalidRequestException {
		
		validationManager.validate(request);
		
		
				
		List<com.orangeleap.tangerine.domain.paymentInfo.Gift> gifts = giftService.readMonetaryGifts(request.getConstituentId());



		GetConstituentGiftResponse response = new GetConstituentGiftResponse();
		ObjectConverter converter = new ObjectConverter();

		for (com.orangeleap.tangerine.domain.paymentInfo.Gift g : gifts) {

			Gift sg = new Gift();


			//
			// load the gift
			// this loads distrubtion lines and customFieldMaps for all related objects...
			g = giftService.readGiftById(g.getId());

			converter.ConvertToJAXB(g, sg);

			if (sg.getPaymentSource() != null)
				sg.getPaymentSource().setCreditCardNumber("");

			response.getGift().add(sg);
		}

		return response;
	}

	@PayloadRoot(localPart = "GetSegmentationByNameRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public com.orangeleap.tangerine.ws.schema.v2.GetSegmentationByNameResponse getSegmentationByName(
			com.orangeleap.tangerine.ws.schema.v2.GetSegmentationByNameRequest req) throws InvalidRequestException {
		com.orangeleap.tangerine.ws.schema.v2.GetSegmentationByNameResponse response = new com.orangeleap.tangerine.ws.schema.v2.GetSegmentationByNameResponse();
		
		validationManager.validate(req);
		
		
		WSClient wsClient = new WSClient();
		Theguru guruPort = wsClient.getTheGuru();

		com.orangeleap.theguru.client.ObjectFactory of = new com.orangeleap.theguru.client.ObjectFactory();
		GetSegmentationByNameRequest getSegmentationListRequest = of
				.createGetSegmentationByNameRequest();
		getSegmentationListRequest.setName(req.getSegmentation());
		GetSegmentationByNameResponse thegururesponse = guruPort
				.getSegmentationByName(getSegmentationListRequest);

		Iterator<Long> it = thegururesponse.getEntityid().iterator();
		while (it.hasNext()) {
			Long id = it.next();

			response.getEntityId().add(id);
		}

		return response;
	}

	@PayloadRoot(localPart = "GetSegmentationByIdRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public com.orangeleap.tangerine.ws.schema.v2.GetSegmentationByIdResponse getSegmentationById(
			com.orangeleap.tangerine.ws.schema.v2.GetSegmentationByIdRequest req) {
		com.orangeleap.tangerine.ws.schema.v2.GetSegmentationByIdResponse response = new com.orangeleap.tangerine.ws.schema.v2.GetSegmentationByIdResponse();

		WSClient wsClient = new WSClient();
		Theguru guruPort = wsClient.getTheGuru();

		com.orangeleap.theguru.client.ObjectFactory of = new com.orangeleap.theguru.client.ObjectFactory();
		com.orangeleap.theguru.client.GetSegmentationByIdRequest getSegmentationListRequest = of
				.createGetSegmentationByIdRequest();
		getSegmentationListRequest.setId(req.getId());
		com.orangeleap.theguru.client.GetSegmentationByIdResponse thegururesponse = guruPort
				.getSegmentationById(getSegmentationListRequest);

		Iterator<Long> it = thegururesponse.getEntityid().iterator();
		while (it.hasNext()) {
			Long id = it.next();

			response.getEntityid().add(id);
		}

		return response;
	}

	@PayloadRoot(localPart = "GetSegmentationListRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListResponse getSegmentationList(
			com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListRequest req)
			throws MalformedURLException, InvalidRequestException {
		com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListResponse response = new com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListResponse();
		
		validationManager.validate(req);
		
		WSClient wsClient = new WSClient();
		Theguru guruPort = wsClient.getTheGuru();

		com.orangeleap.theguru.client.ObjectFactory of = new com.orangeleap.theguru.client.ObjectFactory();
		com.orangeleap.theguru.client.GetSegmentationListRequest getSegmentationListRequest = of
				.createGetSegmentationListRequest();
		getSegmentationListRequest.setDummy("");
		com.orangeleap.theguru.client.GetSegmentationListResponse thegururesponse = guruPort
				.getSegmentationList(getSegmentationListRequest);

		Iterator<Segmentation> it = thegururesponse.getSegmentation()
				.iterator();
		while (it.hasNext()) {
			Segmentation seg = it.next();
			com.orangeleap.tangerine.ws.schema.v2.Segmentation segmentation = new com.orangeleap.tangerine.ws.schema.v2.Segmentation();

			segmentation.setId(seg.getId());
			segmentation.setName(seg.getName());
			segmentation.setDescription(seg.getDescription());
			segmentation.setExecutionCount(seg.getExecutionCount());
			segmentation.setExecutionDate(seg.getExecutionDate());
			segmentation.setExecutionUser(seg.getExecutionUser());
			segmentation.setType(seg.getType());
			response.getSegmentation().add(segmentation);
		}

		return response;
	}

	@PayloadRoot(localPart = "GetSegmentationListByTypeRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListByTypeResponse getSegmentationListByType(
			com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListByTypeRequest req)
			throws MalformedURLException, InvalidRequestException {
		com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListByTypeResponse response = new com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListByTypeResponse();
		WSClient wsClient = new WSClient();
		Theguru guruPort = wsClient.getTheGuru();

		validationManager.validate(req);
		
		com.orangeleap.theguru.client.ObjectFactory of = new com.orangeleap.theguru.client.ObjectFactory();
		com.orangeleap.theguru.client.GetSegmentationListByTypeRequest getSegmentationListRequest = of
				.createGetSegmentationListByTypeRequest();
		getSegmentationListRequest.setType(req.getType());
		com.orangeleap.theguru.client.GetSegmentationListByTypeResponse thegururesponse = guruPort
				.getSegmentationListByType(getSegmentationListRequest);

		Iterator<Segmentation> it = thegururesponse.getSegmentation()
				.iterator();
		while (it.hasNext()) {
			Segmentation seg = it.next();
			com.orangeleap.tangerine.ws.schema.v2.Segmentation segmentation = new com.orangeleap.tangerine.ws.schema.v2.Segmentation();

			segmentation.setId(seg.getId());
			segmentation.setName(seg.getName());
			segmentation.setDescription(seg.getDescription());
			segmentation.setExecutionCount(seg.getExecutionCount());
			segmentation.setExecutionDate(seg.getExecutionDate());
			segmentation.setExecutionUser(seg.getExecutionUser());
			segmentation.setType(seg.getType());
			response.getSegmentation().add(segmentation);
		}

		return response;
	}

	@PayloadRoot(localPart = "AddCommunicationHistoryRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public void addCommunicationHistory(AddCommunicationHistoryRequest req) throws InvalidRequestException {
		ObjectConverter converter = new ObjectConverter();

		validationManager.validate(req);
		
		com.orangeleap.tangerine.domain.CommunicationHistory ch = new com.orangeleap.tangerine.domain.CommunicationHistory();

		if (req.getConstituentId() <= 0) throw new InvalidRequestException("Invalid constituentid in addCommunicationHistory");
		
		converter.ConvertFromJAXB(req.getCommunicationHistory(), ch,siteService.readSite(tangerineUserHelper.lookupUserSiteName()));
		ch.setConstituent(cs.readConstituentById(req.getConstituentId()));

		try {
			communicationHistory.maintainCommunicationHistory(ch);
		} catch (BindException ex) {
			logger.error(ex.getMessage());
			throw new InvalidRequestException(ex.getMessage());
		}

	}

	@PayloadRoot(localPart = "BulkAddCommunicationHistoryRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public void bulkAddCommunicationHistory(
			BulkAddCommunicationHistoryRequest req) throws InvalidRequestException {
		ObjectConverter converter = new ObjectConverter();

		validationManager.validate(req);
		
		com.orangeleap.tangerine.domain.CommunicationHistory ch = new com.orangeleap.tangerine.domain.CommunicationHistory();

		converter.ConvertFromJAXB(req.getCommunicationHistory(), ch,siteService.readSite(tangerineUserHelper.lookupUserSiteName()));

		Iterator<Long> it = req.getConstituentId().iterator();
		while (it.hasNext()) {
			Long Id = (Long) it.next();
			if (Id <= 0) throw new InvalidRequestException("Invalid constituentid in BulkAddCommunicationHistory");
			ch.setConstituent(cs.readConstituentById(Id));

			try {
				communicationHistory.maintainCommunicationHistory(ch);
			} catch (BindException ex) {
				logger.error(ex.getMessage());
				throw new InvalidRequestException(ex.getMessage());
			}
		}
	}

	@PayloadRoot(localPart = "GetCommunicationHistoryRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public GetCommunicationHistoryResponse getCommunicationHistory(
			GetCommunicationHistoryRequest req) throws InvalidRequestException {
		
		validationManager.validate(req);
		
		SortInfo sortInfo = new SortInfo();
		sortInfo.setSort("ch.COMMUNICATION_HISTORY_ID");

		if (req.getConstituentId() <= 0) throw new InvalidRequestException("Invalid constituentid in getCommunicationHistory");
		
		List<com.orangeleap.tangerine.domain.CommunicationHistory> list = communicationHistory.readAllCommunicationHistoryByConstituentId(req.getConstituentId(), sortInfo, Locale.getDefault());

		ObjectConverter converter = new ObjectConverter();
		GetCommunicationHistoryResponse response = new GetCommunicationHistoryResponse();

		for (com.orangeleap.tangerine.domain.CommunicationHistory ch : list) {
			CommunicationHistory sch = new CommunicationHistory();
			//
			// we need to do this so we pull in the custom fields off of a touch point
			ch = communicationHistory.readCommunicationHistoryById(ch.getId());
			converter.ConvertToJAXB(ch, sch);
			response.getCommunicationHistory().add(sch);

		}
		return response;
	}

	@PayloadRoot(localPart = "GetPickListsRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public GetPickListsResponse getPickLists(GetPickListsRequest request) {
		GetPickListsResponse response = new GetPickListsResponse();
		List<Picklist> pickListList = picklistItemService.listPicklists();
		Iterator<Picklist> it = pickListList.iterator();
		ObjectConverter converter = new ObjectConverter();
		while (it.hasNext()) {
			Picklist pl = it.next();

			com.orangeleap.tangerine.ws.schema.v2.Picklist plist = new com.orangeleap.tangerine.ws.schema.v2.Picklist();
			converter.ConvertToJAXB(pl, plist);
			response.getPicklist().add(plist);
		}
		return response;
	}

	@PayloadRoot(localPart = "GetPickListByNameRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public GetPickListByNameResponse getPickListByName(
			GetPickListByNameRequest request) throws InvalidRequestException {
		GetPickListByNameResponse response = new GetPickListByNameResponse();
		
		validationManager.validate(request);
		
		Picklist pl = picklistItemService.getPicklist(request.getName());

		if (pl != null) {
			ObjectConverter converter = new ObjectConverter();

			com.orangeleap.tangerine.ws.schema.v2.Picklist plist = new com.orangeleap.tangerine.ws.schema.v2.Picklist();

			converter.ConvertToJAXB(pl, plist);
			response.setPicklist(plist);
		} else {
			throw new InvalidRequestException("Unable to locate picklist by name");
		}
		return response;
	}
	
	@PayloadRoot(localPart = "AddPickListItemRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public AddPickListItemByNameResponse addPickListItem(AddPickListItemByNameRequest request) throws InvalidRequestException
	{
		AddPickListItemByNameResponse response = new AddPickListItemByNameResponse();

		validationManager.validate(request);
		
		Picklist pl = picklistItemService.getPicklist(request.getPicklistname());
		
		PicklistItem item = new PicklistItem();
		ObjectConverter converter = new ObjectConverter();
		converter.ConvertFromJAXB(request.getPicklistitem(), item,siteService.readSite(tangerineUserHelper.lookupUserSiteName()));
		
		pl.getPicklistItems().add(item);
		
		pl = picklistItemService.maintainPicklist(pl);
		
		com.orangeleap.tangerine.ws.schema.v2.Picklist v2picklist = new com.orangeleap.tangerine.ws.schema.v2.Picklist();
		converter.ConvertToJAXB(pl,v2picklist);
		
		response.setPicklist(v2picklist);
		
		return response;
	}
	
	@PayloadRoot(localPart = "GetPaymentSourcesByConstituentIdRequest", namespace = "http://www.orangeleap.com/orangeleap/services2.0/")
	public GetPaymentSourcesByConstituentIdResponse getPaymentSources(GetPaymentSourcesByConstituentIdRequest request) throws InvalidRequestException {
		GetPaymentSourcesByConstituentIdResponse response = new GetPaymentSourcesByConstituentIdResponse();
		ObjectConverter converter = new ObjectConverter();
		
		if (request.getConstituentId() <= 0)
			throw new InvalidRequestException("Invalid constituent id");
	
		
		List<com.orangeleap.tangerine.domain.PaymentSource> sources = paymentSourceService.readAllPaymentSourcesByConstituentId(request.getConstituentId(), null, null);
		for (com.orangeleap.tangerine.domain.PaymentSource source : sources) {
			com.orangeleap.tangerine.ws.schema.v2.PaymentSource xmlsource = new com.orangeleap.tangerine.ws.schema.v2.PaymentSource();
			
			converter.ConvertToJAXB(source,xmlsource);
			response.getPaymentsources().add(xmlsource);
		}
		return response;
	}
	
}
