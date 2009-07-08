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

import com.orangeleap.tangerine.domain.*;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.domain.paymentInfo.AbstractPaymentInfoEntity;
import com.orangeleap.tangerine.service.*;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

import javax.annotation.Resource;

public abstract class AbstractPaymentService extends AbstractTangerineService {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "auditService")
    protected AuditService auditService;

    @Resource(name = "addressService")
    protected AddressService addressService;

    @Resource(name = "phoneService")
    protected PhoneService phoneService;

    @Resource(name = "emailService")
    protected EmailService emailService;

    @Resource(name = "paymentSourceService")
    protected PaymentSourceService paymentSourceService;

    public void maintainEntityChildren(AbstractEntity entity, Constituent constituent) {
        if (entity instanceof AddressAware) {
            AddressAware addressAware = (AddressAware) entity;
            maintainAddressChild(addressAware, constituent);
        }
        if (entity instanceof PhoneAware) {
            PhoneAware phoneAware = (PhoneAware) entity;
            maintainPhoneChild(phoneAware, constituent);
        }
        if (entity instanceof EmailAware) {
            EmailAware emailAware = (EmailAware) entity;
            maintainEmailChild(emailAware, constituent);
        }
        if (entity instanceof PaymentSourceAware) {
            maintainPaymentSourceChild(entity, constituent);
        }
    }

    private void maintainAddressChild(AddressAware addressAware, Constituent constituent) {
        if (FormBeanType.NEW.equals(addressAware.getAddressType()) && addressAware instanceof NewAddressAware) {
            NewAddressAware newAddressAware = (NewAddressAware) addressAware;
            Address newAddress = newAddressAware.getAddress();
            newAddress.setConstituentId(constituent.getId());

            Address existingAddress = addressService.alreadyExists(newAddress);
            if (existingAddress != null) {
                newAddressAware.setAddress(existingAddress);
                newAddressAware.setSelectedAddress(existingAddress);
                newAddressAware.setAddressType(FormBeanType.EXISTING);
            } else {
                newAddressAware.setAddress(addressService.save(newAddress));
                newAddressAware.setSelectedAddress(newAddressAware.getAddress());
            }
        } else if (FormBeanType.NONE.equals(addressAware.getAddressType())) {
            addressAware.setSelectedAddress(null);

            if (addressAware instanceof NewAddressAware) {
                ((NewAddressAware) addressAware).setAddress(null);
            }
        }
    }

    private void maintainPhoneChild(PhoneAware phoneAware, Constituent constituent) {
        if (FormBeanType.NEW.equals(phoneAware.getPhoneType()) && phoneAware instanceof NewPhoneAware) {
            NewPhoneAware newPhoneAware = (NewPhoneAware) phoneAware;
            Phone newPhone = newPhoneAware.getPhone();
            newPhone.setConstituentId(constituent.getId());

            Phone existingPhone = phoneService.alreadyExists(newPhone);
            if (existingPhone != null) {
                newPhoneAware.setPhone(existingPhone);
                newPhoneAware.setSelectedPhone(existingPhone);
                newPhoneAware.setPhoneType(FormBeanType.EXISTING);
            } else {
                newPhoneAware.setPhone(phoneService.save(newPhone));
                newPhoneAware.setSelectedPhone(newPhoneAware.getPhone());
            }
        } else if (FormBeanType.NONE.equals(phoneAware.getPhoneType())) {
            phoneAware.setSelectedPhone(null);
            if (phoneAware instanceof NewPhoneAware) {
                ((NewPhoneAware) phoneAware).setPhone(null);
            }
        }
    }

    private void maintainEmailChild(EmailAware emailAware, Constituent constituent) {
        if (FormBeanType.NEW.equals(emailAware.getEmailType()) && emailAware instanceof NewEmailAware) {
            NewEmailAware newEmailAware = (NewEmailAware) emailAware;
            Email newEmail = newEmailAware.getEmail();
            newEmail.setConstituentId(constituent.getId());

            Email existingEmail = emailService.alreadyExists(newEmail);
            if (existingEmail != null) {
                newEmailAware.setEmail(existingEmail);
                newEmailAware.setSelectedEmail(existingEmail);
                newEmailAware.setEmailType(FormBeanType.EXISTING);
            } else {
                newEmailAware.setEmail(emailService.save(newEmail));
                newEmailAware.setSelectedEmail(newEmailAware.getEmail());
            }
        } else if (FormBeanType.NONE.equals(emailAware.getEmailType())) {
            emailAware.setSelectedEmail(null);
            if (emailAware instanceof NewEmailAware) {
                ((NewEmailAware) emailAware).setEmail(null);
            }
        }
    }

    private void maintainPaymentSourceChild(AbstractEntity entity, Constituent constituent) {
        PaymentSourceAware paymentSourceAware = (PaymentSourceAware) entity;
        if (PaymentSource.CASH.equals(paymentSourceAware.getPaymentType()) ||
                PaymentSource.CHECK.equals(paymentSourceAware.getPaymentType()) ||
                FormBeanType.NONE.equals(paymentSourceAware.getPaymentSourceType())) {
            paymentSourceAware.setSelectedPaymentSource(null);
            paymentSourceAware.setPaymentSource(null);
        } else if (PaymentSource.ACH.equals(paymentSourceAware.getPaymentType()) ||
                PaymentSource.CREDIT_CARD.equals(paymentSourceAware.getPaymentType())) {
            if (FormBeanType.NEW.equals(paymentSourceAware.getPaymentSourceType())) {
                PaymentSource newPaymentSource = paymentSourceAware.getPaymentSource();
                newPaymentSource.setConstituent(constituent);
                newPaymentSource.setPaymentType(paymentSourceAware.getPaymentType());

                if (entity instanceof AddressAware && FormBeanType.NONE.equals(((AddressAware) entity).getAddressType()) == false) {
                    newPaymentSource.setFromAddressAware((AddressAware) entity);
                }
                if (entity instanceof PhoneAware && FormBeanType.NONE.equals(((PhoneAware) entity).getPhoneType()) == false) {
                    newPaymentSource.setFromPhoneAware((PhoneAware) entity);
                }

                paymentSourceAware.setPaymentSource(paymentSourceService.maintainPaymentSource(newPaymentSource));
                paymentSourceAware.setSelectedPaymentSource(paymentSourceAware.getPaymentSource());
            } else if (FormBeanType.EXISTING.equals(paymentSourceAware.getPaymentSourceType())) {
                paymentSourceService.maintainPaymentSource(paymentSourceAware.getSelectedPaymentSource());
                paymentSourceAware.setPaymentSource(paymentSourceAware.getSelectedPaymentSource());
            }
        }
    }

    protected String getPaymentDescription(AbstractPaymentInfoEntity entity) {
        StringBuilder sb = new StringBuilder();

        if (PaymentSource.ACH.equals(entity.getPaymentType())) {
            sb.append(TangerineMessageAccessor.getMessage("achNumberColon"));
            sb.append(" ").append(entity.getSelectedPaymentSource().getAchAccountNumberDisplay());
        }
        if (PaymentSource.CREDIT_CARD.equals(entity.getPaymentType())) {
            sb.append(TangerineMessageAccessor.getMessage("creditCardNumberColon"));
            sb.append(" ").append(entity.getSelectedPaymentSource().getCreditCardType()).append(" ").append(entity.getSelectedPaymentSource().getCreditCardNumberDisplay());
            sb.append(" ");
            sb.append(entity.getSelectedPaymentSource().getCreditCardExpirationMonth());
            sb.append(" / ");
            sb.append(entity.getSelectedPaymentSource().getCreditCardExpirationYear());
            sb.append(" ");
            sb.append(entity.getSelectedPaymentSource().getCreditCardHolderName());
        }
        if (PaymentSource.CHECK.equals(entity.getPaymentType())) {
            sb.append("\n");
            sb.append(TangerineMessageAccessor.getMessage("checkNumberColon"));
            sb.append(" ");
            sb.append(entity.getCheckNumber());
        }
        if (FormBeanType.NONE.equals(entity.getAddressType()) == false) {
            Address address = entity.getSelectedAddress();
            if (address != null) {
                sb.append("\n");
                sb.append(TangerineMessageAccessor.getMessage("addressColon"));
                sb.append(" ");
                sb.append(StringUtils.trimToEmpty(address.getAddressLine1()));
                sb.append(" ").append(StringUtils.trimToEmpty(address.getAddressLine2()));
                sb.append(" ").append(StringUtils.trimToEmpty(address.getAddressLine3()));
                sb.append(" ").append(StringUtils.trimToEmpty(address.getCity()));
                String state = StringUtils.trimToEmpty(address.getStateProvince());
                sb.append((state.length() == 0 ? "" : (", " + state))).append(" ");
                sb.append(" ").append(StringUtils.trimToEmpty(address.getCountry()));
                sb.append(" ").append(StringUtils.trimToEmpty(address.getPostalCode()));
            }
        }
        if (FormBeanType.NONE.equals(entity.getPhoneType()) == false) {
            Phone phone = entity.getSelectedPhone();
            if (phone != null) {
                sb.append("\n");
                sb.append(TangerineMessageAccessor.getMessage("phoneColon"));
                sb.append(" ");
                sb.append(StringUtils.trimToEmpty(phone.getNumber()));
            }
        }
        return sb.toString();
    }
}
