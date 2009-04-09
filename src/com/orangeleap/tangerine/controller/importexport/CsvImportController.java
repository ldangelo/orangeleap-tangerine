package com.orangeleap.tangerine.controller.importexport;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import au.com.bytecode.opencsv.CSVReader;

import com.orangeleap.tangerine.type.AccessType;

public class CsvImportController extends SimpleFormController {

    public static final String IMPORT_RESULT = "importResult";

	protected final Log logger = LogFactory.getLog(getClass());

	@Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	@SuppressWarnings("unchecked")
	public static boolean importexportAllowed(HttpServletRequest request) {
		Map<String, AccessType> pageAccess = (Map<String, AccessType>)WebUtils.getSessionAttribute(request, "pageAccess");
		return pageAccess.get("/importexport.htm") == AccessType.ALLOWED;
	}
	
	@Override
    protected ModelAndView onSubmit(
			HttpServletRequest request,
			HttpServletResponse response,
			Object command,
			BindException errors) throws Exception {

		if (!importexportAllowed(request)) {
            return null;  // For security only, unauthorized users will not have the menu option to even get here normally.
        }

		String entity = request.getParameter("entity");
		List<String> result = new ArrayList<String>();
		
		MultipartFile mf = ((MultipartHttpServletRequest)request).getFile("file");
        String filename = mf.getOriginalFilename();
		
		if (!filename.toLowerCase().endsWith(".csv")) {
			result.add("Selected import file must be a csv file.");
		} else {
			FileUploadBean bean = (FileUploadBean) command;
			ApplicationContext applicationContext = getApplicationContext();
	
			byte[] file = bean.getFile();
			if (file != null && file.length > 0) {
				result = importFile(entity, file, errors, applicationContext);
			} else {
				result.add("Import file required.");
			}
		}
		
        ModelAndView mav = super.onSubmit(request, response, command, errors);
        mav.addObject(IMPORT_RESULT, result);
        WebUtils.setSessionAttribute(request, IMPORT_RESULT, result);
        return mav;

	}

	// Can also import in separate thread, if it is slow for large files, and return a request id for the response which can be polled for when ready
	private List<String> importFile(String entity, byte[] file, BindException errors, ApplicationContext applicationContext) {

		List<String> result = new ArrayList<String>();
		
		try {
			
			List<String[]> data = parseFile(file);
    		ImportHandler handler = new ImportHandler(entity, data, applicationContext);
	    	handler.importData();
	    	
			String summary = "Adds: " + handler.getAdds() + ", Changes: " + handler.getChanges() + (handler.getDeletes() > 0 ? ", Deletes: " + handler.getDeletes() : "") + ", Errors: " + handler.getErrors().size();
			result.add(summary);
			if (handler.getErrors().size() == 0) {
                result.add("Import successful.");
            }
			for (String error : handler.getErrors()) {
				if (errors.getAllErrors().size() > 1000) {
					result.add("more...");
					break;
				} else {
					result.add(error);
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		    result.add(e.getMessage());
		}
		

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<String[]> parseFile(byte[] file) {
		StringReader sr = new StringReader(new String(file));
		CSVReader reader = new CSVReader(sr);
		try {
			return reader.readAll();
		} catch (IOException e) {
			return new ArrayList<String[]>();
		}
	}


}
