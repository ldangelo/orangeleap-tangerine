package com.orangeleap.tangerine.domain.checkservice;

import org.custommonkey.xmlunit.XMLTestCase;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.util.Date;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.orangeleap.tangerine.domain.checkservice.Header;

/**
 * @version 1.0
 */
public class HeaderTest extends XMLTestCase {

    private final static DateFormat FORMAT = new SimpleDateFormat("MM/dd/yy");

    private final String HEADER = "<Header>" +
            "<type>TEST</type>" +
            "<FileNbr>000002</FileNbr>" +
            "<CoName>Orange Leap</CoName>" +
            "<Date>" + FORMAT.format(new Date()) + "</Date>" +
            "<Site>853</Site>" +
            "<MID>11229</MID>" +
            "</Header>";


    @SuppressWarnings("unchecked")
    public void testHeaderContent() throws Exception {

        Header header = new Header();
        header.setType("TEST");
        header.setFileNumber(2);
        header.setCompanyName("Orange Leap");
        header.setDate(new Date());
        header.setSiteNumber(853);
        header.setMerchantId(11229);

        String xml = marshall(header);
        assertXMLEqual(xml, HEADER);
        
    }


    private String marshall(Header header) throws Exception {

        StringWriter writer = new StringWriter();
        JAXBContext ctx = JAXBContext.newInstance("com.orangeleap.tangerine.domain.checkservice");
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty("jaxb.fragment", true);
        marshaller.marshal(header, writer);
        return writer.toString();
    }


}
