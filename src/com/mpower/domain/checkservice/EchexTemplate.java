package com.mpower.domain.checkservice;

/**
 * This is a cheap interface used to hold the string literals
 * needed for the header and footer of the paperless transaction
 * web service. Paperless transaction uses an ancient Cold Fusion
 * web service with RPC encoding, which none of the modern web
 * service libraries support, so we're cheating and just POSTing
 * to them.
 * @version 1.0
 */
public interface EchexTemplate {

    public final static String HEADER =
            "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsv=\"http://wsv\">\n" +
            "  <soapenv:Header/>\n" +
            "  <soapenv:Body>\n" +
            "    <wsv:DataLoad soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
            "      <DataStream xsi:type=\"xsd:anyType\">";

    public final static String FOOTER =
            "      </DataStream>\n" +
            "    </wsv:DataLoad>\n" +
            "  </soapenv:Body>\n" +
            "</soapenv:Envelope>";


}
