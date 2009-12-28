package com.orangeleap.tangerine.service.ws;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.orangeleap.tangerine.service.CommunicationHistoryService;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;
import com.orangeleap.tangerine.ws.schema.v2.AddCommunicationHistoryRequest;
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
import com.orangeleap.tangerine.ws.schema.v2.GetPickListByNameRequest;
import com.orangeleap.tangerine.ws.schema.v2.GetPickListByNameResponse;
import com.orangeleap.tangerine.ws.schema.v2.GetPickListsRequest;
import com.orangeleap.tangerine.ws.schema.v2.GetPickListsResponse;
import com.orangeleap.tangerine.ws.schema.v2.Gift;
import com.orangeleap.tangerine.ws.schema.v2.Pledge;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdateConstituentRequest;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdateConstituentResponse;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdateGiftRequest;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdateGiftResponse;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdatePledgeRequest;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdatePledgeResponse;
import com.orangeleap.tangerine.ws.schema.v2.SearchConstituentsRequest;
import com.orangeleap.tangerine.ws.schema.v2.SearchConstituentsResponse;
import com.orangeleap.tangerine.ws.util.v2.ObjectConverter;
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

	private GiftService giftService;

	private PledgeService pledgeService;

	private ConstituentService cs;

	@Autowired
	PicklistItemService picklistItemService;

	@Resource(name = "communicationHistoryService")
	private CommunicationHistoryService communicationHistory;

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

	@PayloadRoot(localPart = "GetConstituentByIdRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
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

	@PayloadRoot(localPart = "CreateDefaultConstituentRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
	/**
	 * Describe <code>createDefaultConstituent</code> method here.
	 *
	 * @param cdcr a <code>CreateDefaultConstituentRequest</code> value
	 * @return a <code>CreateDefaultConstituentResponse</code> value
	 */
	public CreateDefaultConstituentResponse createDefaultConstituent(
			CreateDefaultConstituentRequest cdcr) {
		CreateDefaultConstituentResponse cr = new CreateDefaultConstituentResponse();
		com.orangeleap.tangerine.domain.Constituent p = cs
				.createDefaultConstituent();
		com.orangeleap.tangerine.ws.schema.v2.Constituent c = new com.orangeleap.tangerine.ws.schema.v2.Constituent();

		cr.getConstituent().add(c);

		return cr;
	}

	@PayloadRoot(localPart = "SaveOrUpdateConstituentRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
	public SaveOrUpdateConstituentResponse maintainConstituent(
			SaveOrUpdateConstituentRequest p)
			throws ConstituentValidationException, BindException {
		com.orangeleap.tangerine.domain.Constituent c = cs
				.createDefaultConstituent();
		ObjectConverter converter = new ObjectConverter();

		converter.ConvertFromJAXB(p.getConstituent(), c);

		cs.maintainConstituent(c);

		Constituent responseConstituent = new Constituent();
		SaveOrUpdateConstituentResponse response = new SaveOrUpdateConstituentResponse();
		converter.ConvertToJAXB(c, responseConstituent);
		responseConstituent.setAccountNumber(c.getId());
		response.setConstituent(responseConstituent);
		return response;
	}

	@PayloadRoot(localPart = "FindConstituentsRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
	public FindConstituentsResponse findConstituent(
			FindConstituentsRequest request) {
		FindConstituentsResponse cr = new FindConstituentsResponse();
		ObjectConverter converter = new ObjectConverter();

		Map<String, Object> params = new HashMap<String, Object>();
		if (request.getFirstName() != null && !request.getFirstName().isEmpty())
			params.put("firstName", request.getFirstName());
		if (request.getLastName() != null && !request.getLastName().isEmpty())
			params.put("lastName", request.getLastName());
		if (request.getPrimaryAddress() != null) {
			com.orangeleap.tangerine.ws.schema.v2.Address primaryAddress = request
					.getPrimaryAddress();
			if (!primaryAddress.getAddressLine1().isEmpty())
				params.put("primaryAddress.addressLine1", primaryAddress
						.getAddressLine1());
			if (!primaryAddress.getAddressLine2().isEmpty())
				params.put("primaryAddress.addressLine2", primaryAddress
						.getAddressLine2());
			if (!primaryAddress.getAddressLine3().isEmpty())
				params.put("primaryAddress.addressLine3", primaryAddress
						.getAddressLine3());
			if (!primaryAddress.getCity().isEmpty())
				params.put("primaryAddress.city", primaryAddress.getCity());
			if (!primaryAddress.getStateProvince().isEmpty())
				params.put("primaryAddress.state", primaryAddress
						.getStateProvince());
			if (!primaryAddress.getCountry().isEmpty())
				params.put("primaryAddress.country", primaryAddress
						.getCountry());
		}

		if (request.getPrimaryEmail() != null) {
			com.orangeleap.tangerine.ws.schema.v2.Email primaryEmail = request
					.getPrimaryEmail();
			if (!primaryEmail.getEmailAddress().isEmpty())
				params.put("primaryEmail.emailAddress", primaryEmail
						.getEmailAddress());
		}

		if (request.getPrimaryPhone() != null) {
			com.orangeleap.tangerine.ws.schema.v2.Phone primaryPhone = request
					.getPrimaryPhone();
			if (!primaryPhone.getNumber().isEmpty())
				params.put("primaryPhone.number", primaryPhone.getNumber());
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

	@PayloadRoot(localPart = "SearchConstituentsRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
	public SearchConstituentsResponse searchConstituent(
			SearchConstituentsRequest request) {
		SearchConstituentsResponse cr = new SearchConstituentsResponse();
		ObjectConverter converter = new ObjectConverter();

		Map<String, Object> params = new HashMap<String, Object>();
		if (request.getFirstName() != null && !request.getFirstName().isEmpty())
			params.put("firstName", request.getFirstName());
		if (request.getLastName() != null && !request.getLastName().isEmpty())
			params.put("lastName", request.getLastName());
		if (request.getPrimaryAddress() != null) {
			com.orangeleap.tangerine.ws.schema.v2.Address primaryAddress = request
					.getPrimaryAddress();
			if (!primaryAddress.getAddressLine1().isEmpty())
				params.put("primaryAddress.addressLine1", primaryAddress
						.getAddressLine1());
			if (!primaryAddress.getAddressLine2().isEmpty())
				params.put("primaryAddress.addressLine2", primaryAddress
						.getAddressLine2());
			if (!primaryAddress.getAddressLine3().isEmpty())
				params.put("primaryAddress.addressLine3", primaryAddress
						.getAddressLine3());
			if (!primaryAddress.getCity().isEmpty())
				params.put("primaryAddress.city", primaryAddress.getCity());
			if (!primaryAddress.getStateProvince().isEmpty())
				params.put("primaryAddress.state", primaryAddress
						.getStateProvince());
			if (!primaryAddress.getCountry().isEmpty())
				params.put("primaryAddress.country", primaryAddress
						.getCountry());
		}

		if (request.getPrimaryEmail() != null) {
			com.orangeleap.tangerine.ws.schema.v2.Email primaryEmail = request
					.getPrimaryEmail();
			if (!primaryEmail.getEmailAddress().isEmpty())
				params.put("primaryEmail.emailAddress", primaryEmail
						.getEmailAddress());
		}

		if (request.getPrimaryPhone() != null) {
			com.orangeleap.tangerine.ws.schema.v2.Phone primaryPhone = request
					.getPrimaryPhone();
			if (!primaryPhone.getNumber().isEmpty())
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

	@PayloadRoot(localPart = "SaveOrUpdatePledgeRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
	public SaveOrUpdatePledgeResponse maintainPledge(
			SaveOrUpdatePledgeRequest request) {
		com.orangeleap.tangerine.domain.Constituent c = cs
				.readConstituentById(request.getConstituentId());
		com.orangeleap.tangerine.domain.paymentInfo.Pledge p = pledgeService
				.createDefaultPledge(c);

		ObjectConverter converter = new ObjectConverter();

		converter.ConvertFromJAXB(request.getPledge(), p);

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

	@PayloadRoot(localPart = "SaveOrUpdateGiftRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
	public SaveOrUpdateGiftResponse maintainGift(SaveOrUpdateGiftRequest request) {
		com.orangeleap.tangerine.domain.Constituent c = cs
				.readConstituentById(request.getConstituentId());
		com.orangeleap.tangerine.domain.paymentInfo.Gift g = giftService
				.createDefaultGift(c);

		ObjectConverter converter = new ObjectConverter();

		converter.ConvertFromJAXB(request.getGift(), g);
		g.setConstituent(c);

		try {
			giftService.maintainGift(g);
		} catch (BindException e) {
			logger.error(e.getMessage());
		}

		SaveOrUpdateGiftResponse response = new SaveOrUpdateGiftResponse();
		Gift responseGift = new Gift();
		converter.ConvertToJAXB(g, responseGift);
		if (responseGift.getPaymentSource() != null)
			responseGift.getPaymentSource().setCreditCardNumber("");
		response.setGift(responseGift);

		return response;
	}

	@PayloadRoot(localPart = "GetConstituentPledgeRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
	public GetConstituentPledgeResponse getConstituentsPledges(
			GetConstituentPledgeRequest request) {
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

	@PayloadRoot(localPart = "GetConstituentGiftRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
	public GetConstituentGiftResponse getConstituentsGifts(
			GetConstituentGiftRequest request) {
		List<com.orangeleap.tangerine.domain.paymentInfo.Gift> gifts = giftService
				.readMonetaryGifts(request.getConstituentId());

		GetConstituentGiftResponse response = new GetConstituentGiftResponse();
		ObjectConverter converter = new ObjectConverter();

		for (com.orangeleap.tangerine.domain.paymentInfo.Gift g : gifts) {
			Gift sg = new Gift();

			converter.ConvertToJAXB(g, sg);

			if (sg.getPaymentSource() != null)
				sg.getPaymentSource().setCreditCardNumber("");

			response.getGift().add(sg);
		}

		return response;
	}

	@PayloadRoot(localPart = "GetSegmentationByNameRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
	public com.orangeleap.tangerine.ws.schema.v2.GetSegmentationByNameResponse getSegmentationByName(
			com.orangeleap.tangerine.ws.schema.v2.GetSegmentationByNameRequest req) {
		com.orangeleap.tangerine.ws.schema.v2.GetSegmentationByNameResponse response = new com.orangeleap.tangerine.ws.schema.v2.GetSegmentationByNameResponse();

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

	@PayloadRoot(localPart = "GetSegmentationByIdRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
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

	@PayloadRoot(localPart = "GetSegmentationListRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
	public com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListResponse getSegmentationList(
			com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListRequest req)
			throws MalformedURLException {
		com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListResponse response = new com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListResponse();
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

			response.getSegmentation().add(segmentation);
		}

		return response;
	}

	@PayloadRoot(localPart = "GetSegmentationListByTypeRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
	public com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListByTypeResponse getSegmentationListByType(
			com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListByTypeRequest req)
			throws MalformedURLException {
		com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListByTypeResponse response = new com.orangeleap.tangerine.ws.schema.v2.GetSegmentationListByTypeResponse();
		WSClient wsClient = new WSClient();
		Theguru guruPort = wsClient.getTheGuru();

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

			response.getSegmentation().add(segmentation);
		}

		return response;
	}

	@PayloadRoot(localPart = "AddCommunicationHistoryRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
	public void addCommunicationHistory(AddCommunicationHistoryRequest req) {
		ObjectConverter converter = new ObjectConverter();

		com.orangeleap.tangerine.domain.CommunicationHistory ch = new com.orangeleap.tangerine.domain.CommunicationHistory();

		converter.ConvertFromJAXB(req.getCommunicationHistory(), ch);
		ch.setConstituent(cs.readConstituentById(req.getConstituentId()));

		try {
			communicationHistory.maintainCommunicationHistory(ch);
		} catch (BindException ex) {
			logger.error(ex.getMessage());
		}

	}

	@PayloadRoot(localPart = "BulkAddCommunicationHistoryRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
	public void bulkAddCommunicationHistory(
			BulkAddCommunicationHistoryRequest req) {
		ObjectConverter converter = new ObjectConverter();

		com.orangeleap.tangerine.domain.CommunicationHistory ch = new com.orangeleap.tangerine.domain.CommunicationHistory();

		converter.ConvertFromJAXB(req.getCommunicationHistory(), ch);

		Iterator<Long> it = req.getConstituentId().iterator();
		while (it.hasNext()) {
			Long Id = (Long) it.next();
			ch.setConstituent(cs.readConstituentById(Id));

			try {
				communicationHistory.maintainCommunicationHistory(ch);
			} catch (BindException ex) {
				logger.error(ex.getMessage());
			}
		}
	}

	@PayloadRoot(localPart = "GetCommunicationHistoryRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
	public GetCommunicationHistoryResponse getCommunicationHistory(
			GetCommunicationHistoryRequest req) {
		SortInfo sortInfo = new SortInfo();
		sortInfo.setSort("ch.COMMUNICATION_HISTORY_ID");

		PaginatedResult result = communicationHistory
				.readCommunicationHistoryByConstituent(req.getConstituentId(),
						sortInfo);
		ObjectConverter converter = new ObjectConverter();
		List<com.orangeleap.tangerine.domain.CommunicationHistory> list = result
				.getRows();
		GetCommunicationHistoryResponse response = new GetCommunicationHistoryResponse();

		for (com.orangeleap.tangerine.domain.CommunicationHistory ch : list) {
			CommunicationHistory sch = new CommunicationHistory();

			converter.ConvertToJAXB(ch, sch);
			response.getCommunicationHistory().add(sch);

		}
		return response;
	}

	@PayloadRoot(localPart = "GetPickListsRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
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

	@PayloadRoot(localPart = "GetPickListByNameRequest", namespace = "http://www.orangeleap.com/orangeleap/services/2.0")
	public GetPickListByNameResponse getPickListByName(
			GetPickListByNameRequest request) {
		GetPickListByNameResponse response = new GetPickListByNameResponse();
		Picklist pl = picklistItemService.getPicklist(request.getName());

		if (pl != null) {
			ObjectConverter converter = new ObjectConverter();

			com.orangeleap.tangerine.ws.schema.v2.Picklist plist = new com.orangeleap.tangerine.ws.schema.v2.Picklist();

			converter.ConvertToJAXB(pl, plist);
			response.setPicklist(plist);
		}
		return response;
	}
}