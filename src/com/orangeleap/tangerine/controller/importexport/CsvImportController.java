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

package com.orangeleap.tangerine.controller.importexport;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
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
    private static int MAX_ERROR_LENGTH = 100;

	protected final Log logger = OLLogger.getLog(getClass());

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
		
		List<String> result = new ArrayList<String>();
		boolean validRequest = true;
		
		ImportRequest importRequest = new ImportRequest();
		importRequest.setEntity(request.getParameter("entity"));
		try {
			importRequest.setNcoaDate(getDate(request.getParameter("ncoaDate")));
			importRequest.setCassDate(getDate(request.getParameter("cassDate")));
		} catch (Exception e) {
			result.add("Invalid Date.");
			validRequest = false;
		}

		if (validRequest) {
			
			MultipartFile mf = ((MultipartHttpServletRequest)request).getFile("file");
	        String filename = mf.getOriginalFilename();
			
			if (!filename.toLowerCase().endsWith(".csv")) {
				result.add("Selected import file must be a csv file.");
			} else {
				FileUploadBean bean = (FileUploadBean) command;
				ApplicationContext applicationContext = getApplicationContext();
		
				byte[] file = bean.getFile();
				if (file != null && file.length > 0) {
					result = importFile(importRequest, file, errors, applicationContext);
				} else {
					result.add("Import file required.");
				}
			}
		
		}
		
        ModelAndView mav = super.onSubmit(request, response, command, errors);
        mav.addObject(IMPORT_RESULT, result);
        WebUtils.setSessionAttribute(request, IMPORT_RESULT, new ImportResult(result));
        return mav;

	}
	
	private Date getDate(String s) throws ParseException {
	    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	    if (s == null || s.length() == 0) return null;
	    return sdf.parse(s);
	}

	// Can also import in separate thread, if it is slow for large files, and return a request id for the response which can be polled for when ready
	private List<String> importFile(ImportRequest importRequest, byte[] file, BindException errors, ApplicationContext applicationContext) {

		List<String> result = new ArrayList<String>();
		
		try {
			
			List<String[]> data = parseFile(file);
    		ImportHandler handler = new ImportHandler(importRequest, data, applicationContext);
	    	handler.importData();
	    	
			String summary = "Adds: " + handler.getAdds() + ", Changes: " + handler.getChanges() + (handler.getDeletes() > 0 ? ", Deletes: " + handler.getDeletes() : "") + ", Errors: " + handler.getErrorCount();
			result.add(summary);
			if (handler.getErrors().size() == 0) {
                result.add("Import successful.");
            }
			
			
			for (int i = 0; i < handler.getErrors().size(); i++) {
				if (i > 20) {
					result.add("more...");
					break;
				}
				String error = handler.getErrors().get(i);
				if (errors.getAllErrors().size() > 20) {
					result.add("more...");
					break;
				} else {
					if (error.length() > MAX_ERROR_LENGTH) {
						error = error.substring(0, MAX_ERROR_LENGTH) + "...";
					}
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
