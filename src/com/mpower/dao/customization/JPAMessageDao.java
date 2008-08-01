package com.mpower.dao.customization;

import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.mpower.domain.customization.MessageResource;
import com.mpower.type.MessageResourceType;

@Repository("messageDao")
public class JPAMessageDao implements MessageDao {
	
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());


	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public String readMessage(String siteName, MessageResourceType messageResourceType, String messageKey, Locale language) {
		String result = "";
		Query query = em.createNamedQuery("READ_MESSAGE");
		query.setParameter("siteName", siteName);
		query.setParameter("messageResourceType", messageResourceType);
		query.setParameter("messageKey", messageKey);
		query.setParameter("language", language.getLanguage());

		List<MessageResource> results = query.getResultList();
		if (! results.isEmpty()) {
			if (results.size() == 1) {
				result = results.get(0).getMessageValue();
			} else {
                for (MessageResource messageResource : results) {
                    if (messageResource.getSite() != null) {
                        result = messageResource.getMessageValue();
                    }
                }
			}
		}
		return result;
	}
}
