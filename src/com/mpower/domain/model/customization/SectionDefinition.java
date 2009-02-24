package com.mpower.domain.model.customization;

import java.io.Serializable;

import com.mpower.domain.GeneratedId;
import com.mpower.domain.model.Site;
import com.mpower.type.LayoutType;
import com.mpower.type.PageType;

public class SectionDefinition implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private PageType pageType;
    private String sectionName;
    private String defaultLabel;
    private LayoutType layoutType;
    private Integer sectionOrder;
    private Site site;
    private String role;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public LayoutType getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(LayoutType layoutType) {
        this.layoutType = layoutType;
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

    public String getSectionHtmlName() {
        return sectionName.replace('.', '_');
    }
}
