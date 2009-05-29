package com.orangeleap.tangerine.service;

import java.util.List;

import org.springframework.validation.BindException;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface GiftInKindService {

    public GiftInKind maintainGiftInKind(GiftInKind giftInKind) throws BindException;

    public GiftInKind readGiftInKindById(Long giftInKindId);

    public List<GiftInKind> readGiftsInKindByConstituentId(Long constituentId);

    public GiftInKind readGiftInKindByIdCreateIfNull(String giftInKindId, Person constituent);

	public PaginatedResult readPaginatedGiftsInKindByConstituentId(Long constituentId, SortInfo sortinfo);


}
