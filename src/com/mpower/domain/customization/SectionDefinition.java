package com.mpower.domain.customization;

import java.io.Serializable;

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
import com.mpower.type.LayoutType;
import com.mpower.type.PageType;

@Entity
@EntityListeners(value = { EmptyStringNullifyerListener.class })
@Table(name = "SECTION_DEFINITION", uniqueConstraints = @UniqueConstraint(columnNames = { "SITE_NAME", "SECTION_NAME", "ROLE" }))
public class SectionDefinition implements Serializable {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "SECTION_DEFINITION_ID")
    private Long id;

    @Column(name = "PAGE_TYPE")
    @Enumerated(EnumType.STRING)
    private PageType pageType;

    @Column(name = "SECTION_NAME")
    private String sectionName;

    @Column(name = "DEFAULT_LABEL", nullable = false)
    private String defaultLabel;

    @Column(name = "LAYOUT_TYPE")
    @Enumerated(EnumType.STRING)
    private LayoutType layoutType;

    @Column(name = "SECTION_ORDER")
    private Integer sectionOrder;

    @ManyToOne
    @JoinColumn(name = "SITE_NAME")
    private Site site;

    @Column(name = "ROLE")
    private String role;

    public String getSectionHtmlName() {
        return sectionName.replace('.', '_');
    }

    public LayoutType getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(LayoutType layoutType) {
        this.layoutType = layoutType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public PageType getPageType() {
        return pageType;
    }

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Integer getSectionOrder() {
        return sectionOrder;
    }

    public void setSectionOrder(Integer sectionOrder) {
        this.sectionOrder = sectionOrder;
    }

    public String getDefaultLabel() {
        return defaultLabel;
    }

    public void setDefaultLabel(String defaultLabel) {
        this.defaultLabel = defaultLabel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
