package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.MessageDao;
import com.orangeleap.tangerine.domain.customization.MessageResource;
import com.orangeleap.tangerine.type.MessageResourceType;

/** 
 * Corresponds to the MESSAGE_RESOURCE table
 */
@Repository("messageDAO")
public class IBatisMessageDao extends AbstractIBatisDao implements MessageDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisMessageDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
  
    @SuppressWarnings("unchecked")
	@Override
	public String readMessage(MessageResourceType messageResourceType, String messageKey, Locale locale) {
    	if (logger.isTraceEnabled()) {
    	    logger.trace("readMessage: messageResourceType = " + messageResourceType + " messageKey = " + messageKey + " locale = " + locale);
    	}
        Map<String, Object> params = setupParams();
        params.put("messageResourceType", messageResourceType);
        params.put("messageKey", messageKey);
        params.put("language", locale.toString());
        List<MessageResource> results =  getSqlMapClientTemplate().queryForList("READ_MESSAGE_BY_KEY", params);
        
        String message = null;
        if (!results.isEmpty()) {
            if (results.size() == 1) {
            	message = results.get(0).getMessageValue();
            } else {
                for (MessageResource messageResource : results) {
                    if (messageResource.getSite() != null) {
                    	message = messageResource.getMessageValue();
                    }
                }
            }
        }
        return message;
	}
}