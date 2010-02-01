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

package com.orangeleap.tangerine.dao;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.orangeleap.tangerine.domain.Constituent;

public interface ConstituentDao {

    public Constituent readConstituentById(Long id);

    public Constituent readConstituentByAccountNumber(String accountNumber);

    public List<Constituent> readAllConstituentsBySite();

    public List<Constituent> readAllConstituentsBySite(String sortColumn, String dir, int start, int limit, Locale locale);

    public List<Constituent> readAllUpdatedConstituentsBySite(String sortPropertyName, String direction, int start, int limit, Locale locale, int recentDays);

    public int getConstituentCountBySite();

    public Constituent readConstituentByLoginId(String loginId);

    public List<Constituent> readConstituentsByIds(List<Long> ids);

    public Constituent maintainConstituent(Constituent constituent);

    List<Constituent> searchConstituents(Map<String, Object> parameters);
    
    List<Constituent> searchConstituents(Map<String, Object> params, String sortPropertyName, String direction, int start, int limit, Locale locale);
    
    List<Constituent> searchConstituents(Map<String, Object> params, boolean parametersStartWith, String sortPropertyName, String direction, int start, int limit, Locale locale);

	public List<Constituent> readAllConstituentsByAccountRange(Long fromId, Long toId);

	public List<Constituent> findConstituents(Map<String, Object> params,
			List<Long> ignoreIds);
	
    public List<Constituent> fullTextSearchConstituents(String searchText);

    public Long[] getConstituentIdRange();
    
	public void setConstituentFlags();
}
