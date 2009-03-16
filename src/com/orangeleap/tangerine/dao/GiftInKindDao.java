package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;

public interface GiftInKindDao {

    public GiftInKind maintainGiftInKind(GiftInKind giftInKind);

    public GiftInKind readGiftInKindById(Long giftInKindId);

    public List<GiftInKind> readGiftsInKindByConstituentId(Long constituentId);

}
