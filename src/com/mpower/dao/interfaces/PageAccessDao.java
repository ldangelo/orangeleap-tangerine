package com.mpower.dao.interfaces;

import java.util.List;

import com.mpower.domain.model.customization.PageAccess;

public interface PageAccessDao {
    public List<PageAccess> readPageAccess(List<String> roles);
}