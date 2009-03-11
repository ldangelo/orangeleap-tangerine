package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.PageAccess;

public interface PageAccessDao {
    public List<PageAccess> readPageAccess(List<String> roles);
}