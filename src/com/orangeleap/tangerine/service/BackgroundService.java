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

import java.util.List;
import java.util.Locale;

import org.springframework.validation.BindException;

import com.orangeleap.tangerine.domain.Background;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface BackgroundService {

    public Background maintainBackground(Background background) throws BindException;

    public Background readBackgroundByIdCreateIfNull(String communicationHistoryId, Constituent constituent);

    public Background readBackgroundById(Long backgroundId);

    public List<Background> readAllBackgroundByConstituentId(Long constituentId, SortInfo sort, Locale locale);

    public int readCountByConstituentId(Long constituentId);

}
