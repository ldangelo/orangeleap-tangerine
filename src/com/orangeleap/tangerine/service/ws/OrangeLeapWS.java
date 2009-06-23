package com.orangeleap.tangerine.service.ws;


import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.ws.schema.*;
import com.orangeleap.tangerine.ws.util.ObjectConverter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.naming.spi.ObjectFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.providers.ldap.LdapAuthenticationProvider;
import org.springframework.validation.BindException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;


@Endpoint

/**
 * Describe class <code>OrangeLeapWS</code> here.
 *
 * This class is the entry point for all of the java soap requests to orangeleap.
 *
 * @author <a href="mailto:ldangelo@orangeleap.com">Leo A. D'Angelo</a>
 * @version $Id: prj.el,v 1.4 2003/04/23 14:28:25 kobit Exp $
 */
public class OrangeLeapWS {

    private static final Log logger = LogFactory.getLog(LdapAuthenticationProvider.class);

    @Resource(name = "giftService")
    private GiftService gs;

    
    private ConstituentService cs;



    /**
     * Creates a new <code>OrangeLeapWS</code> instance.
     *
     */
    public OrangeLeapWS() {
	cs = null;

    }

    /**
     * Creates a new <code>OrangeLeapWS</code> instance.
     *
     * @param service a <code>ConstituentService</code> value
     */
    public OrangeLeapWS(ConstituentService service) {
	cs = service;
    }
	
    /**
     * Describe <code>getCs</code> method here.
     *
     * @return a <code>com.orangeleap.tangerine.service.ConstituentService</code> value
     */
    public com.orangeleap.tangerine.service.ConstituentService getCs() {
	return cs;
    }

    /**
     * Describe <code>setCs</code> method here.
     *
     * @param cs a <code>com.orangeleap.tangerine.service.ConstituentService</code> value
     */
    public void setCs(com.orangeleap.tangerine.service.ConstituentService cs) {
	this.cs = cs;
    }
	
