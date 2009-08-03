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

package com.orangeleap.tangerine.domain;

import com.orangeleap.tangerine.type.FormBeanType;

public interface PaymentSourceAware {

    public String getPaymentType();
    
    public void setPaymentType(String paymentType);

    public PaymentSource getPaymentSource();

    public void setPaymentSource(PaymentSource paymentSource);

    @Deprecated
    public PaymentSource getSelectedPaymentSource();

    @Deprecated
    public void setSelectedPaymentSource(PaymentSource paymentSource);

    @Deprecated
    public void setPaymentSourceType(FormBeanType type);
    
    @Deprecated
    public FormBeanType getPaymentSourceType();
    
    public void setPaymentSourcePaymentType();
    
    public void setPaymentSourceAwarePaymentType();
}
