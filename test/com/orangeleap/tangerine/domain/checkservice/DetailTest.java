package com.orangeleap.tangerine.domain.checkservice;

import org.custommonkey.xmlunit.XMLTestCase;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.io.StringWriter;

import com.orangeleap.tangerine.domain.checkservice.Detail;

/**
 * @version 1.0
 */
public class DetailTest extends XMLTestCase {

    // date to use for consistency
    private final static Date NOW = new Date();

    // Date format
    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");

    // Time format
    private final static DateFormat TIME_FORMAT = new SimpleDateFormat("kk:mm:ss");

    private final String SHORT_FORM = "<Detail>" +
            "<Date>" + DATE_FORMAT.format(NOW) + "</Date>" +
            "<Time>" + TIME_FORMAT.format(NOW) + "</Time>" +
            "<TXNNbr>123456</TXNNbr>" +
            "<SiteNbr>853</SiteNbr>" +
            "<MID>11229</MID>" +
            "<RuleNbr>5000</RuleNbr>" +
            "<GroupNbr></GroupNbr>" +
            "<checkAmt>19.95</checkAmt>" +
            "<CashBack>0.00</CashBack>" +
            "<VerResult>AUTH</VerResult>" +
            "<Account>121000358</Account>" +
            "<TXNCode>NF</TXNCode>" +
            "<CheckNbr></CheckNbr>" +
            "<DriverID></DriverID>" +
            "<LaneNbr></LaneNbr>" +
            "<Comments></Comments>" +
            "</Detail>";

    private final String LONG_FORM = "<Detail>" +
            "<Date>" + DATE_FORMAT.format(NOW) + "</Date>" +
            "<Time>" + TIME_FORMAT.format(NOW) + "</Time>" +
            "<TXNNbr>123456</TXNNbr>" +
            "<SiteNbr>853</SiteNbr>" +
            "<MID>11229</MID>" +
            "<RuleNbr>5000</RuleNbr>" +
            "<GroupNbr>10</GroupNbr>" +
            "<checkAmt>19.95</checkAmt>" +
            "<CashBack>4.50</CashBack>" +
            "<VerResult>AUTH</VerResult>" +
            "<Account>121000358</Account>" +
            "<TXNCode>NF</TXNCode>" +
            "<CheckNbr>101</CheckNbr>" +
            "<DriverID>DL=TX-123456789</DriverID>" +
            "<LaneNbr>11</LaneNbr>" +
            "<Comments>\"t102881978t 12345o 5561\"</Comments>" +
            "</Detail>";



    public void testShortMessage() throws Exception {

        Detail detail = new Detail();
        detail.setSiteNumber(853);
        detail.setRuleNumber(5000);
        detail.setMerchantId(11229);
        detail.setDateTime(NOW);
        detail.setAccountNumber("121000358");
        detail.setCheckAmount( new BigDecimal("19.95") );
        detail.setTransactionNumber(123456);

        String xml = marshall(detail);

        assertXMLEqual(xml, SHORT_FORM);
    }

    public void testLongMessage() throws Exception {

        Detail detail = new Detail();
        detail.setSiteNumber(853);
        detail.setRuleNumber(5000);
        detail.setMerchantId(11229);
        detail.setDateTime(NOW);
        detail.setAccountNumber("121000358");
        detail.setCheckAmount( new BigDecimal("19.95") );
        detail.setTransactionNumber(123456);

        //optional fields
        detail.setCheckNumber(101);
        detail.setDriverId("DL=TX-123456789");
        detail.setComments("t102881978t 12345o 5561");
        detail.setLaneNumber(11);
        detail.setGroupNumber(10);
        detail.setCashBack( new BigDecimal("4.50"));

        String xml = marshall(detail);
        assertXMLEqual(xml, LONG_FORM);
    }


    private String marshall(Detail detail) throws Exception {

        StringWriter writer = new StringWriter();
        JAXBContext ctx = JAXBContext.newInstance("com.orangeleap.tangerine.domain.checkservice");
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty("jaxb.fragment", true);
        marshaller.marshal(detail, writer);
        return writer.toString();
    }


}
