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

public class CsvExportController extends SimpleFormController {

    protected final Log logger = LogFactory.getLog(getClass());

	@Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if (!CsvImportController.importexportAllowed(request)) {
            return null;  // For security only, unauthorized users will not have the menu option to even get here normally.
        }
		
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
		
		List<List<String>> data = ex.exportAll();
		List<String[]> csvdata = new ArrayList<String[]>();
		for (List<String> line:data) {
			String[] aline = line.toArray(new String[line.size()]);
			csvdata.add(aline);
		}

		writer.writeAll(csvdata);

		return sw.toString();
	}

}
