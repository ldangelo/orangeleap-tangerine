package com.mpower.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mpower.dao.AuditDao;
import com.mpower.dao.SiteDao;
import com.mpower.domain.Audit;
import com.mpower.domain.Auditable;
import com.mpower.domain.Phone;
import com.mpower.domain.Viewable;
import com.mpower.type.AuditType;
import com.mpower.type.EntityType;

@Service("auditService")
public class AuditServiceImpl implements AuditService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditDao")
    private AuditDao auditDao;
    
    @Resource(name = "siteDao")
    private SiteDao siteDao;

    public List<Audit> auditObject(Object object) {
        List<Audit> audits = null;
        if (object instanceof Viewable) {
            if (logger.isDebugEnabled()) {
                logger.debug("audit Viewable");
            }
            audits = auditViewable((Viewable) object);
        } else if (object instanceof Auditable) {
            if (logger.isDebugEnabled()) {
                logger.debug("audit Auditable");
            }
            audits = auditAuditable((Auditable) object);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("don't know how to audit object " + (object == null ? null : object.getClass().getName()));
            }
        }

        if (audits != null && !audits.isEmpty()) {
            for (Audit audit : audits) {
                auditDao.auditObject(audit);
            }
        }
        return audits;
    }

    private List<Audit> auditViewable(Viewable viewable) {
        List<Audit> audits = new ArrayList<Audit>();
        Date date = new Date();
        BeanWrapperImpl bean = new BeanWrapperImpl(viewable);
        if (viewable.getFieldValueMap() == null || viewable.getFieldValueMap().get("id") == null) {
            String name = null;
            if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
                name = SecurityContextHolder.getContext().getAuthentication().getName();
            }
            audits.add(new Audit(AuditType.CREATE, name, date, "Added " + viewable.getClass().getSimpleName() + " " + viewable.getId(), viewable.getSite(), EntityType.valueOf(StringUtils.lowerCase(viewable.getClass().getSimpleName())), viewable.getId()));
            if (logger.isDebugEnabled()) {
                logger.debug("audit Site " + viewable.getSite().getName() + ": added " + viewable.getClass().getSimpleName() + " " + viewable.getId());
            }
        } else {
            if (viewable instanceof Viewable) {
                Map<String, String> fieldLabels = viewable.getFieldLabelMap();
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
                    Object originalBeanProperty = viewable.getFieldValueMap().get(key);
                    if (originalBeanProperty == null && beanProperty == null) {
                        continue;
                    } else if (originalBeanProperty == null && beanProperty != null) {
                        audits.add(new Audit(AuditType.UPDATE, SecurityContextHolder.getContext().getAuthentication().getName(), date, "Id " + viewable.getId() + ": Add " + fieldLabels.get(key) + " " + beanProperty.toString(), viewable.getSite(), EntityType.valueOf(StringUtils.lowerCase(viewable
                                .getClass().getSimpleName())), viewable.getId()));
                        if (logger.isDebugEnabled()) {
                            logger.debug("audit site " + viewable.getSite().getName() + ", id " + viewable.getId() + ": added " + fieldLabels.get(key) + " " + beanProperty.toString());
                        }
                    } else if (originalBeanProperty != null && beanProperty == null) {
                        audits.add(new Audit(AuditType.UPDATE, SecurityContextHolder.getContext().getAuthentication().getName(), date, "Id " + viewable.getId() + ": Delete " + fieldLabels.get(key) + " " + originalBeanProperty.toString(), viewable.getSite(), EntityType.valueOf(StringUtils
                                .lowerCase(viewable.getClass().getSimpleName())), viewable.getId()));
                        if (logger.isDebugEnabled()) {
                            logger.debug("audit site " + viewable.getSite().getName() + ", id " + viewable.getId() + ": delete " + fieldLabels.get(key) + " " + originalBeanProperty.toString());
                        }
                    } else if (!originalBeanProperty.toString().equals(beanProperty.toString())) {
                        audits.add(new Audit(AuditType.UPDATE, SecurityContextHolder.getContext().getAuthentication().getName(), date, "Id " + viewable.getId() + ": Change " + fieldLabels.get(key) + " from " + originalBeanProperty.toString() + " to " + beanProperty.toString(), viewable.getSite(),
                                EntityType.valueOf(StringUtils.lowerCase(viewable.getClass().getSimpleName())), viewable.getId()));
                        if (logger.isDebugEnabled()) {
                            logger.debug("audit site " + viewable.getSite().getName() + ", id " + viewable.getId() + ": change field " + fieldLabels.get(key) + " from " + originalBeanProperty.toString() + " to " + beanProperty.toString());
                        }
                    }
                }
            }
        }
        return audits;
    }

    private List<Audit> auditAuditable(Auditable auditable) {
        List<Audit> audits = new ArrayList<Audit>();
        Date date = new Date();
        Auditable originalObject = auditable.getOriginalObject();
        if (originalObject == null || originalObject.getId() == null) {
            String name = null;
            if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
                name = SecurityContextHolder.getContext().getAuthentication().getName();
            }
            if (logger.isDebugEnabled()) {
                logger.debug("audit Site " + auditable.getSite().getName() + ": added " + auditable.getClass().getSimpleName() + " " + auditable.getId());
            }
            audits.add(new Audit(AuditType.CREATE, name, date, "Added " + auditable.getClass().getSimpleName() + " " + auditable.getId(), auditable.getSite(), EntityType.valueOf(StringUtils.lowerCase(auditable.getClass().getSimpleName())), auditable.getId()));
        } else {
            BeanWrapperImpl bean = new BeanWrapperImpl(auditable);
            Field[] fields = auditable.getClass().getDeclaredFields();
            if (fields != null && fields.length > 0) {
                BeanWrapperImpl oldBean = new BeanWrapperImpl(auditable.getOriginalObject());
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    if (field.isAnnotationPresent(Column.class)) {
                        String fieldName = field.getName();
                        Object newFieldValue = bean.getPropertyValue(fieldName);
                        Object oldFieldValue = oldBean.getPropertyValue(fieldName);
                        if (newFieldValue == null && oldFieldValue == null) {
                            continue;
                        } else if (newFieldValue == null && oldFieldValue != null) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("audit site " + auditable.getSite().getName() + ", id " + auditable.getId() + ": added " + fieldName + " " + newFieldValue);
                            }
                            audits.add(new Audit(AuditType.UPDATE, SecurityContextHolder.getContext().getAuthentication().getName(), date, "Id " + auditable.getId() + ": Add " + fieldName + " " + newFieldValue, auditable.getSite(), EntityType.valueOf(StringUtils.lowerCase(auditable.getClass()
                                    .getSimpleName())), auditable.getId()));
                        } else if (newFieldValue != null && oldFieldValue == null) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("audit site " + auditable.getSite().getName() + ", id " + auditable.getId() + ": delete " + fieldName + " " + oldFieldValue);
                            }
                            audits.add(new Audit(AuditType.UPDATE, SecurityContextHolder.getContext().getAuthentication().getName(), date, "Id " + auditable.getId() + ": Delete " + fieldName + " " + oldFieldValue, auditable.getSite(), EntityType.valueOf(StringUtils.lowerCase(auditable.getClass()
                                    .getSimpleName())), auditable.getId()));
                        } else if (!newFieldValue.toString().equals(oldFieldValue.toString())) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("audit site " + auditable.getSite().getName() + ", id " + auditable.getId() + ": change field " + fieldName + " from " + oldFieldValue.toString() + " to " + newFieldValue.toString());
                            }
                            audits.add(new Audit(AuditType.UPDATE, SecurityContextHolder.getContext().getAuthentication().getName(), date, "Id " + auditable.getId() + ": Change " + fieldName + " from " + oldFieldValue + " to " + newFieldValue, auditable.getSite(), EntityType.valueOf(StringUtils
                                    .lowerCase(auditable.getClass().getSimpleName())), auditable.getId()));
                        }
                    }
                }
            }
        }
        return audits;
    }

    @Override
    public List<Audit> allAuditHistoryForSite(String siteName) {
        return auditDao.allAuditHistoryForSite(siteDao.readSite(siteName));
    }

	@Override
	public List<Audit> AuditHistoryForEntity(String siteName, EntityType entityType, Long objectId) {
        return auditDao.AuditHistoryForEntity(siteDao.readSite(siteName),entityType,objectId);
	}
}
