package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.BackgroundDao;
import com.orangeleap.tangerine.domain.Background;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the Background table
 */
@Repository("backgroundDAO")
public class IBatisBackgroundDao extends AbstractIBatisDao implements BackgroundDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisBackgroundDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public Background maintainBackground(Background background) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainBackground: backgroundId = " + background.getId());
        }
        return (Background)insertOrUpdate(background, "BACKGROUND");
    }

    @Override
    public Background readBackgroundById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readBackgroundById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (Background)getSqlMapClientTemplate().queryForObject("SELECT_BACKGROUND_BY_ID", params);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Background> readBackgroundByConstituentId(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readBackgroundByConstituentId: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (List<Background>)getSqlMapClientTemplate().queryForList("SELECT_BACKGROUND_BY_CONSTITUENT_ID", params);
    }

	@Override
	public int readCountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentId: id = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("id", constituentId);
        return (Integer)getSqlMapClientTemplate().queryForObject("SELECT_BACKGROUND_COUNT_BY_CONSTITUENT_ID", params);
	}

	

}