package com.mpower.dao.customization;

import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mpower.domain.entity.customization.MessageResource;
import com.mpower.domain.type.MessageResourceType;

@Repository("messageDao")
public class JPAMessageDao implements MessageDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public String readMessage(Long siteId, MessageResourceType messageResourceType, String messageKey, Locale language) {
		String result = "";
		Query query = em.createNamedQuery("READ_MESSAGE");
		query.setParameter("siteId", siteId);
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
