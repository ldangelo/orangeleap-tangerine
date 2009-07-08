/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.dao.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.MessageDao;
import com.orangeleap.tangerine.domain.customization.MessageResource;
import com.orangeleap.tangerine.type.MessageResourceType;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Corresponds to the MESSAGE_RESOURCE table
 */
@Repository("messageDAO")
public class IBatisMessageDao extends AbstractIBatisDao implements MessageDao {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

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
        List<MessageResource> results = getSqlMapClientTemplate().queryForList("READ_MESSAGE_BY_KEY", params);

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