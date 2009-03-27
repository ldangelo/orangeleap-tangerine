package com.orangeleap.tangerine.service;

import java.util.List;

public interface SchemaService {
    
	public List<String> readSchemas();
	public void setSchema(String schema);
    
}
