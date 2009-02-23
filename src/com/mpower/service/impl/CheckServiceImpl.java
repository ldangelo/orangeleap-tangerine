package com.mpower.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Required;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import static com.mpower.domain.checkservice.EchexTemplate.*;
import com.mpower.domain.checkservice.Response;
import com.mpower.service.CheckService;
import com.mpower.domain.checkservice.Batch;
import com.mpower.domain.checkservice.PaymentProcessorException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * @version 1.0
 */
@Service("checkService")
public class CheckServiceImpl implements CheckService {

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
    @Required
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
            JAXBContext ctx = JAXBContext.newInstance("com.mpower.domain.checkservice");
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", true);
            marshaller.setProperty("jaxb.fragment", true);
            marshaller.marshal(batch, writer);
            writer.write(FOOTER);

            Document doc = postString(writer.toString());
            Element root = doc.getRootElement();
            Element loadReturn = root.element("Body").element("DataLoadResponse").element("DataLoadReturn");
            SAXReader reader = new SAXReader();
            doc = reader.read(new StringReader(loadReturn.getText()));
            root = doc.getRootElement();

            Element node = root.element("detail");

            String auth = node.elementText("Auth");
            int authNumber = Integer.parseInt(auth);
            String msg = node.elementText("Message").trim();
            String txn = node.elementText("TXNNBR");
            response = new Response(authNumber, txn, msg);

            return response;

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
