package com.orangeleap.tangerine.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.dao.SchemaDao;
import com.orangeleap.tangerine.service.SchemaService;
import com.orangeleap.tangerine.util.TangerineUserHelper;

@Service("schemaService")
public class SchemaServiceImpl extends AbstractTangerineService implements SchemaService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "schemaDAO")
    private SchemaDao schemaDao;
    
    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    
    @Override
	public List<String> readSchemas() {
    	List<String> list = schemaDao.readSchemas();
    	Iterator<String> it = list.iterator();
    	while (it.hasNext()) {
    		String s = (String)it.next();
    		if (EXCLUDE_LIST.contains(s)) it.remove();
    	}
		return list;
	}
    
    // Schemas to exclude
    private static final List<String> EXCLUDE_LIST = new ArrayList<String>();
    static {
    	EXCLUDE_LIST.add("mysql");
    	EXCLUDE_LIST.add("information_schema");
    	EXCLUDE_LIST.add("test");
    	EXCLUDE_LIST.add("jasperserver");
    }

    @Override
	public void setSchema(String schema) {
    	// TODO tangerineUserHelper.setUserAndSiteName(String user, String siteName);
	}

    
}
