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
