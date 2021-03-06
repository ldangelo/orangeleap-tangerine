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

package com.orangeleap.tangerine.controller.importexport.importers;

import java.util.Map;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;

import com.orangeleap.tangerine.controller.importexport.ImportRequest;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.type.PageType;


public class ConstituentImporter extends EntityImporter {
	
    protected final Log logger = OLLogger.getLog(getClass());
    
    private static String ACCOUNT_NUMBER = "accountNumber";

    private ConstituentService constituentService;
    private SiteService siteService;
    private PicklistItemService picklistItemService;

	public ConstituentImporter(ImportRequest importRequest, ApplicationContext applicationContext) {
		super(importRequest, applicationContext);
		constituentService = (ConstituentService)applicationContext.getBean("constituentService");
		siteService = (SiteService)applicationContext.getBean("siteService");
	    picklistItemService = (PicklistItemService)applicationContext.getBean("picklistItemService");
	}
	
	@Override
	public String getIdField() {
		return ACCOUNT_NUMBER;
	}
	
	@Override
	protected PageType getPageType() {
	    return PageType.constituent;
	}



	@Override
	public void importValueMap(String action, Map<String, String> values) throws ConstituentValidationException, BindException {
		
		Constituent constituent;
		
		String id = values.get(getIdField());
		if (action.equals(EntityImporter.ACTION_ADD)) {
			constituent = constituentService.createDefaultConstituent();
			logger.debug("Adding new entity...");
		} else {
			if (id == null) {
                throw new RuntimeException(getIdField() + " field is required for CHANGE or DELETE action.");
            }
		    constituent = constituentService.readConstituentByAccountNumber(id);
			if (constituent == null) {
                throw new RuntimeException(getIdField() + " " + id + " not found.");
            }
			logger.debug("Importing constituent "+id+"...");
		}
		
		// We want constituent relationship maintenance, so type maps are required, similar to manual edit screen.
		siteservice.populateDefaultEntityEditorMaps(constituent);

		// Ignore a blank account number field instead of treating it as 0.
		if (action.equals(EntityImporter.ACTION_ADD)) {
			String accountNumber = values.get(ACCOUNT_NUMBER);
			if (accountNumber != null && accountNumber.length() == 0) values.remove(ACCOUNT_NUMBER);
		}

		if (action.equals(EntityImporter.ACTION_DELETE)) {
			// not currently supported
			throw new RuntimeException("Delete action not supported.");
		} else {
			mapValuesToObject(values, constituent);
			removeBlankFields(constituent);
		}
		
		if (action.equals(EntityImporter.ACTION_ADD)) {
			constituent.setId(null);
		}

		if (constituent.getLoginId() != null && constituent.getLoginId().trim().length() == 0) {
			constituent.setLoginId(null);
		}
		
		if (importRequest.isBypassDuplicateDetection()) constituent.setByPassDuplicateDetection(true);
		constituentService.maintainConstituent(constituent);
		
	}
	
	private void removeBlankFields(Constituent constituent) {
		if (constituent.getPrimaryAddress() != null && !StringUtils.hasText(constituent.getPrimaryAddress().getAddressLine1())) {
			constituent.setPrimaryAddress(null);
		}
		if (constituent.getPrimaryEmail() != null && !StringUtils.hasText(constituent.getPrimaryEmail().getEmailAddress())) {
			constituent.setPrimaryEmail(null);
		}
		if (constituent.getPrimaryPhone() != null && !StringUtils.hasText(constituent.getPrimaryPhone().getNumber())) {
			constituent.setPrimaryPhone(null);
		}
	}
	
	
}
