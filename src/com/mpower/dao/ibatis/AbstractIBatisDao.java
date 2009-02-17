package com.mpower.dao.ibatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public abstract class AbstractIBatisDao extends SqlMapClientDaoSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

}
