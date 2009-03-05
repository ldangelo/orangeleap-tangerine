package com.mpower.domain.model.customization;

import java.io.Serializable;
import java.util.Locale;

import org.springframework.core.style.ToStringCreator;

import com.mpower.domain.GeneratedId;
import com.mpower.domain.model.Site;
import com.mpower.type.MessageResourceType;

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
