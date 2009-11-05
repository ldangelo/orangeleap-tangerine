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

package com.orangeleap.tangerine.service;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.communication.MailService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.springframework.validation.BindException;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface ConstituentService {

    public Constituent maintainConstituent(Constituent constituent) throws ConstituentValidationException, BindException;

    public Constituent readConstituentById(Long id);

    public Constituent readConstituentByAccountNumber(String accountNumber);

    public Constituent readConstituentByLoginId(String id);

    List<Constituent> searchConstituents(Map<String, Object> params);

    public List<Constituent> searchConstituents(Map<String, Object> params, SortInfo sort, Locale locale);

    public List<Constituent> findConstituents(Map<String, Object> params, List<Long> ignoreIds);

    public Constituent createDefaultConstituent();

    public List<Constituent> analyzeLapsedDonor(Date beginDate, Date currentDate);

    public void setLapsedDonor(Long constituentId);

    public List<Constituent> readAllConstituentsBySite();

    public List<Constituent> readAllConstituentsBySite(SortInfo sort, Locale locale);

    public int getConstituentCountBySite();

    public List<Constituent> readAllConstituentsByAccountRange(Long fromId, Long toId);

    /*	boolean hasReceivedCommunication(Long constituentId, String commType);

    */
    public boolean hasReceivedCommunication(Constituent c, Gift g, String commType);

    public boolean hasReceivedCommunication(Long constituentId, String commType,
                                            int number, String timeUnits);
    
    public void updateFullTextSearchIndex(Long constituentId);
    
	public void processConstituent(String schedule, Date compareDate,
			ConstituentService ps, GiftService gs, MailService ms,
			SiteService ss, TangerineUserHelper uh,
			Constituent p, PicklistItemService plis);


}
