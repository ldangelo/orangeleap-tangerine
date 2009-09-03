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

import com.orangeleap.tangerine.controller.importexport.ImportRequest;
import com.orangeleap.tangerine.controller.importexport.ProperCaseAddressUtil;
import com.orangeleap.tangerine.controller.importexport.exporters.AddressExporter;
import com.orangeleap.tangerine.controller.importexport.exporters.FieldDescriptor;
import com.orangeleap.tangerine.controller.importexport.fielddefs.FieldDefUtil;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.AddressService;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindException;

import java.util.List;
import java.util.Map;


public class AddressImporter extends EntityImporter {

    private static String ACCOUNT_FIELD = "accountNumber";

    protected final Log logger = OLLogger.getLog(getClass());

    private AddressService addressService;
    private SiteService siteService;
    private ConstituentService constituentService;

    public AddressImporter(ImportRequest importRequest, ApplicationContext applicationContext) {
        super(importRequest, applicationContext);
        addressService = (AddressService) applicationContext.getBean("addressService");
        constituentService = (ConstituentService) applicationContext.getBean("constituentService");
        siteService = (SiteService) applicationContext.getBean("siteService");
    }

    @Override
    public String getIdField() {
        return "id";
    }

    @Override
    protected PageType getPageType() {
        return PageType.addressManager;
    }

    @Override
    protected boolean ignore(String key) {
        if (AddressExporter.isConstituentReadOnlyField(key) || key.equals(ACCOUNT_FIELD)) return true;
        return false;
    }

    @Override
    protected List<FieldDescriptor> getFieldDescriptors() {

        FieldDefUtil.Exclusion exclusion = new FieldDefUtil.Exclusion() {
            public boolean excludeField(String name, FieldDefinition fd) {
                return exclude(name, fd);
            }
        };

        List<FieldDescriptor> list = FieldDefUtil.getFieldDescriptors(exclusion, getPageType(), siteservice, tangerineUserHelper);

        // Import and export for address cleansing only use different field lists
        list.add(AddressExporter.getFieldDescriptor("id"));
        list.add(AddressExporter.getFieldDescriptor("inactive"));
        list.add(AddressExporter.getFieldDescriptor("undeliverable"));
        list.add(AddressExporter.getFieldDescriptor("receiveCorrespondence"));
        FieldDescriptor fd = AddressExporter.getFieldDescriptor("customFieldMap[addressType]");
        fd.setName("customField[addressType]");
        list.add(fd);
        

        return list;
    }

    private static FieldDefUtil.Exclusion defaultExclusion = FieldDefUtil.getDefaultExclusions();

    protected boolean exclude(String name, FieldDefinition fd) {
        return defaultExclusion.excludeField(name, fd);
    }

    @Override
    public void importValueMap(String action, Map<String, String> values) throws ConstituentValidationException, BindException {

        Address address;

        String id = values.get(getIdField());
        if (action.equals(EntityImporter.ACTION_ADD)) {

            address = new Address();
            String account = values.get(ACCOUNT_FIELD);
            Constituent constituent = constituentService.readConstituentByAccountNumber(account);
            if (constituent == null) throw new RuntimeException("Invalid constituent account " + account);
            address.setConstituentId(constituent.getId());
            logger.debug("Adding new address to " + constituent.getFullName() + "...");

        } else {

            if (id == null) {
                throw new RuntimeException(getIdField() + " field is required for CHANGE or DELETE action.");
            }
            Long lid;
            try {
                lid = new Long(id);
            } catch (Exception e) {
                throw new RuntimeException("Invalid id value " + id);
            }
            address = addressService.readById(lid);
            if (address == null || constituentService.readConstituentById(address.getConstituentId()) == null) {
                throw new RuntimeException(getIdField() + " " + id + " not found.");
            }
            siteService.populateDefaultEntityEditorMaps(address);
            logger.debug("Importing constituent " + id + "...");

        }


        if (action.equals(EntityImporter.ACTION_DELETE)) {
            // TODO How to delete or set to inactive?
            address.setInactive(true);
        } else {
            mapValuesToObject(values, address);
        }

        setCleanseDates(address);

        if (importRequest.isConvertToProperCase()) {
            address.setAddressLine1(ProperCaseAddressUtil.convertToProperCase(address.getAddressLine1()));
            address.setAddressLine2(ProperCaseAddressUtil.convertToProperCase(address.getAddressLine2()));
            address.setAddressLine3(ProperCaseAddressUtil.convertToProperCase(address.getAddressLine3()));
            address.setCity(ProperCaseAddressUtil.convertToProperCase(address.getCity()));
        }


        addressService.save(address);
    }

    private void setCleanseDates(Address address) {
        if (importRequest.getNcoaDate() != null) address.setNcoaDate(importRequest.getNcoaDate());
        if (importRequest.getCassDate() != null) address.setCassDate(importRequest.getCassDate());
    }


}
