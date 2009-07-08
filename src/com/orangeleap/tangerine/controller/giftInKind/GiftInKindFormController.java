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

package com.orangeleap.tangerine.controller.giftInKind;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.service.GiftInKindService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GiftInKindFormController extends TangerineConstituentAttributesFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "giftInKindService")
    protected GiftInKindService giftInKindService;

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        return giftInKindService.readGiftInKindByIdCreateIfNull(request.getParameter(StringConstants.GIFT_IN_KIND_ID), super.getConstituent(request));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        GiftInKind giftInKind = (GiftInKind) command;

        boolean saved = true;
        GiftInKind current = null;
        try {
            current = giftInKindService.maintainGiftInKind(giftInKind);
        }
        catch (BindException e) {
            saved = false;
            current = giftInKind;
            errors.addAllErrors(e);
        }

        ModelAndView mav = null;
        if (saved) {
            mav = new ModelAndView(super.appendSaved(getSuccessView() + "?" + StringConstants.GIFT_IN_KIND_ID + "=" + current.getId() + "&" + StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request)));
        } else {
            current.removeEmptyMutableDetails();
            mav = showForm(request, errors, getFormView());
        }
        return mav;
    }
}
