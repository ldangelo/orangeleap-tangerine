package com.mpower.domain.checkservice;

import org.custommonkey.xmlunit.XMLTestCase;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.StringWriter;

import com.mpower.domain.checkservice.Footer;

/**
 * @version 1.0
 */
public class FooterTest extends XMLTestCase {

    private final static DateFormat FORMAT = new SimpleDateFormat("MM/dd/yy");

    private final static String FOOTER = "<Footer>"+
            "<type>F</type>"+
            "<FileNbr>000002</FileNbr>"+
            "<CoName>Orange Leap</CoName>"+
            "<Date>" + FORMAT.format(new Date()) + "</Date>"+
            "<Site>853</Site>"+
            "<MID>11229</MID>"+
            "<RecCount>00001</RecCount>"+
            "</Footer>";



    @SuppressWarnings("unchecked")
    public void testFooterContent() throws Exception {

        Footer footer = new Footer();
        footer.setFileNumber(2);
        footer.setCompanyName("Orange Leap");
        footer.setDate(new Date());
        footer.setSiteNumber(853);
        footer.setMerchantId(11229);
        footer.addRecord();

        String xml = marshall(footer);
        assertXMLEqual(xml, FOOTER);

    }


    private String marshall(Footer footer) throws Exception {

        StringWriter writer = new StringWriter();
        JAXBContext ctx = JAXBContext.newInstance("com.mpower.domain.checkservice");
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty("jaxb.fragment", true);
        marshaller.marshal(footer, writer);
        return writer.toString();
    }



}
