package com.mpower.controller.importexport;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import au.com.bytecode.opencsv.CSVReader;

import com.mpower.service.SiteService;

public class CsvImportController extends SimpleFormController {

    public static final String IMPORT_RESULT = "importResult";

	protected final Log logger = LogFactory.getLog(getClass());
	
	private SiteService siteService;

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	protected ModelAndView onSubmit(
			HttpServletRequest request,
			HttpServletResponse response,
			Object command,
			BindException errors) throws Exception {

		// TODO check rights?

		String entity = request.getParameter("entity");

		FileUploadBean bean = (FileUploadBean) command;
		
		ApplicationContext applicationContext = getApplicationContext();

		List<String> result = new ArrayList<String>();
		byte[] file = bean.getFile();
		if (file != null && file.length > 0) {
			result = importFile(entity, file, errors, applicationContext);
		} else {
			result.add("Import file required.");
		}
		
        ModelAndView mav = super.onSubmit(request, response, command, errors);
        mav.addObject(IMPORT_RESULT, result);
        request.getSession().setAttribute(IMPORT_RESULT, result);
        return mav;

	}

	// Can also import in separate thread, if it is slow for large files, and return a request id for the response which can be polled for when ready
	private List<String> importFile(String entity, byte[] file, BindException errors, ApplicationContext applicationContext) {

		List<String[]> data = parseFile(file);
		
		ImportHandler handler = new ImportHandler(entity, data, applicationContext);
		handler.importData();
		
		List<String> result = new ArrayList<String>();
		String summary = "Adds: " + handler.getAdds() + ", Changes: " + handler.getChanges() + ", Deletes: " + handler.getDeletes() + ", Errors: " + handler.getErrors().size();
		result.add(summary);
		if (handler.getErrors().size() == 0) result.add("Import successful.");
		for (String error : handler.getErrors()) {
			if (errors.getAllErrors().size() > 1000) {
				result.add("more...");
				break;
			} else {
				result.add(error);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<String[]> parseFile(byte[] file) {
		StringReader sr = new StringReader(new String(file));
		CSVReader reader = new CSVReader(sr);
		try {
			return (List<String[]>)reader.readAll();
		} catch (IOException e) {
			return new ArrayList<String[]>();
		}
	}


}
