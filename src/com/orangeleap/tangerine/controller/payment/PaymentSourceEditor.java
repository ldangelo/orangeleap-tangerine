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

package com.orangeleap.tangerine.controller.payment;

import com.orangeleap.tangerine.controller.constituent.RequiresConstituentEditor;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.service.PaymentSourceService;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;

public class PaymentSourceEditor extends RequiresConstituentEditor {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    private PaymentSourceService paymentSourceService;

    public PaymentSourceEditor() {
        super();
    }

    public PaymentSourceEditor(PaymentSourceService paymentSourceService, String constituentId) {
        super(constituentId);
        this.paymentSourceService = paymentSourceService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (NumberUtils.isDigits(text)) {
            Long paymentSourceId = NumberUtils.createLong(text);
            PaymentSource ps = paymentSourceService.readPaymentSource(paymentSourceId);
            setValue(ps);
        }
    }
}
