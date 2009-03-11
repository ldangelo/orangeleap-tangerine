package com.orangeleap.tangerine.domain.checkservice;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.custommonkey.xmlunit.Diff;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.io.StringWriter;

import com.orangeleap.tangerine.domain.checkservice.Batch;
import com.orangeleap.tangerine.domain.checkservice.Detail;
import com.orangeleap.tangerine.service.CheckService;
import com.orangeleap.tangerine.service.impl.CheckServiceImpl;

/**
 * @version 1.0
 */
public class BatchTest {

    private final static Date NOW = new Date();

    // Date format
    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");

    // Time format
    private final static DateFormat TIME_FORMAT = new SimpleDateFormat("kk:mm:ss");

    private final String BASIC_BATCH = "<Batch>" +
            "<Header>" +
            "<type>H</type>" +
            "<FileNbr>000002</FileNbr>" +
            "<CoName>Orange Leap</CoName>" +
            "<Date>" + DATE_FORMAT.format(new Date()) + "</Date>" +
            "<Site>853</Site>" +
            "<MID>11229</MID>" +
            "</Header>" +
            "<Detail>" +
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
            "</Detail>" +
            "<Footer>"+
            "<type>F</type>"+
            "<FileNbr>000002</FileNbr>"+
            "<CoName>Orange Leap</CoName>"+
            "<Date>" + DATE_FORMAT.format(new Date()) + "</Date>"+
            "<Site>853</Site>"+
            "<MID>11229</MID>"+
            "<RecCount>00001</RecCount>"+
            "</Footer>" +
            "</Batch>";


    private CheckService checkService;

    @Before
    public void setup() {

        CheckServiceImpl impl = new CheckServiceImpl();
        impl.setUrl("http://www.echex.net/wsv/batch102.cfc");
        checkService = impl;

    }

    @Test
    public void testSimpleBatch() throws Exception {

        Batch batch = checkService.createBatch(853,11229,5000,"Orange Leap");
        batch.isTest(false);
        batch.setFileNumber(2);
        Detail detail = new Detail();
        detail.setDateTime(NOW);
        detail.setCheckAmount(new BigDecimal("19.95"));
        detail.setAccountNumber("121000358");
        detail.setTransactionNumber(123456);
        batch.setDetail(detail);
        String xml = marshall(batch);
        Diff diff = new Diff(xml,BASIC_BATCH );
        Assert.assertTrue( diff.similar() );
    }

    @Test
    public void testBounds() {
        Batch batch = null;

        // ensure the correct exceptions are thrown for invalid values
        try {
            batch = checkService.createBatch(-1,11229,5000,"Orange Leap");
            Assert.fail("IllegalArgumentException expected, Site Number negative");
        } catch(IllegalArgumentException ignored) {}

        try {
            batch = checkService.createBatch(9999,11229,5000,"Orange Leap");
            Assert.fail("IllegalArgumentException expected, Site Number too large");
        } catch(IllegalArgumentException ignored) {}

        try {
            batch = checkService.createBatch(2,11229,5000,"Orange Leap");
            Assert.fail("IllegalArgumentException expected, Site Number too short");
        } catch(IllegalArgumentException ignored) {}

        try {
            batch = checkService.createBatch(852, -1,5000,"Orange Leap");
            Assert.fail("IllegalArgumentException expected, Merchant ID negative ");
        } catch(IllegalArgumentException ignored) {}

        try {
            batch = checkService.createBatch(852,1122909,5000,"Orange Leap");
            Assert.fail("IllegalArgumentException expected, Merchant ID too large ");
        } catch(IllegalArgumentException ignored) {}

        try {
            batch = checkService.createBatch(852,11,5000,"Orange Leap");
            Assert.fail("IllegalArgumentException expected, Merchant ID too short ");
        } catch(IllegalArgumentException ignored) {}

        try {
            batch = checkService.createBatch(852,11229,-5000,"Orange Leap");
            Assert.fail("IllegalArgumentException expected, Rule Number negative ");
        } catch(IllegalArgumentException ignored) {}

        try {
            batch = checkService.createBatch(852,11229,909090,"Orange Leap");
            Assert.fail("IllegalArgumentException expected, Rule Number too large");
        } catch(IllegalArgumentException ignored) {}

        try {
            batch = checkService.createBatch(852,11229,21,"Orange Leap");
            Assert.fail("IllegalArgumentException expected, Rule Number too short");
        } catch(IllegalArgumentException ignored) {}

        batch = checkService.createBatch(852,11229,5000,"Orange Leap");

        try {
            batch.setFileNumber(-1);
            Assert.fail("IllegalArgumentException expected, File Number is negative");
        } catch(IllegalArgumentException ignored) {}

        try {
            batch.setFileNumber(0);
            Assert.fail("IllegalArgumentException expected, File Number is zero");
        } catch(IllegalArgumentException ignored) {}

        try {
            batch.setFileNumber(99999999);    
            Assert.fail("IllegalArgumentException expected, File Number too long");
        } catch(IllegalArgumentException ignored) {}
    }



    private String marshall(Batch batch) throws Exception {

        StringWriter writer = new StringWriter();
        JAXBContext ctx = JAXBContext.newInstance("com.orangeleap.tangerine.domain.checkservice");
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty("jaxb.fragment", true);
        marshaller.marshal(batch, writer);
        return writer.toString();
    }




}
