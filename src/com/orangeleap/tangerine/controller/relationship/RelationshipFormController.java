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

package com.orangeleap.tangerine.controller.relationship;

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.ConstituentCustomFieldRelationshipService;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.CustomFieldRelationshipService;
import com.orangeleap.tangerine.service.RelationshipService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class RelationshipFormController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "constituentService")
    private ConstituentService constituentService;

    @Resource(name = "relationshipService")
    private RelationshipService relationshipService;

    @Resource(name = "constituentCustomFieldRelationshipService")
    protected ConstituentCustomFieldRelationshipService constituentCustomFieldRelationshipService;

    @Resource(name = "customFieldRelationshipService")
    protected CustomFieldRelationshipService customFieldRelationshipService;

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;

    @SuppressWarnings("unchecked")
    @Override
    public ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {

        ModelAndView mav = super.showForm(request, response, errors, controlModel);
        Long constituentId = new Long(request.getParameter("constituentId"));
        Constituent constituent = constituentService.readConstituentById(constituentId);

        mav.addObject("form", readForm(request));
        mav.addObject("constituent", constituent);
        return mav;

    }


    @Override
    public Object formBackingObject(HttpServletRequest request) throws ServletException {
        return "";
    }


    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {

        ModelAndView mav = new ModelAndView(getSuccessView());


        Long constituentId = new Long(request.getParameter("constituentId"));
        Constituent constituent = constituentService.readConstituentById(constituentId);
        if (constituent == null) {
            return null;
        }
        String fieldDefinitionId = request.getParameter("fieldDefinitionId");
        FieldDefinition fieldDefinition = fieldDao.readFieldDefinition(fieldDefinitionId);
        String masterfieldDefinitionId = customFieldRelationshipService.getMasterFieldDefinitionId(fieldDefinitionId);
        String fieldName = fieldDefinition.getCustomFieldName();
        List<CustomField> list = getMap(request, constituentId, fieldName);

        int customizeIndex = getIndex(request.getParameter("customizeIndex"));
        CustomField customizeCf = null;
        if (list.size() > 0 && customizeIndex > -1) {
            customizeCf = list.get(customizeIndex);
        }

        String message = "";

        try {

            List<CustomField> existing = relationshipService.readCustomFieldsByConstituentAndFieldName(constituentId, fieldName);
            adjustCCRs(existing, list, masterfieldDefinitionId);

            relationshipService.maintainCustomFieldsByConstituentAndFieldDefinition(constituent.getId(), fieldDefinitionId, list, new ArrayList<Long>());
            List<CustomField> savedlist = relationshipService.readCustomFieldsByConstituentAndFieldName(constituent.getId(), fieldDefinition.getCustomFieldName());
            constituent = constituentService.readConstituentById(constituentId);

            if (customizeCf != null) {
                CustomField customField = getCustomFieldId(customizeCf, savedlist);
                Constituent refconstituent = constituentService.readConstituentById(new Long(customField.getValue()));
                return new ModelAndView("redirect:/relationshipCustomize.htm?constituentId=" + constituentId + "&fieldDefinitionId=" + fieldDefinitionId + "&customFieldId=" + customField.getId() + "&refvalue=" + refconstituent.getFullName());
            }


        } catch (Exception e) {
            logger.error(e);
            message = e.getMessage();
        }

        mav.addObject("message", message);
        mav.addObject("form", readForm(request));
        mav.addObject("constituent", constituent);
        return mav;

    }

    // If any start date has changed, adjust the start dates on the corresponding ccrs before saving.
    // CCRs link to custom fields by start date rather than custom field id since the custom field ids change with each save in IBatisCustomFieldHelper.
    private void adjustCCRs(List<CustomField> oldlist, List<CustomField> newlist, String masterfieldDefinitionId) {
        for (CustomField oldcf : oldlist) {
            boolean found = false;
            for (CustomField newcf : newlist) {
                if (oldcf.getId().equals(newcf.getId()) && oldcf.getValue().equals(newcf.getValue())) {
                    found = true;
                    if (!oldcf.getStartDate().equals(newcf.getStartDate())) {
                        updateStartDate(oldcf.getEntityId(), masterfieldDefinitionId, oldcf.getValue(), oldcf.getStartDate(), newcf.getStartDate());
                        updateStartDate(new Long(oldcf.getValue()), masterfieldDefinitionId, "" + oldcf.getEntityId(), oldcf.getStartDate(), newcf.getStartDate());
                    }
                }
            }
            if (!found) {
                delete(oldcf.getEntityId(), masterfieldDefinitionId, oldcf.getValue(), oldcf.getStartDate());
                delete(new Long(oldcf.getValue()), masterfieldDefinitionId, "" + oldcf.getEntityId(), oldcf.getStartDate());
            }
        }
    }

    private void updateStartDate(Long entityid, String masterfieldDefinitionId, String value, Date oldStartDate, Date newStartDate) {
        ConstituentCustomFieldRelationship ccr = constituentCustomFieldRelationshipService.readByConstituentFieldDefinitionCustomFieldIds(entityid, masterfieldDefinitionId, value, oldStartDate);
        if (ccr != null) {
            ccr.setCustomFieldStartDate(newStartDate);
            constituentCustomFieldRelationshipService.maintainConstituentCustomFieldRelationship(ccr);
        }
    }

    private void delete(Long entityid, String masterfieldDefinitionId, String value, Date oldStartDate) {
        constituentCustomFieldRelationshipService.deleteConstituentCustomFieldRelationship(entityid, masterfieldDefinitionId, value, oldStartDate);
    }

    private CustomField getCustomFieldId(CustomField customizeCf, List<CustomField> savedlist) {
        for (CustomField cf : savedlist) {
            if (cf.getName().equals(customizeCf.getName())
                    && cf.getValue().equals(customizeCf.getValue())
                    && cf.getStartDate().equals(customizeCf.getStartDate())
                    && cf.getEndDate().equals(customizeCf.getEndDate())
                    ) {
                return cf;
            }
        }
        return null;
    }

    private int getIndex(String s) {
        if (s == null || s.trim().length() == 0) {
            return -1;
        }
        return Integer.parseInt(s.substring(s.indexOf('-') + 1, s.lastIndexOf('-'))) - 1;
    }

    private RelationshipForm readForm(HttpServletRequest request) {

        Long constituentId = new Long(request.getParameter("constituentId"));
        Constituent constituent = constituentService.readConstituentById(constituentId);
        if (constituent == null) {
            return null;
        }

        String fieldDefinitionId = request.getParameter("fieldDefinitionId");
        FieldDefinition fieldDefinition = fieldDao.readFieldDefinition(fieldDefinitionId);
        String fieldname = fieldDefinition.getCustomFieldName();
        String fieldlabel = fieldDefinition.getDefaultLabel();

        List<CustomField> list = relationshipService.readCustomFieldsByConstituentAndFieldName(new Long(constituentId), fieldname);

        if (list.size() == 0) {
            CustomField cf = new CustomField();
            cf.setName(fieldname);
            cf.setEntityId(constituent.getId());
            cf.setEntityType("constituent");
            list.add(cf);
        }

        RelationshipForm form = new RelationshipForm();
        form.setCustomFieldList(list);
        form.setRelationshipNames(resolveConstituentRelationship(list));
        form.setConstituent(constituent);
        form.setFieldDefinition(fieldDefinition);
        form.setFieldLabel(fieldlabel);
        form.setFieldType(relationshipService.isIndividualOrganizationRelationship(fieldDefinitionId));

        return form;

    }

    private List<String> resolveConstituentRelationship(List<CustomField> fields) {
        List<String> constituentNames = new ArrayList<String>();
        for (CustomField customField : fields) {
            if (NumberUtils.isDigits(customField.getValue())) {
                Constituent constituent = constituentService.readConstituentById(Long.parseLong(customField.getValue()));
                if (constituent == null) {
                    constituentNames.add(StringConstants.EMPTY);
                } else {
                    constituentNames.add(constituent.getDisplayValue());
                }
            } else {
                constituentNames.add(StringConstants.EMPTY);
            }
        }
        return constituentNames;
    }

    @SuppressWarnings("unchecked")
    private List<CustomField> getMap(HttpServletRequest request, Long constituentId, String fieldName) {
        List<CustomField> list = new ArrayList<CustomField>();
        Enumeration e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String parm = (String) e.nextElement();
            if (parm.startsWith("cfFieldValue")) {
                String fieldnum = parm.substring("cfFieldValue".length());
                int ifieldnum = Integer.parseInt(fieldnum.replace('[', ' ').replace(']', ' ').trim());
                CustomField cf = new CustomField();
                cf.setEntityId(constituentId);
                cf.setEntityType("constituent");
                cf.setName(fieldName);
                String id = request.getParameter("cfId" + fieldnum);
                if (id != null && id.length() > 0) {
                    cf.setId(new Long(id));
                }
                cf.setValue(request.getParameter(parm).trim());
                cf.setSequenceNumber(ifieldnum - 1);
                if (cf.getValue().length() > 0) {
                    list.add(cf);
                    cf.setDisplayStartDate(request.getParameter("cfStartDate" + fieldnum));
                    cf.setDisplayEndDate(request.getParameter("cfEndDate" + fieldnum));
                }
            }
        }
        Collections.sort(list, new Comparator<CustomField>() {
            @Override
            public int compare(CustomField o1, CustomField o2) {
                return o1.getSequenceNumber() - o2.getSequenceNumber();
            }
        });
        return list;
    }


}
