package com.mpower.domain.entity.customization;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;

import com.mpower.domain.entity.Site;
import com.mpower.domain.type.MessageResourceType;

@Entity
@Table(name="MESSAGE_RESOURCE",
		uniqueConstraints={@UniqueConstraint(
				columnNames={"MESSAGE_KEY","LANGUAGE_ABBREVIATION","MESSAGE_RESOURCE_TYPE","SITE_ID"})})
public class MessageResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="MESSAGE_RESOURCE_ID")
	private Long id;

	@Column(name="LANGUAGE_ABBREVIATION", nullable=false)
    private String languageAbbreviation;

    @Column(name="MESSAGE_RESOURCE_TYPE")
    @Enumerated(EnumType.STRING)
    private MessageResourceType messageResourceType;

    @ManyToOne
    @JoinColumn(name="SITE_ID")
    private Site site;

    @Column(name="MESSAGE_KEY")
    private String messageKey;

    @Column(name="MESSAGE_VALUE")
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
