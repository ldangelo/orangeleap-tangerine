package com.orangeleap.tangerine.service;

import java.util.List;

import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;

public interface GiftInKindService {

    public GiftInKind maintainGiftInKind(GiftInKind giftInKind);

    public GiftInKind readGiftInKindById(Long giftInKindId);

    public List<GiftInKind> readGiftsInKindByConstituentId(Long constituentId);

}
