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

package com.orangeleap.tangerine.service.impl;

import org.springframework.stereotype.Service;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;

import static com.orangeleap.tangerine.domain.checkservice.EchexTemplate.*;

import com.orangeleap.tangerine.domain.checkservice.Batch;
import com.orangeleap.tangerine.domain.checkservice.PaymentProcessorException;
import com.orangeleap.tangerine.domain.checkservice.Response;
import com.orangeleap.tangerine.service.CheckService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * Implemenation of the CheckService for sending to Paperpless Payments
 * via the antiquated web service.
 * @version 1.0
 */
@Service("checkService")
public class CheckServiceImpl extends AbstractTangerineService implements CheckService {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    private URL echexUrl;


    public CheckServiceImpl() {

    }

    /**
     * Sets the URL the transactions are posted to. The normal
     * URL for the Paperless Payments web service is:<br/>
     * <code>http://www.echex.net/wsv/batch102.cfc</code>
     *
     * @param echexUrl the web service URL
     */
    public void setUrl(String echexUrl) {
        try {
            this.echexUrl = new URL(echexUrl);
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException(echexUrl + " is not a valid URL", ex);
        }
    }

    public Batch createBatch(int siteNumber, int merchantId, int ruleNumber, String companyName) {
        Batch batch = new Batch();
        batch.setSiteNumber(siteNumber);
        batch.setMerchantId(merchantId);
        batch.setRuleNumber(ruleNumber);
        batch.setCompanyName(companyName);
        return batch;
    }

    /**
     * Sends a Batch to Paperless Payments using the (hacked) web service
     * API. If Batch is null, or the Batch does not contain at least one
     * Detail object, this method will throw an IllegalArgumentException.
     *
     * @param batch the batch to send
     * @return the List of confirmations as Reponse objects
     */
    @SuppressWarnings("unchecked")
    public Response sendBatch(Batch batch) {

        if (batch == null) {
            throw new IllegalArgumentException("Batch cannot be null");
        } else if (batch.getDetail() == null) {
            throw new IllegalArgumentException("Batch must contain a Detail");
        }

        Writer writer = new StringWriter();
        Response response = null;

        try {
            writer.write(HEADER);
            JAXBContext ctx = JAXBContext.newInstance("com.orangeleap.tangerine.domain.checkservice");
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", true);
            marshaller.setProperty("jaxb.fragment", true);
            marshaller.marshal(batch, writer);
            writer.write(FOOTER);

            String payload = writer.toString();
            logger.debug("SENDING---->\r\n" + payload);
            Document doc = postString(payload);
            Element root = doc.getRootElement();
            Element loadReturn = root.element("Body").element("DataLoadResponse").element("DataLoadReturn");
            String responseText = loadReturn.getText();

            // ensure we have a nested XML document
            if (responseText.toUpperCase().indexOf("<BATCH") > -1) {
                SAXReader reader = new SAXReader();
                doc = reader.read(new StringReader(responseText));
                root = doc.getRootElement();

                Element node = root.element("detail");

                String auth = node.elementText("Auth");
                int authNumber = Integer.parseInt(auth);
                String msg = node.elementText("Message").trim();
                String txn = node.elementText("TXNNBR");
                response = new Response(authNumber, txn, msg);

                return response;

            } else {
                // we have a raw String in the SOAP response, which means a error, so
                // grab the text and pitch it as an error
                throw new PaymentProcessorException(responseText);
            }

        } catch (JAXBException ex) {
            throw new PaymentProcessorException("Could not marshall Batch to XML", ex);
        } catch (IOException io) {
            // can't happen with StringWriter, but catch to make Writer happy
            throw new PaymentProcessorException("Failed to write to StringWriter", io);
        } catch (DocumentException doc) {
            throw new PaymentProcessorException("Error parsing Payment Processor Results", doc);
        }

    }

    /**
     * Helper method to POST the XML version of the Batch object to the
     * Paperless Payment web service using an HttpURLConnection. This method
     * does not implement any retry logic but it will wrap checkid IOExceptions
     * with the unchecked PaymentProcessorException.
     *
     * @param data the Batch as XML
     * @return the DOM4J Document containing the response
     */
    Document postString(String data) {
        OutputStream outStream = null;
        InputStream inStream = null;
        Document doc = null;

        try {
            HttpURLConnection conn = (HttpURLConnection) echexUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml");
            conn.setRequestProperty("Content-Length", "" + data.length());
            conn.setRequestProperty("SOAPAction", "");
            conn.setDoOutput(true);
            conn.connect();
            outStream = conn.getOutputStream();
            OutputStreamWriter outWriter = new OutputStreamWriter(outStream, "UTF-8");
            outWriter.write(data);
            outWriter.flush();

            inStream = conn.getInputStream();
            SAXReader reader = new SAXReader();
            doc = reader.read(inStream);
            logger.debug("RECEIVED<------\r\n" + doc.asXML());
            
        } catch (Exception ex) {
            throw new PaymentProcessorException("Failed to send payload", ex);
        } finally {
            try {
                outStream.close();
            } catch (Exception ignored) {
            }
            try {
                inStream.close();
            } catch (Exception ignored) {
            }
        }

        return doc;

    }

}
