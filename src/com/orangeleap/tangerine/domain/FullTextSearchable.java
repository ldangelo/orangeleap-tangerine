package com.orangeleap.tangerine.domain;

import java.util.Set;

// Index by Full Text for search
public interface FullTextSearchable {
	public Set<String> getFullTextSearchKeywords();
}
