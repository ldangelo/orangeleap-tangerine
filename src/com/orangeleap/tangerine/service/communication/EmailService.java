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

package com.orangeleap.tangerine.service.communication;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceProperty;
import com.jaspersoft.jasperserver.irplugin.JServer;
import com.jaspersoft.jasperserver.irplugin.RepositoryReportUnit;
import com.jaspersoft.jasperserver.irplugin.wsclient.WSClient;
import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.CommunicationHistoryService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.BindException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

//@Service("emailSendingService")
public class EmailService implements ApplicationContextAware {
    protected final Log logger = OLLogger.getLog(getClass());
    private JServer jserver = null;
    private String userName = null;
    private String password = null;
    private String uri = null;
    private String templateName = null;
    private String subject = null;
    private CommunicationHistoryService communicationHistoryService;
    private java.util.Map map = new HashMap();
    private Site site;
    private ApplicationContext applicationContext;
    private JasperPrint print;

    private File runReport() {
        File temp = null;
        TangerineUserHelper tuh = (TangerineUserHelper) applicationContext.getBean("tangerineUserHelper");
        jserver = new JServer();
//		jserver.setUsername(tuh.lookupUserName() + "@" + site.getName());
        jserver.setUsername(site.getJasperUserId());
//		jserver.setPassword(tuh.lookupUserPassword());
        jserver.setPassword(site.getJasperPassword());
        jserver.setUrl(uri);
        try {
            WSClient client = jserver.getWSClient();

            Map params = getReportParameters();

            print = getServer().getWSClient().runReport(
                    getReportUnit().getDescriptor(), params);

            temp = File.createTempFile("orangeleap", ".pdf");
            temp.deleteOnExit();
            OutputStream out = new FileOutputStream(temp);
            JasperExportManager.exportReportToPdfStream(print, out);
            out.close();

        } catch (JRException e) {

            logger.error(e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        return temp;
    }

    @SuppressWarnings("unchecked")
    private void setParameters(Constituent p, Gift g) {
        Class c = p.getClass();
        Field[] fields = c.getDeclaredFields();
        for (Field f : fields) {
            try {
                map.put(f.getName(), f.get(p).toString());
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage());
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
            }
        }

        c = g.getClass();
        fields = c.getDeclaredFields();
        for (Field f : fields) {
            try {
                map.put(f.getName(), f.get(p).toString());
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage());
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public void sendMail(String addresses, Constituent p, Gift g, String subject, String templateName) {
        setSubject(subject);
        setTemplateName(templateName);

        Site s = g.getSite();
        setSite(s);

        Map params = getReportParameters();
        params.clear();


        params.put("Id", p.getId().toString());
        params.put("GiftId", g.getId().toString());
        params.put("GiftAmount", g.getAmount().toString());


        //
        // next we extract the output of the report and put it into a mime
        // message
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(site.getSmtpServerName());

        if (site.getSmtpAccountName() != null)
            sender.setUsername(site.getSmtpAccountName());
        if (site.getSmtpPassword() != null)
            sender.setPassword(site.getSmtpPassword());

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
        } catch (MessagingException e2) {
            logger.error(e2.getMessage());
            return;
        }

        File tempFile = runReport();

        if (tempFile != null){
        	FileSystemResource file = new FileSystemResource(tempFile);
            try {
                helper.addAttachment(getTemplateName() + ".pdf", file);
                helper.setText("Thank you for your recent donation!");
                helper.setSubject(subject);
                helper.setFrom(site.getSmtpFromAddress());

                String emailAddresses[] = addresses.split(",");
                for (int i = 0; i < emailAddresses.length; i++)
                    helper.addTo(emailAddresses[i]);

                //
                // finally we mail the message
                sender.send(message);


                tempFile.delete();
            } catch (MessagingException e1) {
                logger.error(e1.getMessage());
                return;
            }

        }else
        	logger.error("Failed to generate report.");


    }

    public void sendMail(Constituent p, Gift g, String subject, String templateName) {
        String strEmailAddrs = "";
        List<Email> selectedEmails = new LinkedList<Email>();
        setSubject(subject);
        setTemplateName(templateName);

        Site s = p.getSite();
        setSite(s);


        //
        // first we run the report passing in the constituent.id as a parameter
        List<Email> emailAddresses = p.getEmails();

        for (Email e : emailAddresses) {
            if (e.isReceiveCorrespondence() && !e.isInactive() && e.isValid()) {
                selectedEmails.add(e);
                strEmailAddrs += e.getEmailAddress() + ",";
            }
        }

        // remove the trailing ,
        strEmailAddrs = strEmailAddrs.substring(0, strEmailAddrs.lastIndexOf(","));

        //
        // no e-mail addresses can receive mail
        if (selectedEmails.size() == 0) {
            return;
        }

        this.sendMail(strEmailAddrs, p, g, subject, templateName);

        //
        // add entry to touchpoints for this e-mail
        for (Email e : selectedEmails) {
            CommunicationHistory ch = new CommunicationHistory();
            ch.setConstituent(p);
            ch.setGiftId(g.getId());
            ch.setSystemGenerated(true);
            ch.setComments("Sent e-mail using template named " + getTemplateName());
            ch.setEntryType("Email");
            ch.setRecordDate(new Date());
            ch.setSelectedEmail(e);
            ch.setCustomFieldValue("template", getTemplateName());

            ch.setSuppressValidation(true);
            try {
                communicationHistoryService.maintainCommunicationHistory(ch);
            } catch (BindException e1) {
                // Should not happen when setSuppressValidation = true;
                logger.error(e1);
            }

        }
    }

    private void setTemplateName(String templateName) {
        this.templateName = templateName;

    }

    private String getTemplateName() {
        return this.templateName;
    }

    private Map getReportParameters() {

        return map;
    }

    private RepositoryReportUnit getReportUnit() {
        ResourceDescriptor rd = new ResourceDescriptor();

        rd.setName(this.getTemplateName());
        rd.setParentFolder("/Reports/" + getSite().getName() + "/emailTemplates");
        rd.setUriString(rd.getParentFolder() + "/" + rd.getName());
        rd.setWsType(ResourceDescriptor.TYPE_REPORTUNIT);
        List<ResourceProperty> p = new ArrayList<ResourceProperty>();

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();

            ResourceProperty rp = new ResourceProperty(me.getKey().toString(), me.getValue().toString());
            p.add(rp);
        }

        rd.setParameters(p);

        return new RepositoryReportUnit(getServer(), rd);
    }

    private JServer getServer() {
        return jserver;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Log getLogger() {
        return logger;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public CommunicationHistoryService getCommunicationHistoryService() {
        return communicationHistoryService;
    }

    public void setCommunicationHistoryService(
            CommunicationHistoryService communicationHistoryService) {
        this.communicationHistoryService = communicationHistoryService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;

    }
}
