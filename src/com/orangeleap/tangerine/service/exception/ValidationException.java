package com.orangeleap.tangerine.service.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.validation.BindException;

public abstract class ValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    private final Logger logger = Logger.getLogger(ValidationException.class.getName());

    protected LinkedHashMap<String, Object[]> validationResults = new LinkedHashMap<String, Object[]>();

    public ValidationException() {
        super();
    }
    
    public ValidationException(String message) {
    	super(message);
    }


    public ValidationException(String msg, Object[] params) {
        super(msg);
        validationResults.put(msg, params == null ? new Object[0] : params);
    }

    public void addValidationResult(String key) {
        validationResults.put(key, new Object[0]);
    }

    public void addValidationResult(String key, Object[] params) {
        validationResults.put(key, params == null ? new Object[0] : params);
    }

    public void addValidationResults(Map<String, Object[]> results) {
        validationResults.putAll(results);
    }

    public Map<String, Object[]> getValidationResults() {
        return validationResults;
    }

    public boolean containsValidationResults() {
        return validationResults.size() > 0;
    }

    // public void createMessages(FacesMessages facesMessages) {
    public void createMessages() {
        if (validationResults != null) {
            for (String key : validationResults.keySet()) {
                // facesMessages.addFromResourceBundle(key, validationResults.get(key));
                logger.log(Level.SEVERE, key, validationResults.get(key));
            }
        }
    }

    public void createMessages(BindException errors) {
        if (validationResults != null) {
            for (String key : validationResults.keySet()) {
                errors.reject(key, validationResults.get(key), key);
            }
        }
    }
}
