package com.orangeleap.tangerine.dao.ibatis;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.VersionDao;
import com.orangeleap.tangerine.domain.Version;

/** 
 * Corresponds to the VERSION table
 */
@Repository("versionDAO")
public class IBatisVersionDao extends AbstractIBatisDao implements VersionDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisVersionDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    

	@Override
	public Version selectVersion(String id) {
        if (logger.isDebugEnabled()) {
            logger.debug("selectVersions:");
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (Version)getSqlMapClientTemplate().queryForObject("SELECT_VERSION_BY_ID", params);
	}

}