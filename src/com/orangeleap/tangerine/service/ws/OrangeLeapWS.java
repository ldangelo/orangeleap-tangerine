/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.service.ws;


import com.orangeleap.tangerine.service.CommunicationHistoryService;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;
import com.orangeleap.tangerine.ws.schema.*;
import com.orangeleap.tangerine.ws.util.ObjectConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.providers.ldap.LdapAuthenticationProvider;
import org.springframework.validation.BindException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Describe class <code>OrangeLeapWS</code> here.
 * <p/>
 * This class is the entry point for all of the java soap requests to orangeleap.
 *
 * @author <a href="mailto:ldangelo@orangeleap.com">Leo A. D'Angelo</a>
 * @version $Id: prj.el,v 1.4 2003/04/23 14:28:25 kobit Exp $
 */
@Endpoint
public class OrangeLeapWS {

    private static final Log logger = LogFactory.getLog(LdapAuthenticationProvider.class);


    private GiftService giftService;


    private ConstituentService cs;

    @Resource(name = "communicationHistoryService")
    private CommunicationHistoryService communicationHistory;

    /**
     * Creates a new <code>OrangeLeapWS</code> instance.
     */
    public OrangeLeapWS() {
        cs = null;

    }

    /**
     * Creates a new <code>OrangeLeapWS</code> instance.
     *
     * @param service a <code>ConstituentService</code> value
     */
    public OrangeLeapWS(ConstituentService service, GiftService gs, CommunicationHistoryService chs) {
        cs = service;
        giftService = gs;
        communicationHistory = chs;
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

    @PayloadRoot(localPart = "CreateDefaultConstituentRequest", namespace = "http://www.orangeleap.com/orangeleap/services/1.0")
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

        cr.getConstituent().add(c);

        return cr;
    }

    @PayloadRoot(localPart = "SaveOrUpdateConstituentRequest", namespace = "http://www.orangeleap.com/orangeleap/services/1.0")
    public void maintainConstituent(SaveOrUpdateConstituentRequest p) throws ConstituentValidationException, BindException {
        com.orangeleap.tangerine.domain.Constituent c = cs.createDefaultConstituent();
        ObjectConverter converter = new ObjectConverter();

        converter.ConvertFromJAXB(p.getConstituent(), c);

        cs.maintainConstituent(c);
    }


    @PayloadRoot(localPart = "FindConstituentsRequest", namespace = "http://www.orangeleap.com/orangeleap/services/1.0")
    public FindConstituentsResponse findConstituent(FindConstituentsRequest request) {
        FindConstituentsResponse cr = new FindConstituentsResponse();
        ObjectConverter converter = new ObjectConverter();

        Map<String, Object> params = new HashMap<String, Object>();
        if (request.getFirstName() != null && !request.getFirstName().isEmpty())
            params.put("firstName", request.getFirstName());
        if (request.getLastName() != null && !request.getLastName().isEmpty())
            params.put("lastName", request.getLastName());
        if (request.getPrimaryAddress() != null) {
            com.orangeleap.tangerine.ws.schema.Address primaryAddress = request.getPrimaryAddress();
            if (!primaryAddress.getAddressLine1().isEmpty())
                params.put("primaryAddress.addressLine1", primaryAddress.getAddressLine1());
            if (!primaryAddress.getAddressLine2().isEmpty())
                params.put("primaryAddress.addressLine2", primaryAddress.getAddressLine2());
            if (!primaryAddress.getAddressLine3().isEmpty())
                params.put("primaryAddress.addressLine3", primaryAddress.getAddressLine3());
            if (!primaryAddress.getCity().isEmpty()) params.put("primaryAddress.city", primaryAddress.getCity());
            if (!primaryAddress.getStateProvince().isEmpty())
                params.put("primaryAddress.state", primaryAddress.getStateProvince());
            if (!primaryAddress.getCountry().isEmpty())
                params.put("primaryAddress.country", primaryAddress.getCountry());
        }

        if (request.getPrimaryEmail() != null) {
            com.orangeleap.tangerine.ws.schema.Email primaryEmail = request.getPrimaryEmail();
            if (!primaryEmail.getEmailAddress().isEmpty())
                params.put("primaryEmail.emailAddress", primaryEmail.getEmailAddress());
        }

        if (request.getPrimaryPhone() != null) {
            com.orangeleap.tangerine.ws.schema.Phone primaryPhone = request.getPrimaryPhone();
            if (!primaryPhone.getNumber().isEmpty()) params.put("primaryPhone.number", primaryPhone.getNumber());
        }


        List<com.orangeleap.tangerine.domain.Constituent> constituents = cs.findConstituents(params, null);
        for (com.orangeleap.tangerine.domain.Constituent co : constituents) {
            com.orangeleap.tangerine.ws.schema.Constituent sc = new Constituent();
            converter.ConvertToJAXB(co, sc);

            cr.getConstituent().add(sc);
        }
        return cr;
    }

