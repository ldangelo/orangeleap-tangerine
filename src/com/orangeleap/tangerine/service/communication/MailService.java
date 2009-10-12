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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.FileDataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceProperty;
import com.jaspersoft.jasperserver.irplugin.JServer;
import com.jaspersoft.jasperserver.irplugin.RepositoryReportUnit;
import com.jaspersoft.jasperserver.irplugin.wsclient.RequestAttachment;
import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.security.common.CasUtil;
import com.orangeleap.tangerine.service.CommunicationHistoryService;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.util.OLLogger;

//@Service("emailSendingService")
public class MailService {
	protected final Log logger = OLLogger.getLog(getClass());
	
    private String baseUri = null;
    private String repositoryUri = null;
	private String templateName = null;
	private String labelTemplateName = null;

	
//	@Autowired
	private ConstituentService constituentService;
	
	private CommunicationHistoryService communicationHistoryService;
	private java.util.Map map = new HashMap();
	private java.util.Map labelMap = new HashMap();
	private Site site;
	

	private File runReport() {
		File temp = null;
		JServer jserver = new JServer();
		jserver.setUsername(site.getJasperUserId());
		jserver.setPassword(site.getJasperPassword());
        jserver.setUrl(baseUri + repositoryUri);
		try {
			
        	CasUtil.populateJserverWithCasCredentials(jserver, baseUri);

			Map params = getReportParameters();

			JasperPrint print = jserver.getWSClient().runReport(
					getReportUnit(jserver).getDescriptor(), params);

			temp = File.createTempFile("orangeleap", ".pdf");
			temp.deleteOnExit();
			OutputStream out= new FileOutputStream(temp);
			JasperExportManager.exportReportToPdfStream(print,out);
			out.close();
			
		} catch (JRException e) {

			logger.error(e.getMessage() + " " + jserver.getUrl() + " " + site.getJasperUserId() + " " + site.getJasperPassword());
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage()+ " " + jserver.getUrl() + " " + site.getJasperUserId() + " " + site.getJasperPassword());
			return null;
		}
		return temp;
	}
	
	private File runLabels() {
		File temp = null;
		JServer jserver = new JServer();

        if (site.getJasperUserId() == null) {
            logger.error("Something went wrong.  Jasper userId is NULL!");
            return null;
        }
		jserver.setUsername(site.getJasperUserId());
		jserver.setPassword(site.getJasperPassword());
        jserver.setUrl(baseUri + repositoryUri);
		try {
			
        	CasUtil.populateJserverWithCasCredentials(jserver, baseUri);

			JasperPrint print = jserver.getWSClient().runReport(
					getLabelReportUnit(jserver).getDescriptor(), this.labelMap);

			temp = File.createTempFile("orangeleap", ".pdf");
			temp.deleteOnExit();
			OutputStream out= new FileOutputStream(temp);
			JasperExportManager.exportReportToPdfStream(print,out);
			out.close();
			
		} catch (JRException e) {

			logger.error(e.getMessage() + " " + jserver.getUrl() + " " + site.getJasperUserId() + " " + site.getJasperPassword());
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage() + " " + jserver.getUrl() + " " + site.getJasperUserId() + " " + site.getJasperPassword());
			return null;
		}
		return temp;
	}

	public void generateMail(List<Constituent> list, String lableTemplateName, String mailingTemplateName) {
		

		setTemplateName(mailingTemplateName);
		setLabelTemplateName(lableTemplateName);

		Constituent p = list.get(0);
		Site s = p.getSite();
		setSite(s);
		
		ArrayList<Long> ids = new ArrayList<Long>();
		
		for (Constituent constituent : list) {
			ids.add(constituent.getId());
		}
		//
		// first we run the report passing in the constituent.id as a parameter
		Map params = getReportParameters();
		params.clear();
		params.put("Ids", ids);
		
		this.labelMap.clear();
		this.labelMap.put("Ids", ids);
		
		File tempLabelFile = runLabels();
		File tempFile = runReport();

        if (tempLabelFile == null) {
            logger.error("Failed to generate Label File");
            return;
        }

        if (tempFile == null) {
            logger.error("Failed to generate report File.");
            return;
        }
		
        
		JServer jserver = new JServer();

		//
		// now put this report into the "Content files" directory of the repository
		ResourceDescriptor labelRD = new ResourceDescriptor();
		ResourceDescriptor reportRD = new ResourceDescriptor();
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			labelRD.setName(getLabelTemplateName() + dateFormat.format(date));
			labelRD.setLabel(labelRD.getName());
			labelRD.setDescription(labelRD.getName());
			labelRD.setParentFolder("/Reports/" + getSite().getName() + "/Content_files");
			labelRD.setUriString(labelRD.getParentFolder() + "/" + labelRD.getName());
			labelRD.setWsType(labelRD.TYPE_CONTENT_RESOURCE);
			labelRD.setResourceProperty(ResourceDescriptor.PROP_CONTENT_RESOURCE_TYPE,ResourceDescriptor.CONTENT_TYPE_PDF);
			labelRD.setIsNew(true);
			labelRD.setHasData(true);
			{
				RequestAttachment[] attachments;
				FileDataSource fileDataSource = new FileDataSource(tempLabelFile);
				RequestAttachment attachment = new RequestAttachment(fileDataSource);
				attachment.setContentID(labelRD.getName());
				attachments = new RequestAttachment[]{attachment};

				jserver = new JServer();
				jserver.setUsername(site.getJasperUserId());
				jserver.setPassword(site.getJasperPassword());
		        jserver.setUrl(baseUri + repositoryUri);
		        CasUtil.populateJserverWithCasCredentials(jserver, baseUri);

		        jserver.getWSClient().putResource(labelRD, attachments);
			}
			
			reportRD.setName(getTemplateName() + dateFormat.format(date));
			reportRD.setLabel(reportRD.getName());
			reportRD.setDescription(reportRD.getName());
			reportRD.setParentFolder("/Reports/" + getSite().getName() + "/Content_files");
			reportRD.setUriString(reportRD.getParentFolder() + "/" + reportRD.getName());
			reportRD.setWsType(reportRD.TYPE_CONTENT_RESOURCE);
			reportRD.setResourceProperty(ResourceDescriptor.PROP_CONTENT_RESOURCE_TYPE,ResourceDescriptor.CONTENT_TYPE_PDF);
			reportRD.setIsNew(true);
			reportRD.setHasData(true);
			
			{
				RequestAttachment[] attachments;
				FileDataSource fileDataSource = new FileDataSource(tempFile);
				RequestAttachment attachment = new RequestAttachment(fileDataSource);
				attachment.setContentID(reportRD.getName());
				attachments = new RequestAttachment[]{attachment};

				jserver = new JServer();
				jserver.setUsername(site.getJasperUserId());
				jserver.setPassword(site.getJasperPassword());
		        jserver.setUrl(baseUri + repositoryUri);
		        CasUtil.populateJserverWithCasCredentials(jserver, baseUri);
				
		        jserver.getWSClient().putResource(reportRD, attachments);
			}

            for (Constituent constituent : list) {
            //
            // Add touchpoint for this constituent so rule will not fire again...
            CommunicationHistory ch = new CommunicationHistory();
            ch.setConstituent(constituent);
            ch.setSystemGenerated(true);
            ch.setComments("Generated mailing using template named " + getTemplateName());
            ch.setEntryType("Mail");
            ch.setRecordDate(new Date());
            ch.setCustomFieldValue("template", getTemplateName());
            ch.setAddress(constituent.getPrimaryAddress());

            ch.setSuppressValidation(true);
            try {
                communicationHistoryService.maintainCommunicationHistory(ch);
            } catch (BindException e1) {
                // Should not happen when setSuppressValidation = true;
                logger.error(e1);
            }
            }
			tempLabelFile.delete();
			tempFile.delete();
		} catch (Exception ex) {
			logger.error(ex.getMessage() + " " + jserver.getUrl() + " " + site.getJasperUserId() + " " + site.getJasperPassword());
		}

	}
	
	private void setLabelTemplateName(String templateName) {
		this.labelTemplateName = templateName;
	}
	
	private String getLabelTemplateName() {
		return this.labelTemplateName;
	}

	private void setTemplateName(String templateName) {
		this.templateName = templateName;
		
	}
	
	private String getTemplateName()
	{
		return this.templateName;
	}

	private Map getReportParameters() {

		return map;
	}

	private RepositoryReportUnit getReportUnit(JServer jserver) 
	{
			ResourceDescriptor rd = new ResourceDescriptor();
			
			rd.setName(this.getTemplateName());
			rd.setParentFolder("/Reports/" + getSite().getName() + "/mailTemplates");
			rd.setUriString(rd.getParentFolder() + "/" + rd.getName());
			rd.setWsType(ResourceDescriptor.TYPE_REPORTUNIT);
			List<ResourceProperty> p = new ArrayList<ResourceProperty>();
			
			Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry) it.next();
			
				ResourceProperty rp = new ResourceProperty(me.getKey().toString(),me.getValue().toString());
				p.add(rp);
			}
			
			rd.setParameters(p);
			
			return new RepositoryReportUnit(jserver,rd);
	}

	private RepositoryReportUnit getLabelReportUnit(JServer jserver) 
	{
			ResourceDescriptor rd = new ResourceDescriptor();
			
			rd.setName(this.getLabelTemplateName());
			rd.setParentFolder("/Reports/" + getSite().getName() + "/mailTemplates");
			rd.setUriString(rd.getParentFolder() + "/" + rd.getName());
			rd.setWsType(ResourceDescriptor.TYPE_REPORTUNIT);
			List<ResourceProperty> p = new ArrayList<ResourceProperty>();
			


			return new RepositoryReportUnit(jserver,rd);
	}

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String uri) {
        this.baseUri = uri;
    }

    public String getRepositoryUri() {
        return repositoryUri;
    }

    public void setRepositoryUri(String uri) {
        this.repositoryUri = uri;
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


	public CommunicationHistoryService getCommunicationHistoryService() {
		return communicationHistoryService;
	}

	public void setCommunicationHistoryService(
			CommunicationHistoryService communicationHistoryService) {
		this.communicationHistoryService = communicationHistoryService;
	}
}
