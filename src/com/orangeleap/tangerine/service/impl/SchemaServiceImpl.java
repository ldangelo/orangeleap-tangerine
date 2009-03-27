package com.orangeleap.tangerine.service.impl;

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
    	// TODO filter out mysql, test, jasperserver, etc (would error out and skip anyway)
		return schemaDao.readSchemas();
	}

    @Override
	public void setSchema(String schema) {
    	// TODO tangerineUserHelper.setUserAndSiteName(String user, String siteName);
	}

    
}