    @PayloadRoot(localPart = "SearchConstituentsRequest", namespace = "http://www.orangeleap.com/orangeleap/services/1.0")
    public SearchConstituentsResponse searchConstituent(SearchConstituentsRequest request) {
        SearchConstituentsResponse cr = new SearchConstituentsResponse();
        ObjectConverter converter = new ObjectConverter();

        Map<String, Object> params = new HashMap<String, Object>();
        if (request.getFirstName() != null && !request.getFirstName().isEmpty())
            params.put("firstName", request.getFirstName());
        if (request.getLastName() != null && !request.getLastName().isEmpty())
            params.put("lastName", request.getLastName());
        if (request.getPrimaryAddress() != null) {
            com.orangeleap.tangerine.ws.schema.Address primaryAddress = request.getPrimaryAddress();
            if (!primaryAddress.getAddressLine1().isEmpty())
                params.put("primaryAddress.addressLine1", primaryAddress.getAddressLine1());
            if (!primaryAddress.getAddressLine2().isEmpty())
                params.put("primaryAddress.addressLine2", primaryAddress.getAddressLine2());
            if (!primaryAddress.getAddressLine3().isEmpty())
                params.put("primaryAddress.addressLine3", primaryAddress.getAddressLine3());
            if (!primaryAddress.getCity().isEmpty()) params.put("primaryAddress.city", primaryAddress.getCity());
            if (!primaryAddress.getStateProvince().isEmpty())
                params.put("primaryAddress.state", primaryAddress.getStateProvince());
            if (!primaryAddress.getCountry().isEmpty())
                params.put("primaryAddress.country", primaryAddress.getCountry());
        }

        if (request.getPrimaryEmail() != null) {
            com.orangeleap.tangerine.ws.schema.Email primaryEmail = request.getPrimaryEmail();
            if (!primaryEmail.getEmailAddress().isEmpty())
                params.put("primaryEmail.emailAddress", primaryEmail.getEmailAddress());
        }

        if (request.getPrimaryPhone() != null) {
            com.orangeleap.tangerine.ws.schema.Phone primaryPhone = request.getPrimaryPhone();
            if (!primaryPhone.getNumber().isEmpty()) params.put("primaryPhone.number", primaryPhone.getNumber());
        }

        List<com.orangeleap.tangerine.domain.Constituent> constituents = cs.searchConstituents(params);
        for (com.orangeleap.tangerine.domain.Constituent co : constituents) {
            com.orangeleap.tangerine.ws.schema.Constituent sc = new Constituent();
            converter.ConvertToJAXB(co, sc);

            cr.getConstituent().add(sc);
        }
        return cr;
    }

    @PayloadRoot(localPart = "SaveOrUpdateGiftRequest", namespace = "http://www.orangeleap.com/orangeleap/services/1.0")
    public void maintainGift(SaveOrUpdateGiftRequest request) {
        com.orangeleap.tangerine.domain.Constituent c = cs.readConstituentById(request.getConstituentId());
        com.orangeleap.tangerine.domain.paymentInfo.Gift g = giftService.createDefaultGift(c);


        ObjectConverter converter = new ObjectConverter();

        converter.ConvertFromJAXB(request.getGift(), g);

        try {
            giftService.maintainGift(g);
        } catch (BindException e) {
            logger.error(e.getMessage());
        }

    }

