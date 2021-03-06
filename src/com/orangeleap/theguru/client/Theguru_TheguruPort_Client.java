
package com.orangeleap.theguru.client;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.2.3
 * Mon Dec 14 11:22:04 CST 2009
 * Generated source version: 2.2.3
 * 
 */

public final class Theguru_TheguruPort_Client {

    private static final QName SERVICE_NAME = new QName("http://www.orangeleap.com/theguru/services/1.0", "theguruService");

    private Theguru_TheguruPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = TheguruService.WSDL_LOCATION;
        if (args.length > 0) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        TheguruService ss = new TheguruService(wsdlURL, SERVICE_NAME);
        Theguru port = ss.getTheguruPort();  
        
        {
        System.out.println("Invoking getSegmentationById...");
        com.orangeleap.theguru.client.GetSegmentationByIdRequest _getSegmentationById_getSegmentationByIdRequest = null;
        com.orangeleap.theguru.client.GetSegmentationByIdResponse _getSegmentationById__return = port.getSegmentationById(_getSegmentationById_getSegmentationByIdRequest);
        System.out.println("getSegmentationById.result=" + _getSegmentationById__return);


        }
        {
        System.out.println("Invoking getSegmentationListByType...");
        com.orangeleap.theguru.client.GetSegmentationListByTypeRequest _getSegmentationListByType_getSegmentationListByTypeRequest = null;
        com.orangeleap.theguru.client.GetSegmentationListByTypeResponse _getSegmentationListByType__return = port.getSegmentationListByType(_getSegmentationListByType_getSegmentationListByTypeRequest);
        System.out.println("getSegmentationListByType.result=" + _getSegmentationListByType__return);


        }
        {
        System.out.println("Invoking getSegmentationByName...");
        com.orangeleap.theguru.client.GetSegmentationByNameRequest _getSegmentationByName_getSegmentationByNameRequest = null;
        com.orangeleap.theguru.client.GetSegmentationByNameResponse _getSegmentationByName__return = port.getSegmentationByName(_getSegmentationByName_getSegmentationByNameRequest);
        System.out.println("getSegmentationByName.result=" + _getSegmentationByName__return);


        }
        {
        System.out.println("Invoking getSegmentationList...");
        com.orangeleap.theguru.client.GetSegmentationListRequest _getSegmentationList_getSegmentationListRequest = null;
        com.orangeleap.theguru.client.GetSegmentationListResponse _getSegmentationList__return = port.getSegmentationList(_getSegmentationList_getSegmentationListRequest);
        System.out.println("getSegmentationList.result=" + _getSegmentationList__return);


        }
        {
        System.out.println("Invoking getSegmentationCountByType...");
        com.orangeleap.theguru.client.GetSegmentationCountByTypeRequest _getSegmentationCountByType_getSegmentationCountByTypeRequest = null;
        com.orangeleap.theguru.client.GetSegmentationCountByTypeResponse _getSegmentationCountByType__return = port.getSegmentationCountByType(_getSegmentationCountByType_getSegmentationCountByTypeRequest);
        System.out.println("getSegmentationCountByType.result=" + _getSegmentationCountByType__return);


        }
        {
        System.out.println("Invoking executeSegmentationById...");
        com.orangeleap.theguru.client.ExecuteSegmentationByIdRequest _executeSegmentationById_executeSegmentationByIdRequest = null;
        com.orangeleap.theguru.client.ExecuteSegmentationByIdResponse _executeSegmentationById__return = port.executeSegmentationById(_executeSegmentationById_executeSegmentationByIdRequest);
        System.out.println("executeSegmentationById.result=" + _executeSegmentationById__return);


        }
        {
        System.out.println("Invoking executeSegmentationByName...");
        com.orangeleap.theguru.client.ExecuteSegmentationByNameRequest _executeSegmentationByName_executeSegmentationByNameRequest = null;
        com.orangeleap.theguru.client.ExecuteSegmentationByNameResponse _executeSegmentationByName__return = port.executeSegmentationByName(_executeSegmentationByName_executeSegmentationByNameRequest);
        System.out.println("executeSegmentationByName.result=" + _executeSegmentationByName__return);


        }

        System.exit(0);
    }

}
