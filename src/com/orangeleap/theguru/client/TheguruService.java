
/*
 * 
 */

package com.orangeleap.theguru.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.3
 * Wed Nov 11 17:37:50 CST 2009
 * Generated source version: 2.2.3
 * 
 */


@WebServiceClient(name = "theguruService", 
                  wsdlLocation = "file:/Users/ldangelo/Development/Clementine/war/WEB-INF/theguru.wsdl",
                  targetNamespace = "http://www.orangeleap.com/theguru/services/1.0") 
public class TheguruService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://www.orangeleap.com/theguru/services/1.0", "theguruService");
    public final static QName TheguruPort = new QName("http://www.orangeleap.com/theguru/services/1.0", "theguruPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/Users/ldangelo/Development/Clementine/war/WEB-INF/theguru.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:/Users/ldangelo/Development/Clementine/war/WEB-INF/theguru.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public TheguruService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public TheguruService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TheguruService() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns Theguru
     */
    @WebEndpoint(name = "theguruPort")
    public Theguru getTheguruPort() {
        return super.getPort(TheguruPort, Theguru.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Theguru
     */
    @WebEndpoint(name = "theguruPort")
    public Theguru getTheguruPort(WebServiceFeature... features) {
        return super.getPort(TheguruPort, Theguru.class, features);
    }

}
