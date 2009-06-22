package com.orangeleap.tangerine.dao.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.PostBatchDao;
import com.orangeleap.tangerine.dao.JournalDao;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.Journal;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("journalDAO")
public class IBatisJournalDao extends AbstractIBatisDao implements JournalDao {

	/** Logger for this class and subclasses */
	protected final Log logger = OLLogger.getLog(getClass());

	@Autowired
	public IBatisJournalDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
	}

	@Override
	public Journal maintainJournal(Journal journal) {
		if (logger.isTraceEnabled()) {
			logger.trace("maintainJournal: journal = " + journal.getId());
		}
		Journal aJournal = (Journal) insertOrUpdate(journal, "JOURNAL");
        return aJournal;
	}

}