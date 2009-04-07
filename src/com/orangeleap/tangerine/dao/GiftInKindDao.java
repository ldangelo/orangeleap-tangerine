package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface GiftInKindDao {

    public GiftInKind maintainGiftInKind(GiftInKind giftInKind);

    public GiftInKind readGiftInKindById(Long giftInKindId);

    public List<GiftInKind> readGiftsInKindByConstituentId(Long constituentId);

	public PaginatedResult readPaginatedGiftsInKindByConstituentId(Long constituentId, SortInfo sortinfo);

}
