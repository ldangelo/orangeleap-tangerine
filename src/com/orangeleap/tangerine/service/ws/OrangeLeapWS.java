package com.orangeleap.tangerine.service.ws;

import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.ws.schema.*;
import org.springframework.validation.BindException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;


@Endpoint
public class OrangeLeapWS {
	private ConstituentService cs;
	private ObjectFactory of = new ObjectFactory();
	public OrangeLeapWS() {
		cs = null;
	}

	public OrangeLeapWS(ConstituentService service) {
		cs = service;
	}
	
	public com.orangeleap.tangerine.service.ConstituentService getCs() {
		return cs;
	}

	public void setCs(com.orangeleap.tangerine.service.ConstituentService cs) {
		this.cs = cs;
	}
	
	@PayloadRoot(localPart="CreateDefaultConstituentRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
	public CreateDefaultConstituentResponse createDefaultConstituent(CreateDefaultConstituentRequest cdcr) {
		CreateDefaultConstituentResponse cr = new CreateDefaultConstituentResponse();
		com.orangeleap.tangerine.domain.Constituent p = cs.createDefaultConstituent();
		com.orangeleap.tangerine.ws.schema.Constituent c = new com.orangeleap.tangerine.ws.schema.Constituent();
		
		c.setFirstName("Test");
		
		cr.getConstituent().add(c);

		return cr;
	}
	
	@PayloadRoot(localPart="SaveOrUpdateConstituentRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
	public void maintainConstituent(SaveOrUpdateConstituentRequest p) throws ConstituentValidationException, BindException {
	    //cs.maintainConstituent(p);
	}

    @PayloadRoot(localPart="FindConstituentsRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
    public FindConstituentsResponse findConstituent(FindConstituentsRequest request) {
		FindConstituentsResponse cr = new FindConstituentsResponse();
		com.orangeleap.tangerine.domain.Constituent p = cs.createDefaultConstituent();
		com.orangeleap.tangerine.ws.schema.Constituent c = new com.orangeleap.tangerine.ws.schema.Constituent();
		
		c.setFirstName("Test");
		
		cr.getConstituent().add(c);


		return cr;
    }

    @PayloadRoot(localPart="SearchConstituentsRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
    public SearchConstituentsResponse searchConstituent(SearchConstituentsRequest request) {
		SearchConstituentsResponse cr = new SearchConstituentsResponse();
		com.orangeleap.tangerine.domain.Constituent p = cs.createDefaultConstituent();
		com.orangeleap.tangerine.ws.schema.Constituent c = new com.orangeleap.tangerine.ws.schema.Constituent();
		
		c.setFirstName("Test");

		cr.getConstituent().add(c);		

		return cr;
    }

    @PayloadRoot(localPart="SaveOrUpdateGiftRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
    public void maintainGift(SaveOrUpdateGiftRequest request) {
    }

    @PayloadRoot(localPart="GetConstituentGiftRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
    public GetConstituentGiftResponse getConstituentsGifts(GetConstituentGiftRequest request) {
	return null;
    }

    @PayloadRoot(localPart="GetSegmentationRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
    public GetSegmentationResponse getSegmentation(GetSegmentationRequest req)
    {
        return null;
    }

    @PayloadRoot(localPart="GetSegmentationListRequest",namespace="http://www.orangeleap.com/orangeleap/service/1.0")
    public GetSegmentationListResponse getSegmentationList(GetSegmentationListRequest req)
    {
        return null;
    }

    @PayloadRoot(localPart="AddCommunicationHistoryRequest",namespace="http://www.orangeleap.com/orangeleap/service/1.0")
    public void addCommunicationHistory(AddCommunicationHistoryRequest req)
    {

    }

    @PayloadRoot(localPart="GetCommunicationHistoryRequest",namespace="http://www.orangeleap.com/orangeleap/service/1.0")
    public GetCommunicationHistoryResponse getCommunicationHistory(GetCommunicationHistoryRequest req)
    {
        return null;
    }
}
