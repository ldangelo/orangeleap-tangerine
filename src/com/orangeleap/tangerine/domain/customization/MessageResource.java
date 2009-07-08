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

package com.orangeleap.tangerine.domain.customization;

import com.orangeleap.tangerine.domain.GeneratedId;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.type.MessageResourceType;
import org.springframework.core.style.ToStringCreator;

import java.io.Serializable;
import java.util.Locale;

public class MessageResource implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String languageAbbreviation;
    private MessageResourceType messageResourceType;
    private Site site;
    private String messageKey;
    private String messageValue;

    public Locale getLocale() {
        return new Locale(languageAbbreviation);
    }

    public void setLocale(Locale locale) {
        languageAbbreviation = locale.getLanguage();
    }

    public String getLanguageAbbreviation() {
        return languageAbbreviation;
    }

    public void setLanguageAbbreviation(String languageAbbreviation) {
        this.languageAbbreviation = languageAbbreviation;
    }

    public MessageResourceType getMessageResourceType() {
        return messageResourceType;
    }

    public void setMessageResourceType(MessageResourceType messageResourceType) {
        this.messageResourceType = messageResourceType;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageValue() {
        return messageValue;
    }

    public void setMessageValue(String messageValue) {
        this.messageValue = messageValue;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).append("messageKey", messageKey).append("messageValue", messageValue).append("messageResourceType", messageResourceType).
                append("languageAbbreviation", languageAbbreviation).append("site", site).toString();
    }
}
