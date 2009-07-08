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
import com.orangeleap.tangerine.dao.JournalDao;
import com.orangeleap.tangerine.domain.Journal;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("journalDAO")
public class IBatisJournalDao extends AbstractIBatisDao implements JournalDao {

    /**
     * Logger for this class and subclasses
     */
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