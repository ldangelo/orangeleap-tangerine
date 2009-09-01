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

package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.PaymentSource;

import java.util.List;
import java.util.Locale;

public interface PaymentSourceDao {

    public PaymentSource maintainPaymentSource(PaymentSource paymentSource);

    public PaymentSource readPaymentSourceByProfile(Long constituentId, String profile);

    public List<PaymentSource> readAllPaymentSources(Long constituentId);

    public List<PaymentSource> readActivePaymentSources(Long constituentId);

    public List<PaymentSource> readActivePaymentSourcesByTypes(Long constituentId, List<String> paymentTypes);

    public PaymentSource readPaymentSourceById(Long paymentSourceId);

    public List<PaymentSource> readExistingCreditCards(String creditCardNumber);

    public List<PaymentSource> readExistingAchAccounts(String achAccountNum, String achRoutingNum);

    List<PaymentSource> readAllPaymentSourcesByConstituentId(Long constituentId, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale);

    int readCountByConstituentId(Long constituentId);
}
