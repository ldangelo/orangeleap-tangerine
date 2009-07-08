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

package com.orangeleap.tangerine.service.impl;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.CreditCardService;

@Service("creditCardService")
@Transactional(propagation = Propagation.REQUIRED)
public class CreditCardServiceImpl extends AbstractTangerineService implements CreditCardService {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

	
    @Override
    public Gift processCreditCard(Gift gift) {
        // TODO: implement call to processing system and get a confirmation
        // local validation should already be done to minimize charges
        String confirmation = null;
        gift.setAuthCode(confirmation);
        return gift;
    }
}
