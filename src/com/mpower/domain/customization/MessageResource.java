package com.mpower.domain.customization;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Site;
import com.mpower.domain.listener.EmptyStringNullifyerListener;
import com.mpower.type.MessageResourceType;

@Entity
@EntityListeners(value = { EmptyStringNullifyerListener.class })
@Table(name = "MESSAGE_RESOURCE", uniqueConstraints = { @UniqueConstraint(columnNames = { "MESSAGE_KEY", "LANGUAGE_ABBREVIATION", "MESSAGE_RESOURCE_TYPE", "SITE_NAME" }) })
public class MessageResource implements Serializable {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "MESSAGE_RESOURCE_ID")
    private Long id;

    @Column(name = "LANGUAGE_ABBREVIATION", nullable = false)
    private String languageAbbreviation;

    @Column(name = "MESSAGE_RESOURCE_TYPE")
    @Enumerated(EnumType.STRING)
    private MessageResourceType messageResourceType;

    @ManyToOne
    @JoinColumn(name = "SITE_NAME")
    private Site site;

    @Column(name = "MESSAGE_KEY")
    private String messageKey;

    @Column(name = "MESSAGE_VALUE")
    private String messageValue;

    public Locale getLocale() {
        return new Locale(languageAbbreviation);
    }

    public void setLocale(Locale locale) {
        languageAbbreviation = locale.getLanguage();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
