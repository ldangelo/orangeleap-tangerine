package com.mpower.controller.importexport;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import au.com.bytecode.opencsv.CSVWriter;

import com.mpower.controller.importexport.exporters.EntityExporter;
import com.mpower.controller.importexport.exporters.EntityExporterFactory;
import com.mpower.service.SiteService;

public class CsvExportController extends SimpleFormController {

    protected final Log logger = LogFactory.getLog(getClass());

	
	private SiteService siteService;

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}


	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
        // TODO check rights?
		
		String entity = request.getParameter("entity");
		String exportData = getExport(entity);

		response.setContentType("application/x-download"); 
		response.setHeader("Content-Disposition", "attachment; filename=" + entity + "-export.csv");
		response.setContentLength(exportData.length());
		PrintWriter out = response.getWriter();
		out.print(exportData);
		out.flush();

		return null;

	}

	private String getExport(String entity) {
		
		StringWriter sw = new StringWriter();
		CSVWriter writer = new CSVWriter(sw);
		
		ApplicationContext applicationContext = getApplicationContext();

		EntityExporter ex = new EntityExporterFactory().getEntityExporter(entity, applicationContext);
		
		List<String[]> data = ex.exportAll();

		writer.writeAll(data);

		return sw.toString();
	}

}
