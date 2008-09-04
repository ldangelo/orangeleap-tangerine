package com.mpower.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mpower.dao.AuditDao;
import com.mpower.domain.Audit;
import com.mpower.domain.Phone;
import com.mpower.domain.Viewable;
import com.mpower.type.AuditType;

@Service("auditService")
public class AuditServiceImpl implements AuditService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditDao")
    private AuditDao auditDao;

    public List<Audit> auditObject(Viewable entity) {
        List<Audit> audits = new ArrayList<Audit>();
        Date date = new Date();
        BeanWrapperImpl bean = new BeanWrapperImpl(entity);
        if (entity.getFieldValueMap() == null || entity.getFieldValueMap().get("id") == null) {
            String name = null;
            if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
                name = SecurityContextHolder.getContext().getAuthentication().getName();
            }
            audits.add(new Audit(AuditType.CREATE, name, date, "Added " + entity.getClass().getSimpleName() + " " + entity.getId()));
            logger.info("audit: Added " + entity.getClass().getSimpleName() + " " + entity.getId());
        } else {
            if (entity instanceof Viewable) {
                Map<String, String> fieldLabels = entity.getFieldLabelMap();
                for (String key : fieldLabels.keySet()) {
                    String fieldName = key;
                    Object beanProperty = bean.getPropertyValue(fieldName);
                    if (beanProperty instanceof Phone) {
                        fieldName = key + ".number";
                        beanProperty = bean.getPropertyValue(fieldName);
                    }
                    if (beanProperty instanceof String) {
                        beanProperty = StringUtils.trimToNull((String) beanProperty);
                    }
                    Object originalBeanProperty = entity.getFieldValueMap().get(key);
                    if (originalBeanProperty == null && beanProperty == null) {
                        continue;
                    } else if (originalBeanProperty == null && beanProperty != null) {
                        audits.add(new Audit(AuditType.UPDATE, SecurityContextHolder.getContext().getAuthentication().getName(), date, "Add " + fieldLabels.get(key) + " " + beanProperty.toString()));
                        logger.info("audit: Added " + fieldLabels.get(key) + " " + beanProperty.toString());
                    } else if (originalBeanProperty != null && beanProperty == null) {
                        audits.add(new Audit(AuditType.UPDATE, SecurityContextHolder.getContext().getAuthentication().getName(), date, "Delete " + fieldLabels.get(key) + " " + originalBeanProperty.toString()));
                        logger.info("audit: Delete " + fieldLabels.get(key) + " " + originalBeanProperty.toString());
                    } else if (!originalBeanProperty.toString().equals(beanProperty.toString())) {
                        audits.add(new Audit(AuditType.UPDATE, SecurityContextHolder.getContext().getAuthentication().getName(), date, "Change " + fieldLabels.get(key) + " from " + originalBeanProperty.toString() + " to " + beanProperty.toString()));
                        logger.info("audit: Change " + fieldLabels.get(key) + " from " + originalBeanProperty.toString() + " to " + beanProperty.toString());
                    }
                }
            }
        }

        if (!audits.isEmpty()) {
            for (Audit audit : audits) {
                auditDao.auditObject(audit);
            }
        }
        return audits;
    }

	@Override
	public List<Audit> allAuditHistoryForSite(String siteName) {
		return auditDao.allAuditHistoryForSite(siteName);
	}
}
