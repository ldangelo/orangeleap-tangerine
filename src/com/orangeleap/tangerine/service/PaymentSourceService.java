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

package com.orangeleap.tangerine.service;

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.PaymentSourceAware;
import com.orangeleap.tangerine.domain.Constituent;
import org.springframework.validation.BindException;

public interface PaymentSourceService {

    public PaymentSource maintainPaymentSource(PaymentSource paymentSource) throws BindException;

    public List<PaymentSource> readPaymentSources(Long constituentId);

    public List<PaymentSource> filterValidPaymentSources(Long constituentId);

    public PaymentSource readPaymentSource(Long paymentSourceId);

    public PaymentSource readPaymentSourceCreateIfNull(String paymentSourceId, Constituent constituent);

    public PaymentSource findPaymentSourceProfile(Long constituentId, String profile);

    public List<PaymentSource> readAllPaymentSourcesACHCreditCard(Long constituentId);
    
    public List<PaymentSource> readActivePaymentSourcesByTypes(Long constituentId, List<String> paymentTypes);

    Map<String, List<PaymentSource>> groupPaymentSources(Long constituentId, PaymentSource selectedPaymentSource);
    
    public Map<String, Object> checkForSameConflictingPaymentSources(PaymentSourceAware paymentSourceAware);
    
    public Map<String, Object> checkForSameConflictingPaymentSources(PaymentSource paymentSource);
}
