package com.mpower.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity
@Table(name = "DISTRO_LINE_CUSTOM_FIELD")
public class DistributionLineCustomField implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "DISTRO_LINE_CUSTOM_FIELD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "DISTRO_LINE_ID")
    private DistributionLine distributionLine;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOM_FIELD_ID")
    private CustomField customField;

    public DistributionLineCustomField() {
    }

    public DistributionLineCustomField(DistributionLine distributionLine, CustomField customField) {
        this.distributionLine = distributionLine;
        this.customField = customField;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DistributionLine getDistributionLine() {
        return distributionLine;
    }

    public void setDistributionLine(DistributionLine distributionLine) {
        this.distributionLine = distributionLine;
    }

    public CustomField getCustomField() {
        return customField;
    }

    public void setCustomField(CustomField customField) {
        this.customField = customField;
    }
}