    @PayloadRoot(localPart="CreateDefaultConstituentRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
    /**
     * Describe <code>createDefaultConstituent</code> method here.
     *
     * @param cdcr a <code>CreateDefaultConstituentRequest</code> value
     * @return a <code>CreateDefaultConstituentResponse</code> value
     */
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
	com.orangeleap.tangerine.domain.Constituent c = cs.createDefaultConstituent();
        ObjectConverter converter = new ObjectConverter();

	converter.ConvertFromJAXB(p.getConstituent(),c);

	cs.maintainConstituent(c);
    }

    
    @PayloadRoot(localPart="FindConstituentsRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
    public FindConstituentsResponse findConstituent(FindConstituentsRequest request) {
	FindConstituentsResponse cr = new FindConstituentsResponse();
	com.orangeleap.tangerine.ws.schema.Constituent c = request.getConstituent();
        ObjectConverter converter = new ObjectConverter();
	
        Map<String,Object> params = new HashMap<String,Object>();

        if (c.getFirstName() != null && c.getFirstName() != "")
            params.put("firstName",c.getFirstName());
        if (c.getLastName() != null && c.getLastName() != "")
            params.put("lastName",c.getLastName());
	
	
        List<com.orangeleap.tangerine.domain.Constituent> constituents = cs.findConstituents(params,null);
        for (com.orangeleap.tangerine.domain.Constituent co : constituents) {
            com.orangeleap.tangerine.ws.schema.Constituent sc = new Constituent();
            converter.ConvertToJAXB(co,sc);
	    
            cr.getConstituent().add(sc);
        }
	return cr;
    }

    @PayloadRoot(localPart="SearchConstituentsRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
    public SearchConstituentsResponse searchConstituent(SearchConstituentsRequest request) {
		SearchConstituentsResponse cr = new SearchConstituentsResponse();
		com.orangeleap.tangerine.ws.schema.Constituent c = request.getConstituent();
        ObjectConverter converter = new ObjectConverter();

        Map<String,Object> params = new HashMap<String,Object>();

        if (c.getFirstName() != null && c.getFirstName() != "")
            params.put("firstName",c.getFirstName());
        if (c.getLastName() != null && c.getLastName() != "")
            params.put("lastName",c.getLastName());


        List<com.orangeleap.tangerine.domain.Constituent> constituents = cs.searchConstituents(params);
        for (com.orangeleap.tangerine.domain.Constituent co : constituents) {
            com.orangeleap.tangerine.ws.schema.Constituent sc = new Constituent();
            converter.ConvertToJAXB(co,sc);

            cr.getConstituent().add(sc);
        }
		return cr;
    }

    @PayloadRoot(localPart="SaveOrUpdateGiftRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
    public void maintainGift(SaveOrUpdateGiftRequest request) {
        com.orangeleap.tangerine.domain.Constituent c = cs.readConstituentById(request.getConstituentId());
        com.orangeleap.tangerine.domain.paymentInfo.Gift g = gs.createDefaultGift(c);


        ObjectConverter converter = new ObjectConverter();

        converter.ConvertFromJAXB(request.getGift(),g);

        try {
            gs.maintainGift(g);
        } catch (BindException e) {
            logger.error(e.getMessage());
        }

    }

    @PayloadRoot(localPart="GetConstituentGiftRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
    public GetConstituentGiftResponse getConstituentsGifts(GetConstituentGiftRequest request) {
        List<com.orangeleap.tangerine.domain.paymentInfo.Gift> gifts = gs.readMonetaryGifts(request.getConstituentId());

        GetConstituentGiftResponse response = new GetConstituentGiftResponse();
        ObjectConverter converter = new ObjectConverter();

        for (com.orangeleap.tangerine.domain.paymentInfo.Gift g: gifts) {
            Gift sg = new Gift();

            converter.ConvertToJAXB(g,sg);
            response.getGift().add(sg);
        }

        return response;
    }



    private GetSegmentationResponse getLapsedDonors()
    {
               List<com.orangeleap.tangerine.domain.Constituent> constituents = cs.readAllConstituentsBySite();
        GetSegmentationResponse response = new GetSegmentationResponse();
        ObjectConverter converter = new ObjectConverter();

        for (com.orangeleap.tangerine.domain.Constituent c : constituents) {
            if (c.isLapsedDonor()) {
                Constituent sc = new Constituent();

                converter.ConvertToJAXB(c,sc);
                response.getConstituent().add(sc);
            }
        }
        return response;
    }

    private GetSegmentationResponse getMajorDonors()
    {
        List<com.orangeleap.tangerine.domain.Constituent> constituents = cs.readAllConstituentsBySite();
        GetSegmentationResponse response = new GetSegmentationResponse();
        ObjectConverter converter = new ObjectConverter();

        for (com.orangeleap.tangerine.domain.Constituent c : constituents) {
            if (c.isMajorDonor()) {
                Constituent sc = new Constituent();

                converter.ConvertToJAXB(c,sc);
                response.getConstituent().add(sc);
            }
        }
        return response;
    }

    @PayloadRoot(localPart="GetSegmentationRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
    public GetSegmentationResponse getSegmentation(GetSegmentationRequest req)
    {

        if (req.getSegmentation().equals("Lapsed Donor"))
            return getLapsedDonors();
        if (req.getSegmentation().equals("Major Donor"))
            return getMajorDonors();

        return null;
    }

    @PayloadRoot(localPart="GetSegmentationListRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
    public GetSegmentationListResponse getSegmentationList(GetSegmentationListRequest req)
    {
        GetSegmentationListResponse response = new GetSegmentationListResponse();

        response.getSegmentation().add("Major Donor");
        response.getSegmentation().add("Lapsed Donor");
        
        return response;
    }

    @PayloadRoot(localPart="AddCommunicationHistoryRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
    public void addCommunicationHistory(AddCommunicationHistoryRequest req)
    {

    }

    @PayloadRoot(localPart="GetCommunicationHistoryRequest",namespace="http://www.orangeleap.com/orangeleap/services/1.0")
    public GetCommunicationHistoryResponse getCommunicationHistory(GetCommunicationHistoryRequest req)
    {
        return null;
    }
}