    @PayloadRoot(localPart = "GetConstituentGiftRequest", namespace = "http://www.orangeleap.com/orangeleap/services/1.0")
    public GetConstituentGiftResponse getConstituentsGifts(GetConstituentGiftRequest request) {
        List<com.orangeleap.tangerine.domain.paymentInfo.Gift> gifts = giftService.readMonetaryGifts(request.getConstituentId());

        GetConstituentGiftResponse response = new GetConstituentGiftResponse();
        ObjectConverter converter = new ObjectConverter();

        for (com.orangeleap.tangerine.domain.paymentInfo.Gift g : gifts) {
            Gift sg = new Gift();

            converter.ConvertToJAXB(g, sg);
            response.getGift().add(sg);
        }

        return response;
    }


    private GetSegmentationResponse getLapsedDonors() {
        List<com.orangeleap.tangerine.domain.Constituent> constituents = cs.readAllConstituentsBySite();
        GetSegmentationResponse response = new GetSegmentationResponse();
        ObjectConverter converter = new ObjectConverter();

        for (com.orangeleap.tangerine.domain.Constituent c : constituents) {
            if (c.isLapsedDonor()) {
                Constituent sc = new Constituent();

                converter.ConvertToJAXB(c, sc);
                response.getConstituent().add(sc);
            }
        }
        return response;
    }

    private GetSegmentationResponse getMajorDonors() {
        List<com.orangeleap.tangerine.domain.Constituent> constituents = cs.readAllConstituentsBySite();
        GetSegmentationResponse response = new GetSegmentationResponse();
        ObjectConverter converter = new ObjectConverter();

        for (com.orangeleap.tangerine.domain.Constituent c : constituents) {
            if (c.isMajorDonor()) {
                Constituent sc = new Constituent();

                converter.ConvertToJAXB(c, sc);
                response.getConstituent().add(sc);
            }
        }
        return response;
    }

    @PayloadRoot(localPart = "GetSegmentationRequest", namespace = "http://www.orangeleap.com/orangeleap/services/1.0")
    public GetSegmentationResponse getSegmentation(GetSegmentationRequest req) {

        if (req.getSegmentation().equals("Lapsed Donor"))
            return getLapsedDonors();
        if (req.getSegmentation().equals("Major Donor"))
            return getMajorDonors();

        return null;
    }

    @PayloadRoot(localPart = "GetSegmentationListRequest", namespace = "http://www.orangeleap.com/orangeleap/services/1.0")
    public GetSegmentationListResponse getSegmentationList(GetSegmentationListRequest req) {
        GetSegmentationListResponse response = new GetSegmentationListResponse();

        Segmentation segmentation = new Segmentation();
        segmentation.setId(0);
        segmentation.setName("Major Donor");
        segmentation.setDescription("All Major Donors");
        

        response.getSegmentation().add(segmentation);
        
        segmentation = new Segmentation();
        segmentation.setId(1);
        segmentation.setName("Lapsed Donor");
        segmentation.setDescription("All Lapsed Donors");
        
        response.getSegmentation().add(segmentation);

        return response;
    }

    @PayloadRoot(localPart = "AddCommunicationHistoryRequest", namespace = "http://www.orangeleap.com/orangeleap/services/1.0")
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

    @PayloadRoot(localPart = "GetCommunicationHistoryRequest", namespace = "http://www.orangeleap.com/orangeleap/services/1.0")
    public GetCommunicationHistoryResponse getCommunicationHistory(GetCommunicationHistoryRequest req) {
        SortInfo sortInfo = new SortInfo();
        sortInfo.setSort("ch.COMMUNICATION_HISTORY_ID");

        PaginatedResult result = communicationHistory.readCommunicationHistoryByConstituent(req.getConstituentId(), sortInfo);
        ObjectConverter converter = new ObjectConverter();
        List<com.orangeleap.tangerine.domain.CommunicationHistory> list = result.getRows();
        GetCommunicationHistoryResponse response = new GetCommunicationHistoryResponse();

        for (com.orangeleap.tangerine.domain.CommunicationHistory ch : list) {
            CommunicationHistory sch = new CommunicationHistory();

            converter.ConvertToJAXB(ch, sch);
            response.getCommunicationHistory().add(sch);


        }
        return response;
    }

}
