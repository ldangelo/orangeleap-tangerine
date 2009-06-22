package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.CacheGroup;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.Journal;
import com.orangeleap.tangerine.type.CacheGroupType;


public interface JournalDao {

    public Journal maintainJournal(Journal journal);

}