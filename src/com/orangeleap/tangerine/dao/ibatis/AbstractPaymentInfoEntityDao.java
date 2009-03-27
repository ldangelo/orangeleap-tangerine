package com.orangeleap.tangerine.dao.ibatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.domain.paymentInfo.AbstractPaymentInfoEntity;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;

public abstract class AbstractPaymentInfoEntityDao<T extends AbstractPaymentInfoEntity> extends AbstractIBatisDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected AbstractPaymentInfoEntityDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    /** 
     * Insert distribution lines
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
