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
import com.orangeleap.tangerine.domain.paymentInfo.AbstractPaymentInfoEntity;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public abstract class AbstractPaymentInfoEntityDao<T extends AbstractPaymentInfoEntity> extends AbstractIBatisDao {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    protected AbstractPaymentInfoEntityDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    /**
     * Insert distribution lines
     *
     * @param entity
     * @param idProperty
     */
    protected void insertDistributionLines(T entity, String idProperty) {
        if (entity.getDistributionLines() != null) {
            for (DistributionLine line : entity.getDistributionLines()) {
                line.resetIdToNull();
                BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(line);
                bw.setPropertyValue(idProperty, entity.getId());
                insertOrUpdate(line, "DISTRO_LINE");
            }
        }
    }

    /**
     * Load custom fields for Distribution Lines
     *
     * @param entity
     */
    protected void loadDistributionLinesCustomFields(T entity) {
        if (entity != null && entity.getDistributionLines() != null) {
            for (DistributionLine line : entity.getDistributionLines()) {
                loadCustomFields(line);
            }
        }
    }
}
