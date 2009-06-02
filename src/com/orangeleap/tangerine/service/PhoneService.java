package com.orangeleap.tangerine.service;

import com.orangeleap.tangerine.domain.communication.Phone;

public interface PhoneService extends CommunicationService<Phone> {

	void maintainResetReceiveCorrespondenceText(Long constituentId);

	void resetReceiveCorrespondenceText(Phone entity);

}
