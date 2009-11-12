package com.orangeleap.theguru.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.2.3
 * Wed Nov 11 17:37:50 CST 2009
 * Generated source version: 2.2.3
 * 
 */
 
@WebService(targetNamespace = "http://www.orangeleap.com/theguru/services/1.0", name = "theguru")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface Theguru {

    @WebResult(name = "GetSegmentationByIdResponse", targetNamespace = "http://www.orangeleap.com/theguru/services/1.0", partName = "GetSegmentationByIdResponse")
    @WebMethod(operationName = "GetSegmentationById")
    public GetSegmentationByIdResponse getSegmentationById(
        @WebParam(partName = "GetSegmentationByIdRequest", name = "GetSegmentationByIdRequest", targetNamespace = "http://www.orangeleap.com/theguru/services/1.0")
        GetSegmentationByIdRequest getSegmentationByIdRequest
    );

    @WebResult(name = "GetSegmentationListByTypeResponse", targetNamespace = "http://www.orangeleap.com/theguru/services/1.0", partName = "GetSegmentationListByTypeResponse")
    @WebMethod(operationName = "GetSegmentationListByType")
    public GetSegmentationListByTypeResponse getSegmentationListByType(
        @WebParam(partName = "GetSegmentationListByTypeRequest", name = "GetSegmentationListByTypeRequest", targetNamespace = "http://www.orangeleap.com/theguru/services/1.0")
        GetSegmentationListByTypeRequest getSegmentationListByTypeRequest
    );

    @WebResult(name = "GetSegmentationByNameResponse", targetNamespace = "http://www.orangeleap.com/theguru/services/1.0", partName = "GetSegmentationByNameResponse")
    @WebMethod(operationName = "GetSegmentationByName")
    public GetSegmentationByNameResponse getSegmentationByName(
        @WebParam(partName = "GetSegmentationByNameRequest", name = "GetSegmentationByNameRequest", targetNamespace = "http://www.orangeleap.com/theguru/services/1.0")
        GetSegmentationByNameRequest getSegmentationByNameRequest
    );

    @WebResult(name = "GetSegmentationListResponse", targetNamespace = "http://www.orangeleap.com/theguru/services/1.0", partName = "GetSegmentationListResponse")
    @WebMethod(operationName = "GetSegmentationList")
    public GetSegmentationListResponse getSegmentationList(
        @WebParam(partName = "GetSegmentationListRequest", name = "GetSegmentationListRequest", targetNamespace = "http://www.orangeleap.com/theguru/services/1.0")
        GetSegmentationListRequest getSegmentationListRequest
    );

    @WebResult(name = "ExecuteSegmentationByIdResponse", targetNamespace = "http://www.orangeleap.com/theguru/services/1.0", partName = "ExecuteSegmentationByIdResponse")
    @WebMethod(operationName = "ExecuteSegmentationById")
    public ExecuteSegmentationByIdResponse executeSegmentationById(
        @WebParam(partName = "ExecuteSegmentationByIdRequest", name = "ExecuteSegmentationByIdRequest", targetNamespace = "http://www.orangeleap.com/theguru/services/1.0")
        ExecuteSegmentationByIdRequest executeSegmentationByIdRequest
    );

    @WebResult(name = "ExecuteSegmentationByNameResponse", targetNamespace = "http://www.orangeleap.com/theguru/services/1.0", partName = "ExecuteSegmentationByNameResponse")
    @WebMethod(operationName = "ExecuteSegmentationByName")
    public ExecuteSegmentationByNameResponse executeSegmentationByName(
        @WebParam(partName = "ExecuteSegmentationByNameRequest", name = "ExecuteSegmentationByNameRequest", targetNamespace = "http://www.orangeleap.com/theguru/services/1.0")
        ExecuteSegmentationByNameRequest executeSegmentationByNameRequest
    );
}
