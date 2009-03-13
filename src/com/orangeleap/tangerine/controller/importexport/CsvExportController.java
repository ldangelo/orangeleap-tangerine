package com.orangeleap.tangerine.controller.importexport;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import au.com.bytecode.opencsv.CSVWriter;

import com.orangeleap.tangerine.controller.importexport.exporters.EntityExporter;
import com.orangeleap.tangerine.controller.importexport.exporters.EntityExporterFactory;

@SuppressWarnings("deprecation")
public class CsvExportController extends SimpleFormController {

    protected final Log logger = LogFactory.getLog(getClass());
    
    
    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true)); // TODO: custom date format
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        return new ExportRequest();
    }
    
    private final Date FUTURE_DATE = new Date("1/1/2100");
    private final Date PAST_DATE = new Date("1/1/1900");

	@Override
	protected ModelAndView processFormSubmission(
			HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		ExportRequest er = (ExportRequest)command;
		if (er.getFromDate() == null) er.setFromDate(PAST_DATE);
		if (er.getToDate() == null) er.setToDate(FUTURE_DATE);
		
		if (!CsvImportController.importexportAllowed(request)) {
            return null;  // For security only, unauthorized users will not have the menu option to even get here normally.
        }
		
		String exportData = getExport(er);

		response.setContentType("application/x-download"); 
		response.setHeader("Content-Disposition", "attachment; filename=" + er.getEntity() + "-export.csv");
		response.setContentLength(exportData.length());
		PrintWriter out = response.getWriter();
		out.print(exportData);
		out.flush();

		return null;

	}

	private String getExport(ExportRequest er) {


		StringWriter sw = new StringWriter();
		CSVWriter writer = new CSVWriter(sw);
		
		ApplicationContext applicationContext = getApplicationContext();

		EntityExporter ex = new EntityExporterFactory().getEntityExporter(er, applicationContext);
		if (ex == null) return "";
		
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
