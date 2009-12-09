package com.orangeleap.tangerine.service.segmentation;
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


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;

import com.orangeleap.tangerine.domain.Segmentation;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.theguru.client.ExecuteSegmentationByIdRequest;
import com.orangeleap.theguru.client.ExecuteSegmentationByIdResponse;
import com.orangeleap.theguru.client.ExecuteSegmentationByNameRequest;
import com.orangeleap.theguru.client.ExecuteSegmentationByNameResponse;
import com.orangeleap.theguru.client.GetSegmentationByIdRequest;
import com.orangeleap.theguru.client.GetSegmentationByIdResponse;
import com.orangeleap.theguru.client.GetSegmentationByNameRequest;
import com.orangeleap.theguru.client.GetSegmentationByNameResponse;
import com.orangeleap.theguru.client.GetSegmentationListByTypeRequest;
import com.orangeleap.theguru.client.GetSegmentationListByTypeResponse;
import com.orangeleap.theguru.client.ObjectFactory;
import com.orangeleap.theguru.client.Theguru;
import com.orangeleap.theguru.client.WSClient;

//@Service("segmentationService")
public class SegmentationService  {
	protected final Log logger = OLLogger.getLog(getClass());

    private String baseUri = null;
    private String repositoryUri = null;

    private ObjectFactory objFactory = null;

    public SegmentationService() {
    	objFactory = new ObjectFactory();
}

    /**
     * Returns true if there is a segmentation with the provided Id.
     * @param segmentationId
     * @return
     */
    public Boolean segmentationExists(Long segmentationId){
    	if (this.getSegmentationById(segmentationId) != null)
    		return true;
    	else
    		return false;
    }


    /**
     * Returns a segmentation.
     * @param segmentationId
     * @return
     */
    public GetSegmentationByIdResponse getSegmentationById(Long segmentationId){
    	Theguru theGuru = new WSClient().getTheGuru();
    	GetSegmentationByIdRequest req = objFactory.createGetSegmentationByIdRequest();
        req.setId(segmentationId);
        GetSegmentationByIdResponse resp = theGuru.getSegmentationById(req);
		return resp;
    }

    /**
     * Executes a segmentation by Id and returns the segmentation in the response.
     * @param segmentationId
     * @return
     */
    public ExecuteSegmentationByIdResponse executeSegmentationById(Long segmentationId){
    	Theguru theGuru = new WSClient().getTheGuru();
        ExecuteSegmentationByIdRequest req = objFactory.createExecuteSegmentationByIdRequest();
        req.setId(segmentationId);
        ExecuteSegmentationByIdResponse resp = theGuru.executeSegmentationById(req);
		return resp;
    }



    /**
     * Executes a segmentation by name and returns the segmentation in the response.
     * @param segmentationName
     * @return
     */
    public ExecuteSegmentationByNameResponse executeSegmentationByName(String segmentationName){

    	Theguru theGuru = new WSClient().getTheGuru();

        ExecuteSegmentationByNameRequest req = objFactory.createExecuteSegmentationByNameRequest();
        req.setName(segmentationName);
        ExecuteSegmentationByNameResponse resp = theGuru.executeSegmentationByName(req);
		return resp;

    }


	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}


	public String getBaseUri() {
		return baseUri;
	}


	public void setRepositoryUri(String repositoryUri) {
		this.repositoryUri = repositoryUri;
	}


	public String getRepositoryUri() {
		return repositoryUri;
	}




}
